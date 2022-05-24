package com.example.cruddemo.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "house_person")
public class House_Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "p_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="h_id")
    private House house;

    public House_Person() {
    }

    public House_Person(Person person, House house) {
        this.person = person;
        this.house = house;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House_Person that = (House_Person) o;
        return id == that.id && person.equals(that.person) && house.equals(that.house);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, house);
    }

    @Override
    public String toString() {
        return "House_Person{" +
                "id=" + id +
                ", person=" + person +
                ", house=" + house +
                '}';
    }
}
