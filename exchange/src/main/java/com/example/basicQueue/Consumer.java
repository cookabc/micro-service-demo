package com.example.basicQueue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Consumer
 *
 * @author: Xugang Song
 * @create: 2020/11/3
 */
public class Consumer {
    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement"})
    public static void main(String[] args) throws InterruptedException {
        try {
            BasicQueue queue = new BasicQueue("./", "task");
            Random rnd = new Random();
            while (true) {
                byte[] bytes = queue.dequeue();
                if (bytes == null) {
                    Thread.sleep(rnd.nextInt(1000));
                    continue;
                }
                System.out.println("consume: " + new String(bytes, StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
