public class QuadraticProbing<K,V> implements  HandleConllision<K,V> {

    public void remove(HashItem<K, V>[] hashtable, K key) {
        int index = hash(hashtable,key);
        int count =1;
        while (hashtable[index]!=null)
        {
            if(hashtable[index].getValue() != null && hashtable[index].getKey() !=null)
            {
                if(hashtable[index].getKey() == key)
                {
                    HashItem<K,V> deletedItem = new HashItem<K, V>();
                    hashtable[index]=deletedItem;
                    break;
                }
            }
            index = index + count * count;
            count++;
            index %= hashtable.length;
        }
    }

    public V getValue(HashItem<K, V>[] hashtable, K key) {
        int index = hash(hashtable,key);
        int count=0;
        while (hashtable[index] != null)
        {
            //tranh loop vo tan
            if(count> hashtable.length)
            {
                break;
            }
            if(hashtable[index].getValue() != null && hashtable[index].getKey() != null)
            {
                if(hashtable[index].getKey() == key)
                    return hashtable[index].getValue();
            }
            count++;
            index = index  + count * count;
            index %= hashtable.length;
        }
        return null;
    }

    public void put(HashItem<K, V>[] hashtable, K key, V value) {
        int index = hash(hashtable,key);
        HashItem<K,V> newItem = new HashItem<K, V>(key,value);
        int count= 1;
        while (hashtable[index] != null && hashtable[index].getValue() != null && hashtable[index].getKey() != null)
        {

            index = index  + count * count;
            count++;
            index %= hashtable.length;
        }
        if((hashtable[index] == null) || (hashtable[index].getKey()==null && hashtable[index].getValue() == null))
        {
            hashtable[index] = newItem;
        }

    }
    public int hash(HashItem<K, V>[] hashtable, K key)
    {
        int index = Math.abs(key.hashCode());
        return index % hashtable.length;
    }
}
