import javax.swing.*;

public class MainMenu {
    JFrame page = new JFrame();
    private JLabel lblWelcome;
    private JPanel panel1;
    private String nama, nim;
    MainMenu(String nama, String nim){
        this.nama = nama;
        this.nim = nim;
        ListMahasiswa.page.dispose();
        FirstPage.page.dispose();
        page.setContentPane(panel1);
        page.setTitle("Main Menu");
        page.setResizable(false);
        page.setSize(1920, 1080);
        page.setLocationRelativeTo(null);
        page.setVisible(true);
        page.setDefaultCloseOperation(page.EXIT_ON_CLOSE);
        lblWelcome.setText("Welcome, " + nama + " | " + nim);

    }
}
