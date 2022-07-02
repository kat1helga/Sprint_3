import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.BaseAPI;
import model.Order;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;

public class TestCreateOrder {
    @Before
    public void setUp() {
        RestAssured.baseURI = BaseAPI.baseUrl;
    }

    @Test
    public void checkCreateOrderResponseHaveTrack()
    {
        Order order = Order.createOrderWithoutColors();
        Response response = BaseAPI.postOrders(order);

        BaseAPI.assertResponseStatusCode(response, SC_CREATED);

        Integer track = response.path("track");
        System.out.println(track);
    }

    // Для заказа нет метода очистки данных.
    // Только отмена и ручка отмены не работает кстати.
    // Наставник ответил, что "чистим, что можем), поэтому after в тестах к заказу - нет
    /*
    @After{
    }
    */
}
