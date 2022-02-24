import model.Configuration;
import model.Customer;
import model.FixedWindow;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FixedWindowRateLimiter implements RateLimiter {
    private static final Integer MAX_ALLOWED_CREDITS_PER_CUSTOMER = 100;

    private final Map<Customer, FixedWindow> customerFixedWindowMap;
    private final Integer maxAllowedRequests;
    private final Long maxTimeWindow;

    public FixedWindowRateLimiter(Configuration configuration) {
        customerFixedWindowMap = new HashMap<>();
        maxAllowedRequests = configuration.getMaxAllowedRequests();
        maxTimeWindow = TimeUnit.MILLISECONDS.convert(configuration.getDuration(), configuration.getTimeUnit());
    }


    @Override
    public boolean allow(Customer customer) {
        long currentRequestTimestamp = System.currentTimeMillis(); //mock this, use a date provider instead of System.current
        FixedWindow thisCustomerWindow = customerFixedWindowMap.getOrDefault(customer, new FixedWindow(currentRequestTimestamp, maxAllowedRequests));
        System.out.println("For Customer : " + customer.getId() + " Current Available requests = " + String.valueOf(thisCustomerWindow.getAvailableRequests()));
        if (currentRequestTimestamp - thisCustomerWindow.getWindowStart() < maxTimeWindow) {
            if (thisCustomerWindow.getAvailableRequests() > 0) {
                thisCustomerWindow.setAvailableRequests(thisCustomerWindow.getAvailableRequests() - 1);
                customerFixedWindowMap.put(customer, thisCustomerWindow);
                return true;
            } else if(Math.min(thisCustomerWindow.getAvailableCredits() , MAX_ALLOWED_CREDITS_PER_CUSTOMER ) > 0) { //this is always 100
                thisCustomerWindow.consumeCredit();
                customerFixedWindowMap.put(customer, thisCustomerWindow);
                return true;
            }
        } else {
            thisCustomerWindow.resetWindow(maxAllowedRequests , thisCustomerWindow.getWindowStart() + maxTimeWindow);
            customerFixedWindowMap.put(customer, thisCustomerWindow);
            return true;
        }
        return false;
    }
}
