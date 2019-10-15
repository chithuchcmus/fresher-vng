import java.util.ArrayList;

public interface Dictionary {
    public boolean contains(String word);
    public void insert(String word);
    public void AutoSuggestions(String prefix);

}
