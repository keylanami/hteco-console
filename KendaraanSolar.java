
public class KendaraanSolar extends Kendaraan {

    private final FaktorEmisi faktorEmisi;

    public KendaraanSolar(
            int id,
            String nama,
            String platNo,
            double efisiensiKmPerLiter,
            int userId,
            FaktorEmisi faktorEmisi
    ) {
        super(id, nama, platNo, efisiensiKmPerLiter, userId);
        this.faktorEmisi = faktorEmisi;
    }

    @Override
    protected double hitungEmisiPerPerjalanan(Perjalanan p) {
        double liter = p.getJarakKm() / efisiensiKmPerLiter;
        return faktorEmisi.hitungEmisi(liter);
    }
}
