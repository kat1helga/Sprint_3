package model;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class BaseAPI {

    public static final String baseUrl = "http://qa-scooter.praktikum-services.ru";

    public static Response postOrders(Object body) {
        return postRequest("/api/v1/orders", body);
    }

    public static Response getOrders() {
        return getRequest("/api/v1/orders");
    }

    public static Response postLogin(Object body) {
        return postRequest("/api/v1/courier/login", body);
    }

    public static Response postCourier(Object body) {
        return postRequest("/api/v1/courier", body);
    }

    public static Response deleteCourier(int id) {
        return deleteRequest("/api/v1/courier/" + id);
    }

    private static Response postRequest(String request, Object body) {
        return given()
                .spec(getRecSpec())
                .body(body)
                .when()
                .post(request);
    }

    private static Response deleteRequest(String request) {
        return given()
                .spec(getRecSpec())
                .when()
                .delete(request);
    }

    private static Response getRequest(String request) {
        return given()
                .get(request);
    }

    private static RequestSpecification getRecSpec(){
        return new RequestSpecBuilder().log(LogDetail.ALL)
                .setContentType(ContentType.JSON).build();
    }

    public static void assertResponseStatusCode(Response response, int statusCode) {
        response.then()
                .assertThat()
                .log().all()
                .statusCode(statusCode);
    }

    public static Response postLoginByCourier(Courier courier) {
        return postLoginByLoginAndPassword(courier.getLogin(), courier.getPassword());
    }

    public static Response postLoginByLoginAndPassword(String login, String password) {
        CourierCredentials courierCredentials = new CourierCredentials(login, password);
        return postLogin(courierCredentials);
    }

    public static void deleteCouriers(ArrayList<Courier> couriers) {
        System.out.println("Clear started");
        for(int i = 0; i < couriers.size(); i++) {
            System.out.println("Clear courier " + i + " started");

            Courier courier = couriers.get(i);

            Response response = postLoginByCourier(courier);

            int statusCode = response.statusCode();
            boolean isCourierCreated = statusCode == SC_OK;
            if(isCourierCreated) {
                int courierId = response.path("id");
                deleteCourier(courierId);
            }
        }
        couriers.clear();
    }
}
