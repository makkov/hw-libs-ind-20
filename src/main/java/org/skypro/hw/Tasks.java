package org.skypro.hw;

import java.util.*;

public class Tasks {

    public static void main(String[] args) {

        List<Integer> nums = new ArrayList<>(List.of(1, 1, 2, 3, 4, 4, 5, 5, 6, 7));
//        task1(nums);
//        task2(nums);
        List<String> strings = new ArrayList<>(List.of("hello", "world", "java", "hello", "java", "jvm", "java"));
//        task3(strings);
        task4(strings);
    }

    public static void task1(List<Integer> nums) {
        for (Integer i : nums) {
            if (i % 2 != 0) {
                System.out.println(i);
            }
        }
    }

    public static void task2(List<Integer> nums) {
        Set<Integer> integerSet = new TreeSet<>(nums);
        for (Integer i : integerSet) {
            if (i % 2 == 0) {
                System.out.println(i);
            }
        }
    }

    public static void task3(List<String> strings) {
        System.out.println(new TreeSet<>(strings));
    }

    public static void task4(List<String> strings) {
        Map<String, Integer> countByWord = new HashMap<>();
        for (String str : strings) {
            if (countByWord.containsKey(str)) {
                countByWord.put(str, countByWord.get(str) + 1);
            } else {
                countByWord.put(str, 1);
            }
        }

        for (Map.Entry<String, Integer> entry : countByWord.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println(entry);
            }
        }
    }
}
