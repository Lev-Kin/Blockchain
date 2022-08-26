package blockchain.logic;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class BlockManager {
    private final int zeros;
    private Block block;

    public BlockManager(int zeros, Block block) {
        this.zeros = zeros;
        this.block = Objects.requireNonNullElseGet(block, () ->
                new Block(BigInteger.ZERO,
                        null,
                        null,
                        "0",
                        null)
        );
    }

    public void createBlock() {
        long start = System.nanoTime();
        BigInteger id = block.getId().add(BigInteger.ONE);
        BigInteger timestamp = BigInteger.valueOf(new Date().getTime());

        String magic;
        String hash;

        Random random = new Random();
        do {
            magic = String.valueOf(random.nextInt());
            hash = applySha256(block.getHash(), magic, id, timestamp);
        } while (!hash.startsWith("0".repeat(zeros)));

        block = new Block(id, timestamp, block.getHash(), hash, magic);
        long end = System.nanoTime() - start;
        System.out.println("\n" + block);
        System.out.println("Block was generating for " + end / 1_000_000_000 + " seconds");
    }

    public String applySha256(String prevHash, String magic, BigInteger id, BigInteger timestamp) {
        String data = prevHash + magic + id + timestamp;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexes = new StringBuilder();
            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexes.append('0');
                hexes.append(hex);
            }
            return hexes.toString();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}


