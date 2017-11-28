import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import java.io.File;
import java.net.URI;


public class PassageRetrievalServer
{
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);


        URI webRootUri = new File("web/PassageRetrievalLayout.html").toURI();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setBaseResource(Resource.newResource(webRootUri));
//        ServletHolder holderPwd = new ServletHolder("default",DefaultServlet.class);
//        holderPwd.setInitParameter("dirAllowed", "true");
//        context.addServlet(holderPwd,"/");
//        server.start();
//        server.join();
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(UserQueryConverterServlet.class, "/*");
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {context, handler, new DefaultHandler()});
        server.setHandler(handlers);
        server.start();
        server.join();



//        ResourceHandler handler = new ResourceHandler();
//        handler.setBaseResource(Resource.newClassPathResource("."));
//
//
//
//        HandlerList handlers = new HandlerList();
//        handlers.setHandlers(new Handler[] {handler, new DefaultHandler() });
//        server.setHandler(handlers);
//        server.start();
//        server.join();
    }
}
