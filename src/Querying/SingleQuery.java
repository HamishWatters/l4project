package Querying;

import Querying.Query;

import java.util.List;

public class SingleQuery extends Query {
    private String queryText;

    public SingleQuery(String queryText)
    {
        super();
        this.queryText = queryText;
    }

    @Override
    public void display()
    {
        System.out.println(queryText);
    }

    @Override
    public List<String> getHeadings() {
        return null;
    }

    @Override
    public String getTitle() {
        return queryText;
    }

    public String getQueryText(){return this.queryText;}
}
