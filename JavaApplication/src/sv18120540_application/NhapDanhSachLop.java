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
import sv18120540_hibernate_pojo.Lop;

/**
 *
 * @author Sy Pham
 */
public class NhapDanhSachLop {

    private JTextField textField;
    private String path;

    public void kichHoat() {
        JFrame frame = new JFrame("Nhập Danh Sách Lớp");
        frame.setBounds(100, 100, 450, 300);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Mã Lớp");
        lblNewLabel.setBounds(45, 65, 56, 16);
        frame.getContentPane().add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(136, 62, 116, 22);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("File");
        btnNewButton.setBounds(296, 61, 70, 25);
        btnNewButton.addActionListener(new LayDuongDan());
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Tạo Danh Sách");
        btnNewButton_1.setBounds(121, 153, 153, 25);
        btnNewButton_1.addActionListener(new TaoDanhSach());
        frame.getContentPane().add(btnNewButton_1);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    class TaoDanhSach implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                
                if (!textField.getText().isEmpty() && path != null) {
                    boolean isSuccess = themSinhVienVaoLop(path);
                    String message = isSuccess ? "Tạo Danh Sách Thành Công" : "Tạo Danh Sách Không Thành Công";

                    TaoTaiKhoan.taoTaiKhoan(textField.getText());

                    JOptionPane.showMessageDialog(null, message);
                    textField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Tạo Danh Sách Không Thành Công");
                }

            } catch (UnsupportedEncodingException ex) {
                JOptionPane.showMessageDialog(null, "Tạo Danh Sách Không Thành Công");
                textField.setText("");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Tạo Danh Sách Thành Công - Lỗi file");
                textField.setText("");
            }
        }

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

    public static boolean kiemTraSVTonTai(String maSinhVien) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Lop lop = null;

        try {
            lop = (Lop) session.get(Lop.class, maSinhVien);
        } catch (HibernateException e) {
            //e.printStackTrace();
        } finally {
            session.close();
        }

        if (lop == null) {
            return false;
        }

        return true;
    }

    public static boolean kiemTraLopTonTai(String maLop) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Danhsachlop dsLop = null;

        try {
            dsLop = (Danhsachlop) session.get(Danhsachlop.class, maLop);
        } catch (HibernateException e) {
            //e.printStackTrace();
        } finally {
            session.close();
        }

        if (dsLop == null) {
            return false;
        }

        return true;
    }

    public static boolean themSinhVienVaoLop(Lop lop) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        if (kiemTraSVTonTai(lop.getMssv()) == true) {
            return false;
        }

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(lop);
            transaction.commit();
        } catch (HibernateException e) {
            //e.printStackTrace();
        } finally {
            session.close();
        }

        return true;
    }

    public boolean themSinhVienVaoLop(String duongDan) throws FileNotFoundException, UnsupportedEncodingException, IOException {

        boolean isSuccess = false;

        try {
            String malop = textField.getText();
            Danhsachlop dsLop = new Danhsachlop(malop);
            themLopVaoDanhSach(dsLop);

            BufferedReader file = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File(duongDan)), "UTF-8"));

            String duLieu = file.readLine();

            while ((duLieu = file.readLine()) != null && duLieu.isEmpty() == false) {

                String[] cot = duLieu.split(",", 0);

                Lop lop = new Lop();
                lop.setMssv(cot[1]);
                lop.setHoten(cot[2]);
                lop.setGioitinh(cot[3]);
                lop.setCmnnd(cot[4]);
                lop.setDanhsachlop(dsLop);

                themSinhVienVaoLop(lop);

            }
            
            isSuccess = true;
        } finally {
            return isSuccess;
        }

    }

    public static void themLopVaoDanhSach(Danhsachlop lop) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        if (kiemTraLopTonTai(lop.getMalop()) == true) {
            return;
        }

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(lop);
            transaction.commit();
        } catch (HibernateException e) {
            //e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
