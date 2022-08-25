
//class Vehicle {
//    public static void main(String[] args) {
//        EnjoyVehicle.startVehicle();
//    }
//    class Engine {
//        void start() {
//            System.out.println("RRRrrrrrrr....");
//        }
//    }
//}

class EnjoyVehicle {
    public static void startVehicle() {
        // start your vehicle
        Vehicle vehicle = new Vehicle();
        Vehicle.Engine engine = vehicle.new Engine();
        engine.start();
    }
}
