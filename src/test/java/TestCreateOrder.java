import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import model.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class TestCreateOrder {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void CheckCreateOrderResponseHaveTrack()
    {
        Order order = Order.CreateOrderWithoutColors();
        Integer track = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_CREATED)
                .extract()
                .path("track");
        System.out.println(track);
    }
    //Для заказа нет метода очистки данных. Только отмена и ручка отмены не работает кстати. Наставник ответил, что "чистим, что можем), поэтому after в тестах к заказу - нет
   /* @After{

    }*/
}
