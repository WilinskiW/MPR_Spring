package pl.edu.pjatk.MPR_2_Spring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//Pakiet "model" reprezentują jakieś twarde dane
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String make;
    private String color;
    private long identification;

    public Car(String make, String color) {
        this.make = make;
        this.color = color;
        generateHashCode();
    }

    public Car() {}

    private void generateHashCode(){
        this.identification = make.hashCode() + color.hashCode();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        generateHashCode();
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
        generateHashCode();
    }

    public long getIdentification() {
        return identification;
    }
}
