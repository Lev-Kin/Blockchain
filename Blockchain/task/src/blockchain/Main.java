package blockchain;

import blockchain.logic.BlockManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter how many zeros the hash must start with: ");

        BlockManager manager = new BlockManager(scanner.nextInt(), null);
        manager.createBlock();
        manager.createBlock();
        manager.createBlock();
        manager.createBlock();
        manager.createBlock();
    }
}


