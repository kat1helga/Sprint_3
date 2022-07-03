import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.BaseAPI;
import model.Courier;
import org.junit.*;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;


public class TestCreateCourier {

    private ArrayList<Courier> couriers = new ArrayList<Courier>();

    @Before
    public void setUp() {
        RestAssured.baseURI = BaseAPI.baseUrl;
    }

    @After
    public void clear() {
        BaseAPI.deleteCouriers(couriers);
    }

    @Test
    @DisplayName("CheckCreateCourierWithSameCredentials")
    public void checkCreateCourierWithSameCredentials() {
        Courier courier = Courier.getRandomCourier();

        createCourierRequest(courier);
        Response response = createCourierRequest(courier);

        BaseAPI.assertResponseStatusCode(response, SC_CONFLICT);

        // Здесь баг, ответ , который приходит ""Этот логин уже используется. Используйте другой""
        // Я не могу поменять тест, это неправильно с точки зрения тестирования.
        // Это будет подстраивание под результат теста, а не тест по требованиям.
        // Наставник отвечал уже нам, что этом тесте падает ошибка.
        String messageResult = response.path("message");
        assertEquals("Этот логин уже используется", messageResult);
    }

    @Test
    @DisplayName("CheckCreateCourierWrightStatusCode")
    public void checkCreateCourierWrightStatusCode() {
        Courier courier = Courier.getRandomCourier();

        Response response = createCourierRequest(courier);

        BaseAPI.assertResponseStatusCode(response, SC_CREATED);
    }

    @Test
    @DisplayName("CheckCreateCourierWrightResponse")
    public void checkCreateCourierWrightResponse() {
        Courier courier = Courier.getRandomCourier();

        Response response = createCourierRequest(courier);

        boolean ok = response.path("ok");
        assertTrue(ok);
    }

    @Test
    @DisplayName("CheckCreateCourierOnlyRequiredField")
    public void checkCreateCourierOnlyRequiredField() {
        Courier courier = Courier.getRandomCourierLoginAndPassword();

        Response response = createCourierRequest(courier);
        BaseAPI.assertResponseStatusCode(response, SC_CREATED);

        boolean ok = response.path("ok");
        assertTrue(ok);
    }

    @Test
    @DisplayName("CheckCreateCourierWithoutRequiredFields")
    public void checkCreateCourierWithoutRequiredFields() {
        Courier courierFirst = Courier.getRandomCourierLogin();

        Response response = createCourierRequest(courierFirst);
        BaseAPI.assertResponseStatusCode(response, SC_BAD_REQUEST);

        String messageResultOnlyLogin = response.path("message");
        assertEquals("Недостаточно данных для создания учетной записи", messageResultOnlyLogin);

        Courier courierSecond = Courier.getRandomCourierPassword();

        response = createCourierRequest(courierSecond);
        BaseAPI.assertResponseStatusCode(response, SC_BAD_REQUEST);

        String messageResultOnlyPassword = response.path("message");
        assertEquals("Недостаточно данных для создания учетной записи", messageResultOnlyPassword);
    }

    private Response createCourierRequest(Courier courier) {
        couriers.add(courier);
        return BaseAPI.postCourier(courier);
    }
}
