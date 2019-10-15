import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

public class ConnectRedission {
    public static RedissonClient redissonClient = null;
    public static Config config;
    private ConnectRedission()
    {

    }
    public  static RedissonClient getRedissonClient()
    {

        if(redissonClient==null)
        {
            config = new Config();
            config.useSingleServer()
                    .setAddress("redis://127.0.0.1:6379");

            redissonClient = Redisson.create(config);
            return redissonClient;
        }
        return redissonClient;
    }
}
