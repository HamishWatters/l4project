package Querying;

import Results.Result;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.structures.Index;
import org.terrier.structures.MetaIndex;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class QueryCoordinator {
    private Index index;
    private MetaIndex meta;
    private Manager queryManager;
    private Set<String> stopList;

    public QueryCoordinator()
    {
        this.index = Index.createIndex("index","data");
        this.meta = index.getMetaIndex();
        this.queryManager = new Manager(index);
        this.stopList = importStoplist();
    }


    public void executeQuery(Query query)
    {
        StringBuilder titleBuilder = new StringBuilder("");
        for (String s: query.getTitle().split(" ")) {
            if (!stopList.contains(s.toLowerCase()))
                titleBuilder.append(s).append(" ");
        }
        String title = titleBuilder.toString();
        if (query.getHeadings() != null)
        {
            for (String heading : query.getHeadings())
            {
                StringBuilder headingNoStoplist = new StringBuilder("");
                for (String s : heading.split(" "))
                    if (!stopList.contains(s.toLowerCase()))
                        headingNoStoplist.append(s).append(" ");
                SearchRequest srq = queryManager.newSearchRequest(String.valueOf(query.getQueryId()), title + headingNoStoplist.toString());
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


    private static Set<String> importStoplist()
    {
        Set<String> words = new HashSet<>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("stoplist.txt"));
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
