import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class DoubleHashing <K,V> implements HandleConllision<K,V> {

    private final int PRIME_H1 = 151;
    private final int PRIME_H2 = 153;

    public void remove(HashItem<K, V>[] hashtable, K key) {
        int index = doubleHashing(key,0,hashtable.length);
        HashItem<K,V> currentItem  = hashtable[index];
        int i=1;
        while (currentItem != null)
        {
            if(currentItem.getKey() == key)
            {
                HashItem<K,V> deletedItem = new HashItem<K, V>();
                hashtable[index] = deletedItem;
                break;
            }
            index= doubleHashing(key,i,hashtable.length);
            i++;
            currentItem= hashtable[index];
        }
    }

    public V getValue(HashItem<K, V>[] hashtable, K key) {

        int index = doubleHashing(key,0,hashtable.length);
        HashItem<K,V> currentItem = hashtable[index];
        int i=1;
        while(currentItem != null)
        {
            if(currentItem.getKey() != null && currentItem.getValue() !=null )
            {
                if(currentItem.getKey() == key)
                {
                    return currentItem.getValue();
                }
            }
            index = doubleHashing(key,i,hashtable.length);
            i++;
            currentItem = hashtable[index];
        }
        return null;
    }

    public void put(HashItem<K, V>[] hashtable, K key, V value) {

        HashItem<K, V> newitem = new HashItem<K, V>(key, value);
        int index = doubleHashing(key, 0,hashtable.length);
        HashItem<K, V> currentItem = hashtable[index];
        int i = 1;
        while (currentItem != null) {
            if (currentItem.getKey() != null && currentItem.getValue() != null) {
                if (currentItem.getKey() == key) {
                    break;
                }
            }
            index = doubleHashing(key, i,hashtable.length);
            currentItem = hashtable[index];
            i++;
        }
        hashtable[index] = newitem;
    }

    public int hash(K key, final int a,int numberBucket) {
        long hash = 0;
        try {
            byte[] keyCode = serialize(key);
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
    public int doubleHashing(K key,final int attemp, int length){
        int hash1 = hash(key,PRIME_H1,length);
        int hash2 = hash(key,PRIME_H2,length);
        return (hash1 + (attemp* (hash2+1))) % length;
    }

    //convert key to array byte
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            byte[] yourBytes = bos.toByteArray();
            return  yourBytes;
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }
}
