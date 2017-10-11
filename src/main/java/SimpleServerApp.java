import handlers.HystrixServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class SimpleServerApp {

    public static void main( String[] args ) throws Exception
    {
        Server server = new Server(8081);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(handler);
        handler.addServlet(HystrixServlet.class, "/hystrix");
        server.start();
        server.dumpStdErr();
        server.join();
    }
}
