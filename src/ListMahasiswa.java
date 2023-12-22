import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * Kelas ListMahasiswa merupakan antarmuka pengguna untuk pemilihan akun mahasiswa.
 * Pengguna dapat memilih akun dari daftar yang ditampilkan dalam JComboBox.
 */
public class ListMahasiswa {

    /** JFrame untuk halaman pemilihan akun. */
    public static JFrame page = new JFrame();

    /** Panel utama yang berisi elemen-elemen GUI. */
    private JPanel panel1;

    /** JComboBox untuk menampilkan daftar akun mahasiswa. */
    private JComboBox comboBox1;

    /** Label untuk menampilkan informasi akun yang dipilih. */
    private JLabel lblMahasiswa;

    /** Tombol untuk melakukan login dengan akun yang dipilih. */
    private JButton LOGINButton;

    /**
     * Konstruktor untuk kelas ListMahasiswa.
     * Menginisialisasi GUI, menambahkan data akun ke dalam JComboBox, dan menangani aksi login.
     */
    ListMahasiswa() {
        // Mengisi ComboBox dengan daftar akun mahasiswa
        populateComboBox();

        // Konfigurasi JFrame
        page.setContentPane(panel1);
        page.setTitle("Pemilihan Akun");
        page.setResizable(false);
        page.setSize(350, 200);
        page.setLocationRelativeTo(null);
        page.setVisible(true);
        page.setDefaultCloseOperation(page.DISPOSE_ON_CLOSE);

        // Menambahkan ActionListener untuk ComboBox
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblMahasiswa.setText((String) comboBox1.getSelectedItem());
            }
        });

        // Menambahkan ActionListener untuk tombol LOGIN
        LOGINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(lblMahasiswa.getText(), "none, pilih item dalam combobox")) {
                    JOptionPane.showMessageDialog(LOGINButton, "Pilih akun terlebih dahulu.", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Memisahkan data akun menjadi nama dan NIM
                    String[] data = lblMahasiswa.getText().split(",");
                    // Membuat instance MainMenu dengan data akun yang dipilih
                    MainMenu mainMenu = new MainMenu(data[0], data[1]);
                }
            }
        });
    }

    /**
     * Mengisi ComboBox dengan data akun mahasiswa dari file "data_mahasiswa.txt".
     */
    private void populateComboBox() {
        try (BufferedReader br = new BufferedReader(new FileReader("data_mahasiswa.txt"))) {
            String line;

            // Membaca setiap baris dalam file
            while ((line = br.readLine()) != null) {
                // Menambahkan setiap baris sebagai item ComboBox
                comboBox1.addItem(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
