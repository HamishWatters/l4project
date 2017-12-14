package Querying;

import Results.Result;
import java.util.ArrayList;
import java.util.List;

public class Heading {
    String heading;
    Heading parent;
    List<Heading> subheadings;
    Result result;

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

    public String getHeading()
    {
        return this.heading;
    }

    public boolean hasSubheadings() { return !this.subheadings.isEmpty();}

    public Heading getParent() { return this.parent; }

    public boolean hasParent() { return this.parent != null;}

    public Result getResult() { return this.result; }

    public void setParent(Heading parent) { this.parent = parent; }

    public void setResult(Result result) { this.result = result; }

    public void display()
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
