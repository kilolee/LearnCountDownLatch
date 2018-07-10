import java.util.concurrent.CountDownLatch;

/**
 * Created by kilo on 2018/7/10.
 */
public abstract class BaseHealthChecker implements Runnable {

    private CountDownLatch latch;
    private String serviceName;
    private boolean serviceUp;


    public BaseHealthChecker(String serviceName, CountDownLatch latch) {
        this.latch = latch;
        this.serviceName = serviceName;
        this.serviceUp = false;
    }

    @Override
    public void run() {
        try {
            verifyService();
            serviceUp = true;
        } catch (Throwable t) {
            t.printStackTrace();
            serviceUp = false;
        } finally {
            if (latch != null) {
                latch.countDown();
            }
        }

    }

    public String getServiceName() {
        return serviceName;
    }

    public boolean isServiceUp() {
        return serviceUp;
    }

    public abstract void verifyService();
}
