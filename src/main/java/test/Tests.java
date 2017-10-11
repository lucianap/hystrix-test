package test;

import handlers.PostSender;
import org.junit.Test;

public class Tests {

    private final String ERROR_RATE = "0.1";

    @Test
    public void testSendingRequests() throws Exception {
        PostSender ps = new PostSender();
        for (int i = 0; i < 1000; i++) {
            ps.sendGet("http://localhost:9091/hystrix?errorRate=" + ERROR_RATE);
        }

        ps.sendGet("http://localhost:9091/hystrix?summarization=true");
    }

}
