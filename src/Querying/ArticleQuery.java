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
        System.out.print(title + " [");
        for (Heading heading: headings)
        {
            heading.display();
        }
        System.out.println("]");
    }

    public String getTitle()
    {
        return this.title;
    }

    public List<Heading> getHeadings()
    {
        return this.headings;
    }

    public void addHeading(Heading h)
    {
        this.headings.add(h);
    }

    public Heading getHeading(int headingNumber) {
        if (headingNumber >= headings.size())
            return null;
        return headings.get(headingNumber);
    }
}
