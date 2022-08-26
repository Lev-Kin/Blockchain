package blockchain.logic;

import java.math.BigInteger;

public record BlockInfo(BigInteger id, BigInteger timestamp, String prevHash) {

}


