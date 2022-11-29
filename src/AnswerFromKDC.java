public class AnswerFromKDC {
    private KDCtoAlice kdCtoAlice;
    private KDCtoBob kdCtoBob;

    public AnswerFromKDC(KDCtoAlice kdCtoAlice, KDCtoBob kdCtoBob) {
        this.kdCtoAlice = kdCtoAlice;
        this.kdCtoBob = kdCtoBob;
    }

    public KDCtoAlice getKdCtoAlice() {
        return kdCtoAlice;
    }

    public void setKdCtoAlice(KDCtoAlice kdCtoAlice) {
        this.kdCtoAlice = kdCtoAlice;
    }

    public KDCtoBob getKdCtoBob() {
        return kdCtoBob;
    }

    public void setKdCtoBob(KDCtoBob kdCtoBob) {
        this.kdCtoBob = kdCtoBob;
    }
}
