/**
 * @author <>
 * @id matrnr
 */

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.text.DecimalFormatSymbols;

public class VehicleManagement {
    private final VehicleDAO vehicleDAO;
    
    public VehicleManagement(String fileName){
        this.vehicleDAO = new SerializedVehicleDAO(fileName);
    }

    public static DecimalFormat createDecimalFormat(){
        DecimalFormatSymbols decimalFormatSymbol = DecimalFormatSymbols.getInstance();
		decimalFormatSymbol.setDecimalSeparator('.');
		return new DecimalFormat("0.00", decimalFormatSymbol);
    }

    public void printAll(){
        for(Vehicle v : vehicleDAO.getVehicleList()){
            printVehicle(v);
        }
    }

    public void printVehicle(int id){
        Vehicle v = vehicleDAO.getVehicle(id);
        if(v != null){
            printVehicle(v);
        }
    }

    public void addVehicle(String args[]) {
        if((args[2].equalsIgnoreCase("truck") && args.length != 8) || (args[2].equalsIgnoreCase("car") && args.length != 9)){
            throw new IllegalArgumentException("Invalid parameter.");
        }
        try{
            int id = Integer.parseInt(args[3]);
            String brand = args[4];
            String model = args[5];
            int year = Integer.parseInt(args[6]);
            double basePrice = Double.parseDouble(args[7]);
            if(args[2].equalsIgnoreCase("car")){
                int inspectionYear = Integer.parseInt(args[8]);
                vehicleDAO.saveVehicle(new Car(inspectionYear, brand, model, year, basePrice, id));
            }
            else if(args[2].equalsIgnoreCase("truck")){
                vehicleDAO.saveVehicle(new Truck(brand, model, year, basePrice, id));
            }
            else{
                throw new IllegalArgumentException("Invalid parameter.");
            }
        }
        catch(NumberFormatException e){
            throw new IllegalArgumentException("Invalid parameter.");
        }
    }

    public void delVehicle(int id){
        vehicleDAO.deleteVehicle(id);
    }

    public void countVehicle(){
        System.out.println((int)vehicleDAO.getVehicleList().stream().count());
    }

    public void countCars(){
        System.out.println((int)vehicleDAO.getVehicleList().stream().filter(x -> x instanceof Car).count());
    }

    public void countTrucks(){
        System.out.println((int)vehicleDAO.getVehicleList().stream().filter(x -> x instanceof Truck).count());
    }

    public void meanPrice(){
        DecimalFormat decimalFormat = createDecimalFormat();
        List<Vehicle> vehicles = vehicleDAO.getVehicleList();
        System.out.println(decimalFormat.format(vehicles.stream().mapToDouble(x -> x.getPrice()).sum()/vehicles.size()));
    }

    public void oldestVehicles(){
        int year = vehicleDAO.getVehicleList().stream().max((x,y) -> Integer.compare(x.getAge(), y.getAge())).get().getYear();
        StringBuilder str = new StringBuilder();
        List<Vehicle> vehicles = vehicleDAO.getVehicleList().stream().filter(x -> {return x.getYear() == year;}).collect(Collectors.toList());
        for(Vehicle v:vehicles){
            str.append("Id: ").append(v.getId()).append("\n");
        }
        System.out.print(str.toString());
    }

    private void printVehicle(Vehicle v){
        DecimalFormat decimalFormat = createDecimalFormat();
        StringBuilder str = new StringBuilder();
        str.append(spacer("Type: ")).append(v.getClass().getName().toString()).append("\n");
        str.append(spacer("Id: ")).append(v.getId()).append("\n");
        str.append(spacer("Brand: ")).append(v.getBrand()).append("\n");
        str.append(spacer("Model: ")).append(v.getModel()).append("\n");
        str.append(spacer("Year: ")).append(v.getYear()).append("\n");
        if(v instanceof Car){
            str.append(spacer("Inspection: ")).append(((Car)v).getInspectionYear()).append("\n");
        }
        str.append(spacer("Base price: ")).append(decimalFormat.format(v.getBasePrice())).append("\n");
        str.append(spacer("Price: ")).append(decimalFormat.format(v.getPrice())).append("\n");
        System.out.println(str.toString());
    }

    private String spacer(String input){
        String whitespaces = "";
        for(int i = 0; i < 12 - input.length(); i++){
            whitespaces += " ";
        }
        return input + whitespaces;
    }
}