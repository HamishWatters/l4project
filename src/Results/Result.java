package Results;

import org.terrier.matching.ResultSet;
import org.terrier.structures.MetaIndex;

import java.io.IOException;

public class Result {
    private ResultSet resultSet;
    private Long timestamp;
    private MetaIndex meta;
    private String resultParagraph;
    private int bestResult;                 // For use in ResultHandler while determining best docs for each heading

    public Result(ResultSet resultSet, MetaIndex meta)
    {
        this.resultSet = resultSet;
        this.meta = meta;
        this.timestamp = System.currentTimeMillis();
        this.resultParagraph = "";
        this.bestResult = -1;
    }

    ResultSet getResultSet() { return this.resultSet;}
    boolean hasMatching() { return this.bestResult > -1;}
    void setBestResult(int i) {this.bestResult = i;}
    void setResultParagraph()
    {
        try {
            this.resultParagraph = meta.getItem("body", this.bestResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    String getResultParagraph(int i){return this.resultParagraph;}
    public Long getTimestamp() { return this.timestamp;}

}
