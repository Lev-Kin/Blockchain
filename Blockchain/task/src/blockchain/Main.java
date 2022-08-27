package blockchain;

import blockchain.logic.Block;
import blockchain.logic.BlockInfo;
import blockchain.logic.BlockManager;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static volatile BlockInfo info;
    private static final List<String> TEMPLATE_MSG = new ArrayList<>(List.of(
            "Ashes: Hey, I'm first!",
            "Keeper: You always will be first because it is your blockchain!",
            "Keeper: it's not fair",
            "Ashes: That's nice msg",
            "Charcoal: Is anybody out there?",
            "Charcoal: Where is my blockchain?",
            "Ashes: It's not your blockchain...",
            "Charcoal: Hey Ashes, nice chat",
            "Ashes: You're welcome."
    ));

    public static void main(String[] args) throws InterruptedException {

        List<Block> blockchain = Collections.synchronizedList(new ArrayList<>());
        List<String> messages = Collections.synchronizedList(new ArrayList<>());

        final int minersCount = 4;
        ExecutorService miners = Executors.newFixedThreadPool(minersCount);

        BlockManager manager = new BlockManager(0, 5);
        info = manager.createBlockInfo(null, "");

        for (int i = 0; i < minersCount; i++) {
            miners.submit(() -> {
                outer: while (blockchain.size() < 5) {
                    final int size = blockchain.size();
                    long start = System.nanoTime();

                    String magic;
                    String hash;

                    Random random = new Random();
                    do {
                        magic = String.valueOf(random.nextInt());
                        hash = manager.sha256(info, magic);
                        if (size != blockchain.size()) {
                            continue outer;
                        }
                    } while (!manager.validate(hash));

                    int end = (int) ((System.nanoTime() - start) / 1_000_000_000);

                    Block block = manager.createBlock(info, magic);
                    synchronized (Main.class) {
                        if (block != null && blockchain.size() == size) {
                            while (messages.isEmpty()) { }
                            String message = String.join("\n", messages);
                            messages.clear();
                            info = manager.createBlockInfo(block, message);
                            blockchain.add(block);
                            System.out.println("\nBlock:");
                            System.out.println("Created by miner # " + Thread.currentThread().getId());
                            System.out.println(block);
                            System.out.println("Block was generating for " + end + " seconds");
                            manager.updateZeros(end, size + 10);
                        }
                    }
                }
            });
        }

        miners.shutdown();

        Random random = new Random();
        while (!miners.isTerminated()) {
            long time = random.nextInt(10) * 10L;
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            messages.add(TEMPLATE_MSG.get(random.nextInt(TEMPLATE_MSG.size())));
        }

    }
}


