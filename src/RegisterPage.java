import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Kelas RegisterPage merupakan antarmuka pengguna untuk proses registrasi mahasiswa.
 * Pengguna diminta untuk memasukkan nama dan NIM, kemudian data tersebut akan disimpan
 * dalam file "data_mahasiswa.txt" setelah dilakukan validasi.
 */
public class RegisterPage {

    /** JFrame untuk halaman registrasi. */
    JFrame page = new JFrame();

    /** Label untuk menampilkan instruksi nama. */
    private JLabel lblName;

    /** Label untuk menampilkan instruksi NIM. */
    private JLabel lblNIM;

    /** TextField untuk memasukkan nama pengguna. */
    JTextField txtName;

    /** TextField untuk memasukkan NIM pengguna. */
    JTextField txtNIM;

    /** Tombol untuk melakukan registrasi. */
    private JButton registerButton;

    /** Panel utama yang berisi elemen-elemen GUI. */
    private JPanel panel1;

    /**
     * Konstruktor untuk kelas RegisterPage.
     * Inisialisasi GUI dan menambahkan ActionListener untuk tombol registrasi.
     */
    RegisterPage() {
        // Konfigurasi JFrame
        page.setContentPane(panel1);
        page.setTitle("REGISTER");
        page.setResizable(false);
        page.setSize(350, 200);
        page.setLocationRelativeTo(null);
        page.setVisible(true);
        page.setDefaultCloseOperation(page.DISPOSE_ON_CLOSE);

        // Menambahkan ActionListener untuk tombol registrasi
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation operasi = new Operation(RegisterPage.this);
                String data = txtName.getText() + "," + txtNIM.getText();

                // Validasi input pengguna dan penanganan kesalahan
                if (operasi.textBoxisEmpty()) {
                    JOptionPane.showMessageDialog(registerButton, "Masukkan Nama atau NIM dengan benar.", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else if (operasi.isNameTooLong()) {
                    JOptionPane.showMessageDialog(registerButton, "Nama tidak boleh lebih dari 50 karakter.", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else if (!operasi.isValidNIM()) {
                    JOptionPane.showMessageDialog(registerButton, "NIM harus terdiri dari 15 angka.", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else if (operasi.checkNamaNim(data)) {
                    JOptionPane.showMessageDialog(registerButton, "Nama atau NIM sudah ditemukan di dalam file.", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Jika semua validasi berhasil, lakukan registrasi
                    boolean writeToFile = operasi.appendToFile(data);
                    if (writeToFile) {
                        JOptionPane.showMessageDialog(registerButton, "Berhasil Register.", "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                        RegisterPage.this.page.dispose(); // Tutup halaman registrasi setelah registrasi berhasil
                    } else {
                        JOptionPane.showMessageDialog(registerButton, "Gagal.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}

/**
 * Kelas Operation menyediakan operasi-operasi bantuan untuk kelas RegisterPage.
 * Ini mencakup validasi input, pengecekan duplikasi data, dan penulisan ke file.
 */
class Operation {

    /** Referensi ke kelas RegisterPage. */
    private RegisterPage rPage;

    /**
     * Konstruktor untuk kelas Operation.
     *
     * @param rPage Referensi ke kelas RegisterPage.
     */
    Operation(RegisterPage rPage) {
        this.rPage = rPage;
    }

    /**
     * Memeriksa apakah Nama atau NIM sudah ada dalam file "data_mahasiswa.txt".
     *
     * @param data Data Nama dan NIM yang akan diperiksa.
     * @return true jika Nama atau NIM sudah ada, false jika belum.
     */
    boolean checkNamaNim(String data) {
        try (BufferedReader br = new BufferedReader(new FileReader("data_mahasiswa.txt"))) {
            String line;
            int lineNumber = 0;

            // Membaca setiap baris dalam file
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String nimFromFile = parts.length > 1 ? parts[1].trim() : null;
                if (nimFromFile != null && nimFromFile.equals(rPage.txtNIM.getText())) {
                    return true; // NIM sudah ada
                } else if (line.contains(data)) {
                    return true;
                }
            }
        } catch (IOException ignored) {

        }
        return false;
    }

    /**
     * Memeriksa apakah panjang Nama lebih dari 50 karakter.
     *
     * @return true jika panjang Nama lebih dari 50 karakter, false jika tidak.
     */
    boolean isNameTooLong() {
        String nama = rPage.txtName.getText().trim();
        return nama.length() > 50;
    }

    /**
     * Memeriksa apakah NIM memiliki panjang 15 karakter dan terdiri dari angka.
     *
     * @return true jika NIM valid, false jika tidak.
     */
    boolean isValidNIM() {
        String nim = rPage.txtNIM.getText().trim();
        return nim.length() == 15 && nim.matches("\\d+");
    }

    /**
     * Memeriksa apakah salah satu dari TextField kosong.
     *
     * @return true jika salah satu TextField kosong, false jika tidak.
     */
    boolean textBoxisEmpty() {
        boolean nama = rPage.txtName.getText().matches("[a-zA-Z]+");
        boolean nim = rPage.txtNIM.getText().matches("\\d+");
        return !nama || !nim;
    }

    /**
     * Menambahkan data ke dalam file "data_mahasiswa.txt".
     *
     * @param data Data yang akan ditambahkan ke dalam file.
     * @return true jika penulisan ke file berhasil, false jika tidak.
     */
    boolean appendToFile(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data_mahasiswa.txt", true))) {
            // Tulis data ke file dengan newline
            writer.write(data);
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

