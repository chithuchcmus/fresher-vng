public class Singleton {

    private static Singleton instance = new Singleton();
    public String message;

    private Singleton(){
        message = "Only one instance";
    }
    public String getMessage()
    {
        return message;
    }

    public static Singleton getInstance(){
        return instance;
    }
}
