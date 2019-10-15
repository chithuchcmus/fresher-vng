import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws FileNotFoundException {

        IContainer iContainer = IContainer.getInstanceIContainer();
        Dictionary dictionary = iContainer.getDictionary("trie");
        FolderHelper helper = new FolderHelper();
        String urlFolder = "E:\\Fresher\\data_algorithm\\blogs\\blogs";
        long before = System.currentTimeMillis();
        helper.getDataFromFolderToDictionary(dictionary,urlFolder);
        long now = System.currentTimeMillis();
        System.out.println("Seconds elapsed: " + (now-before)+ " MiliSseconds." );

        while (true)
        {
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter test");
            String test = myObj.nextLine();  // Read user input

            long before1 = System.currentTimeMillis();
            boolean contain = dictionary.contains(test);
            long now1 = System.currentTimeMillis();
            System.out.println(contain);
            System.out.println("Seconds elapsed: " + (now1-before1)+ " MiliSseconds." );
            dictionary.AutoSuggestions(test);

        }


    }
}
