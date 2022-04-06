package Utilities;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Predicate;

public class Input {

    public static String takeUniqueStringInput(String prompt, String errorMessage, Predicate<String> predicate) {
        Scanner input = new Scanner(System.in);
        String s;

        while (true) {
            System.out.print(prompt);
            s = input.next();

            if (predicate.test(s))
                break;

            System.out.printf(errorMessage, s);
        }
        return s;
    }

    public static Integer takeIntegerInput(String prompt, String errorMessage, Predicate<Integer> predicate) {
        Scanner input = new Scanner(System.in);
        int n;

        while (true) {
            System.out.print(prompt);

            try {
                n = input.nextInt();
                if (predicate.test(n))
                    break;
                System.out.println(errorMessage);
            }

            catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Please enter a numeric value!!!");
            }
        }
        return n;
    }
}
