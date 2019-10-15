import java.util.ArrayList;

public class Trie implements Dictionary {
    private TrieNode root;
    final private int MaxPrefixWord = 6;
    public static int countElement =0 ;
    private  static int currentPrefixWord = 0;
    final private int A = 10;
    final private int Z = 35;
    final private int a = 36;
    final private int z = 61;
    final private int INDEX_SPECIAL_CHARACTOR = 62;
    final private int SPECIAL_CHARACTOR = 39; // ki tu '
    public Trie()
    {
        root = new TrieNode();
    }
    public boolean contains(String word) {
        int level = 0;
        int length = word.length();
        int index = -1;
        TrieNode tNode = root;
        for(level=0; level< length;level++)
        {
            //get  index of charactor in array
            index = getIndexOfChar(word.charAt(level));
            if(index >= 0 )
            {
                //if null word don't exist in Trie
                if(tNode.children[index] == null)
                {
                    return false;
                }
                tNode = tNode.children[index];
            }
            else
                return false;

        }
        if(tNode != null && tNode.isEndWord == true)
        {
            return true;
        }
        return false;

    }
    public void insert(String word)
    {
        int length = word.length();
        if(length <= 0)
            return;
        if(contains(word))
            return;
        int level = 0;
        int index = -1;
        TrieNode tNode = root;
        for(level=0; level< length;level++)
        {
            //get  index of charactor in array
            index = getIndexOfChar(word.charAt(level));
            if(index >= 0)
            {
                if(tNode.children[index]==null)
                {
                    tNode.children[index]= new TrieNode();
                }
                tNode = tNode.children[index];
            }
            else
                break;
        }
        tNode.isEndWord=true;
        countElement++;

    }

    public void AutoSuggestions(String prefix)
    {
        int index = -1;
        int length = prefix.length();
        TrieNode tNode = root;
        for(int level=0; level< length;level++)
        {
            index = getIndexOfChar(prefix.charAt(level));
            if(index >= 0 )
            {
                if(tNode.children[index] == null)
                {
                    System.out.println("Không tồn tại trong CSDL");
                    return;
                }
                tNode = tNode.children[index];
            }
            else
                return;

        }
        if(tNode.isEndWord == true)
        {
            if(!tNode.isLastNode())
            {
                System.out.println("5 Từ gần nhất với mẫu có trong CSDL: \n");
                printAutoSuggestions(tNode,prefix);
            }
        }
    }


    public int getIndexOfChar(char c)
    {
        // A- >Z
        // 0-9 is number
        // 10 -> 35 is A->Z
        // 36-> 61 is a->z
        //because index ascii of A And Z with 0->9
        if(c >= 'A' && c <= 'Z' )
        {
            return c-'0'-7;
        }
        else if(c >= 'a' && c <='z')
        {
            return c-'0'-13;
        }
        else if(c>= '0' && c<='9')
        {
            return c-'0';
        }
        else if( (int)c == SPECIAL_CHARACTOR)
        {
            return INDEX_SPECIAL_CHARACTOR;
        }
        return -1;

    }
    public char convertFromIndexToChar(int index)
    {
        // A- >Z
        // 0-9 is number
        // 10 -> 35 is A->Z
        // 36-> 61 is a->z
        if(index >= A && index <= Z)
        {
            return (char)(index  + 7 + '0');
        }
        else if(index >= a && index <= z){
            return (char)(index + 13 +'0');
        }
        else if(index == INDEX_SPECIAL_CHARACTOR)
        {
            return (char)(SPECIAL_CHARACTOR);
        }
        else
            return (char)(index + '0');
    }
    private boolean printAutoSuggestions(TrieNode rootChildren,String prefix) {

        if(currentPrefixWord > MaxPrefixWord)
        {
            return false;
        }
        if(rootChildren.isEndWord == true)
        {
            System.out.println(prefix + "\n");
            currentPrefixWord++;
        }
        if(rootChildren.isLastNode())
        {
            return true;
        }
        for(int index = 0; index < TrieNode.ALPHABET_SIZE;index++)
        {
            if(rootChildren.children[index] != null)
            {
                char charAtNode = convertFromIndexToChar(index);
                String temp = prefix + charAtNode;
                boolean checkContinue= printAutoSuggestions(rootChildren.children[index],temp);
                if(!checkContinue)
                {
                    currentPrefixWord =0 ;
                    break;
                }
            }
        }
        return true;
    }


}
