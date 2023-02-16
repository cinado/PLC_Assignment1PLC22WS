/**
 * @author <>
 * @id matrnr
 */

public class VehicleCLI {

	enum Commands{
		SHOW,
		ADD,
		DEL,
		COUNT,
		MEANPRICE,
		OLDEST;

		public static Commands fromCmd(String cmd) {
			for(Commands x:Commands.values()){
				if(x.name().equalsIgnoreCase(cmd)){
					return x;
				}
			}
			throw new IllegalArgumentException("Invalid parameter.");
		}
	}

	public static void main(String[] args) {
		try{
			if(args.length < 2){
				throw new IllegalArgumentException("Invalid parameter.");
			}
			VehicleManagement vm = new VehicleManagement(args[0]);
			switch(Commands.fromCmd(args[1])){
				case SHOW:
					if(args.length > 2){
						vm.printVehicle(Integer.parseInt(args[2]));
					}
					else{
						vm.printAll();
					}
					break;
				case ADD:
					vm.addVehicle(args);
					break;
				case DEL:
					vm.delVehicle(Integer.parseInt(args[2]));
					break;
				case COUNT:
					if(args.length > 2){
						if(args[2].equalsIgnoreCase("car")){
							vm.countCars();
						}
						else if(args[2].equalsIgnoreCase("truck")){
							vm.countTrucks();
						}
						else{
							System.out.println("Invalid parameter.");
						}
					}
					else{
						vm.countVehicle();
					}
					break;
				case MEANPRICE:
					vm.meanPrice();
					break;
				case OLDEST:
					vm.oldestVehicles();
					break;
				default:
					System.out.println("Invalid parameter.");
					break;
			}
		}
		catch(Exception e){
			System.out.println("Error: " + e.getMessage());
		}
	}
}