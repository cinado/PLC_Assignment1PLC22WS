public class Truck extends Vehicle {
    public Truck(String brand, String model, int year, double basePrice, int id){
        super(brand, model, year, basePrice, id);
    }

    @Override
    public double getDiscount(){
        return Math.min(0.05 * this.getAge(), 0.2);
    }
}