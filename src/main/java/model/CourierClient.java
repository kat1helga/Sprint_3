package model;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class CourierClient {

public final static String baseURL = "http://qa-scooter.praktikum-services.ru";

Courier courier = Courier.getRandomCourier();

    public static Boolean createCourier(Courier courier){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(baseURL + "/api/v1/courier")
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok");
}

    public static RequestSpecification getRecSpec(){
       return new RequestSpecBuilder().log(LogDetail.ALL)
               .setContentType(ContentType.JSON).build();
    }

}
