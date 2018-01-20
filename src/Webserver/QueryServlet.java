package Webserver;

import Querying.Query;
import Querying.UserQueryConverter;
import Results.ResultHandler;
import Results.ResultRenderer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        Query query = UserQueryConverter.generateWebQuery(request.getQueryString());
        JettyServer.getCoordinator().executeQuery(query);
        ResultHandler handler = new ResultHandler(query);
        handler.getBestDocumentNoDuplicates();
        handler.printResults();
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(ResultRenderer.generateHtml(query));
    }

}
