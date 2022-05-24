package com.example.cruddemo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "house")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="house_name")
    private String house_name;

    @OneToMany(mappedBy = "house",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = false)
    private List<House_Person> house_people = new ArrayList<>();

    public House() {
    }

    public House(String house_name) {
        this.house_name = house_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public List<House_Person> getHouse_people() {
        return house_people;
    }

    public void setHouse_people(List<House_Person> house_people) {
        this.house_people = house_people;
    }

    public void addHousePeople(House_Person hp){
        this.house_people.add(hp);
    }
}
