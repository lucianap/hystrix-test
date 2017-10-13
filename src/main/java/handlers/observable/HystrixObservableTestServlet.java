package handlers.observable;

import model.User;
import model.UserResult;
import rx.Observable;
import rx.Observer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HystrixObservableTestServlet extends HttpServlet {

    private static String errorRate = "0.5";
    public static int userId = 0;

    public static List<UserResult> userSuccessed = new ArrayList<UserResult>();
    public static List<UserResult> usersFailed = new ArrayList<UserResult>();
    public static int failures = 0;

    public void reset(){
        userSuccessed = new ArrayList<UserResult>();
        usersFailed = new ArrayList<UserResult>();
        failures = 0;
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
            IOException {

        userId++;

        if (request.getParameter("errorRate") != null) {
            errorRate = request.getParameter("errorRate");
        }

        if(request.getParameter("summarization") == null) {

            HystrixObservableCommandTest testCommand = new HystrixObservableCommandTest(new User(), errorRate);
            Observable<UserResult> observer = testCommand.observe();

            observer.subscribe(new Observer<UserResult>() {

                public void onCompleted() {
                    System.out.println("THE PROCESS ENDED");
                }

                public void onError(Throwable e) {
                    failures++;
                    System.out.println("Failures: " + failures);
                    e.printStackTrace();
                }

                public void onNext(UserResult success) {
                    System.out.println("User: " + success.u.myId + "  state:" + success.state);
                    if(success.state.equals("success")){
                        userSuccessed.add(success);
                    } else if (success.state.equals("fail")) {
                        usersFailed.add(success);
                    }
                }
            });

        } else {

            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("Summarization:");
            response.getWriter().println("\nUser success: " + userSuccessed.size() + "\n");
            response.getWriter().println("\nFailed count: " + failures + "\n");
            response.getWriter().println("\nUser failed: " + usersFailed.size() + "\n");
            reset();

        }

    }
}
