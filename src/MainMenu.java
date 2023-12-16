import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainMenu {
    private static ArrayList<Integer> indexNotes;
    JFrame page = new JFrame();
    private JLabel lblWelcome;
    private JPanel panel1;
    private JList<String> list1;
    private JButton tambahButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JPanel panel2;
    private JPanel panel3;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextArea textArea1;
    private String nama, nim;
    MainMenu(String nama, String nim){
        this.nama = nama;
        this.nim = nim;
        populateList();
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        ListMahasiswa.page.dispose();
        FirstPage.page.dispose();
        page.setContentPane(panel1);
        page.setTitle("Main Menu");
        page.setResizable(false);
        page.setSize(960, 540);
        page.setLocationRelativeTo(null);
        page.setVisible(true);
        page.setDefaultCloseOperation(page.EXIT_ON_CLOSE);
        lblWelcome.setText("Welcome, " + nama + " | " + nim);

        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try{
                if (!e.getValueIsAdjusting()) {
                    int indexList = list1.getSelectedIndex();
                    deleteButton.setEnabled(true);
                    updateButton.setEnabled(true);
                    String [] test = getLineByIndex(indexList);
                    textField1.setText(test[1]);
                    textField2.setText(test[2]);
                    textField3.setText(test[3]);
                    textArea1.setText(test[4]);
                }}catch (Exception ignored){

                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteLine();
                populateList();
            }
        });
    }
    public static String[] getLineByIndex(int index) {
        try (BufferedReader reader = new BufferedReader(new FileReader("notes.txt"))) {
            String line;
            int currentIndex = 0;

            // Membaca file baris per baris
            while ((line = reader.readLine()) != null) {
                // Mengecek apakah indeks sudah sesuai
                if (currentIndex == index) {
                    // Pecah string menggunakan tanda koma
                    return line.split(",", 5);
                }

                currentIndex++;
            }
        } catch (IOException ignored) {

        }
        return null;
    }

    void populateList() {
        indexNotes = new ArrayList<Integer>();
        DefaultListModel clearModel = new DefaultListModel();
        clearModel.clear();
        list1.setModel(clearModel);
        DefaultListModel<String> listModel = new DefaultListModel<>();

        try (BufferedReader br = new BufferedReader(new FileReader("notes.txt"))) {
            int currentIndex = 0;
            String line;
            while ((line = br.readLine()) != null) {
                // Memisahkan data pada setiap baris menggunakan koma sebagai delimiter
                String[] tokens = line.split(",");

                // Menambahkan data pada indeks ke-1 ke dalam DefaultListModel jika indeks ke-0 sama dengan targetValue
                if (tokens.length > 0 && tokens[0].trim().equals(nim)) {
                    indexNotes.add(currentIndex);
                    if (tokens.length > 1) {
                        listModel.addElement(tokens[1].trim());
                    }
                }
                currentIndex++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(listModel.isEmpty()){
            JOptionPane.showMessageDialog(deleteButton, "Belum ada notes di akun ini. Silahkan tambah notes", "Warning", JOptionPane.PLAIN_MESSAGE);
        }else {
            list1.setModel(listModel);
        }
    }
    void deleteLine(){
        try {
            int index = indexNotes.get(list1.getSelectedIndex());
            File inputFile = new File("notes.txt");
            File temporaryFile = new File("tempFile.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(temporaryFile));

            String currentLine;
            int currentLineIndex = 0;

            // Baca dan salin setiap baris ke file sementara kecuali yang akan dihapus
            while ((currentLine = reader.readLine()) != null) {
                if (currentLineIndex != index) {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                currentLineIndex++;
            }

            writer.close();
            reader.close();

            // Hapus file asli
            if (!inputFile.delete()) {
                return;
            }

            // Ganti nama file sementara menjadi nama file asli
            if (!temporaryFile.renameTo(inputFile)) {
                return;
            }
            JOptionPane.showMessageDialog(deleteButton, "Berhasil menghapus.", "SUCCESS", JOptionPane.PLAIN_MESSAGE);


        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(deleteButton, "File tidak ditemukan.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(deleteButton, "Terjadi kesalahan saat membaca atau menulis file.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

}
