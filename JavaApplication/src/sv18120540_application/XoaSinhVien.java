/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sv18120540_hibernate_pojo.Lop;
import sv18120540_hibernate_pojo.LopMonhoc;
import sv18120540_hibernate_pojo.LopMonhocId;

/**
 *
 * @author Sy Pham
 */
public class XoaSinhVien {

    private JTextField textField;
    private JTextField textField_1;

    public void kichHoat() {
        JFrame frame = new JFrame("Xóa Sinh Viên");
        frame.setBounds(100, 100, 450, 300);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Mã Sinh Viên");
        lblNewLabel.setBounds(51, 51, 89, 16);
        frame.getContentPane().add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(213, 48, 116, 22);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Mã Lớp");
        lblNewLabel_1.setBounds(51, 121, 73, 16);
        frame.getContentPane().add(lblNewLabel_1);

        textField_1 = new JTextField();
        textField_1.setBounds(213, 118, 116, 22);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        JButton btnNewButton = new JButton("Xác nhận");
        btnNewButton.addActionListener((ActionEvent e) -> {
            xoaSinhVien();
        });

        btnNewButton.setBounds(162, 182, 97, 25);
        frame.getContentPane().add(btnNewButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void xoaSinhVien() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean isSuccess = false;
        String mssv = textField.getText();
        String maLop = textField_1.getText();
        try {

            Lop l = (Lop) session.get(Lop.class, mssv);
            if (l != null) {
                if (l.getDanhsachlop().getMalop().equals(maLop)) {
                    xoaSinhVien(l);
                    isSuccess = true;
                }

            }

            if (isSuccess == false) {
                LopMonhoc lm = (LopMonhoc) session.get(LopMonhoc.class, new LopMonhocId(mssv, maLop));

                if (lm != null) {
                    xoaSinhVien(lm);
                    isSuccess = true;
                }

            }

        } finally {
            session.close();
            String mess = isSuccess ? "Xóa Sinh Viên thành công" : "Thông tin Sinh Viên không đúng";
            JOptionPane.showMessageDialog(null, mess);
        }

       
    }

    public void xoaSinhVien(Lop sv) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(sv);
            transaction.commit();
        } catch (HibernateException ex) {
            transaction.rollback();
            //System.err.println(ex);
        } finally {
            session.close();
        }

    }

    public void xoaSinhVien(LopMonhoc sv) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(sv);
            transaction.commit();
        } catch (HibernateException ex) {
            transaction.rollback();
            //System.err.println(ex);
        } finally {
            session.close();
        }

    }

}
