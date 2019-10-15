public class HashItem <K,V> {
    K key;
    V value;

    public HashItem()
    {
        this.key = null;
        this.value =null;
    }
    public HashItem( K key,  V value)
    {
        this.key = key;
        this.value =value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void setKey(K key) {
        this.key = key;
    }

}
