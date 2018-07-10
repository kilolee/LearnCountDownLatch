import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by kilo on 2018/7/10.
 */
public class ApplicationStartupUtil {

    private static List<BaseHealthChecker> services;
    private static CountDownLatch latch;

    private ApplicationStartupUtil() {
    }

    private final static ApplicationStartupUtil INSTANCE = new ApplicationStartupUtil();

    public static ApplicationStartupUtil getInstance(){
        return INSTANCE;
    }

    public static boolean checkExternalServices() throws InterruptedException {
        //Initialize the latch with number of service checkers
        latch = new CountDownLatch(3);

        //All add checker in lists
        services = new ArrayList<BaseHealthChecker>();
        services.add(new NetworkHealthChecker("Network Service",latch));
        services.add(new DatabaseHealthChecker("DataBase Service",latch));
        services.add(new CacheHealthChecker("Cache Service",latch));

        //Start service checkers using executor framework
        Executor executor = Executors.newFixedThreadPool(services.size());

        for (final BaseHealthChecker v : services){
            executor.execute(v);
        }

        latch.await();
        for (final BaseHealthChecker v : services){
            if (!v.isServiceUp()){
                return false;
            }
        }
        return true;
    }
}
