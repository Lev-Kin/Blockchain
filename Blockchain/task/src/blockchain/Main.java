package blockchain;

import blockchain.block.BlockInfo;
import blockchain.block.BlockManager;
import blockchain.economy.Transaction;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static volatile BlockInfo info;

    public static void main(String[] args)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {

        final int blockchainSize = 15;
        final int keySize = 1024;

        BlockManager manager = new BlockManager(0, 5);

        List<User> users = new ArrayList<>(List.of(
                new User("Ashes", keySize),
                new User("Charcoal", keySize),
                new User("Keeper", keySize)
        ));

        List<Miner> miners = new ArrayList<>(List.of(
                new Miner(new User("miner0", keySize), manager),
                new Miner(new User("miner1", keySize), manager),
                new Miner(new User("miner2", keySize), manager),
                new Miner(new User("miner3", keySize), manager)
        ));

        ExecutorService service = Executors.newFixedThreadPool(miners.size());
        for (var miner : miners) {
            service.submit(() -> {
                while (manager.size() < blockchainSize) {
                    String magic = miner.mine();
                    if (magic != null) {
                        manager.createBlock(magic, miner);
                    }
                }
            });
        }
        service.shutdown();

        Random random = new Random();
        while (!service.isTerminated()) {
            long time = random.nextInt(10) * 10L;
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            User from = users.get(random.nextInt(users.size()));
            User to = users.get(random.nextInt(users.size()));
            BigInteger cash = BigInteger.valueOf(random.nextInt(99) + 1);
            Transaction transaction = new Transaction(from, to, cash);
            manager.add(transaction);
        }
    }
}

