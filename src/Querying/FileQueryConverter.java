package Querying;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileQueryConverter {
    private static List<Query> generateQueriesFromQrels(File qrelFile)
    {
        List<Query> queries = new ArrayList<>();
        Query currentQuery = null;
        try {
            Scanner scanner = new Scanner(qrelFile);
            String line;
            while (scanner.hasNextLine())
            {
                line = scanner.nextLine();
                String[] compSplit = line.split(" ");
                if (compSplit.length != 4)
                {
                    System.err.println("Invalid line: " + line);
                    continue;
                }
                String[] querySplit = convertText(compSplit[0]).split("/");
                if (querySplit.length == 1)
                {
                    currentQuery = new SingleQuery(convertText(querySplit[0]));
                    currentQuery.setId(compSplit[0]);
                    queries.add(currentQuery);
                    continue;
                }
                ArticleQuery longQuery = (ArticleQuery)currentQuery;
                String title = querySplit[0];
                if (currentQuery == null || !currentQuery.getTitle().equals(title))
                {
                    longQuery = new ArticleQuery(title, new ArrayList<>());
                    if (currentQuery != null)
                        queries.add(currentQuery);
                    currentQuery = longQuery;
                }
                String headingString = querySplit[1];
                Heading heading;
                if ((heading = longQuery.getHeading(headingString)) == null)
                {
                    heading = new Heading(headingString);
                    longQuery.addHeading(heading);
                }
                if (querySplit.length > 2)
                {
                    Heading subheading;
                    for (int i = 2; i < querySplit.length; i++) {
                        if ((subheading = heading.getSubheading(querySplit[i])) == null)
                        {
                            subheading = new Heading(querySplit[i]);
                            heading.addSubheading(subheading);
                        }
                        heading = subheading;
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return queries;
    }

    private static String convertText(String text)
    {
        return text.replace("%20"," ").replace("%22", " ").replace("."," ").replace(":","");
    }

    public static void main(String[] args)
    {
        QueryCoordinator coordinator = new QueryCoordinator();
        List<Query> queries = generateQueriesFromQrels(new File("/home/hamish/Documents/IndividualProject/benchmarkY1train/train.benchmarkY1train.fold0.cbor.hierarchical.qrels"));
        for (Query q: queries) {
            coordinator.executeQuery(q);
        }

    }
}
