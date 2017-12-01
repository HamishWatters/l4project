package Querying;

import Results.Result;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.structures.Index;
import org.terrier.structures.MetaIndex;
import org.terrier.terms.Stemmer;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class QueryCoordinator {
    private static final String STOPLIST_FILE = "stoplist.txt";

    private Index index;
    private MetaIndex meta;
    private Manager queryManager;
    private Set<String> stoplist;

    public QueryCoordinator()
    {
        this.index = Index.createIndex("index","data");
        this.meta = index.getMetaIndex();
        this.queryManager = new Manager(index);
        this.stoplist = importStoplist();
    }


    public void executeQuery(Query query)
    {
        String title = filterStopwords(query.getTitle());
        if (query.getHeadings() != null)
        {
            for (String heading : query.getHeadings())
            {
                heading = filterStopwords(heading);
                SearchRequest srq = queryManager.newSearchRequest(String.valueOf(query.getQueryId()), title + heading);
                srq.addMatchingModel("Matching", query.getModel().name());
                queryManager.runSearchRequest(srq);
                try {
                    query.addResult(new Result(meta.getItem("body", srq.getResultSet().getDocids()[0]), srq.getResultSet()));
                } catch (IOException e) {
                    query.addResult(new Result("", srq.getResultSet()));
                }
            }
        }
    }

    private String filterStopwords(String startQuery)
    {
        StringBuilder endQueryBuilder = new StringBuilder("");
        for (String word: startQuery.split(" "))
        {
            if (!stoplist.contains(word))
                endQueryBuilder.append(word).append(" ");
        }
        return endQueryBuilder.toString();
    }


    private static Set<String> importStoplist()
    {
        Set<String> words = new HashSet<>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(STOPLIST_FILE));
            String s;
            s = br.readLine();
            while ( s != null)
            {
                words.add(s);
                s = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

}
