package geektime.task4;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Week4Day3 {
  public static void main(String[] args) throws Exception {
    // 方法1 使用静态变量
    staticFieldFuc1();
    // 方法2 使用对象
    objectFuc2();
    // 方法3 Callable
    callableFunc3();
    Thread.sleep(1000);
    // 方法4 Future+callable
    futureFunc4();
    Thread.sleep(3000);
    // 方法5 FutureTask+callable
    futureTaskFunc5();
    Thread.sleep(1000);
    // 方法6 CompletableFuture
    completableFutureFunc6();
    Thread.sleep(1000);
    // 方法7 CompletionService
    completionFunc7();
  }

  /**
   * 方法1 使用静态变量
   * */
  private static int staticNum = 0;
  public static void staticFieldFuc1() throws Exception{
    new Thread(() -> {
      for (int i=0; i<10; i++) {
        staticNum += i;
      }
    }).start();

    Thread.sleep(200);
    System.out.println("func1 result = " + staticNum);
  }

  /**
   * 方法2 使用对象
   * */
  public static void objectFuc2() throws Exception{
    People people = new People("test", 10);
    PeoThread peoThread = new PeoThread(people);
    peoThread.start();

    Thread.sleep(200);
    System.out.println("func2 result = " + people.getAge());
  }

  /**
   * 方法3 Callable
   * */
  public static void callableFunc3() throws Exception{

    Task1 task1 = new Task1();
    int result = task1.call();
    System.out.println("func3 result = " + result);
  }

  /**
   * 方法4 Future+callable
   * */
  static ExecutorService executorService = Executors.newCachedThreadPool();
  public static void futureFunc4() throws Exception{
    Task1 task1 = new Task1();
    Future<Integer> future = executorService.submit(task1);
    executorService.shutdown();
    Thread.sleep(1000);
    int result = future.get();
    System.out.println("func4 result = " + result);
  }

  /**
   * 方法5 FutureTask+callable
   * */
  public static void futureTaskFunc5() throws Exception{
    Task1 task1 = new Task1();
    FutureTask<Integer> futureTask = new FutureTask<>(task1);
    executorService = Executors.newCachedThreadPool();
    executorService.submit(futureTask);
    executorService.shutdown();
    Thread.sleep(1000);
    int result = futureTask.get();
    System.out.println("func5 result = " + result);
  }

  /**
   * 方法6 CompletableFuture
   * */
  public static void completableFutureFunc6() throws Exception{
    CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
      int sum = 0;
      for (int i=0; i<100; i++) {
        sum += i;
      }
      return sum;
    });
    int result = completableFuture.get();
    System.out.println("func6 result = " + result);
  }

  /**
   * 方法7 CompletionService
   * */
  public static void completionFunc7() throws Exception{
    executorService = Executors.newFixedThreadPool(1);
    try {
      CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);
      Task1 task1 = new Task1();
      completionService.submit(task1);
      int result = completionService.take().get();
      System.out.println("func7 result = " + result);
    } finally {
      executorService.shutdown();
    }
  }
}

class Task1 implements Callable<Integer> {
  @Override
  public Integer call() throws Exception {
    int sum = 0;
    for (int i=0; i<100; i++) {
      sum += i;
    }
    return sum;
  }
}

class PeoThread extends Thread{
  private People people;
  PeoThread(People people) {
    this.people = people;
  }

  @Override
  public void run() {
    people.setAge(1000);
  }
}

class People {
  private String name;
  private int age;

  People(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        ", age=" + age +
        '}';
  }
}
