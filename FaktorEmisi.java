public class FaktorEmisi {
    private final String jenisBBM;
    private double faktorCO2perLiter;

    public FaktorEmisi(String jenisBBM, double faktorCO2perLiter) {
        this.jenisBBM = jenisBBM;
        this.faktorCO2perLiter = faktorCO2perLiter;
    }

    public double hitungEmisi(double liter) {
        return liter * faktorCO2perLiter;
    }

    public void setFaktorCO2perLiter(double faktorCO2perLiter) {
        if (faktorCO2perLiter <= 0) {
            throw new IllegalArgumentException("Faktor emisi harus > 0");
        }
        this.faktorCO2perLiter = faktorCO2perLiter;
    }

    public double getFaktorCO2perLiter() {
        return faktorCO2perLiter;
    }

    public String getJenisBBM() {
        return jenisBBM;
    }
}
