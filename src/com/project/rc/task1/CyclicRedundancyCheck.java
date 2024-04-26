package com.project.rc.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CyclicRedundancyCheck {

    public static void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter binary string: ");
            String message = scanner.nextLine();
            System.out.print("Enter generator polynomial: ");
            String generatorPolynomial = scanner.nextLine();

            validateInput(message, generatorPolynomial);
            List<Integer> extendedMessage = computeExtendedMessage(message, generatorPolynomial);
            System.out.print("\n--------------------------------\n\n");
            System.out.print("Extended message: ");
            display(extendedMessage);

            List<Integer> extendedMessageCopy = new ArrayList<>(extendedMessage);
            List<Integer> rest = computeRest(extendedMessageCopy, generatorPolynomial);
            System.out.print("Rest: ");
            if (rest.isEmpty()) {
                System.out.println(0);
            } else {
                display(rest);
            }

            List<Integer> destinationMessage = new ArrayList<>(extendedMessage);
            for (int i = 0; i < rest.size(); ++i) {
                destinationMessage.removeLast();
            }
            destinationMessage.addAll(rest);
            System.out.print("\n--------------------------------\n\n");
            System.out.print("Destination message: ");
            display(destinationMessage);

            List<Integer> finalRest = computeRest(destinationMessage, generatorPolynomial);
            System.out.print("Final rest: ");
            if (finalRest.isEmpty()) {
                System.out.println(0);
                System.out.println("Message has no errors!");
            } else {
                display(finalRest);
                System.out.println("Message has errors!");
            }
        } catch (RuntimeException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private static void validateInput(String message, String generatorPolynomial) {
        for (char ch : message.toCharArray()) {
            if (ch != '0' && ch != '1') {
                throw new RuntimeException("Message must contain only binary chars");
            }
        }
        for (char ch : generatorPolynomial.toCharArray()) {
            if (ch != '0' && ch != '1') {
                throw new RuntimeException("Generator polynomial must contain only binary chars");
            }
        }
        if (generatorPolynomial.startsWith("0")) {
            throw new RuntimeException("Generator polynomial must start with 1");
        }
        if (message.length() <= generatorPolynomial.length()) {
            throw new RuntimeException("Message must be longer than generator polynomial");
        }
    }

    private static List<Integer> computeExtendedMessage(String message, String generatorPolynomial) {
        List<Integer> extendedMessage = new ArrayList<>();
        for (char ch : message.toCharArray()) {
            extendedMessage.add(ch - '0');
        }
        int rank = generatorPolynomial.length() - 1;
        for (int i = 0; i < rank; ++i) {
            extendedMessage.add(0);
        }
        return extendedMessage;
    }

    private static void display(List<Integer> extendedMessage) {
        for (Integer bit : extendedMessage) {
            System.out.print(bit);
        }
        System.out.println("\n");
    }

    private static void display(String generatorPolynomial) {
        for (char ch : generatorPolynomial.toCharArray()) {
            System.out.print(ch);
        }
        System.out.println();
    }

    private static List<Integer> computeRest(List<Integer> extendedMessage, String generatorPolynomial) {
        while (extendedMessage.size() >= generatorPolynomial.length()) {
            System.out.print("Message: ");
            display(extendedMessage);
            System.out.print("Generator polynomial: ");
            display(generatorPolynomial);
            System.out.print("XOR Result: ");
            List<Integer> xorResult = new ArrayList<>();
            for (int i = 0; i < extendedMessage.size(); ++i) {
                if (i < generatorPolynomial.length()) {
                    xorResult.add(extendedMessage.get(i) ^ generatorPolynomial.charAt(i) - '0');
                } else {
                    xorResult.add(extendedMessage.get(i));
                }
            }
            String xorResultStr = xorResult.stream().map(String::valueOf).collect(Collectors.joining(""));
            display(xorResultStr);
            String newExtendedMessageStr = xorResultStr.replaceFirst("^0+", "");
            extendedMessage.clear();
            for (char ch : newExtendedMessageStr.toCharArray()) {
                extendedMessage.add(ch - '0');
            }
            System.out.println();
        }
        return extendedMessage;
    }
}
