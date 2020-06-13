/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sv18120540_hibernate_pojo.Danhsachlop;
import sv18120540_hibernate_pojo.DanhsachlopMonhoc;
import sv18120540_hibernate_pojo.Diem;
import sv18120540_hibernate_pojo.DiemId;
import sv18120540_hibernate_pojo.Lop;

/**
 *
 * @author Sy Pham
 */
public class NhapDiem {

    private JTextField textField;
    private String path;
    private String lop;

    public void kichHoat() {
       
        JFrame frame = new JFrame("Nhập điểm");
        frame.setBounds(100, 100, 475, 300);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Mã lớp");
        lblNewLabel.setBounds(44, 66, 56, 16);
        frame.getContentPane().add(lblNewLabel);

        String[] malop = dsLop();
        lop = malop[0];
        JComboBox comboBox = new JComboBox(malop);
        comboBox.addActionListener((ActionEvent e) -> {
            lop = (String) comboBox.getSelectedItem();
        });
        comboBox.setBounds(149, 63, 125, 22);
        frame.getContentPane().add(comboBox);

        JButton btnNewButton = new JButton("File");
        btnNewButton.addActionListener(new LayDuongDan());
        btnNewButton.setBounds(323, 62, 97, 25);
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Nhập điểm");
        btnNewButton_1.addActionListener(new NhapDiemLop());
        btnNewButton_1.setBounds(149, 173, 117, 25);
        frame.getContentPane().add(btnNewButton_1);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    class LayDuongDan implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser file = new JFileChooser();
            file.showOpenDialog(null);

            if (JFileChooser.APPROVE_OPTION == 0) {
                path = file.getSelectedFile().getPath();
            }
        }

    }

    class NhapDiemLop implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                boolean isSuccess = nhapDiem();
                String message = isSuccess ? "Nhập điểm thành công" : "Nhập điểm thất bại";
                JOptionPane.showMessageDialog(null, message);

            } catch (FileNotFoundException ex) {
                //Logger.getLogger(NhapDiem.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Nhập điểm thất bại");
            } catch (IOException ex) {
                //Logger.getLogger(NhapDiem.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Nhập điểm thất bại");

            }

        }

    }

    public boolean nhapDiem() throws UnsupportedEncodingException, FileNotFoundException, IOException {
        String maLop = lop;

        if (path.isEmpty()) {
            return false;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean isSuccess = false;

        try {
            BufferedReader file = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File(path)), "UTF-8"));

            String duLieu = file.readLine();

            while ((duLieu = file.readLine()) != null && !duLieu.isEmpty()) {

                String[] cot = duLieu.split(",", 0);

                String mssv = cot[1];
                Lop lop = (Lop) session.get(Lop.class, mssv);
                DanhsachlopMonhoc lopMonhoc = new DanhsachlopMonhoc(maLop);
                String hoTen = cot[2];
                double diemGK = Double.parseDouble(cot[3]);
                double diemCK = Double.parseDouble(cot[4]);
                double diemKhac = Double.parseDouble(cot[5]);
                double diemTong = Double.parseDouble(cot[6]);

                DiemId id = new DiemId(mssv, maLop);
                Diem diem = new Diem(id, lopMonhoc, lop, hoTen, BigDecimal.valueOf(diemCK), BigDecimal.valueOf(diemCK), BigDecimal.valueOf(diemKhac), BigDecimal.valueOf(diemTong));

                nhapDiem(diem);

                isSuccess = true;
            }
        } finally {
            session.close();
            return isSuccess;
        }

    }

    public static boolean kiemTraDiemTonTai(DiemId id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Diem diem = null;

        try {
            diem = (Diem) session.get(Diem.class, id);
        } catch (HibernateException e) {
            //e.printStackTrace();
        } finally {
            session.close();
        }

        if (diem == null) {
            return false;
        }

        return true;
    }

    public void nhapDiem(Diem diem) {

        if (kiemTraDiemTonTai(diem.getId())) {
            return;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(diem);
            transaction.commit();
        } catch (HibernateException e) {
            //e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public String[] dsLop() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<String> lop = new ArrayList<>();

        try {

            ArrayList<DanhsachlopMonhoc> ds = (ArrayList<DanhsachlopMonhoc>) session.createQuery("from DanhsachlopMonhoc").list();
            for (int i = 0; i < ds.size(); i++) {
                DanhsachlopMonhoc l = ds.get(i);
                lop.add(l.getMalopMonhoc());
            }

        } finally {
            session.close();
        }

        return lop.toArray(new String[lop.size()]);

    }

}
