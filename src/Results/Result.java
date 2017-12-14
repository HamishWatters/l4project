package Results;

import org.terrier.matching.ResultSet;
import org.terrier.structures.MetaIndex;

import java.io.IOException;

public class Result {
    private ResultSet resultSet;
    private Long timestamp;
    private MetaIndex meta;
    private String resultParagraph;

    public Result(ResultSet resultSet, MetaIndex meta)
    {
        this.resultSet = resultSet;
        this.meta = meta;
        this.timestamp = System.currentTimeMillis();
        this.resultParagraph = "";
    }

    public ResultSet getResultSet() { return this.resultSet;}
    public void setResultParagraph(int i)
    {
        try {
            this.resultParagraph = meta.getItem("body", i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getResultParagraph(int i)
    {
        return this.resultParagraph;
    }
    public Long getTimestamp() { return this.timestamp;}

}
