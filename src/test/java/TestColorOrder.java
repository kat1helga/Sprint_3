import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import model.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.path.json.JsonPath.given;
import static org.apache.http.HttpStatus.SC_CREATED;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
public class TestColorOrder {
        private final String[] colors;

        public TestColorOrder(String[] colors) {
                this.colors = colors;
        }

        @Parameterized.Parameters
        public static Object[][] getColorsData() {
                return new Object[][] {
                        { new String[] { "BLACK" } },
                        { new String[] { "GREY" } },
                        { new String[] { "BLACK", "GREY" } },
                        { new String[] { } }
                };
        }

        @Before
        public void setUp() {
                RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        }

        @Test
        public void CheckCreateOrderColors()
        {
                Order order = Order.CreateOrderWithColors(colors);
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .body(order)
                        .when()
                        .post("/api/v1/orders")
                        .then()
                        .assertThat()
                        .log().all()
                        .statusCode(SC_CREATED);
        }

        //Для заказа нет метода очистки данных. Только отмена и ручка отмены не работает кстати. Наставник ответил, что "чистим, что можем), поэтому after в тестах к заказу - нет
   /* @After{

    }*/
}








/*import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.path.json.JsonPath.given;
import static org.apache.http.HttpStatus.SC_CREATED;

    import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

public class TestColorOrder {

@RunWith(Parameterized.class)
public class TestOrderColor()
{
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String color;

    public TestOrderColor(String firstName, String lastName, String address, String metroStation, String phone, Integer rentTime, String deliveryDate, String comment, String color)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData()
    {
        return new Object[][]
        {
        {"Ivan", "Ivanov", "street Lenina, 9", "5", "+7 800 355 35 55", 2, "2022-09-06", "test comment", new String[] {"BLACK"}},
        {"Petr", "Petrov", "street Mira, 9", "9", "+7 800 666 35 55", 3, "2022-07-06", "test comment", "GREY"}
        };
    };
}
    @Before
        public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
        @Test
        public void CheckCreateOrderPositive()
        {
        model.Order order = new model.Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

        //Integer track =
        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(order)
            .when()
            .post("/api/v1/orders")
            .then()
            .assertThat()
            .log().all()
            .statusCode(SC_CREATED);
            /*.extract()
            .path("track");*/
        //System.out.println(track);
     /*   }

}
*/