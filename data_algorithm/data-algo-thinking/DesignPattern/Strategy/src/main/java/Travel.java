public class Travel {
    private Traffic traffic;

    public Travel(Traffic traffic){
        this.traffic = traffic;
    }

    public void move(){
        traffic.move();
    }
}
