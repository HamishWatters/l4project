package Results;

import Querying.Heading;
import Querying.Query;

public class ResultRenderer {
    public static String generateHtml(Query q)
    {
        StringBuilder html = new StringBuilder ("<h3>");
        html.append(q.getTitle()).append("</h3>\n");
        for (Heading h: q.getHeadings())
            renderRecurse(h, html);
        return html.toString();
    }

    private static void renderRecurse(Heading h, StringBuilder sb)
    {
        sb.append("<h5>").append(h.getName()).append("</h5>\n");
        sb.append("<p>").append(h.getResult().getResultParagraph(0)).append("</p>\n");
        for (Heading sh: h.getSubheadings())
            renderRecurse(sh,sb);
    }
}
