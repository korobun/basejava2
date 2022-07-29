package com.alxkor.webapp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 4, 2, 5, 1, 2, 7, 7, 8, 3}));

        List<Integer> integers = Arrays.asList(1,2,3);

        System.out.println(oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        int result = 0;

        List<Integer> numbers = Arrays.stream(values)
                .distinct()
                .boxed()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        for (int i = 0; i < numbers.size(); i++) {
            result += numbers.get(i) * Math.pow(10, i);
        }

        return result;
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        final int sum = integers.stream().mapToInt(Integer::valueOf).sum();
        return integers.stream()
                .filter(e -> (sum % 2 == 0) != (e % 2 == 0))
                .collect(Collectors.toList());
    }
}