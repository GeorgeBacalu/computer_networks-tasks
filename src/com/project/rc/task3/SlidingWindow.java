package com.project.rc.task3;

import java.util.*;

public class SlidingWindow {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void run() throws InterruptedException {
        System.out.print("Enter the number of packages to send over the network: ");
        int nrFrames = scanner.nextInt();
        System.out.print("Enter waiting time for each package to receive an acknowledgement (ms): ");
        int waitingTime = scanner.nextInt();
        System.out.print("Enter window size: ");
        int windowSize = scanner.nextInt();
        int lostFrame = random.nextInt(nrFrames);
        System.out.println("Lost frame: " + lostFrame);
        int lostAcknowledgement;
        do {
            lostAcknowledgement = random.nextInt(nrFrames);
        } while (lostAcknowledgement == lostFrame);
        System.out.println("Lost acknowledgement: " + lostAcknowledgement);
        boolean[] acknowledgedFrames = new boolean[nrFrames];
        List<Integer> acknowledgedFramesIndexes = new ArrayList<>();
        int currentFrame = 0;

        while (currentFrame < nrFrames) {
            int endFrame = Math.min(currentFrame + windowSize, nrFrames);
            sendFrames(currentFrame, endFrame, lostFrame, acknowledgedFrames);
            Thread.sleep(waitingTime);
            receiveAcknowledgements(currentFrame, endFrame, lostFrame, lostAcknowledgement, acknowledgedFrames, acknowledgedFramesIndexes);
            currentFrame = advanceWindowIfPossible(currentFrame, acknowledgedFrames);
            if (currentFrame > lostFrame && !acknowledgedFrames[lostFrame]) {
                resendLostFrame(lostFrame, acknowledgedFrames, acknowledgedFramesIndexes);
            }
            if (currentFrame > lostAcknowledgement && !acknowledgedFrames[lostAcknowledgement]) {
                resendLostAcknowledgement(lostAcknowledgement, acknowledgedFrames, acknowledgedFramesIndexes);
            }
        }
        System.out.println("Received packages in order: ");
        acknowledgedFramesIndexes.forEach(System.out::println);
        System.out.println("Received packages sorted to form the original message: ");
        acknowledgedFramesIndexes.sort(Comparator.naturalOrder());
        acknowledgedFramesIndexes.forEach(System.out::println);
    }

    private static void sendFrames(int currentFrame, int endFrame, int lostFrame, boolean[] acknowledgedFrames) {
        for (int j = currentFrame; j < endFrame; ++j) {
            if (!acknowledgedFrames[j]) {
                System.out.println("Frame " + j + (j == lostFrame && !acknowledgedFrames[lostFrame] ? " lost" : " sent"));
            }
        }
    }

    private static void receiveAcknowledgements(int currentFrame, int endFrame, int lostFrame, int lostAcknowledgement, boolean[] acknowledgedFrames, List<Integer> acknowledgedFramesIndexes) {
        for (int j = currentFrame; j < endFrame; ++j) {
            if (j != lostFrame && !acknowledgedFrames[j]) {
                if (j == lostAcknowledgement) {
                    System.out.println("Acknowledgement " + j + " lost");
                } else {
                    System.out.println("Acknowledgement " + j + " received");
                    acknowledgedFrames[j] = true;
                    acknowledgedFramesIndexes.add(j);
                }
            }
        }
    }

    private static int advanceWindowIfPossible(int currentFrame, boolean[] acknowledgedFrames) {
        int originalFrame = currentFrame;
        while (currentFrame < acknowledgedFrames.length && acknowledgedFrames[currentFrame]) currentFrame++;
        return currentFrame == originalFrame && !acknowledgedFrames[currentFrame] ? currentFrame + 1 : currentFrame;
    }

    private static void resendLostFrame(int lostFrame, boolean[] acknowledgedFrames, List<Integer> acknowledgedFramesIndexes) {
        if (!acknowledgedFrames[lostFrame]) {
            System.out.println("Resending frame " + lostFrame);
            System.out.println("Frame " + lostFrame + " successfully sent after loss");
            acknowledgedFrames[lostFrame] = true;
            acknowledgedFramesIndexes.add(lostFrame);
        }
    }

    private static void resendLostAcknowledgement(int lostAcknowledgement, boolean[] acknowledgedFrames, List<Integer> acknowledgedFramesIndexes) {
        if (!acknowledgedFrames[lostAcknowledgement]) {
            System.out.println("Resending acknowledgement for frame " + lostAcknowledgement);
            System.out.println("Acknowledgement " + lostAcknowledgement + " successfully received after loss");
            acknowledgedFrames[lostAcknowledgement] = true;
            acknowledgedFramesIndexes.add(lostAcknowledgement);
        }
    }
}
