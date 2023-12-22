## Nama Proyek: Manajemen Mahasiswa

### Deskripsi

Proyek ini adalah aplikasi sederhana untuk manajemen mahasiswa. Aplikasi ini memungkinkan pengguna untuk mendaftar, masuk, dan melihat atau mengelola catatan-catatan penting.

### Struktur Proyek

Proyek terdiri dari beberapa kelas:

1. **FirstPage**: Kelas ini menangani tampilan halaman pertama aplikasi. Pengguna dapat memilih untuk masuk atau mendaftar.

2. **RegisterPage**: Kelas ini menangani proses pendaftaran mahasiswa baru. Data mahasiswa baru akan disimpan dalam file "data_mahasiswa.txt".

3. **ListMahasiswa**: Kelas ini menampilkan daftar mahasiswa yang telah terdaftar dan memungkinkan pengguna untuk memilih akun mahasiswa tertentu.

4. **MainMenu**: Kelas ini merupakan halaman utama setelah masuk. Pengguna dapat melihat, menambah, menghapus, atau memperbarui catatan-catatan mereka.

### Cara Menjalankan Aplikasi

1. Pastikan Java Development Kit (JDK) terinstal di sistem Anda.
2. Compile dan jalankan `FirstPage.java` untuk memulai aplikasi.

```bash
javac FirstPage.java
java FirstPage
```

### Petunjuk Penggunaan

1. Pada halaman pertama, pengguna dapat memilih untuk masuk atau mendaftar.
2. Jika memilih mendaftar, pengguna akan diarahkan ke halaman pendaftaran. Isi data mahasiswa baru dan klik "Register".
3. Jika memilih masuk, pengguna dapat memilih akun dari daftar mahasiswa yang telah terdaftar.
4. Setelah masuk, pengguna dapat melihat, menambah, menghapus, atau memperbarui catatan-catatan.

### Struktur File

1. **data_mahasiswa.txt**: File ini menyimpan data mahasiswa yang telah terdaftar.
2. **notes.txt**: File ini menyimpan catatan-catatan yang terkait dengan setiap mahasiswa.

### Kontribusi
    - Syarif Hermawan  
	- Muhammad Izzat Farahidi  
	- Panji Kusuma Adji Prasetyo
Jika Anda ingin berkontribusi pada proyek ini, silakan buka [Panduan Kontribusi](CONTRIBUTING.md).

### Lisensi

Proyek ini dilisensikan di bawah [Lisensi Nama Lisensi]. Lihat file [LISENSI](LISENSI.md) untuk detail lebih lanjut.