import java.time.YearMonth;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n=== HTECO ===");
            System.out.println("1. Login");
            System.out.println("2. Register (Pemilik Kendaraan)");
            System.out.println("0. Keluar Aplikasi");
            System.out.print("Pilih: ");

            int pilih = safeIntInput();

            switch (pilih) {
                case 1 -> handleLogin();
                case 2 -> handleRegister();
                case 0 -> {
                    System.out.println("Bye bye! Stay green!");
                    isRunning = false;
                }
                default -> Toast.show("Pilihan tidak valid");
            }
        }
    }


    private static void handleLogin() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        User foundUser = DataStore.users.stream()
                .filter(u -> u.getEmail().equals(email) && u.getPasswordHash().equals(pass))
                .findFirst()
                .orElse(null);

        if (foundUser == null) {
            Toast.show("Login Gagal! Email atau password salah.");
            return;
        }

        Toast.show("Selamat datang, " + foundUser.getUsername() + "!");

        if (foundUser instanceof Admin) {
            menuAdminLoop((Admin) foundUser);
        } else if (foundUser instanceof PemilikKendaraan) {
            menuUserLoop((PemilikKendaraan) foundUser);
        }
    }

    private static void handleRegister() {
        System.out.println("--- Register New User ---");
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        boolean exists = DataStore.users.stream().anyMatch(u -> u.getEmail().equals(email));
        if (exists) {
            Toast.show("Email sudah terdaftar!");
            return;
        }

        int newId = DataStore.users.size() + 1;
        
        DataStore.users.add(new PemilikKendaraan(newId, username, email, pass));
        Toast.show("Registrasi Berhasil! Silakan Login.");
    }


    private static void menuAdminLoop(Admin admin) {
        while (true) {
            System.out.println("\n=== DASHBOARD ADMIN ===");
            System.out.println("1. Kelola Faktor Emisi (Master Data)");
            System.out.println("2. Lihat Semua User");
            System.out.println("3. Laporan Total Emisi Sistem");
            System.out.println("0. Logout");
            System.out.print("> ");

            int pilih = safeIntInput();

            if (pilih == 1) ubahFaktorEmisi();
            else if (pilih == 2) lihatSemuaUser();
            else if (pilih == 3) lihatLaporanSistem();
            else if (pilih == 0) break;
            else Toast.show("Menu tidak tersedia");
        }
    }

    private static void ubahFaktorEmisi() {
        System.out.println("\n--- Ubah Faktor Emisi (kgCO2) ---");
        System.out.println("1. Bensin (Saat ini: " + DataStore.bensin.getFaktorCO2perLiter() + ")");
        System.out.println("2. Solar (Saat ini: " + DataStore.solar.getFaktorCO2perLiter() + ")");
        System.out.println("3. Listrik Grid (Saat ini: " + DataStore.listrik.getFaktorCO2perLiter() + ")");
        System.out.print("Pilih yang mau diubah: ");
        
        int tipe = safeIntInput();
        FaktorEmisi target = switch(tipe) {
            case 1 -> DataStore.bensin;
            case 2 -> DataStore.solar;
            case 3 -> DataStore.listrik;
            default -> null;
        };

        if (target == null) {
            Toast.show("Tipe salah");
            return;
        }

        System.out.print("Masukkan nilai faktor emisi baru: ");
        double nilaiBaru = sc.nextDouble(); 
        sc.nextLine(); 

        try {
        
            target.setFaktorCO2perLiter(nilaiBaru);
            Toast.show("Faktor emisi " + target.getJenisBBM() + " berhasil diupdate!");
        } catch (IllegalArgumentException e) {
            Toast.show("Gagal " + e.getMessage());
        }
    }

    private static void lihatSemuaUser() {
        System.out.println("\n--- Daftar Pengguna Aplikasi ---");
        for (User u : DataStore.users) {
            String role = (u instanceof Admin) ? "[ADMIN]" : "[USER]";
            System.out.printf("%s ID:%d - %s (%s)\n", role, u.getId(), u.getUsername(), u.getEmail());
        }
    }

    private static void lihatLaporanSistem() {
        double totalEmisiDunia = 0;
        int totalKendaraan = 0;

        for (User u : DataStore.users) {
            if (u instanceof PemilikKendaraan pk) {
                totalKendaraan += pk.getKendaraans().size();
                for (Kendaraan k : pk.getKendaraans()) {
                    totalEmisiDunia += k.hitungTotalEmisi();
                }
            }
        }

        System.out.println("\n--- Laporan Lingkungan ---");
        System.out.println("Total Kendaraan Terdaftar: " + totalKendaraan + " unit");
        System.out.printf("Total Jejak Karbon Sistem: %.2f kg CO2\n", totalEmisiDunia);
    }



    private static void menuUserLoop(PemilikKendaraan user) {
        while (true) {
            System.out.println("\n=== DASHBOARD USER: " + user.getUsername() + " ===");
            System.out.println("1. Garasi Saya (List & Hapus)");
            System.out.println("2. Tambah Kendaraan Baru");
            System.out.println("3. Catat Perjalanan");
            System.out.println("4. History & Statistik");
            System.out.println("0. Logout");
            System.out.print("> ");

            int pilih = safeIntInput();

            try {
                switch (pilih) {
                    case 1 -> kelolaGarasi(user);
                    case 2 -> tambahKendaraanUser(user);
                    case 3 -> catatPerjalanan(user);
                    case 4 -> lihatStatistik(user);
                    case 0 -> { return; }
                    default -> throw new InputTidakValidException("menu");
                }
            } catch (AppException e) {
                Toast.show("Watch out " + e.getMessage());
            }
        }
    }

    private static void kelolaGarasi(PemilikKendaraan user) {
        if (user.getKendaraans().isEmpty()) throw new KendaraanKosongException();
        
        System.out.println("\n--- Garasi ---");
        List<Kendaraan> list = user.getKendaraans();
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
        
        System.out.println("\nKetik nomor untuk hapus, atau 0 kembali");
        System.out.print("Pilih: ");
        int idx = safeIntInput() - 1;
        
        if (idx >= 0 && idx < list.size()) {
            Kendaraan removed = list.get(idx);
            Toast.show("Fitur hapus belum diimplementasi di class Model, tapi logicnya disini.");
        }
    }

    private static void tambahKendaraanUser(PemilikKendaraan user) {
        System.out.println("\n--- Tambah Kendaraan ---");
        System.out.println("1. Kendaraan Bensin");
        System.out.println("2. Kendaraan Solar");
        System.out.println("3. Kendaraan Listrik (EV)");
        System.out.print("Pilih Tipe: ");
        
        int jenis = safeIntInput();
        
        System.out.print("Nama Kendaraan (ex: Avanza 2020): ");
        String nama = sc.nextLine();
        System.out.print("Plat Nomor: ");
        String plat = sc.nextLine();

        Kendaraan k = null;
        
        switch (jenis) {
            case 1 -> {
                System.out.print("Efisiensi (km/liter): ");
                double ef = sc.nextDouble(); sc.nextLine();
                k = new KendaraanBensin(0, nama, plat, ef, user.getId(), DataStore.bensin);
            }
            case 2 -> {
                System.out.print("Efisiensi (km/liter): ");
                double ef = sc.nextDouble(); sc.nextLine();
                k = new KendaraanSolar(0, nama, plat, ef, user.getId(), DataStore.solar);
            }
            case 3 -> {
                k = new KendaraanListrik(0, nama, plat, user.getId(), DataStore.listrik);
            }
            default -> throw new InputTidakValidException("tipe kendaraan");
        }

        user.tambahKendaraan(k);
        Toast.show("Kendaraan berhasil parkir di garasi!");
    }

    private static void catatPerjalanan(PemilikKendaraan user) {
        Kendaraan k = pilihKendaraan(user);
        
        System.out.print("Jarak tempuh (km): ");
        double jarak = sc.nextDouble(); sc.nextLine();
        
        if (jarak <= 0) throw new InputTidakValidException("jarak (harus positif)");

        k.tambahPerjalanan(new Perjalanan(jarak, java.time.LocalDate.now()));
        
        Toast.show("Perjalanan dicatat! Emisi bertambah.");
    }

    private static void lihatStatistik(PemilikKendaraan user) {
        Kendaraan k = pilihKendaraan(user);
        
        System.out.println("\nStatistik: " + k.getNama());
        if (k.getPerjalanans().isEmpty()) {
            System.out.println("Belum ada riwayat perjalanan.");
            return;
        }

        double totalJarak = 0;
        for (Perjalanan p : k.getPerjalanans()) {
            System.out.printf("- %s: %.1f km\n", p.getTanggal(), p.getJarakKm());
            totalJarak += p.getJarakKm();
        }
        
        double totalEmisi = k.hitungTotalEmisi();
        System.out.println("---------------------------");
        System.out.printf("Total Jarak: %.1f km\n", totalJarak);
        System.out.printf("Total Emisi: %.2f kg CO2\n", totalEmisi);
    }


    private static Kendaraan pilihKendaraan(PemilikKendaraan user) {
        if (user.getKendaraans().isEmpty()) throw new KendaraanKosongException();

        System.out.println("Pilih Kendaraan:");
        List<Kendaraan> list = user.getKendaraans();
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getNama());
        }
        System.out.print("> ");
        int idx = safeIntInput() - 1;

        if (idx < 0 || idx >= list.size()) throw new InputTidakValidException("pilihan kendaraan");
        return list.get(idx);
    }

    private static int safeIntInput() {
        try {
            int i = sc.nextInt();
            sc.nextLine(); 
            return i;
        } catch (Exception e) {
            sc.nextLine(); 
            return -1;
        }
    }
}