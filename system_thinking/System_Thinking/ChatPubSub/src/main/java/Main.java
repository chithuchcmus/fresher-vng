import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

public class Main {
    public static void main(String Args[])
    {
       AppChat appChat = new AppChat();
       appChat.pub();
    }
}

