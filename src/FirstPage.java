import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FirstPage{
    public static FirstPage fPage;
    public static RegisterPage rPage;
    public static JFrame page = new JFrame();
    private JButton LOGINButton;
    private JPanel panel1;
    private JButton REGISTERButton;

    public FirstPage() {

        page.setContentPane(panel1);
        page.setTitle("Manajemen Mahasiswa");
        page.setResizable(false);
        page.setSize(350, 200);
        page.setLocationRelativeTo(null);
        page.setVisible(true);
        page.setDefaultCloseOperation(page.EXIT_ON_CLOSE);

        REGISTERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rPage = new RegisterPage();
            }
        });
        LOGINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(FileisEmpty()){
                    JOptionPane.showMessageDialog(LOGINButton, "FILE tidak ada atau kosong.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }else {
                    ListMahasiswa lMahasiswa = new ListMahasiswa();
                }
            }
        });
    }
    boolean FileisEmpty(){
        File f = new File("data_mahasiswa.txt");
        return !f.isFile() || f.length() == 0;
    }
    public static void main(String[] args) {
        MainMenu menu = new MainMenu("syarif", "123");
    }
}
