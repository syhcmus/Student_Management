/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
    private String maLop;

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

        String[] lop = new XemDanhSach().dsLop();
        maLop = lop[0];
        JComboBox comboBox = new JComboBox(lop);
        comboBox.addActionListener((ActionEvent e) -> {
            maLop = (String) comboBox.getSelectedItem();
        });
        comboBox.setBounds(213, 118, 116, 22);
        frame.getContentPane().add(comboBox);

        JButton btnNewButton = new JButton("Xác nhận");
        btnNewButton.addActionListener((ActionEvent e) -> {
            xoaSinhVien();
            frame.dispose();
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
        
        try {

            Lop l = (Lop) session.get(Lop.class, mssv);
            if (l != null) {
                if (l.getDanhsachlop().getMalop().equals(maLop)) {
                    session.close();
                    
                    
                    isSuccess = xoaSinhVien(l);
                }

            }

            if (isSuccess == false) {
                session = HibernateUtil.getSessionFactory().openSession();
                LopMonhoc lm = (LopMonhoc) session.get(LopMonhoc.class, new LopMonhocId(mssv, maLop));
                session.close();

                if (lm != null) {
                    
                    isSuccess =  xoaSinhVien(lm);
                }

            }

        } finally {
            if(session.isOpen())
                session.close();
            String mess = isSuccess ? "Xóa Sinh Viên thành công " : "Xóa Sinh Viên Không thành công";
            JOptionPane.showMessageDialog(null, mess);
        }

    }

    public boolean xoaSinhVien(Lop sv) {

        boolean isSuccess = false;
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(sv);
            transaction.commit();
            isSuccess = true;
        } catch (HibernateException ex) {
            transaction.rollback();
            //System.err.println(ex);
        } finally {
            session.close();
            return isSuccess;
        }
        
         

    }

    public boolean xoaSinhVien(LopMonhoc sv) {

         boolean isSuccess = false;
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(sv);
            transaction.commit();
            isSuccess = true;
        } catch (HibernateException ex) {
            transaction.rollback();
            //System.err.println(ex);
        } finally {
            session.close();
            return isSuccess;
        }

    }

}
