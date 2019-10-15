public class main {
    public static void main(String args[])
    {
        // instantiating Singleton class with variable x
        Singleton x = Singleton.getInstance();

        // instantiating Singleton class with variable y
        Singleton y = Singleton.getInstance();


        System.out.println("String from x is " + x.getMessage());
        System.out.println("String from y is " + y.getMessage());

    }
}
