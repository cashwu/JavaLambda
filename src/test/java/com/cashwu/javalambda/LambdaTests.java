package com.cashwu.javalambda;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

/**
 * @author cash.wu
 * @since 2024/05/31
 */
public class LambdaTests {

    @Test
    public void supplier_consumer() {

        Supplier<String> supplier = () -> {
            System.out.println("i am a supplier");
            return "Hello World";
        };
        System.out.println("supplier : " + supplier.get());

        Consumer<String> consumer = (String s) -> {
            System.out.println("i am a consumer");
            System.out.println("consumer : " + s);
        };
        consumer.accept("Hello World");
    }

    @Test
    public void predicate() {

        List<String> strings = new ArrayList<>(List.of("one", "two", "three", "four", "five"));

        //        Predicate<String> filter = s -> !s.startsWith("t");
        //        strings.removeIf(filter);

        strings.removeIf(s -> !s.startsWith("t"));

        //        Consumer<String> action = s -> System.out.println(s);
        //        strings.forEach(action);

        strings.forEach(s -> System.out.println(s));

    }

    @Test
    public void function() {

        User sarah = new User("Sarah");
        User james = new User("james");
        User mary = new User("mary");
        User john = new User("john");

        List<User> users = List.of(sarah, james, mary, john);

        List<String> names = new ArrayList<>();

        Function<User, String> toName = (User user) -> user.name();

        for (User user : users) {
            String name = toName.apply(user);
            names.add(name);
        }

        users.forEach(u -> System.out.println(u));
        names.forEach(a -> System.out.println(a));
    }

    @Test
    public void naming_supplier_function() {

        IntSupplier supplier = () -> 10;
        int i = supplier.getAsInt();
        System.out.println("i = " + i);

        DoubleToIntFunction function = value -> (int) Math.floor(value);
        int pi = function.applyAsInt(Math.PI);
        System.out.println("pi = " + pi);
    }

    @Test
    public void consumer_with_andThen() {

        Consumer<String> c1 = s -> System.out.println("c1 consumer : " + s);
        Consumer<String> c2 = s -> System.out.println("c2 consumer : " + s);

        //        Consumer<String> c3 = s -> {
        //            c1.accept(s);
        //            c2.accept(s);
        //        };

        Consumer<String> c3 = c1.andThen(c2);
        c3.accept("Hello World");
    }

    @Test
    public void predicate_negate_and() {

        Predicate<String> isNull = s -> s == null;
        System.out.println("For null = " + isNull.test(null));
        System.out.println("For Hello = " + isNull.test("Hello"));

        Predicate<String> isEmpty = s -> s.isEmpty();
        System.out.println("For empty = " + isEmpty.test(""));
        System.out.println("For Hello = " + isEmpty.test("Hello"));

        Predicate<String> p = isNull.negate().and(isEmpty.negate());
        System.out.println("For null = " + p.test(null));
        System.out.println("For empty = " + p.test(""));
        System.out.println("For Hello = " + p.test("Hello"));

    }

    @Test
    public void comparator() {

        List<String> strings = new ArrayList<>(
                List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine"));

        Comparator<String> cmp = (s1, s2) -> s1.compareTo(s2);

        strings.sort(cmp);
        System.out.println(strings);

        //        Function<String, Integer> toLength = s -> s.length();
        ToIntFunction<String> toLength = s -> s.length();

        //        Comparator<String> cmp1 = (s1, s2) -> Integer.compare(s1.length(), s2.length());
        Comparator<String> cmp1 = Comparator.comparingInt(toLength);

        strings.sort(cmp1);
        System.out.println(strings);
    }

    @Test
    public void comparator_key() {

        User sarah = new User("sarah", 28);
        User james = new User("james", 35);
        User mary = new User("mary", 33);
        User john1 = new User("john", 24);
        User john2 = new User("john", 25);

        List<User> users = Arrays.asList(sarah, james, mary, john1, john2);
        Comparator<User> cmpName = Comparator.comparing(user -> user.name());
        Comparator<User> cmpAge = Comparator.comparing(user -> user.age());
        Comparator<User> comparator = cmpName.thenComparing(cmpAge);
        Comparator<User> reversed = comparator.reversed();

        users.sort(reversed);
        users.forEach(System.out::println);

    }
}


record User(String name, int age) {

    public User(String name) {
        this(name, 0);
    }
}

