package com.project.rc.task2;

import java.util.Random;
import java.util.Scanner;

public class TokenRing {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final Token token = Token.getInstance();
    private static final Computer[] computers = new Computer[10];

    public static void run() {
        generateComputers();
        boolean tokenUsed = false;
        int previousSourceIndex = 0;
        String response;
        do {
            displayComputers();
            int sourceIndex = random.nextInt(10);
            if (tokenUsed) {
                passToken(previousSourceIndex, sourceIndex);
            }
            previousSourceIndex = sendMessage(sourceIndex);
            tokenUsed = true;
            System.out.println("Do you want to continue (y/n)? ");
            response = scanner.next();
        } while (token.isFree() && response.startsWith("y"));
    }

    private static void generateComputers() {
        for (int i = 0; i < computers.length; ++i) {
            computers[i] = new Computer("C" + i, generateIpAddress());
        }
    }

    private static String generateIpAddress() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; ++i) {
            if (i > 0) {
                stringBuilder.append(".");
            }
            stringBuilder.append(random.nextInt(255));
        }
        return stringBuilder.toString();
    }

    private static void displayComputers() {
        for (Computer computer : computers) {
            System.out.println(computer);
        }
    }

    private static void passToken(int previousSourceIndex, int newSourceIndex) {
        System.out.println("Passing token to new source: " + computers[newSourceIndex].getBuffer());
        for (int i = previousSourceIndex; ; ++i) {
            int currentIndex = i % computers.length;
            if (currentIndex == newSourceIndex) {
                System.out.println(computers[currentIndex].getBuffer() + ": Took token");
                token.setFree(false);
                break;
            } else {
                System.out.println(computers[currentIndex].getBuffer() + ": Move token");
            }
        }
    }

    private static int sendMessage(int sourceIndex) {
        int destinationIndex = selectDestination(sourceIndex);
        System.out.println("Source: " + computers[sourceIndex].getBuffer());
        System.out.println("Destination: " + computers[destinationIndex].getBuffer());
        System.out.print("Enter message: ");
        String message = scanner.next();
        setupTokenForTransmission(sourceIndex, destinationIndex, message);
        sendTokenToDestination(sourceIndex, destinationIndex);
        sendTokenToSource(destinationIndex, sourceIndex);
        updateDestinationData(destinationIndex, message);
        token.setFree(true);
        return sourceIndex;
    }

    private static int selectDestination(int sourceIndex) {
        int destinationIndex;
        do {
            destinationIndex = random.nextInt(computers.length);
        } while (destinationIndex == sourceIndex);
        return destinationIndex;
    }

    private static void setupTokenForTransmission(int sourceIndex, int destinationIndex, String message) {
        token.setSourceIp(computers[sourceIndex].getIpAddress());
        token.setDestinationIp(computers[destinationIndex].getIpAddress());
        token.setMessage(message);
        token.setFree(false);
    }

    private static void sendTokenToDestination(int sourceIndex, int destinationIndex) {
        System.out.println(computers[sourceIndex].getBuffer() + ": Took token");
        for (int i = sourceIndex; ; ++i) {
            int currentIndex = i % computers.length;
            if (currentIndex == destinationIndex) {
                System.out.println(computers[currentIndex].getBuffer() + ": Reached destination");
                token.setReachedDestination(true);
                break;
            } else {
                System.out.println(computers[currentIndex].getBuffer() + ": Move token");
            }
        }
    }

    private static void sendTokenToSource(int destinationIndex, int sourceIndex) {
        System.out.println(computers[destinationIndex].getBuffer() + ": Took token");
        for (int i = destinationIndex; ; ++i) {
            int currentIndex = i % computers.length;
            if (currentIndex == sourceIndex) {
                System.out.println(computers[currentIndex].getBuffer() + ": Returned to source");
                token.setReachedDestination(true);
                break;
            } else {
                System.out.println(computers[currentIndex].getBuffer() + ": Move token");
            }
        }
    }

    private static void updateDestinationData(int destinationIndex, String message) {
        Computer destination = computers[destinationIndex];
        String existingData = destination.getData();
        destination.setData(existingData == null ? message : existingData + ":" + message);
    }
}
