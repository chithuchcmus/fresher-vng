public class IContainer {
    private static final IContainer iContainer = new IContainer();
    private Dictionary dictionary;
    private IContainer()
    {

    }
    public static IContainer getInstanceIContainer()
    {
        return iContainer;
    }
    public Dictionary getDictionary(String type)
    {
        String stringType = type.toLowerCase();
        if(stringType.equals("trie"))
        {
            dictionary = new Trie();
            return dictionary;
        }
        else if(stringType.equals("bloomfilter"))
        {
            dictionary = new BloomFilter();
            return dictionary;
        }
        //default
        dictionary = new Trie();
        return dictionary;
    }
}
