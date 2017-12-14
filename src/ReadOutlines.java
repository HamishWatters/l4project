import Querying.ArticleQuery;
import Querying.Heading;
import Querying.Query;
import Querying.QueryCoordinator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadOutlines {
    public static List<ArticleQuery> fromQrels(File qrelFiles)
    {
        List<ArticleQuery> queries = new ArrayList<>();
        try {
            Scanner s = new Scanner(qrelFiles);
            String line;
            while (s.hasNextLine())
            {
                line = s.nextLine();
                String rawquery = line.split(" ")[0];
                rawquery = rawquery.replace("%20"," ").replace("%22"," ");
                String[] split = rawquery.split("/");
                ArticleQuery query = null;
                for (ArticleQuery q: queries)
                    if (q.getTitle().equals(split[0]))
                        query = q;
                if (query == null)
                    query = new ArticleQuery(split[0],new ArrayList<>());
                Heading heading = null;
                if (split.length > 1)
                {
                    for (Heading h: query.getHeadings())
                        if (h.getHeading().equals(split[1]))
                            heading = h;
                    if (heading == null)
                    {
                        heading = new Heading(split[1]);
                        query.addHeading(heading);
                    }
                }
                for (int i =2; i < split.length; i++)
                {
                    boolean found = false;
                    for (Heading h: heading.getSubheadings())
                        if (h.getHeading().equals(split[i]))
                        {
                            heading = h;
                            found = true;
                            break;
                        }
                    if (!found)
                    {
                        Heading newHeading = new Heading(split[i]);
                        heading.addSubheading(newHeading);
                        heading = newHeading;
                    }
                }
                queries.add(query);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return queries;
    }

    public static void main(String[] args)
    {
        List<ArticleQuery> queries = fromQrels(new File("/home/hamish/Documents/IndividualProject/benchmarkY1train/train.benchmarkY1train.fold0.cbor.hierarchical.qrels"));
        QueryCoordinator q = new QueryCoordinator();
        try {
            q.generateTopicFile(queries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
