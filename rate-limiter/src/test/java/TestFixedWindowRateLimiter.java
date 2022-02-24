import model.Configuration;
import model.Customer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TestFixedWindowRateLimiter {

    @Test
    public void testFixedWindow_SingleCustomer() throws InterruptedException {
        RateLimiter rl = new FixedWindowRateLimiter(new Configuration(2 ,1 , TimeUnit.SECONDS));
        Customer c = new Customer("cust");

        System.out.println(rl.allow(c));
        System.out.println(rl.allow(c));
        CountDownLatch waiter = new CountDownLatch(1);
        waiter.await(100 , TimeUnit.MILLISECONDS);
        System.out.println(rl.allow(c));
        System.out.println(rl.allow(c));
        waiter.await(600 , TimeUnit.MILLISECONDS);
        System.out.println(rl.allow(c));
        System.out.println(rl.allow(c));
        waiter.await(600 , TimeUnit.MILLISECONDS);
        System.out.println(rl.allow(c));
        System.out.println(rl.allow(c));
        waiter.await(600 , TimeUnit.MILLISECONDS);
        System.out.println(rl.allow(c));
        System.out.println(rl.allow(c));
    }
}
