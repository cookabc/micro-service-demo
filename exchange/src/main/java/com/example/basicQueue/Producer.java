package com.example.basicQueue;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Producer
 *
 * @author: Xugang Song
 * @create: 2020/11/3
 */
public class Producer {
    @SuppressWarnings({"InfiniteLoopStatement", "BusyWait"})
    public static void main(String[] args) {
        try {
            BasicQueue queue = new BasicQueue("./", "task");
            int i = 0;
            Random rnd = new Random();
            while (true) {
                String msg = "task " + (i++);
                queue.enqueue(msg.getBytes(StandardCharsets.UTF_8));
                System.out.println("produce: " + msg);
                Thread.sleep(rnd.nextInt(1000));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
