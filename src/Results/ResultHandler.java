package Results;

import Querying.Query;
import Results.Result;

import java.util.List;

public class ResultHandler {

    private static String makeResults(Query query)
    {
        StringBuilder formattedResults = new StringBuilder("");
        List<Result> results = query.getResults();
        formattedResults.append(query.getTitle()).append("\n");
        if (query.getHeadings() == null)
        {
            formattedResults.append(results.get(0).getResultParagraph());
            return formattedResults.toString();
        }
        else
        {
            for (int i = 0; i < query.getHeadings().size(); i++)
            {
                formattedResults.append(query.getHeadings().get(i)).append("\n");
                formattedResults.append(query.getResults().get(i).getResultParagraph()).append("\n");
            }
        }
        return formattedResults.toString();
    }

    public static void printResults(Query query)
    {
        System.out.println(makeResults(query));
    }
}
