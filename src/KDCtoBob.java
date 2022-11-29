public class KDCtoBob {
    private byte[] cipheredSessionKey;
    private byte[] cipheredIdAlice;
    private byte[] cipheredLifetime;

    public KDCtoBob(byte[] cipheredSessionKey, byte[] cipheredIdAlice, byte[] cipheredLifetime) {
        this.cipheredSessionKey = cipheredSessionKey;
        this.cipheredIdAlice = cipheredIdAlice;
        this.cipheredLifetime = cipheredLifetime;
    }

    public byte[] getCipheredSessionKey() {
        return cipheredSessionKey;
    }

    public void setCipheredSessionKey(byte[] cipheredSessionKey) {
        this.cipheredSessionKey = cipheredSessionKey;
    }

    public byte[] getCipheredIdAlice() {
        return cipheredIdAlice;
    }

    public void setCipheredIdAlice(byte[] cipheredIdAlice) {
        this.cipheredIdAlice = cipheredIdAlice;
    }

    public byte[] getCipheredLifetime() {
        return cipheredLifetime;
    }

    public void setCipheredLifetime(byte[] cipheredLifetime) {
        this.cipheredLifetime = cipheredLifetime;
    }
}
