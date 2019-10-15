import org.redisson.api.RList;
import org.redisson.api.RMapCache;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AppChat
{
    public RedissonClient redissonClient = ConnectRedission.getRedissonClient();
    public RTopic topic = redissonClient.getTopic(AppHelper.chanel);


    public void pub()
    {
        //get new meesage from channel
        sub();
        //get old message from chanel
        getOldMess();

        //Input username
        Scanner reader = new Scanner(System.in);  // Reading from System.i
        System.out.println("Text your name: ");
        String userName= reader.nextLine(); // Scans the next token of the input as an int.


        //store message in cache
        RList<Message> mapCache = redissonClient.getList(AppHelper.stored);

        System.out.println("Message: ");
        while(true)
        {
            //Input content message
            String content;
            content= reader.nextLine();
            //pub message in cache
            mapCache.add(new Message(content,userName));
            //cache store last one day
            mapCache.expire(10, TimeUnit.SECONDS);
            //pub message into chanel
            long clientsReceivedMessage = topic.publish(new Message(content,userName));
        }
    }
    public void getOldMess()
    {

        RList<Message> messageRList = redissonClient.getList(AppHelper.stored);

        for (Message message : messageRList)
        {
            System.out.println(message.getUserName() + " " + message.getContext() +"\n");
        }

    }

    //get message from chanel when have a change in there
    public void sub()
    {
        topic.addListener(Message.class, new MessageListener<Message>()
        {
            @Override
            public void onMessage(CharSequence charSequence, Message message) {
                System.out.println(message.userName  +": " +message.context +  "\n");
            }
        });
    }


}

