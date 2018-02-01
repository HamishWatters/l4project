package Results;

import Querying.Heading;
import Querying.Query;

import java.io.FileWriter;
import java.io.IOException;

public class ResultRenderer {

    private static final String BOOTSTRAP_CDN = "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css\" integrity=\"sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy\" crossorigin=\"anonymous\">";
    public static String generateHtml(Query q)
    {
        StringBuilder html = new StringBuilder ("<html><head><title>Results</title><link rel=\"stylesheet\" type=\"text/css\" href=\"../static/results-style.css\">");
        html.append(BOOTSTRAP_CDN);
        html.append("</head><body><h3>");
        html.append(q.getTitle()).append("</h3>\n");
        for (Heading h: q.getHeadings())
            renderRecurse(h, html);
        html.append("</body>");
        return html.toString();
    }

    public static void writeHtml(Query q)
    {
        try {
            FileWriter fw = new FileWriter("web/results/" + q.getId() + ".html");
            fw.write(generateHtml(q));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void renderRecurse(Heading h, StringBuilder sb)
    {
        sb.append("<h5>").append(h.getName()).append("</h5>\n");
        sb.append("<p>").append(h.getResult().getResultParagraph(0)).append("</p>\n");
        for (Heading sh: h.getSubheadings())
            renderRecurse(sh,sb);
    }
}
