package com.project.rc.task1;

import java.util.*;

public class BidimensionalParityBits {

    public static void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter binary string with length multiple of 7: ");
            String message = scanner.nextLine();
            validateMessage(message);
            List<List<Integer>> messageMatrix = transformToMatrix(message);
            computeParityBits(messageMatrix);
            displayMatrix(messageMatrix);
            simulateCorruption(messageMatrix);
            displayMatrix(messageMatrix);
            findCorruptedBit(messageMatrix);
        } catch (RuntimeException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private static void validateMessage(String message) {
        if (message.length() % 7 != 0) {
            throw new RuntimeException("Message length must be multiple of 7");
        }
        for (char ch : message.toCharArray()) {
            if (ch != '0' && ch != '1') {
                throw new RuntimeException("Message must contain only binary chars");
            }
        }
    }

    private static List<List<Integer>> transformToMatrix(String message) {
        List<List<Integer>> messageMatrix = new ArrayList<>();
        for (int i = 0; i < message.length() / 7; ++i) {
            messageMatrix.add(new ArrayList<>());
            for (int j = 0; j < 7; ++j) {
                messageMatrix.get(i).add(message.charAt(i * 7 + j) - '0');
            }
        }
        return messageMatrix;
    }

    private static void computeParityBits(List<List<Integer>> messageMatrix) {
        for (List<Integer> row : messageMatrix) {
            row.add(row.stream().reduce(0, Integer::sum) % 2);
        }
        messageMatrix.add(new ArrayList<>());
        for (int j = 0; j < messageMatrix.getFirst().size() - 1; ++j) {
            int sum = 0;
            for (int i = 0; i < messageMatrix.size() - 1; ++i) {
                sum += messageMatrix.get(i).get(j);
            }
            messageMatrix.getLast().add(sum % 2);
        }
    }

    private static void displayMatrix(List<List<Integer>> messageMatrix) {
        for (List<Integer> row : messageMatrix) {
            for (Integer bit : row) {
                System.out.print(bit + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void simulateCorruption(List<List<Integer>> messageMatrix) {
        Random random = new Random();
        int row = random.nextInt(0, messageMatrix.size());
        int column = random.nextInt(0, messageMatrix.getFirst().size());
        messageMatrix.get(row).set(column, messageMatrix.get(row).get(column) == 0 ? 1 : 0);
    }

    private static void findCorruptedBit(List<List<Integer>> messageMatrix) {
        for (int i = 0; i < messageMatrix.size() - 1; ++i) {
            int sumRow = 0;
            for (int j = 0; j < messageMatrix.get(i).size() - 1; ++j) {
                sumRow += messageMatrix.get(i).get(j);
            }
            int wantedParityRow = sumRow % 2;
            if (messageMatrix.get(i).getLast() != wantedParityRow) {
                int corruptRow = i;
                for (int j = 0; j < messageMatrix.getFirst().size() - 1; ++j) {
                    int sumColumn = 0;
                    for (int k = 0; k < messageMatrix.size() - 1; ++k) {
                        sumColumn += messageMatrix.get(k).get(j);
                    }
                    int wantedParityColumn = sumColumn % 2;
                    if (messageMatrix.getLast().get(j) != wantedParityColumn) {
                        int corruptColumn = j;
                        System.out.println("Corrupt bit: (" + corruptRow + ", " + corruptColumn + ")");
                    }
                }
            }
        }
    }
}
