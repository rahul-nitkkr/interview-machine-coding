import model.Customer;

public interface RateLimiter {

    public boolean allow(Customer customer);
}
