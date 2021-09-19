import java.util.Random;
import java.util.concurrent.Callable;

public class MyThread implements Callable <Integer> {
    private final int DELAY_MIN = 2000;
    private final int DELAY_MAX_STEP = 2000;
    private int count = 0;
    private int maxcount = 1;

    public MyThread() {
        Random random = new Random();
        maxcount = random.nextInt(8);
    }

    @Override
    public Integer call() throws Exception {
        Random random = new Random();
        try {
            while (count < maxcount){
                Thread.sleep(DELAY_MIN + random.nextInt(DELAY_MAX_STEP));
                System.out.printf("Сообщает поток %s - работаю.\n", Thread.currentThread().getName());
                count++;
            }
        } catch (InterruptedException e) {

        } finally {
            System.out.printf("Сообщает поток %s - завершился.\n", Thread.currentThread().getName());
        }
        return count;
    }
}
