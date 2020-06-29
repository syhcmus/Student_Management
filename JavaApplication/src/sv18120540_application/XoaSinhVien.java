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

    private JTextField studentIDText;
    private String classID;

    public void kichHoat() {
        JFrame frame = new JFrame("Xóa Sinh Viên");
        frame.setBounds(100, 100, 450, 300);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel studentIDLabel = new JLabel("Mã Sinh Viên");
        studentIDLabel.setBounds(51, 51, 89, 16);
        frame.getContentPane().add(studentIDLabel);

        studentIDText = new JTextField();
        studentIDText.setBounds(213, 48, 116, 22);
        frame.getContentPane().add(studentIDText);
        studentIDText.setColumns(10);

        JLabel classIDLabel = new JLabel("Mã Lớp");
        classIDLabel.setBounds(51, 121, 73, 16);
        frame.getContentPane().add(classIDLabel);

        String[] classIDArr = new XemDanhSach().dsLop();
        classID = classIDArr[0];
        JComboBox comboBox = new JComboBox(classIDArr);
        comboBox.addActionListener((ActionEvent e) -> {
            classID = (String) comboBox.getSelectedItem();
        });
        comboBox.setBounds(213, 118, 116, 22);
        frame.getContentPane().add(comboBox);

        JButton confirmButton = new JButton("Xác nhận");
       confirmButton.addActionListener((ActionEvent e) -> {
            xoaSinhVien();
            frame.dispose();
        });

        confirmButton.setBounds(162, 182, 97, 25);
        frame.getContentPane().add(confirmButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void xoaSinhVien() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean isSuccess = false;
        String mssv = studentIDText.getText();
        
        try {

            Lop l = (Lop) session.get(Lop.class, mssv);
            if (l != null) {
                if (l.getDanhsachlop().getMalop().equals(classID)) {
                    session.close();
                    
                    
                    isSuccess = xoaSinhVien(l);
                }

            }

            if (isSuccess == false) {
                session = HibernateUtil.getSessionFactory().openSession();
                LopMonhoc lm = (LopMonhoc) session.get(LopMonhoc.class, new LopMonhocId(mssv, classID));
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
            ex.printStackTrace();
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
