package pl.edu.pjatk.MPR_2_Spring.model;
//Pakiet "model" reprezentują jakieś twarde dane
public class Car {
    private String make;
    private String color;

    public Car(String make, String color) {
        this.make = make;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }
}
