import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.logging.Level;

class Block {
    private String hash;
    private String prevHash;
    private ArrayList<Transaction> transactions;
    private long timestamp;
    private int nonce;

    // for generating nonce
    SecureRandom secureRandom = new SecureRandom();

    public Block(String prevHash, long timestamp) {
        this.setPrevHash(prevHash);
        this.timestamp = timestamp;
        this.setHash(calculateBlockHash());
        this.nonce = secureRandom.nextInt(10);
        this.transactions = new ArrayList<>();
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return this.getHash() + " " + this.getPrevHash();
    }

    public String calculateBlockHash() {
        String dataToHash = prevHash + Long.toString(timestamp) + Integer.toString(nonce);
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            Logger logger = Logger.getLogger(Block.class.getName());
            logger.log(Level.SEVERE, ex.getMessage());
        }
        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

}