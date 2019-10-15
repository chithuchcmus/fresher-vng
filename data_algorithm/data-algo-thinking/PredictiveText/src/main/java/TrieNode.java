
public class TrieNode  implements Cloneable {
   final public static int ALPHABET_SIZE = 63;
    public TrieNode[] children;
    public boolean isEndWord;
    public TrieNode()
    {
        children = new TrieNode[ALPHABET_SIZE];
        isEndWord = false;
        for(int i=0;i<ALPHABET_SIZE;i++)
        {
            children[i]=null;
        }
    }
    public boolean isLastNode()
    {
        for(int i=0;i<ALPHABET_SIZE;i++)
        {
            if(children[i]!=null)
                return false;
        }
        return true;
    }
}
