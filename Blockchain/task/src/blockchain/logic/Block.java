package blockchain.logic;

import blockchain.Message;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;


public final class Block implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final BlockInfo info;
    private final String hash;
    private final String magic;

    Block(BigInteger id, BigInteger timestamp, String prevHash, List<Message> messages, String hash, String magic) {
        this(new BlockInfo(id, timestamp, prevHash, messages), hash, magic);
    }

    Block(BlockInfo info, String hash, String magic) {
        this.info = info;
        this.hash = hash;
        this.magic = magic;
    }

    public BigInteger getId() {
        return info.id();
    }

    public BigInteger getTimestamp() {
        return info.timestamp();
    }

    public String getPrevHash() {
        return info.prevHash();
    }

    public String getHash() {
        return hash;
    }

    public String getMagic() {
        return magic;
    }

    @Override
    public String toString() {
        return "Id: " + info.id() + "\n" +
                "Timestamp: " + info.timestamp() + "\n" +
                "Magic number: " + magic + "\n" +
                "Hash of the previous block: \n" + info.prevHash() + "\n" +
                "Hash of the block: \n" + hash + "\n" +
                "Block data: " + info.format();
    }
}



