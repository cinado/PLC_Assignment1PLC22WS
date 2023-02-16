/**
 * @author <>
 * @id matrnr
 */

import java.util.Calendar;

public class Car extends Vehicle{
    private final int lastInspectionYear;

    public Car(int lastInspectionYear, String brand, String model, int year, double basePrice, int id){
        super(brand, model, year, basePrice, id);
        if(Calendar.getInstance().get(Calendar.YEAR) < lastInspectionYear){
            throw new IllegalArgumentException("Inspection year invalid.");
        }
        this.lastInspectionYear = lastInspectionYear;
    }

    @Override
    public double getDiscount(){
        return Math.min(0.05 * this.getAge() + 0.02 * (Calendar.getInstance().get(Calendar.YEAR) - lastInspectionYear), 0.15);
    }

    public int getInspectionYear(){
        return lastInspectionYear;
    }
}