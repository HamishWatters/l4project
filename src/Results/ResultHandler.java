package Results;

import Querying.Heading;
import Querying.Query;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ResultHandler {
    private static final char NEW_LINE = '\n';
    private Query query;

    public ResultHandler(Query query)
    {
        this.query = query;
    }

    public void getBestDocumentNoDuplicates()
    {
        HashMap<Integer, Heading> usedDocs = new HashMap<>();
        int docsFound = 0;
        ArrayList<Heading> headingQueue = new ArrayList<>();
        for (Heading h: query.getHeadings())
        {
            h.getAllNestedSubheadings(headingQueue);
        }
        for (Heading h: headingQueue)
        {
            int[] docids = h.getResult().getResultSet().getDocids();
            int bestDoc = -1;
            for (int i = 0; i < docids.length; i++)
            {
                if (usedDocs.containsKey(docids[i]))
                    continue;
                else
                {
                    usedDocs.put(docids[i], h);
                    bestDoc = docids[i];
                }
            }
            h.getResult().setResultParagraph(bestDoc);
        }

    }

    private String makeResults()
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
                formattedResults.append(heading.getResult().getResultParagraph(0)).append(NEW_LINE);
                if (heading.hasSubheadings())
                    recurseHeadingResults(formattedResults,heading);
            }
        }
        return formattedResults.toString();
    }

    private void recurseHeadingResults(StringBuilder sb, Heading heading)
    {
        for (Heading h: heading.getSubheadings())
        {
            sb.append((h.getHeading())).append(NEW_LINE);
            sb.append(h.getResult().getResultParagraph(0)).append(NEW_LINE);
            if (h.hasSubheadings())
                recurseHeadingResults(sb, h);
        }
    }

    public void printResults()
    {
        System.out.println(makeResults());
    }
    public void writeResults(File file)
    {
        try
        {
            FileWriter fw = new FileWriter(file);
            fw.write(makeResults());
            fw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
