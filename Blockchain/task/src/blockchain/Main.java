package blockchain;

import blockchain.logic.Block;
import blockchain.logic.BlockInfo;
import blockchain.logic.BlockManager;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static volatile BlockInfo info;

    public static void main(String[] args) throws InterruptedException {

        List<Block> blockchain = Collections.synchronizedList(new ArrayList<>());
        ExecutorService miners = Executors.newCachedThreadPool();
        BlockManager manager = new BlockManager(0, 0);
        info = manager.createBlockInfo(null);

        for (int i = 0; i < 4; i++) {
            miners.submit(() -> {
                while (blockchain.size() < 5) {
                    final int size = blockchain.size();
                    long start = System.nanoTime();

                    String magic;
                    String hash;

                    Random random = new Random();
                    do {
                        magic = String.valueOf(random.nextInt());
                        hash = manager.sha256(info, magic);
                    } while (!manager.validate(hash));

                    int end = (int) ((System.nanoTime() - start) / 1_000_000_000);

                    synchronized (Main.class) {
                        Block block = manager.createBlock(info, magic);
                        if (block != null && blockchain.size() == size) {
                            info = manager.createBlockInfo(block);
                            blockchain.add(block);
                            System.out.println("\nBlock:");
                            System.out.println("Created by miner # " + Thread.currentThread().getId());
                            System.out.println(block);
                            System.out.println("Block was generating for " + end + " seconds");
                            manager.updateZeros(end, size + 1);
                        }
                    }
                }
            });
        }


        miners.awaitTermination(10, TimeUnit.SECONDS);

        miners.shutdown();

    }
}


