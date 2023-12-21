import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class ListMahasiswa {
    public static JFrame page = new JFrame();
    private JPanel panel1;
    private JComboBox comboBox1;
    private JLabel lblMahasiswa;
    private JButton LOGINButton;

    ListMahasiswa(){
        populateComboBox();
        page.setContentPane(panel1);
        page.setTitle("Pemilihan akun");
        page.setResizable(false);
        page.setSize(350, 200);
        page.setLocationRelativeTo(null);
        page.setVisible(true);
        page.setDefaultCloseOperation(page.DISPOSE_ON_CLOSE);

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblMahasiswa.setText((String) comboBox1.getSelectedItem());
            }
        });

        LOGINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(lblMahasiswa.getText(), "none, pilih item dalam combobox")){
                    JOptionPane.showMessageDialog(LOGINButton, "Pilih akun terlebih dahulu.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }else {
                    String[] data = lblMahasiswa.getText().split(",");
                    MainMenu mainMenu = new MainMenu(data[0], data[1]);
                }
            }
        });
    }
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
