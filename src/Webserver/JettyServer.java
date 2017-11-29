package Webserver;

import Querying.Query;
import Querying.QueryCoordinator;
import Querying.UserQueryConverter;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.IOException;

public class JettyServer {
    private Server webserver;
    private QueryCoordinator coordinator;

    public JettyServer(String bindAddress, int port, String webappRoot) throws IOException
    {
        webserver = new Server();
        ServerConnector connector = new ServerConnector(webserver);
        connector.setPort(port);
        if (bindAddress != null) connector.setHost(bindAddress);
        webserver.setConnectors(new Connector[]{connector});

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/");
        webAppContext.setResourceBase(webappRoot);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{webAppContext});
        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[]{contexts, new DefaultHandler()});
        webserver.setHandler(handlers);

        coordinator = new QueryCoordinator();
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

    public static void main(String[] args) throws Exception
    {
        if (args.length != 2)
            return;
        new JettyServer(null, Integer.parseInt(args[0]), args[1]).start();
    }


}
