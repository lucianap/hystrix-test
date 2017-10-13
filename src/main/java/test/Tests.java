package test;

import handlers.PostSender;
import handlers.blocking.HystrixTestCommand;
import handlers.observable.HystrixObservableCommandTest;
import model.User;
import model.UserResult;
import org.junit.Test;
import rx.Observable;
import rx.Observer;

import java.util.ArrayList;
import java.util.List;

public class Tests {

    private final String ERROR_RATE = "0.5";

    private List<UserResult> usersProcessed = new ArrayList<UserResult>();

    @Test
    public void testSendingRequestsObservable() throws Exception {


        Observer<UserResult> resultsObserver = new Observer<UserResult>() {

            public void onCompleted() { }

            public void onError(Throwable throwable) { }

            public void onNext(UserResult userResult) { usersProcessed.add(userResult); }

        };

        PostSender ps = new PostSender();
        for (int i = 0; i < 1000; i++) {
            HystrixObservableCommandTest observableCommandTest = new HystrixObservableCommandTest(new User(), ERROR_RATE);
            Observable<UserResult> observer = observableCommandTest.observe();
            observer.subscribe(resultsObserver);
        }

        Thread.sleep(1000L);

        for(UserResult userProcessed : usersProcessed){
            System.out.println("User: " + userProcessed.u.myId + " State: " + userProcessed.state);
        }

        ps.sendGet("http://localhost:9091/hystrix?summarization=true");

    }


    @Test
    public void testSendingRequestsBlocking() throws Exception {
        PostSender ps = new PostSender();
        for (int i = 0; i < 1000; i++) {
            HystrixTestCommand observableCommandTest = new HystrixTestCommand(ERROR_RATE, new User().myId);
            observableCommandTest.execute();
        }

        Thread.sleep(1000L);

        for(UserResult userProcessed : usersProcessed){
            System.out.println("User: " + userProcessed.u.myId + " State: " + userProcessed.state);
        }

        ps.sendGet("http://localhost:9091/hystrix?summarization=true");

    }

}
