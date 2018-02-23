package Results;

import Querying.Heading;
import Querying.Query;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ResultHandler {
    private static final char NEW_LINE = '\n';
    private Query query;

    public ResultHandler(Query query)
    {
        this.query = query;
    }

    public void getBestDocumentByScore()
    {
        HashMap<Integer, Heading> usedDocs = new HashMap<>();
        HashMap<Integer, Double> docScores = new HashMap<>();
        LinkedList<Heading> headingQueue = new LinkedList<>();
        for (Heading h: query.getHeadings()) {
            h.getAllNestedSubheadings(headingQueue);
        }
        Heading h;
        while (!headingQueue.isEmpty())
        {
            h = headingQueue.pop();
            if (!h.getResult().hasMatching())
            {
                int[] docids = h.getResult().getResultSet().getDocids();
                double[] scores = h.getResult().getResultSet().getScores();
                int bestDoc = -1;
                for (int i = 0; i < docids.length; i++)
                {
                    if (!usedDocs.containsKey(docids[i]))
                    {
                        usedDocs.put(docids[i], h);
                        docScores.put(docids[i], scores[i]);
                        bestDoc = docids[i];
                        break;
                    }
                    else if (scores[i] > docScores.get(docids[i]))
                    {
                        Heading oldHeading = usedDocs.get(docids[i]);
                        oldHeading.getResult().setBestResult(-1);
                        headingQueue.add(oldHeading);
                        usedDocs.put(docids[i], h);
                        docScores.put(docids[i], scores[i]);
                        bestDoc = docids[i];
                        break;
                    }
                }
                h.getResult().setBestResult(bestDoc);
            }
        }
        for (Heading heading: query.getHeadings())
            heading.getAllNestedSubheadings(headingQueue);
        for (Heading heading: headingQueue)
            heading.getResult().setResultParagraph();
    }
    /**
     * For the current query, sets the result paragraph for each heading. Currently simply checks if a doc
     * has already been used, and if so, takes the next document etc.
     * TODO: Should use the score of the doc to determine which doc finds an alternative
     */
    public void getBestDocumentNoDuplicates()
    {
        HashMap<Integer, Heading> usedDocs = new HashMap<>();
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
                if (!usedDocs.containsKey(docids[i]))
                {
                    usedDocs.put(docids[i], h);
                    bestDoc = docids[i];
                    break;
                }
            }
            h.getResult().setBestResult(bestDoc);
        }
        for (Heading h: query.getHeadings())
            h.getResult().setResultParagraph();
    }

    public void getBestDocument()
    {
        ArrayList<Heading> headingQueue = new ArrayList<>();
        for (Heading h: query.getHeadings())
            h.getAllNestedSubheadings(headingQueue);
        for (Heading h: headingQueue)
        {
            h.getResult().setBestResult(h.getResult().getResultSet().getDocids()[0]);
            h.getResult().setResultParagraph();
        }
    }

    /**
     * Converts query into title, heading, paragraph, etc.
     * @return A string representation of the query results
     */
    private String makeResults()
    {
        StringBuilder formattedResults = new StringBuilder("");
        formattedResults.append(query.getTitle()).append(NEW_LINE);
        if (query.getHeadings() == null)
        {
//            formattedResults.append(results.get(0).getResultParagraph());  TODO: Redo logic for single queries
            return formattedResults.toString();
        }
        else
        {
            for (Heading heading: query.getHeadings())
            {
                formattedResults.append(heading.getName()).append(NEW_LINE);
                formattedResults.append(heading.getResult().getResultParagraph(0)).append(NEW_LINE);
                if (heading.hasSubheadings())
                    recurseHeadingResults(formattedResults,heading);
            }
        }
        return formattedResults.toString();
    }

    /**
     *  Runs through all nested headings and converts them into readable results.
     * @param sb: StringBuilder to generate the result representation in
     * @param heading: The heading to append to the builder
     */
    private void recurseHeadingResults(StringBuilder sb, Heading heading)
    {
        for (Heading h: heading.getSubheadings())
        {
            sb.append((h.getName())).append(NEW_LINE);
            sb.append(h.getResult().getResultParagraph(0)).append(NEW_LINE);
            if (h.hasSubheadings())
                recurseHeadingResults(sb, h);
        }
    }

    /**
     * Prints the results to stdout
     */
    public void printResults()
    {
        System.out.println(makeResults());
    }

    /**
     * Writes the results to a specified file
     * @param  file destination for results
     */
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
