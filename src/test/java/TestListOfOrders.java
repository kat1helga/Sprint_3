import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.BaseAPI;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestListOfOrders {
    @Before
    public void setUp() {
        RestAssured.baseURI = BaseAPI.baseUrl;
    }

    @Test
    @DisplayName("CheckReturnBasicListOfOrder")
    public void CheckReturnBasicListOfOrder() {
        Response response = BaseAPI.getOrders();
        BaseAPI.assertResponseStatusCode(response, SC_OK);

        response.then()
                .assertThat()
                .body("orders" ,notNullValue());//Наставник, сказал, что так можно

        System.out.println(response.body().asString());
    }
}
