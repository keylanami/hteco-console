public class KendaraanListrik extends Kendaraan {

    private final FaktorEmisi faktorEmisi; 

    public KendaraanListrik(
            int id,
            String nama,
            String platNo,
            int userId,
            FaktorEmisi faktorEmisi 
    ) {
        super(id, nama, platNo, 0, userId);
        this.faktorEmisi = faktorEmisi;
    }

    @Override
    protected double hitungEmisiPerPerjalanan(Perjalanan p) {
        double konsumsiKwh = p.getJarakKm() * 0.15; 
        return faktorEmisi.hitungEmisi(konsumsiKwh);
    }
}