# 🌿 HTECO - Carbon Footprint Monitoring System

> **Tugas Besar Pemrograman Berorientasi Objek (OOP)**
>
> Platform monitoring emisi kendaraan berbasis web yang mengintegrasikan Frontend modern (Next.js) dengan Backend Java (Javalin) yang menerapkan prinsip-prinsip OOP secara ketat.

![HTECO Banner](https://via.placeholder.com/1000x300?text=HTECO+Dashboard+Preview) 
*(Ganti link di atas dengan screenshot dashboard aplikasi kamu nanti)*

---

## 📖 Tentang Project

**HTECO** (High Tech Eco) adalah aplikasi yang membantu pemilik kendaraan memantau dampak lingkungan dari aktivitas berkendara mereka. Aplikasi ini menghitung emisi karbon ($CO_2$) berdasarkan jenis kendaraan, bahan bakar, dan jarak tempuh.

Project ini dibangun dengan arsitektur **Monorepo** yang memisahkan logic Frontend dan Backend, dihubungkan menggunakan REST API dengan autentikasi JWT.

---

## 🚀 Fitur Utama

### 👤 User (Pemilik Kendaraan)
* **Manajemen Garasi (OOP Polymorphism):** Menambahkan berbagai jenis kendaraan (Bensin, Solar, Listrik) dengan perilaku perhitungan emisi yang berbeda-beda.
* **Trip Logging:** Mencatat perjalanan harian.
* **Real-time Calculation:** Perhitungan emisi otomatis berdasarkan *Emission Factor* terkini.
* **Insight Dashboard:** Melihat total jarak dan total emisi bulanan.

### 🛡️ Admin
* **Master Data Management:** Mengubah faktor emisi (misal: update standar emisi listrik/grid).
* **User Monitoring:** Melihat daftar pengguna terdaftar.
* **Global Report:** Memantau total emisi dari seluruh sistem.

---

## 🛠️ Tech Stack

### Backend (Java)
* **Language:** Java JDK 17+
* **Framework:** [Javalin](https://javalin.io/) (Lightweight Web Framework)
* **Security:** JWT (JSON Web Token) & BCrypt
* **Architecture:** MVC / DAO Pattern

### Frontend (JavaScript)
* **Framework:** Next.js 14 (App Router)
* **Styling:** Tailwind CSS
* **State Management:** React Context API
* **Icons/Font:** Google Fonts (JetBrains Mono) & Emojis

---

## 🧠 Penerapan Konsep OOP

Project ini dinilai berdasarkan implementasi konsep OOP pada Backend Java:

1.  **Inheritance (Pewarisan):**
    * `User` adalah parent dari `Admin` dan `PemilikKendaraan`.
    * `Kendaraan` adalah parent dari `KendaraanBensin`, `KendaraanSolar`, dan `KendaraanListrik`.
2.  **Polymorphism (Banyak Bentuk):**
    * Method `hitungEmisiPerPerjalanan()` di-override oleh setiap jenis kendaraan dengan rumus berbeda.
    * Sistem memperlakukan semua tipe mobil sebagai objek `Kendaraan` di dalam List.
3.  **Encapsulation (Pembungkusan):**
    * Semua atribut entitas bersifat `private`.
    * Akses data melalui *Getter* dan *Setter*.
4.  **Abstraction (Abstraksi):**
    * Penggunaan `abstract class` pada entitas dasar yang tidak boleh diinstansiasi langsung.

---

## 📂 Struktur Folder

```bash
HTECO/
├── backend/            # Source code Java (Javalin)
│   ├── src/main/java/
│   │   ├── controller/ # Handler request HTTP
│   │   ├── model/      # Entitas OOP (User, Kendaraan, Trip)
│   │   ├── util/       # JWT Generator, Database Config
│   │   └── App.java    # Entry Point (Main)
│   └── pom.xml         # Maven Config
│
├── frontend/           # Source code Next.js
│   ├── app/            # Pages & Layouts (Admin & User)
│   ├── components/     # Reusable UI Components
│   ├── context/        # State Management (Auth)
│   └── package.json    # NPM Config
│
└── README.md           # Dokumentasi Project
