package Querying;

import Results.Result;
import java.util.ArrayList;
import java.util.List;

public class Heading {
    private String heading;
    private Heading parent;
    private List<Heading> subheadings;
    private Result result;

    public Heading(String heading)
    {
        this.heading = heading;
        this.parent = null;
        this.subheadings = new ArrayList<>();
        this.result = null;
    }

    public void addSubheading(Heading subheading)
    {
        this.subheadings.add(subheading);
        subheading.setParent(this);
    }

    /**
     * Adds all of this headings subheadings recursively into the provided headings list
     * @param headings a list for all of this headings subheadings to be added to
     */
    public void getAllNestedSubheadings(List<Heading> headings)
    {
        headings.add(this);
        for (Heading h: this.subheadings)
            h.getAllNestedSubheadings(headings);
    }

    public List<Heading> getSubheadings()
    {
        return this.subheadings;
    }

    public String getName()
    {
        return this.heading;
    }

    public boolean hasSubheadings() { return !this.subheadings.isEmpty();}

    Heading getParent() { return this.parent; }

    boolean hasParent() { return this.parent != null;}

    public Result getResult() { return this.result; }

    void setParent(Heading parent) { this.parent = parent; }

    void setResult(Result result) { this.result = result; }

    void display()
    {
        System.out.print(this.heading);
        if (this.hasSubheadings()) {
            System.out.print(" [ ");
            for (Heading h : this.subheadings) {
                h.display();
                System.out.print(" ");
            }
            System.out.print("]");
        }
        System.out.print(" ");
    }


}
