package ru.otus.objects;

import java.util.Objects;

public class Three {
    public Integer a;
    public Integer b;
    public Two two;

    public Three(Integer a, Integer b, Two two) {
        this.a = a;
        this.b = b;
        this.two = two;
    }

    @Override
    public String toString() {
        return "Three{" +
                "a=" + a +
                ", b=" + b +
                ", two=" + two +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Three three = (Three) o;
        return Objects.equals(a, three.a) &&
                Objects.equals(b, three.b) &&
                Objects.equals(two, three.two);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, two);
    }
}
