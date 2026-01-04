

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

    

public class PemilikKendaraan extends User {

    private List<Kendaraan> kendaraans = new ArrayList<>();

    public PemilikKendaraan(int id, String username, String email, String password) {
        super(id, username, email, password, Role.USER);
    }

    public void tambahKendaraan(Kendaraan kendaraan) {
        kendaraans.add(kendaraan);
    }

    public List<Kendaraan> getKendaraans() {
        return List.copyOf(kendaraans);
    }

    public double hitungEmisiBulanan(YearMonth bulan) {
        return kendaraans.stream()
                .flatMap(k -> k.getPerjalanans().stream()
                        .filter(p -> YearMonth.from(p.getTanggal()).equals(bulan))
                        .map(p -> k.hitungEmisiPerPerjalanan(p))
                )
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}