package org.seckill.web;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {
    public static void main(String[] args) {
       final String str = "abc";
        ExecutorService service = Executors.newFixedThreadPool(3);
        service.execute(new Runnable() {
            public void run() {
                System.out.println("1"+str);
            }
        });
        service.execute(new Runnable() {
            public void run() {
                System.out.println("2"+str);
            }
        });
        service.execute(new Runnable() {
            public void run() {
                System.out.println("3"+str);
            }
        });
    }
}
