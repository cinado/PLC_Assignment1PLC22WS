/**
 * @author <>
 * @id matrnr
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SerializedVehicleDAO implements VehicleDAO {
    private final String fileName;

    public SerializedVehicleDAO(String fileName){
        if (fileName.isEmpty() || fileName == null) {
            throw new IllegalArgumentException("Filename invalid.");
        }
        
        this.fileName = fileName;
    }

    @Override
    public List<Vehicle> getVehicleList() {
        return deserializeVehicles();
    }

    @Override
    public Vehicle getVehicle(int id) {
        for (Vehicle vehicle : this.getVehicleList()) {
            if (vehicle.getId() == id) {
                return vehicle;
            }
        }
        return null;
    }

    @Override
    public void saveVehicle(Vehicle vehicle) {
        List<Vehicle> vehicles = this.getVehicleList();
        if (vehicles.stream().anyMatch(x -> x.getId() == vehicle.getId())) {
            throw new IllegalArgumentException("Vehicle already exists. (id=" + vehicle.getId() + ")");
        }
        vehicles.add(vehicle);
        serializeVehicles(vehicles);
    }

    @Override
    public void deleteVehicle(int id) {
        List<Vehicle> vehicles = this.getVehicleList();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == id) {
                vehicles.remove(vehicle);
                serializeVehicles(vehicles);
                return;
            }
        }
        throw new IllegalArgumentException("Vehicle not found. (id=" + id +")");
    }

    private void serializeVehicles(List<Vehicle> vehicles) {
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }
        try {
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(fileName, true));
            writer.writeObject(vehicles);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error during serialization: " + e.getMessage());
            System.exit(1);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Vehicle> deserializeVehicles(){
        File file = new File(fileName);
        List<Vehicle> vehicles = new ArrayList<>();
        if (!file.exists()) {
            return vehicles;
        }
        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file));
            vehicles = (List<Vehicle>) reader.readObject();
            reader.close();
        }
        catch (Exception e) {
            System.out.println("Error during deserialization: " + e.getMessage());
            System.out.flush();
            System.exit(1);
        }
        return vehicles;
    }
}