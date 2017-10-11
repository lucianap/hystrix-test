package handlers;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import model.User;
import model.UserResult;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class HystrixObservableCommandTest extends HystrixObservableCommand<UserResult> {

    User u;
    String errorRate;

    private String url = "http://localhost:9090/faulty";
    private String RESULT_SUCCESS = "success";
    private String RESULT_FAIL = "fail";

    protected HystrixObservableCommandTest(User u, String errorRate) {
        super(HystrixCommandGroupKey.Factory.asKey("UserGroup"));
        this.u = u;
        this.errorRate = errorRate;
    }

    @Override
    protected Observable<UserResult> construct() {
        return Observable.create(new Observable.OnSubscribe<UserResult>() {
            public void call(Subscriber<? super UserResult> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        //Call for external server with an errorRate of X
                        PostSender ps = new PostSender();
                        ps.sendGet(url + "?errorRate=" + errorRate);

                        observer.onNext(new UserResult(u, RESULT_SUCCESS));
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        } ).subscribeOn(Schedulers.io());
    }

    @Override
    protected Observable<UserResult> resumeWithFallback() {
        return Observable.just(new UserResult(u, RESULT_FAIL));
    }
}
