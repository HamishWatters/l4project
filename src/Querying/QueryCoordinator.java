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

    public void generateTopicFile(List<ArticleQuery> queries) throws IOException {
        FileWriter fw = new FileWriter("topics");
        for (Query q: queries)
        {
            List<Heading> headingQueue = new ArrayList<>();
            for (Heading h: q.getHeadings())
                h.getAllNestedSubheadings(headingQueue);
            for (Heading h: headingQueue)
            {
                fw.write(convertHeadingToTerrierLanguage(q.getTitle(), h, true) + '\n');
            }
        }
        fw.close();
    }

    public void executeQuery(Query query) {
        String title = filterStopwords(query.getTitle());
        if (query.getHeadings() != null)
        {
            List<Heading> headingQueue = new ArrayList<>();
            for (Heading heading: query.getHeadings())
                heading.getAllNestedSubheadings(headingQueue);
            for (Heading heading : headingQueue)
            {
                String queryString = convertHeadingToTerrierLanguage(title,heading, true);
                SearchRequest srq = queryManager.newSearchRequest(String.valueOf(query.getQueryId()), queryString);
                srq.addMatchingModel("Matching", query.getModel().name());
                queryManager.runSearchRequest(srq);
                heading.setResult(new Result(srq.getResultSet(), meta));
            }
        }
        else
        {
            SearchRequest srq = queryManager.newSearchRequest(String.valueOf(query.getQueryId()), query.getTitle());
            srq.addMatchingModel("Matching", query.getModel().name());
            queryManager.runSearchRequest(srq);
            //TODO: SingleQuery needs to retain results somehow now that results are stored under headings
            //TODO: Maybe worth just having a single query use one heading object
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
    private String convertHeadingToTerrierLanguage(String title, Heading h, boolean weighted)
    {
        Heading index = h;
        int count = 1;
        while ((index = index.getParent()) != null) count++;
        StringBuilder terrierQuery = new StringBuilder();
        for (String s: title.split(" ")) {
        terrierQuery.append(s);
        if (weighted)
            terrierQuery.append("^1.25");
        terrierQuery.append(" ");
        }
        for (String s: h.getHeading().split(" "))
        {
            terrierQuery.append(s);
            if (weighted)
                terrierQuery.append("^1.55");
            terrierQuery.append(" ");
        }
        float n = 1;
        while (h.hasParent())
        {
            h = h.getParent();
            terrierQuery.append(h.getHeading());
            if (weighted)
                terrierQuery.append("^").append(1-(n/10));
            terrierQuery.append(" ");
        }
        System.out.println(terrierQuery.toString());
        return terrierQuery.toString();
    }

    private String filterStopwords(String startQuery)
    {
        StringBuilder endQueryBuilder = new StringBuilder("");
        for (String word: startQuery.toLowerCase().split(" "))
        {
            if (!stoplist.contains(word))
                endQueryBuilder.append(word).append(" ");
        }
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

}
