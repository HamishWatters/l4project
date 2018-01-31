package Querying;

import Querying.Query;

import java.util.ArrayList;
import java.util.List;

public class SingleQuery extends Query {
    private Heading queryText;
    private List<Heading> headings;

    SingleQuery(String queryText)
    {
        super();
        this.queryText = new Heading(queryText);
        makeId();
        this.headings = new ArrayList<>();
        headings.add(this.queryText);
    }

    @Override
    public void display()
    {
        System.out.println(queryText.getName());
    }

    @Override
    public List<Heading> getHeadings() { return headings;}

    @Override
    public String getTitle() {
        return queryText.getName();
    }
}
