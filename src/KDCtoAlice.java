public class KDCtoAlice {
    private byte [] cipheredSessionKey;
    private byte [] cipheredNonce;
    private byte [] cipheredLifetime;
    private byte [] cipheredIdBob;

    public KDCtoAlice(byte[] cipheredSessionKey, byte[] cipheredNonce, byte[] cipheredLifetime, byte[] cipheredIdBob) {
        this.cipheredSessionKey = cipheredSessionKey;
        this.cipheredNonce = cipheredNonce;
        this.cipheredLifetime = cipheredLifetime;
        this.cipheredIdBob = cipheredIdBob;
    }

    public KDCtoAlice() {
    }

    public byte[] getCipheredSessionKey() {
        return cipheredSessionKey;
    }

    public void setCipheredSessionKey(byte[] cipheredSessionKey) {
        this.cipheredSessionKey = cipheredSessionKey;
    }

    public byte[] getCipheredNonce() {
        return cipheredNonce;
    }

    public void setCipheredNonce(byte[] cipheredNonce) {
        this.cipheredNonce = cipheredNonce;
    }

    public byte[] getCipheredLifetime() {
        return cipheredLifetime;
    }

    public void setCipheredLifetime(byte[] cipheredLifetime) {
        this.cipheredLifetime = cipheredLifetime;
    }

    public byte[] getCipheredIdBob() {
        return cipheredIdBob;
    }

    public void setCipheredIdBob(byte[] cipheredIdBob) {
        this.cipheredIdBob = cipheredIdBob;
    }
}
