public class main {
    public static void main(String[] args) {
        Travel travel = new Travel(new PlanesMove());
        travel.move();
        //in ra fly
        travel = new Travel(new VehicleMove());
        travel.move();
        //in ra vehicle move
    }
}
