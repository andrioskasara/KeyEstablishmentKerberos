public class AnswerFromKDC {
    private KDCtoBob kdCtoBob;
    private KDCtoAlice kdCtoAlice;

    public AnswerFromKDC(KDCtoBob kdCtoBob, KDCtoAlice kdCtoAlice) {
        this.kdCtoBob = kdCtoBob;
        this.kdCtoAlice = kdCtoAlice;
    }

    public AnswerFromKDC() {
    }

    public KDCtoBob getKdCtoBob() {
        return kdCtoBob;
    }

    public void setKdCtoBob(KDCtoBob kdCtoBob) {
        this.kdCtoBob = kdCtoBob;
    }

    public KDCtoAlice getKdCtoAlice() {
        return kdCtoAlice;
    }

    public void setKdCtoAlice(KDCtoAlice kdCtoAlice) {
        this.kdCtoAlice = kdCtoAlice;
    }
}
