import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;
import model.Order;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestListOfOrders {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }


    @Test
    @DisplayName("CheckReturnBasicListOfOrder")
    public void CheckReturnBasicListOfOrder()
    {
        Response response =given()
                .log().all()
                .get("/api/v1/orders");
                response.then()
                        .log().all()
                        .statusCode(SC_OK)
                        .assertThat()
                        .body("orders" ,notNullValue());//Наставник, сказал, что так можно

        System.out.println(response.body().asString());
    }


}
