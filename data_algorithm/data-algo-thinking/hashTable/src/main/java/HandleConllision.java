public interface HandleConllision<K,V> {

    public void remove(HashItem<K, V>[] hashtable,K key);
    public V getValue(HashItem<K, V>[] hashtable,K key);
    public void put(HashItem<K, V>[] hashtable,K key, V value);
}
