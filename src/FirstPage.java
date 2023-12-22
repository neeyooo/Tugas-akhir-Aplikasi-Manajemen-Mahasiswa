import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Kelas FirstPage merepresentasikan titik masuk utama untuk aplikasi Manajemen Mahasiswa.
 * Ini mencakup antarmuka pengguna grafis (GUI) sederhana dengan tombol login dan registrasi.
 */
public class FirstPage {

    /** Instance dari kelas FirstPage. */
    public static FirstPage fPage;

    /** Instance dari kelas RegisterPage. */
    public static RegisterPage rPage;

    /** JFrame utama untuk aplikasi. */
    public static JFrame page = new JFrame();

    /** Tombol yang digunakan untuk login pengguna. */
    private JButton LOGINButton;

    /** Panel yang berisi komponen GUI. */
    private JPanel panel1;

    /** Tombol yang digunakan untuk registrasi pengguna. */
    private JButton REGISTERButton;

    /**
     * Membangun objek FirstPage baru, menginisialisasi GUI, dan menyiapkan pendengar acara untuk tombol.
     */
    public FirstPage() {

        // Inisialisasi JFrame dan atur propertinya
        page.setContentPane(panel1);
        page.setTitle("Manajemen Mahasiswa");
        page.setResizable(false);
        page.setSize(350, 200);
        page.setLocationRelativeTo(null);
        page.setVisible(true);
        page.setDefaultCloseOperation(page.EXIT_ON_CLOSE);

        // Tambahkan ActionListener untuk REGISTERButton
        REGISTERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rPage = new RegisterPage();
            }
        });

        // Tambahkan ActionListener untuk LOGINButton
        LOGINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (FileisEmpty()) {
                    JOptionPane.showMessageDialog(LOGINButton, "FILE tidak ada atau kosong.", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    ListMahasiswa lMahasiswa = new ListMahasiswa();
                }
            }
        });
    }

    /**
     * Memeriksa apakah file "data_mahasiswa.txt" kosong atau tidak ada.
     *
     * @return true jika file kosong atau tidak ada, false sebaliknya.
     */
    boolean FileisEmpty() {
        File f = new File("data_mahasiswa.txt");
        return !f.isFile() || f.length() == 0;
    }

    /**
     * Metode utama untuk meluncurkan aplikasi Manajemen Mahasiswa.
     *
     * @param args Argumen baris perintah (tidak digunakan dalam aplikasi ini).
     */
    public static void main(String[] args) {
        fPage = new FirstPage();
    }
}
