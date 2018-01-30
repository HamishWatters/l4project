package Webserver;

import Querying.Query;
import Querying.QueryCoordinator;
import Querying.UserQueryConverter;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.servlet.ServletHandler;

import java.io.IOException;

public class JettyServer {
    private Server webserver;
    private static QueryCoordinator coordinator;

    public JettyServer(String bindAddress, int port) throws IOException
    {

        org.eclipse.jetty.util.log.Log.setLog(null);
        webserver = new Server();
        ServerConnector connector = new ServerConnector(webserver);
        connector.setPort(port);
        if (bindAddress != null) connector.setHost(bindAddress);
        webserver.setConnectors(new Connector[]{connector});

        ContextHandler context = new ContextHandler();
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("web/");
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(QueryServlet.class, "/query");

        context.setContextPath("/");
        context.setHandler(resourceHandler);
        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[] {context, servletHandler});

        coordinator = new QueryCoordinator();

        webserver.setHandler(handlers);
    }

    public void executeQuery(String queryJson)
    {
        Query query = UserQueryConverter.generateQuery(queryJson);
        coordinator.executeQuery(query);
        /**
         * Get results to client, somehow
         */
    }

    public void start() throws Exception
    {
        webserver.start();
    }
    public void stop() throws Exception
    {
        webserver.stop();
    }

    public static QueryCoordinator getCoordinator()
    {
        return coordinator;
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length != 1)
        {
            System.out.println("Usage: JettyServer <port>");
            System.exit(1);
        }
        new JettyServer(null, Integer.parseInt(args[0])).start();
    }


}
