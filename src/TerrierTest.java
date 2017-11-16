import org.terrier.matching.ResultSet;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.structures.Index;
import org.terrier.structures.IndexOnDisk;
import org.terrier.structures.MetaIndex;
import org.terrier.utility.ApplicationSetup;

import java.io.IOException;
import java.util.HashMap;

public class TerrierTest {
    public static void main(String[] args) throws IOException {
        RawQuery articleQuery = RawQuery.readQueryFile("/tmp/myQuery.txt");
        Index index = IndexOnDisk.createIndex("index","data");
        MetaIndex meta = index.getMetaIndex();
        ApplicationSetup.setProperty("querying.postfilters.order","org.terrier.querying.SimpleDecorate");
        ApplicationSetup.setProperty("querying.postfilters.controls","decorate:org.terrier.querying.SimpleDecorate");
        Manager queryingManager = new Manager(index);
        HashMap<String, Integer> articleResults = new HashMap<>();
        for (String h: articleQuery.getHeadings())
        {
            SearchRequest srq = queryingManager.newSearchRequestFromQuery(articleQuery.getTitle() + " " + h);
            srq.addMatchingModel("Matching", "BM25");
            srq.setControl("decorate", "on");
            queryingManager.runSearchRequest(srq);
            articleResults.put(h, srq.getResultSet().getDocids()[0]);
        }
        System.out.println(articleQuery.getTitle());
        System.out.println();
        for (String h: articleResults.keySet())
        {
            System.out.println(h);
            System.out.println(meta.getItem("docno",articleResults.get(h)));
            System.out.println();
        }

    }
}
