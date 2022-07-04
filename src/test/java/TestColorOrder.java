import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.BaseAPI;
import model.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;

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
                RestAssured.baseURI = BaseAPI.baseUrl;
        }

        @Test
        public void checkCreateOrderColors() {
                Order order = Order.createOrderWithColors(colors);
                Response response = BaseAPI.postOrders(order);
                BaseAPI.assertResponseStatusCode(response, SC_CREATED);
        }
}








