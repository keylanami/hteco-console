import java.time.LocalDate;
public class Perjalanan {

    private final double jarakKm;
    private final LocalDate tanggal;

    public Perjalanan(double jarakKm, LocalDate tanggal) {
        if (jarakKm <= 0) {
            throw new IllegalArgumentException("Jarak harus lebih dari 0");
        }
        this.jarakKm = jarakKm;
        this.tanggal = tanggal;
    }

    public double getJarakKm() {
        return jarakKm;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }
}

