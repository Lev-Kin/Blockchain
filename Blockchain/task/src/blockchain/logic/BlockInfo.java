package blockchain.logic;

import java.io.Serializable;
import java.math.BigInteger;


public record BlockInfo(BigInteger id, BigInteger timestamp, String prevHash) implements Serializable {

}
