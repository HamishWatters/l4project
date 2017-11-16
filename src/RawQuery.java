import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RawQuery {

    private String title;
    private List<String> headings;

    public RawQuery(String title)
    {
        this.title = title;
    }
    public RawQuery(String title, List<String> headings)
    {
        this.title = title;
        this.headings = headings;
    }

    public String getTitle()
    {
        return title;
    }

    public List<String> getHeadings()
    {
        return headings;
    }

    public static RawQuery readQueryFile(String path)
    {
        String title, line;
        ArrayList<String> headings = new ArrayList<>();
        try {
            Scanner reader = new Scanner(new File(path));
            title = reader.nextLine();
            while (reader.hasNextLine())
            {
                line = reader.nextLine();
                headings.add(line);
            }
            if (headings.isEmpty())
                return new RawQuery(title);
            else
                return new RawQuery(title, headings);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
