import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        final ExecutorService thPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        System.out.println("=====================ПЕРВЫЙ ВАРИАНТ=====================");
        first(thPool);

        System.out.println("\n\n=====================ВТОРОЙ ВАРИАНТ=====================");
        second(thPool);

        System.out.println("\n\n=====================ТРЕТИЙ ВАРИАНТ=====================");
        third(thPool);


        thPool.shutdown();
    }

    private static void third(ExecutorService thPool) {
        List <MyThread> taskList = new ArrayList<>();
        taskList.add(new MyThread());
        taskList.add(new MyThread());
        taskList.add(new MyThread());
        taskList.add(new MyThread());

        Integer result = null;

        try {
            result = thPool.invokeAny(taskList);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("\n========Printing the results======");

        System.out.println("Результат: " + result);
    }

    private static void second(ExecutorService thPool) {
        List <MyThread> taskList = new ArrayList<>();
        taskList.add(new MyThread());
        taskList.add(new MyThread());
        taskList.add(new MyThread());
        taskList.add(new MyThread());

        List<Future<Integer>> execList = null;

        try {
            execList = thPool.invokeAll(taskList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n========Printing the results======");

        for (int i = 0; i < execList.size(); i++) {
            Future<Integer> future = execList.get(i);
            try {
                Integer result = future.get();
                System.out.println("Результат " + (i + 1) + ": " + result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private static void first(ExecutorService thPool) {
        Callable<Integer> myCallable1 = new MyThread();
        Callable<Integer> myCallable2 = new MyThread();
        Callable<Integer> myCallable3 = new MyThread();
        Callable<Integer> myCallable4 = new MyThread();

        final Future<Integer> task1 = thPool.submit(myCallable1);
        final Future<Integer> task2 = thPool.submit(myCallable2);
        final Future<Integer> task3 = thPool.submit(myCallable3);
        final Future<Integer> task4 = thPool.submit(myCallable4);

        try {
            while(!task1.isDone() || !task2.isDone() || !task3.isDone() || !task4.isDone())
            {
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {

        }
        System.out.println("\n========Printing the results======");
        try
        {
            System.out.println("Результат первого : " + task1.get());
            System.out.println("Результат второго : " + task2.get());
            System.out.println("Результат третьего : " + task3.get());
            System.out.println("Результат четвертого : " + task4.get());
        }
        catch (Exception ie)
        {
            ie.printStackTrace(System.err);
        }
    }
}
