import java.io.Serializable;

public class Message implements Serializable {
    public String userName;
    public String context;
    public Message()
    {
        userName=null;
        context=null;
    }
    public Message(String context,String userName)
    {
        this.userName=userName;
        this.context=context;
    }

    public String getUserName() {
        return userName;
    }

    public String getContext() {
        return context;
    }

}
