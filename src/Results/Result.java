package Results;

import org.terrier.matching.ResultSet;

public class Result {
    private String resultParagraph;
    private ResultSet resultSet;
    private Long timestamp;

    public Result(String resultParagraph, ResultSet resultSet)
    {
        this.resultParagraph = resultParagraph;
        this.resultSet = resultSet;
        this.timestamp = System.currentTimeMillis();
    }

    public ResultSet getResultSet() { return this.resultSet;}
    public String getResultParagraph() { return this.resultParagraph;}
    public Long getTimestamp() { return this.timestamp;}

}
