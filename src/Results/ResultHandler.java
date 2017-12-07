package Results;

import Querying.Heading;
import Querying.Query;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultHandler {
    private static final char NEW_LINE = '\n';

    private static String makeResults(Query query)
    {
        StringBuilder formattedResults = new StringBuilder("");
        formattedResults.append(query.getTitle()).append(NEW_LINE);
        if (query.getHeadings() == null)
        {
//            formattedResults.append(results.get(0).getResultParagraph());
            return formattedResults.toString();
        }
        else
        {
            for (Heading heading: query.getHeadings())
            {
                formattedResults.append(heading.getHeading()).append(NEW_LINE);
                formattedResults.append(heading.getResult().getResultParagraph()).append(NEW_LINE);
                if (heading.hasSubheadings())
                    recurseHeadingResults(formattedResults,heading);
            }
        }
        return formattedResults.toString();
    }

    private static void recurseHeadingResults(StringBuilder sb, Heading heading)
    {
        for (Heading h: heading.getSubheadings())
        {
            sb.append((h.getHeading())).append(NEW_LINE);
            sb.append(h.getResult().getResultParagraph()).append(NEW_LINE);
            if (h.hasSubheadings())
                recurseHeadingResults(sb, h);
        }
    }

    public static void printResults(Query query)
    {
        System.out.println(makeResults(query));
    }
    public static void writeResults(Query query, File file)
    {
        try
        {
            FileWriter fw = new FileWriter(file);
            fw.write(makeResults(query));
            fw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
