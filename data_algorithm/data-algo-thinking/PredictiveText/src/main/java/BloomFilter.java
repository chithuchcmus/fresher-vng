import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

public class BloomFilter  implements Dictionary{
    //https://stackoverflow.com/questions/658439/how-many-hash-functions-does-my-bloom-filter-need
    //Công thức tính bloomfilter
    //p~0.1
    //n hash function ~ 4
    //n~700.000
    public  static int countElement = 0;
    final private int SIZE_BLOOMFILTER = 3500000;
    private boolean[] bitarray ;
    private final int PRIME_H1 = 151;
    private final int PRIME_H2 = 153;
    private final int PRIME_H3 = 163;
    private final int PRIME_H4 = 167;
    private final int HASH_COUNT = 4;
    public BloomFilter()
    {
        bitarray = new boolean[SIZE_BLOOMFILTER];
        for(int i=0; i< SIZE_BLOOMFILTER;i++)
            bitarray[i]=false;
    }
    @Override
    public boolean contains(String word) {
        if(!bitarray[h1(word)]|| !bitarray[h2(word)] || !bitarray[h3(word)] || !bitarray[h4(word)] )
            return false;
        return true;
    }

    @Override
    public void insert(String word) {
        if(word.length() <= 0)
            return;
        bitarray[h1(word)]=true;
        bitarray[h2(word)]=true;
        bitarray[h3(word)]=true;
        bitarray[h4(word)]=true;
        countElement++;
    }

    @Override
    public void AutoSuggestions(String prefix) {
        return;
    }


    private int h1(String word)
    {
        return hash(word,PRIME_H1,SIZE_BLOOMFILTER);
    }
    private int h2(String word) {
        return hash(word,PRIME_H2,SIZE_BLOOMFILTER);
    }
    private int h3(String word)
    {
        return hash(word,PRIME_H3,SIZE_BLOOMFILTER);
    }
    private int h4(String word)
    {
        return hash(word,PRIME_H4,SIZE_BLOOMFILTER);
    }


    public int hash(String key, int a,int numberBucket) {
        long hash = 0;
        try {
            byte[] keyCode =key.getBytes(StandardCharsets.UTF_8);
            int lengthKeyCode = keyCode.length;
            for (int i = 0; i < lengthKeyCode; i++) {
                hash += (long) Math.pow(a * 1.0, lengthKeyCode - (i + 1)) * keyCode[i] ;
                hash = hash % numberBucket;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return (int)hash;
    }
}
