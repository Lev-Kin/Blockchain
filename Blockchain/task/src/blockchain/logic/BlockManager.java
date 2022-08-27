package blockchain.logic;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;

public class BlockManager {
    private volatile int zeros;
    private volatile int averageTime;

    public BlockManager(int zeros, int averageTime) {
        this.zeros = zeros;
        this.averageTime = averageTime;
    }

    public Block createBlock(BlockInfo info, String magic) {
        String hash = sha256(info, magic);
        return validate(hash) ? new Block(info, hash, magic) : null;
    }

    public BlockInfo createBlockInfo(Block block) {
        BigInteger id = block == null ? BigInteger.ONE : block.getId().add(BigInteger.ONE);
        BigInteger timestamp = BigInteger.valueOf(new Date().getTime());
        String prevHash = block == null ? "0" : block.getHash();
        return new BlockInfo(id, timestamp, prevHash);
    }

    public synchronized void updateZeros(int time, int size) {
        this.averageTime = (averageTime + time) / size;

        if (averageTime > time) {
            zeros++;
            System.out.println("N was increased to " + zeros);
        } else if (averageTime == time) {
            System.out.println("N stays the same");
        } else {
            this.zeros = zeros == 0 ? 0 : zeros - 1;
            System.out.println("N was decreased to " + zeros);
        }
    }

    public boolean validate(String hash) {
        return hash.startsWith("0".repeat(zeros));
    }

    public String sha256(BlockInfo info, String magic) {
        return sha256(info.prevHash() + magic + info.id() + info.timestamp());

    }

    public String sha256(String prevHash, String magic, BigInteger id, BigInteger timestamp) {
        return sha256(prevHash + magic + id + timestamp);

    }

    public String sha256(String data) {
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


