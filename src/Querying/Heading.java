package Querying;

import java.util.ArrayList;
import java.util.List;

public class Heading {
    String heading;
    List<Heading> subheadings;

    public Heading(String heading)
    {
        this.heading = heading;
        this.subheadings = new ArrayList<>();
    }

    public void addSubheading(Heading subheading)
    {
        this.subheadings.add(subheading);
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
