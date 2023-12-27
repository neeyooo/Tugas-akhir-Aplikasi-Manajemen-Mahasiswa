import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Kelas MainMenu merepresentasikan antarmuka pengguna utama aplikasi Manajemen Catatan Mahasiswa.
 * Ini mencakup fitur-fitur seperti menampilkan, menambahkan, menghapus, dan memperbarui catatan.
 */
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
    private int repeatUpdate, repeatTambah;


    /**
     * Konstruktor untuk kelas MainMenu.
     *
     * @param nama Nama pengguna yang sedang aktif.
     * @param nim NIM pengguna yang sedang aktif.
     */
    MainMenu(String nama, String nim) {
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
                try {
                    if (!e.getValueIsAdjusting()) {
                        int indexList = list1.getSelectedIndex();
                        deleteButton.setEnabled(true);
                        updateButton.setEnabled(true);
                        String[] test = getLineByIndex(indexNotes.get(indexList));
                        textField1.setText(test[1]);
                        textField2.setText(test[2]);
                        textField3.setText(test[3]);
                        textArea1.setText(test[4]);
                    }
                } catch (Exception ignored) {

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
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLine();
            }
        });
        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahLine();
            }
        });
    }
    // Metode lainnya (seperti getLineByIndex, clearField, populateList, tambahLine, deleteLine, updateLine, isValidDateFormat) tetap sama.
    public static String[] getLineByIndex(int index) {
        try (BufferedReader reader = new BufferedReader(new FileReader("notes.txt"))) {
            String line;
            int currentIndex = 0;

            // Read file line by line
            while ((line = reader.readLine()) != null) {
                // Check if the index is correct
                if (currentIndex == index) {
                    // Split the string using a comma
                    return line.split(",", 5);
                }

                currentIndex++;
            }
        } catch (IOException ignored) {

        }
        return null;
    }

    void clearField() {
        DefaultListModel clearModel = new DefaultListModel();
        clearModel.clear();
        list1.setModel(clearModel);
        indexNotes = new ArrayList<Integer>();
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textArea1.setText("");
        deleteButton.setEnabled(false);
        updateButton.setEnabled(false);
        textField1.setEnabled(false);
        textField1.setText("");
        textField2.setEnabled(false);
        textField1.setText("");
        textField3.setEnabled(false);
        textField1.setText("");
        textArea1.setEnabled(false);
        textArea1.setText("");
    }

    void populateList() {
        clearField();
        DefaultListModel<String> listModel = new DefaultListModel<>();

        try (BufferedReader br = new BufferedReader(new FileReader("notes.txt"))) {
            int currentIndex = 0;
            String line;
            while ((line = br.readLine()) != null) {
                // Split data on each line using a comma as a delimiter
                String[] tokens = line.split(",");

                // Add data at index 1 to the DefaultListModel if index 0 is equal to targetValue
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

        if (listModel.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada catatan untuk akun ini. Silakan tambahkan catatan.", "Warning", JOptionPane.PLAIN_MESSAGE);
        } else {
            list1.setModel(listModel);
        }
    }

    void tambahLine() {
        repeatTambah += 1;
        String textToAppend = "";  // Pindahkan deklarasi ke luar blok if

        if (repeatTambah == 1) {
            textField1.setEnabled(true);
            textField1.setEditable(true);
            textField1.setText("");
            textField2.setEnabled(true);
            textField2.setEditable(true);
            textField2.setText("");
            textField3.setEnabled(true);
            textField3.setEditable(true);
            textField3.setText("");
            textArea1.setEnabled(true);
            textArea1.setEditable(true);
            textArea1.setText("");
            JOptionPane.showMessageDialog(tambahButton, "Setelah memasukkan data. Silahkan klik tombol Update lagi untuk memperbarui.", "Info", JOptionPane.PLAIN_MESSAGE);
        } else {

            if (textField1.getText().isEmpty() || textField2.getText().isEmpty() || textField3.getText().isEmpty() || textArea1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(tambahButton, "Silakan isi semua kolom teks.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Tambahkan validasi panjang kata pada kolom mata kuliah (textField1)
            if (textField1.getText().length() > 15 || !textField1.getText().matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(tambahButton, "Hanya diperbolehkan 15 karakter pada kolom nama tugas. Dan hanya karakter yang diijinkan.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Tambahkan validasi panjang kata pada kolom mata kuliah (textField2)
            if (textField2.getText().length() > 20 || !textField2.getText().matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(tambahButton, "Hanya diperbolehkan 20 karakter pada kolom mata kuliah. Dan hanya karakter yang diijinkan.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (textArea1.getText().length() > 255 ) {
                JOptionPane.showMessageDialog(tambahButton, "Hanya diperbolehkan 255 karakter pada kolom deskripsi.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Validate the date format before adding
            if (isValidDateFormat(textField3.getText())) {
                textToAppend = String.format("%s,%s,%s,%s,%s", nim, textField1.getText(), textField2.getText(), textField3.getText(), textArea1.getText());
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("notes.txt", true))) {
                    // Append text to the end of the file
                    writer.write(textToAppend);
                    writer.newLine(); // Tambahkan baris baru untuk baris baru
                    JOptionPane.showMessageDialog(tambahButton, "Sukses Ditambahkan.", "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                repeatTambah = 0;
                populateList();
            } else {
                JOptionPane.showMessageDialog(tambahButton, "Format tanggal deadline tidak valid. Silakan gunakan dd-mm-yyyy.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    void deleteLine() {
        try {
            int index = indexNotes.get(list1.getSelectedIndex());
            File inputFile = new File("notes.txt");
            File temporaryFile = new File("tempFile.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(temporaryFile));

            String currentLine;
            int currentLineIndex = 0;

            // Membaca dan menyalin setiap baris ke file sementara kecuali yang akan dihapus
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

            // Ganti nama file sementara menjadi nama file aslinya
            if (!temporaryFile.renameTo(inputFile)) {
                return;
            }
            JOptionPane.showMessageDialog(deleteButton, "Sukses Menghapus.", "SUCCESS", JOptionPane.PLAIN_MESSAGE);

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(deleteButton, "File not found.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(deleteButton, "An error occurred while reading or writing the file.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }
    void updateLine(){
        repeatUpdate += 1;
        if(repeatUpdate == 1) {
            textField1.setEnabled(true); textField1.setEditable(true);
            textField2.setEnabled(true);textField2.setEditable(true);
            textField3.setEnabled(true);textField3.setEditable(true);
            textArea1.setEnabled(true);textArea1.setEditable(true);
            JOptionPane.showMessageDialog(updateButton, "Setelah memasukkan data. Silahkan klik tombol Update lagi untuk memperbarui.", "Info", JOptionPane.PLAIN_MESSAGE);
        }else {
            if (textField1.getText().isEmpty() || textField2.getText().isEmpty() || textField3.getText().isEmpty() || textArea1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(updateButton, "Silakan isi semua kolom teks.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Tambahkan validasi panjang kata pada kolom mata kuliah (textField1)
            if (textField1.getText().length() > 15 || !textField1.getText().matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(updateButton, "Hanya diperbolehkan 15 karakter pada kolom nama tugas. Dan hanya karakter yang diijinkan.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Tambahkan validasi panjang kata pada kolom mata kuliah (textField2)
            if (textField2.getText().length() > 20 || !textField2.getText().matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(updateButton, "Hanya diperbolehkan 20 karakter pada kolom mata kuliah. Dan hanya karakter yang diijinkan.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isValidDateFormat(textField3.getText())) {
                JOptionPane.showMessageDialog(updateButton, "Format tanggal tidak valid. Silakan gunakan dd-mm-yyyy.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (textArea1.getText().length() > 255 ) {
                JOptionPane.showMessageDialog(updateButton, "Hanya diperbolehkan 255 karakter pada kolom deskripsi.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                // Baca semua baris dari file ke dalam List
                Path path = Paths.get("notes.txt");
                List<String> lines = Files.readAllLines(path);

                // Periksa apakah index yang diinginkan ada dalam rentang
                int indexToUpdate = indexNotes.get(list1.getSelectedIndex());
                String newText = String.format("%s,%s,%s,%s,%s",nim,textField1.getText(),textField2.getText(),textField3.getText(),textArea1.getText());
                if (indexToUpdate >= 0 && indexToUpdate < lines.size()) {
                    // Update baris di index tertentu

                    lines.set(indexToUpdate, newText);

                    // Tulis kembali data yang telah diubah ke dalam file
                    Files.write(path, lines, StandardOpenOption.WRITE);

                }
                JOptionPane.showMessageDialog(updateButton, "Berhasil mengupdate.", "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                repeatUpdate =0 ;
                populateList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    boolean isValidDateFormat(String date) {
        if (date.length() != 10) {
            return false; // Panjang tanggal harus tepat 10 karakter (dd-MM-yyyy)
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false); // Strict validation

        try {
            // Parse the date
            Date parsedDate = sdf.parse(date);

            // Ensure that the parsed date matches the input date
            return sdf.format(parsedDate).equals(date);
        } catch (ParseException e) {
            return false; // Parsing failed
        }
    }
}
