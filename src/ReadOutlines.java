import Querying.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ReadOutlines {
    private static List<Query> fromQrels(File qrelFiles)
    {
        List<Query> queries = new ArrayList<>();
        try {
            Scanner s = new Scanner(qrelFiles);
            String line, oldquery = "";
            while (s.hasNextLine())
            {
                boolean newQuery = false;
                line = s.nextLine();
                String rawquery = line.split(" ")[0];
                if (rawquery.equals(oldquery))
                    continue;
                oldquery = rawquery;
                String safeQuery = rawquery.replace("%20"," ").replace("%22"," ");
                String[] split = safeQuery.split("/");
                Query query = null;
                for (Query q: queries)
                    if (q.getTitle().equals(split[0]))
                        query = q;
                if (query == null)
                {
                    if (split.length == 1)
                        query = new SingleQuery(split[0]);
                    else {
                        query = new ArticleQuery(split[0], new ArrayList<>());
                    }
                    newQuery = true;
                }
                Heading heading = null;
                if (split.length > 1)
                {
                    for (Heading h: query.getHeadings())
                        if (h.getName().equals(split[1]))
                            heading = h;
                    if (heading == null)
                    {
                        heading = new Heading(split[1]);
                        if (split.length == 2)
                            heading.setId(rawquery);
                        ((ArticleQuery)query).addHeading(heading);
                    }
                }
                for (int i =2; i < split.length; i++)
                {
                    boolean found = false;
                    for (Heading h: heading.getSubheadings())
                        if (h.getName().equals(split[i]))
                        {
                            heading = h;
                            found = true;
                            break;
                        }
                    if (!found)
                    {
                        System.out.println("Created new subheading " + split[i] + " at depth " + i + " for query " + rawquery);
                        Heading newHeading = new Heading(split[i]);
                        if (i == split.length -1)
                            newHeading.setId(rawquery);
                        heading.addSubheading(newHeading);
                        heading = newHeading;
                    }
                }
                if (newQuery)
                {
                    query.setId(query.getTitle());
                    queries.add(query);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return queries;
    }

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Usage: ReadOutlines <qrelfile>");
        }
        List<Query> queries = fromQrels(new File(args[0]));
        System.out.println("bam"+queries.size());
        QueryCoordinator q = new QueryCoordinator();
        try {
            q.generateTopicFile(queries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
