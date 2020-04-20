package ru.otus.hibernate.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "addresses")
public class AddressDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "address_id")
    private long id;

    @Column(name = "street")
    private String street;

    public AddressDataSet() {
    }

    public AddressDataSet(String street) {
        this.street = street;
    }

    public AddressDataSet(AddressDataSet address) {
        this.street = new String(address.getStreet());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDataSet that = (AddressDataSet) o;
        return id == that.id &&
                Objects.equals(street, that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street);
    }
}
