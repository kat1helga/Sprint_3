import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierCredentials;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static model.CourierClient.*;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;


public class TestCreateCourier {

    private ArrayList<Courier> couriers = new ArrayList<Courier>();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @After
    public void Clear()
    {
        System.out.println("Clear started");
        for(int i = 0; i < couriers.size(); i++) {

            System.out.println("Clear courier " + i + " started");

            Courier courier = couriers.get(i);

            CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
            ExtractableResponse<Response> response = given()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(courierCredentials)
                    .when()
                    .post("/api/v1/courier/login")
                    .then()
                    .assertThat()
                    .log().all()
                    .extract();

            int statusCode = response.statusCode();
            boolean isCourierCreated = statusCode == SC_OK;
            if(isCourierCreated)
            {
                int courierId = response.path("id");
                given()
                        .spec(getRecSpec())
                        .when()
                        .delete(baseURL + "/api/v1/courier/"+courierId);
            }
        }

        couriers.clear();
    }

    @Test
    @DisplayName("CheckCreateCourierPositive")
    public void CheckCreateCourierPositive() {
        Courier courier = Courier.getRandomCourier();
        couriers.add(courier);

        boolean ok = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok");

        assertTrue(ok);
    }

    @Test
    @DisplayName("CheckCreateCourierWithSameCredentials")
    public void CheckCreateCourierWithSameCredentials() {
            Courier courier = Courier.getRandomCourier();
            couriers.add(courier);

            given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(courier)
            .when()
            .post("/api/v1/courier")
            .then()
            .assertThat()
            .log().all()
            .statusCode(SC_CREATED)
            .extract()
            .path("ok");

    String messageResult =  given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(courier)
            .when()
            .post("/api/v1/courier")
            .then()
            .assertThat()
            .log().all()
            .statusCode(SC_CONFLICT)
            .extract()
            .path("message");
    //Здесь баг, ответ , который приходит ""Этот логин уже используется. Используйте другой""
        assertEquals("Этот логин уже используется", messageResult);
    }

    @Test
    @DisplayName("CheckCreateCourierWrightStatusCode")
    public void CheckCreateCourierWrightStatusCode() {
        Courier courier = Courier.getRandomCourier();
        couriers.add(courier);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_CREATED);

    }

    @Test
    @DisplayName("CheckCreateCourierWrightResponse")
    public void CheckCreateCourierWrightResponse() {
        Courier courier = Courier.getRandomCourier();
        couriers.add(courier);

        boolean ok = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .extract()
                .path("ok");

        assertTrue(ok);
    }

    @Test
    @DisplayName("CheckCreateCourierOnlyRequiredField")
    public void CheckCreateCourierOnlyRequiredField() {
        Courier courier = Courier.getRandomCourierLoginAndPassword();
        couriers.add(courier);

        boolean ok = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok");

        assertTrue(ok);
    }

    @Test
    @DisplayName("CheckCreateCourierWithoutRequiredFields")
    public void CheckCreateCourierWithoutRequiredFields() {
        Courier courierFirst = Courier.getRandomCourierLogin();
        couriers.add(courierFirst);

        String messageResultOnlyLogin = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierFirst)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");

        System.out.println("11111");
        assertEquals("Недостаточно данных для создания учетной записи", messageResultOnlyLogin);

        Courier courierSecond = Courier.getRandomCourierPassword();
        couriers.add(courierSecond);
        String messageResultOnlyPassword = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierSecond)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");

        System.out.println("222222");
        assertEquals("Недостаточно данных для создания учетной записи", messageResultOnlyPassword);
        System.out.println("33333");
    }

  /*  @After
    public Boolean deleteCouriersData(int id) {
        given()
                .log().all()
                .delete("/api/v1/courier/"+id)
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_OK)
                .extract()
                .path("ok");
    }*/
}
