import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.BaseAPI;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class TestCourierLogin {

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
    @DisplayName("CheckLoginCourierPositive")
    public void checkLoginCourierPositive() {
        Courier courier = createRandomCourier();

        Response response = BaseAPI.postLoginByCourier(courier);
        BaseAPI.assertResponseStatusCode(response, SC_OK);

        Integer id = response.path("id");
        System.out.println(id);
    }

    @Test
    @DisplayName("CheckLoginCourierWithoutRequiredFieldPassword")
    public void checkLoginCourierWithoutRequiredFieldPassword() {
        Courier courier = createRandomCourier();

        Response response = BaseAPI.postLoginByLoginAndPassword(courier.getLogin(), "");
        BaseAPI.assertResponseStatusCode(response, SC_BAD_REQUEST);

        String messageResult = response.path("message");
        assertEquals("Недостаточно данных для входа", messageResult);
    }

    @Test
    @DisplayName("CheckLoginCourierWithoutRequiredFieldLogin")
    public void checkLoginCourierWithoutRequiredFieldLogin() {
        Courier courier = createRandomCourier();

        Response response = BaseAPI.postLoginByLoginAndPassword("", courier.getPassword());
        BaseAPI.assertResponseStatusCode(response, SC_BAD_REQUEST);

        String messageResult = response.path("message");
        assertEquals("Недостаточно данных для входа", messageResult);
    }

    @Test
    @DisplayName("CheckLoginCourierWithNonexistentCredentials")
    public void checkLoginCourierWithNonexistentCredentials() {
        Response response = BaseAPI.postLoginByLoginAndPassword("NonexistentLogin","NonexistentPassword");
        BaseAPI.assertResponseStatusCode(response, SC_NOT_FOUND);

        String messageResult = response.path("message");
        assertEquals("Учетная запись не найдена", messageResult);
    }

    @Test
    @DisplayName("CheckLoginCourierWithErrorInPassword")
    public void checkLoginCourierWithErrorInPassword() {
        Courier courier = createRandomCourier();

        Response response = BaseAPI.postLoginByLoginAndPassword(courier.getLogin(), courier.getPassword() + "x");
        BaseAPI.assertResponseStatusCode(response, SC_NOT_FOUND);

        String messageResult = response.path("message");
        assertEquals("Учетная запись не найдена", messageResult);
    }

    @Test
    @DisplayName("CheckLoginCourierWithErrorInLogin")
    public void checkLoginCourierWithErrorInLogin() {
        Courier courier = createRandomCourier();

        Response response = BaseAPI.postLoginByLoginAndPassword(courier.getLogin() + "x", courier.getPassword());
        BaseAPI.assertResponseStatusCode(response, SC_NOT_FOUND);

        String messageResult = response.path("message");
        assertEquals("Учетная запись не найдена", messageResult);
    }

    private Courier createRandomCourier() {
        Courier courier = Courier.getRandomCourier();
        couriers.add(courier);
        BaseAPI.postCourier(courier);
        return courier;
    }
}
