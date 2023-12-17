import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RegisterPage{
    JFrame page = new JFrame();
    private JLabel lblName;
    private JLabel lblNIM;
    JTextField txtName;
    JTextField txtNIM;
    private JButton registerButton;
    private JPanel panel1;

    RegisterPage(){
        page.setContentPane(panel1);
        page.setTitle("REGISTER");
        page.setResizable(false);
        page.setSize(350, 200);
        page.setLocationRelativeTo(null);
        page.setVisible(true);
        page.setDefaultCloseOperation(page.DISPOSE_ON_CLOSE);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation operasi = new Operation(FirstPage.rPage);
                String data = txtName.getText() + "," + txtNIM.getText();
                if(operasi.textBoxisEmpty()){
                    JOptionPane.showMessageDialog(registerButton, "Masukkan Nama atau NIM dengan benar.", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else if (operasi.checkNamaNim(data)) {
                    JOptionPane.showMessageDialog(registerButton, "Nama atau NIM sudah ditemukan di dalam file.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }else {
                    boolean writeTofile = operasi.appendToFile(data);
                    if(writeTofile){
                        JOptionPane.showMessageDialog(registerButton, "Berhasil Register.","SUCCESS",JOptionPane.PLAIN_MESSAGE);
                        FirstPage.rPage.page.dispose();
                    }else {
                        JOptionPane.showMessageDialog(registerButton, "Gagal.","ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

}

class Operation{
    private RegisterPage rPage;
    Operation(RegisterPage rPage){
        this.rPage = rPage;
    }
    boolean checkNamaNim(String data){
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
    boolean textBoxisEmpty(){
        boolean nama = rPage.txtName.getText().matches("[a-zA-Z]+");
        boolean nim = rPage.txtNIM.getText().matches("\\d+");
        return !nama || !nim;
    }
    boolean appendToFile(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data_mahasiswa.txt" , true))) {
            // Tulis data ke file dengan newline
            writer.write(data);
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
