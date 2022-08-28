package blockchain.logic;

import blockchain.Message;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;


public record BlockInfo(BigInteger id, BigInteger timestamp, String prevHash, List<Message> messages)
        implements Serializable {

    public String format() {
        if (messages.isEmpty()) {
            return "no messages";
        }

        return "\n" + messages.stream().map(Message::toString).collect(Collectors.joining("\n"));
    }
}

