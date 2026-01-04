import java.util.ArrayList;
import java.util.List;

public abstract class Kendaraan {
    
    protected int id;
    protected String nama;
    protected String platNo;
    protected double efisiensiKmPerLiter;
    protected int userId;

    protected List<Perjalanan> perjalanans = new ArrayList<>();

    protected Kendaraan(
            int id,
            String nama,
            String platNo,
            double efisiensiKmPerLiter,
            int userId
    ) {
        this.id = id;
        this.nama = nama;
        this.platNo = platNo;
        this.efisiensiKmPerLiter = efisiensiKmPerLiter;
        this.userId = userId;
    }

    public final double hitungTotalEmisi() {
        return perjalanans.stream()
                .mapToDouble(this::hitungEmisiPerPerjalanan)
                .sum();
    }


    
    protected abstract double hitungEmisiPerPerjalanan(Perjalanan perjalanan);

    public void tambahPerjalanan(Perjalanan perjalanan) {
        perjalanans.add(perjalanan);
    }




    public List<Perjalanan> getPerjalanans() {
        return List.copyOf(perjalanans);
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    @Override
    public String toString() {
        return nama + " (" + getClass().getSimpleName() + ")";
    }
}
