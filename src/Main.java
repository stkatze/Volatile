package org.example;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static AtomicInteger length3 = new AtomicInteger(0);
    private static AtomicInteger length4 = new AtomicInteger(0);
    private static AtomicInteger length5 = new AtomicInteger(0);


    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_00];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        List<Thread> list = new ArrayList<>();

        list.add(new Thread(() -> {
            for (String text : texts) {
                StringBuffer buffer = new StringBuffer(text);
                String result = buffer.reverse().toString();
                if (text.equals(result)) {
                    counter(text);
                }
            }
        }));


        list.add(new Thread(() -> {
                    for (String text : texts) {
                        boolean result = true;
                        for (int i = 0; i < text.length() - 1; i++) {
                            if (text.charAt(i) != text.charAt(i + 1)) {
                                result = false;
                            }
                        }

                        if (result) {
                            counter(text);
                        }
                    }
                })
        );

        list.add(new Thread(() -> {
            for (String text : texts) {
                boolean result = true;
                for (int i = 0; i < text.length() - 1; i++) {
                    if (text.charAt(i) > text.charAt(i + 1)) {
                        result = false;
                    }
                }
                if (result) {
                    counter(text);
                }
            }
        }));

        for (Thread thread : list) {
            thread.start();
        }

        for (Thread thread : list) {
            thread.join();
        }


        System.out.println(
                "Красивых слов с длиной 3: " + length3 + "шт.\n" +
                        "Красивых слов с длиной 4: " + length4 + "шт.\n" +
                        "Красивых слов с длиной 5: " + length5 + "шт."
        );

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void counter(String text) {
        switch (text.length()) {
            case 3:
                length3.addAndGet(1);
                break;
            case 4:
                length4.addAndGet(1);
                break;
            case 5:
                length5.addAndGet(1);
                break;
        }
    }
}