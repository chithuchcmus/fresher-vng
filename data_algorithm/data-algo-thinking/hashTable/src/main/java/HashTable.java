import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class HashTable <K,V> {
    private HashItem<K, V>[] bucket;
    private HandleConllision<K,V> handle;
    private int sizeHashTable;
    private int count;
    private final int baseSize = 11;
    private final int h1 = 151;
    private final int h2 = 153;
    private final int MAX_SIZE_UP = 70;
    private final int MAX_SIZE_DOWN = 10;

    public int getCount()
    {
        return  count;
    }
    public int getCurrentSize()
    {
        return sizeHashTable;
    }
    public int getMaxSize()
    {
        return bucket.length;
    }

    public HashTable() {
        handle = new DoubleHashing<K, V>();
        sizeHashTable = baseSize;
        bucket = (HashItem<K, V>[]) new HashItem[sizeHashTable];
        count = 0;
    }
    public  HashTable(HandleConllision<K,V> handle)
    {
        sizeHashTable = baseSize;
        bucket = (HashItem<K, V>[]) new HashItem[sizeHashTable];
        count = 0;
        this.handle = handle;
    }
    public V getValue(K key) {
        return handle.getValue(bucket,key);
    }
    public void remove(K key)
    {
        handle.remove(bucket,key);
        count--;
        resizeDownBucket(bucket);

    }

    public void put(K key, V value) {
        handle.put(bucket,key,value);
        count++;
        resizeUpBucket(bucket);
    }


    public void resizeUpBucket(HashItem<K, V>[] hashtable)
    {
        int load = count * 100 / hashtable.length;
        if( load > MAX_SIZE_UP)
        {
            int newsize = hashtable.length * 2;
            resizeBucket(newsize);
        }

    }
    public  void resizeDownBucket(HashItem<K, V>[] hashtable)
    {
        int load = count * 100 / hashtable.length;
        if( load < MAX_SIZE_DOWN)
        {
            int newsize = (int) hashtable.length / 2;
            resizeBucket(newsize);
        }

    }

    public void resizeBucket(int newsizeArray)
    {
        if(newsizeArray < baseSize)
            return;
        newsizeArray = findNextPrime(newsizeArray);
        HashItem<K,V>[] newbucket = (HashItem<K, V>[]) new HashItem[newsizeArray];
        for(int i=0;i<sizeHashTable;i++)
        {
            HashItem<K,V> currentItem = bucket[i];
            if(currentItem != null && currentItem.getValue() != null && currentItem.getKey() != null)
            {
                handle.put(newbucket,currentItem.getKey(),currentItem.getValue());
            }
        }
        bucket = newbucket;
        sizeHashTable = newsizeArray;

    }

    public int findNextPrime(int currentNum)
    {

        while (currentNum > 0)
        {
            currentNum++;
            if(isPrime(currentNum))
            {
                return currentNum;
            }
        }
        return 0;
    }
    public boolean isPrime (int num)
    {
        if(num < 2)
        {
            return false;
        }
        else
        {
            boolean isPrime = true;
            for(int divisor = 2; divisor <= Math.sqrt((int)num); divisor++) {
                if (num % divisor == 0) {
                    isPrime = false;
                    break; // num is not a prime, no reason to continue checking
                }
            }
            return isPrime;
        }
    }
}
