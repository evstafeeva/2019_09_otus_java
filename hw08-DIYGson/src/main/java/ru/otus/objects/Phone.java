package ru.otus.objects;

import java.util.Objects;

public class Phone {
    private long phone;
    private String operator;

    public Phone(Integer phone, String operator) {
        this.phone = phone;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "phone=" + phone +
                ", operator='" + operator + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone1 = (Phone) o;
        return phone == phone1.phone &&
                Objects.equals(operator, phone1.operator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, operator);
    }
}
