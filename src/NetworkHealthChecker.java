import java.util.concurrent.CountDownLatch;

/**
 * Created by kilo on 2018/7/10.
 */
public class NetworkHealthChecker extends BaseHealthChecker {


    public NetworkHealthChecker(String serviceName, CountDownLatch latch) {
        super(serviceName, latch);
    }

    @Override
    public void verifyService() {
        System.out.println("Checking "+this.getServiceName());
        try {
            Thread.sleep(7000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(this.getServiceName()+" is up");
    }
}
