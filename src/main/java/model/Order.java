package model;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] colors;

    public Order(String firstName, String lastName, String address, String metroStation, String phone, Integer rentTime, String deliveryDate, String comment, String[] colors) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.colors = colors;
    }

    public static Order createOrderWithColors(String[] colors) {
        String firstName = RandomStringUtils.randomAlphabetic(7);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphabetic(6);
        String metroStation = Integer.toString(RandomUtils.nextInt(1, 20));
        String phone = RandomStringUtils.randomNumeric(11);
        Integer rentTime = RandomUtils.nextInt(1, 8);
        String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String comment = RandomStringUtils.randomAlphabetic(20);
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colors);
    }

    public static Order createOrderWithoutColors() {
        return createOrderWithColors(null);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public String[] getColors() {
        return colors;
    }
}

