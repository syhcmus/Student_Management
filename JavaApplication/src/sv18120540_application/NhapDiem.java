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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.swing.JButton;
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

    public void kichHoat() {
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Mã Lớp");
        lblNewLabel.setBounds(39, 56, 56, 16);
        frame.getContentPane().add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(138, 53, 116, 22);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("File");
        btnNewButton.setBounds(298, 52, 97, 25);
        btnNewButton.addActionListener(new LayDuongDan());
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Nhập Điểm");
        btnNewButton_1.setBounds(152, 169, 97, 25);
        btnNewButton_1.addActionListener(new NhapDiemLop());
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
                nhapDiem();

                

            } catch (FileNotFoundException ex) {
                Logger.getLogger(NhapDiem.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NhapDiem.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void nhapDiem() throws UnsupportedEncodingException, FileNotFoundException, IOException {
        String maLop = textField.getText();
        
        if(path.isEmpty())
            return;

        if (!DanhSachLopChoMonHoc.kiemTraLopTonTai(maLop) || maLop.isEmpty()) {
            return;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();

        BufferedReader file = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(path)), "UTF-8"));

        String duLieu = file.readLine();

        while ((duLieu = file.readLine()) != null && !duLieu.isEmpty()) {
            System.out.println(duLieu);

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

}
