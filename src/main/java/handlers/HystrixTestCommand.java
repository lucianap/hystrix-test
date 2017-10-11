package handlers;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URLConnection;

public class HystrixTestCommand extends HystrixCommand<String> {

    private final String errorRate;
    private int userId;

    private String url = "http://localhost:9090/faulty";

    public HystrixTestCommand(String errorRate, int userId) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.errorRate = errorRate;
        this.userId = userId;
    }

    @Override
    protected String run() throws Exception {
        PostSender ps = new PostSender();
        ps.sendGet(url+"?errorRate="+errorRate);
        return "I'm fine";
    }

}
