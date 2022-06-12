package model;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }


    public Courier() {
    }

    public static Courier getRandomCourier(){
    String login = RandomStringUtils.randomAlphabetic(10);
    String password = RandomStringUtils.randomAlphabetic(12);
    String firstName = RandomStringUtils.randomAlphabetic(8);
    return new Courier(login, password, firstName);
    }

    public static Courier getRandomCourierLogin(){
        String login = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, null, null);
    }

    public static Courier getRandomCourierPassword(){
        String password = RandomStringUtils.randomAlphabetic(10);
        return new Courier(null, password, null);
    }

    public static Courier getRandomCourierLoginAndPassword(){
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, null);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
