import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static model.CourierClient.*;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class TestCourierLogin {

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
    @DisplayName("CheckLoginCourierPositive")
    public void CheckLoginCourierPositive() {
        Courier courier = Courier.getRandomCourier();
        couriers.add(courier);
        createCourier(courier);
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());

        Integer id = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierCredentials)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_OK)
                .extract()
                .path("id");
             System.out.println(id);
    }

    @Test
    @DisplayName("CheckLoginCourierWithoutRequiredFieldPassword")
    public void CheckLoginCourierWithoutRequiredFieldPassword() {
        Courier courier = Courier.getRandomCourier();
        couriers.add(courier);
        createCourier(courier);
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), "");

        String messageResult = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierCredentials)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");

        assertEquals("Недостаточно данных для входа", messageResult);
    }

    @Test
    @DisplayName("CheckLoginCourierWithoutRequiredFieldLogin")
    public void CheckLoginCourierWithoutRequiredFieldLogin() {
        Courier courier = Courier.getRandomCourier();
        couriers.add(courier);
        createCourier(courier);
        CourierCredentials courierCredentials = new CourierCredentials("", courier.getPassword());

        String messageResult = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierCredentials)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");

        assertEquals("Недостаточно данных для входа", messageResult);
    }

    @Test
    @DisplayName("CheckLoginCourierWithNonexistentCredentials")
    public void CheckLoginCourierWithNonexistentCredentials() {
        CourierCredentials courierCredentials = new CourierCredentials("NonexistentLogin","NonexistentPassword");
        String messageResult = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierCredentials )
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .path("message");

        assertEquals("Учетная запись не найдена", messageResult);
    }

    @Test
    @DisplayName("CheckLoginCourierWithErrorInPassword")
    public void CheckLoginCourierWithErrorInPassword() {
        Courier courier = Courier.getRandomCourier();
        couriers.add(courier);
        createCourier(courier);
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword() + "x");
        String messageResult = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierCredentials )
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .path("message");

        assertEquals("Учетная запись не найдена", messageResult);
    }

    @Test
    @DisplayName("CheckLoginCourierWithErrorInLogin")
    public void CheckLoginCourierWithErrorInLogin() {
        Courier courier = Courier.getRandomCourier();
        couriers.add(courier);
        createCourier(courier);
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin() + "x", courier.getPassword());
        String messageResult = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierCredentials )
                .when()
                .post("/api/v1/courier/login")
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .path("message");
        String message = "Учетная запись не найдена";
        assertEquals(message, messageResult);
    }


}
