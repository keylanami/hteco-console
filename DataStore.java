import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static List<User> users = new ArrayList<>();
    
    public static FaktorEmisi bensin = new FaktorEmisi("Bensin", 2.31); // kg CO2 per liter
    public static FaktorEmisi solar = new FaktorEmisi("Solar", 2.68);
    public static FaktorEmisi listrik = new FaktorEmisi("Listrik (Grid)", 0.85); // kg CO2 per kWh

    static {
        users.add(new Admin(1, "admin", "admin@hteco.com", "admin123"));
        users.add(new PemilikKendaraan(2, "kei_user", "kei@hteco.com", "user123"));
    }
}