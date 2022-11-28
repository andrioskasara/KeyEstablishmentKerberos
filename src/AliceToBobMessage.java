public class AliceToBobMessage {
    private byte[] cipheredIdAlice;
    private byte[] cipheredTimestamp;
    private KDCtoBob kdCtoBob;

    public AliceToBobMessage(byte[] cipheredIdAlice, byte[] cipheredTimestamp, KDCtoBob kdCtoBob) {
        this.cipheredIdAlice = cipheredIdAlice;
        this.cipheredTimestamp = cipheredTimestamp;
        this.kdCtoBob = kdCtoBob;
    }

    public AliceToBobMessage() { }

    public byte[] getCipheredIdAlice() {
        return cipheredIdAlice;
    }

    public void setCipheredIdAlice(byte[] cipheredIdAlice) {
        this.cipheredIdAlice = cipheredIdAlice;
    }

    public byte[] getCipheredTimestamp() {
        return cipheredTimestamp;
    }

    public void setCipheredTimestamp(byte[] cipheredTimestamp) {
        this.cipheredTimestamp = cipheredTimestamp;
    }

    public KDCtoBob getKdCtoBob() {
        return kdCtoBob;
    }

    public void setKdCtoBob(KDCtoBob kdCtoBob) {
        this.kdCtoBob = kdCtoBob;
    }
}
