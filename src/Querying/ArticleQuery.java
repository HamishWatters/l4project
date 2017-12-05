package Querying;

import java.util.List;

public class ArticleQuery extends Query {

    private String title;
    private List<Heading> headings;

    public ArticleQuery(String title, List<Heading> headings) {
        super();
        this.title = title;
        this.headings = headings;
    }

    public void display()
    {
        System.out.println(title);
        System.out.println();
        for (Heading heading: headings)
        {
            System.out.println(heading.toString());
        }
    }

    public String getTitle()
    {
        return this.title;
    }

    public List<Heading> getHeadings()
    {
        return this.headings;
    }

    public Heading getHeading(int headingNumber) {
        if (headingNumber >= headings.size())
            return null;
        return headings.get(headingNumber);
    }
}
