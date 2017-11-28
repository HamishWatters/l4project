package Querying;

import java.util.List;

public class ArticleQuery extends Query {

    private String title;
    private List<String> headings;

    public ArticleQuery(String title, List<String> headings) {
        super();
        this.title = title;
        this.headings = headings;
    }

    public void display()
    {
        System.out.println(title);
        System.out.println();
        for (String heading: headings)
        {
            System.out.println(heading);
        }
    }

    @Override
    public String getTitle()
    {
        return this.title;
    }

    @Override
    public List<String> getHeadings()
    {
        return this.headings;
    }

    public String getHeading(int headingNumber) {
        if (headingNumber >= headings.size())
            return "";
        return headings.get(headingNumber);
    }
}
