package ru.otus.objects;

import java.util.Objects;

public class Two {
    public Integer a;
    public Integer b;
    public One one;

    public Two(Integer a, Integer b, One one) {
        this.a = a;
        this.b = b;
        this.one = one;
    }

    @Override
    public String toString() {
        return "Two{" +
                "a=" + a +
                ", b=" + b +
                ", one=" + one +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Two two = (Two) o;
        return Objects.equals(a, two.a) &&
                Objects.equals(b, two.b) &&
                Objects.equals(one, two.one);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, one);
    }
}
