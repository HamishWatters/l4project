package Querying;

import Results.Result;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.structures.Index;
import org.terrier.structures.MetaIndex;

import java.io.*;
import java.util.*;

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

    public void generateTopicFile(List<Query> queries) throws IOException {
        FileWriter fw = new FileWriter("topics");
        for (Query q: queries)
        {
            System.out.println(q.getId());
            List<Heading> headingQueue = new ArrayList<>();
            for (Heading h: q.getHeadings())
                h.getAllNestedSubheadings(headingQueue);
            for (Heading h: headingQueue)
            {
                if (q instanceof ArticleQuery && h.getId() != null) fw.write(h.getId() + " " + convertHeadingToTerrierLanguage(q.getTitle(), h) + '\n');
                //else fw.write(q.getId() + " " + q.getTitle() + '\n');
            }
        }
        fw.close();
    }

    public void executeQuery(Query query)
    {
        String title = filterStopwords(query.getTitle());
        List<Heading> headingQueue = new ArrayList<>();
        for (Heading heading: query.getHeadings())
            heading.getAllNestedSubheadings(headingQueue);
        for (Heading heading : headingQueue)
        {
            String queryString = convertHeadingToTerrierLanguage(title,heading);
            SearchRequest srq = queryManager.newSearchRequest(query.getId(), queryString);
            srq.addMatchingModel("Matching", query.getModel().name());
            queryManager.runSearchRequest(srq);
            heading.setResult(new Result(srq.getResultSet(), meta));
        }
    }

    /**
     * @param title : title of the query
     * @param h : query heading to be converted into query
     * @return : a string representation of the query for that heading
     * Produces a terrier query from a heading by adding a title, each nested subheading leading to
     * the specified heading, and gives decreasing weights the further nested the heading has eg. the
     * query title will have weight multiplied by how many parent headings the specified heading has
     */
    private String convertHeadingToTerrierLanguage(String title, Heading h)
    {
        Heading index = h;
        Set<String> usedTerms = new HashSet<>();
        int count = 1;
        while ((index = index.getParent()) != null) count++;
        StringBuilder terrierQuery = new StringBuilder();
        String[] tSplit = filterStopwords(removePunctuation(title)).split(" ");
        for (String s: tSplit)
        {
            if (!usedTerms.contains(s))
            {
                usedTerms.add(s);
                terrierQuery.append(s);
                terrierQuery.append("^1.25 ");
            }
        }
        if (tSplit.length > 2)
        {
            for (String s: tSplit)
                terrierQuery.append(s.substring(0,1));
            terrierQuery.append(" ");
        }
        String[] hSplit = filterStopwords(removePunctuation(h.getName())).split(" ");
        for (String s: hSplit)
        {
            if (!usedTerms.contains(s))
            {
                usedTerms.add(s);
                terrierQuery.append(s);
                terrierQuery.append("^1.55 ");
            }
        }
        if (hSplit.length > 2)
        {

            for (String s: hSplit) {
                if (s.length() > 0)
                    terrierQuery.append(s.substring(0, 1));
            }
            terrierQuery.append(" ");
        }
        float n = 1;
        while (h.hasParent())
        {
            h = h.getParent();
            terrierQuery.append(filterStopwords(removePunctuation(h.getName())));
            terrierQuery.append("^").append(1-(n/10));
            terrierQuery.append(" ");
        }
        return terrierQuery.toString().toLowerCase();
    }

    private String removePunctuation(String str)
    {
        return str.replaceAll("[:.]","").replace("-"," ");
    }

    private String filterStopwords(String startQuery)
    {
        StringBuilder endQueryBuilder = new StringBuilder("");
        for (String word: startQuery.toLowerCase().split(" "))
        {
            if (!stoplist.contains(word))
                endQueryBuilder.append(word).append(" ");
        }
        if (endQueryBuilder.length() > 0)
            endQueryBuilder.setLength(endQueryBuilder.length()-1);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }
}
