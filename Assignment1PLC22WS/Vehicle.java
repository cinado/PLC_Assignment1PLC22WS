/**
 * @author <>
 * @id matrnr
 */

import java.io.Serializable;
import java.util.Calendar;

public abstract class Vehicle implements Serializable {
    private final String brand;
    private final String model;
    private final int year;
    private final double basePrice;
    private final int id;

    public Vehicle(String brand, String model, int year, double basePrice, int id){
        if(Calendar.getInstance().get(Calendar.YEAR) < year){
            throw new IllegalArgumentException("Year built invalid.");
        }
        else if(brand.isEmpty() || model.isEmpty()){
            throw new IllegalArgumentException("Invalid parameter.");
        }
        else if(basePrice <= 0){
            throw new IllegalArgumentException("Base price invalid.");
        }

        this.brand = brand;
        this.model = model;
        this.year = year;
        this.basePrice = basePrice;
        this.id = id;
    }

    public int getAge(){
        return Calendar.getInstance().get(Calendar.YEAR) - year;
    }

    public double getPrice(){
        return basePrice - (basePrice*getDiscount());
    }

    public int getId(){
        return this.id;
    }

    public abstract double getDiscount();

    public String getBrand() {
        return this.brand;
    }

    public String getModel() {
        return this.model;
    }

    public int getYear() {
        return this.year;
    }

    public double getBasePrice() {
        return this.basePrice;
    }
}