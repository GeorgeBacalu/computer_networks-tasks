package com.project.rc;

import com.project.rc.task1.BidimensionalParityBits;
import com.project.rc.task1.CyclicRedundancyCheck;
import com.project.rc.task2.TokenRing;
import com.project.rc.task3.SlidingWindow;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("\n--- Computer Networks Menu ---");
                System.out.println("1. Bidimensional Parity Bits");
                System.out.println("2. Cyclic Redundancy Check");
                System.out.println("3. Token Ring");
                System.out.println("4. Sliding Window Protocol");
                System.out.println("5. Exit");
                System.out.print("Enter your option: ");
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1: BidimensionalParityBits.run(); break;
                    case 2: CyclicRedundancyCheck.run(); break;
                    case 3: TokenRing.run(); break;
                    case 4: SlidingWindow.run(); break;
                    case 5: scanner.close(); return;
                    default: System.out.println("Invalid option");
                }
            }
        } catch (RuntimeException exception) {
            System.out.println("Error: " + exception.getMessage());
        } finally {
            scanner.close();
        }
    }
}
