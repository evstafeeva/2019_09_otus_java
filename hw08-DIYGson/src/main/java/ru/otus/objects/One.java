package ru.otus.objects;

import java.util.Objects;

public class One {
    public Integer a;

    public One(Integer a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "One{" +
                "a=" + a +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        One one = (One) o;
        return Objects.equals(a, one.a);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a);
    }
}
