package handlers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HystrixServlet extends HttpServlet {

    private static String errorRate = "0.5";

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
            IOException {

        HystrixTestCommand test = new HystrixTestCommand(errorRate);
        test.execute();

    }
}