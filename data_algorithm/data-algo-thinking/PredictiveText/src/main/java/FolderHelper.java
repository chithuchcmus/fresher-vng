
import java.io.*;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class FolderHelper {

    public  void readDataFromFile(Dictionary dictionary, String url) {
        try {
            File f = new File(url);
            FileInputStream inputStream = new FileInputStream(f);
            Scanner sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if(!containTag(line))
                {
                    List<String> items = Arrays.asList(line.split("(?=[^'])\\W"));
                    for (String str : items)
                    {
                        dictionary.insert(str);
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("wrong");
        }
    }
    public void getDataFromFolderToDictionary(Dictionary dictionary, String urlFolder)
    {

        File folder = new File(urlFolder);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                readDataFromFile(dictionary,file.getAbsoluteFile().toString());
            }
        }
    }
    private boolean containTag(String pattern)
    {
        if(pattern.equals("") || pattern.matches("\\s+"))
            return true;
        pattern = pattern.toLowerCase();
        if(pattern.contains("blog") || pattern.contains("post") || pattern.contains("date"))
            return true;
        return false;
    }

}
