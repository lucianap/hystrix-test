package handlers;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URLConnection;

public class HystrixTestCommand extends HystrixCommand<String> {

    private final String errorRate;

    public HystrixTestCommand(String errorRate) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.errorRate = errorRate;
    }

    @Override
    protected String run() throws Exception {
        PostSender ps = new PostSender();
        ps.sendGet("http://localhost:8080/test?errorRate="+errorRate);
        return "I'm fine";
    }
}
