/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sv18120540_hibernate_pojo.Diem;
import sv18120540_hibernate_pojo.DiemId;

/**
 *
 * @author Sy Pham
 */
public class SuaDiem extends Thread {

    private DiemId id;
    private Diem diem;
    private JTable table;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;

    public SuaDiem(DiemId id, JTable table) {
        this.id = id;
        this.table = table;
        this.start();

    }

    public SuaDiem() {
    }
    
    

    @Override
    public void run() {

        if (NhapDiem.kiemTraDiemTonTai(id) == false) {
            JOptionPane.showMessageDialog(null, "MSSV Không Đúng Hoặc Không Tồn Tại Trong Lớp");
            return;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();

        diem = (Diem) session.get(Diem.class, id);
        
        session.close();

        kichHoat();
    }

    public void kichHoat() {
        JFrame frame = new JFrame("Sửa Điểm");
        frame.setBounds(100, 100, 450, 419);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("MSSV");
        lblNewLabel.setBounds(74, 39, 56, 16);
        frame.getContentPane().add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(237, 36, 116, 22);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Lớp");
        lblNewLabel_1.setBounds(74, 81, 56, 16);
        frame.getContentPane().add(lblNewLabel_1);

        textField_1 = new JTextField();
        textField_1.setBounds(237, 78, 116, 22);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Điểm GK");
        lblNewLabel_2.setBounds(74, 130, 56, 16);
        frame.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Điểm CK");
        lblNewLabel_3.setBounds(74, 176, 56, 16);
        frame.getContentPane().add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Điểm khác");
        lblNewLabel_4.setBounds(74, 230, 86, 16);
        frame.getContentPane().add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Điểm tổng");
        lblNewLabel_5.setBounds(74, 278, 116, 16);
        frame.getContentPane().add(lblNewLabel_5);

        JButton btnNewButton = new JButton("Xác nhận");
        btnNewButton.setBounds(154, 321, 97, 25);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                suaDiem();

                JOptionPane.showMessageDialog(null, "Cập Nhật Điểm thành công");
                frame.dispose();
            }
        });
        frame.getContentPane().add(btnNewButton);

        textField_2 = new JTextField();
        textField_2.setBounds(237, 127, 116, 22);
        frame.getContentPane().add(textField_2);
        textField_2.setColumns(10);

        textField_3 = new JTextField();
        textField_3.setBounds(237, 173, 116, 22);
        frame.getContentPane().add(textField_3);
        textField_3.setColumns(10);

        textField_4 = new JTextField();
        textField_4.setBounds(237, 227, 116, 22);
        frame.getContentPane().add(textField_4);
        textField_4.setColumns(10);

        textField_5 = new JTextField();
        textField_5.setBounds(237, 275, 116, 22);
        frame.getContentPane().add(textField_5);
        textField_5.setColumns(10);

        textField.setText(diem.getId().getMssv());
        textField.setEditable(false);
        textField_1.setText(diem.getId().getMalopMonhoc());
        textField_1.setEditable(false);
        textField_2.setText(diem.getDiemGk().toString());
        textField_3.setText(diem.getDiemCk().toString());
        textField_4.setText(diem.getDiemkhac().toString());
        textField_5.setText(diem.getDiemtong().toString());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void suaDiem() {
        diem.setDiemGk(BigDecimal.valueOf(Double.parseDouble(textField_2.getText())));
        diem.setDiemCk(BigDecimal.valueOf(Double.parseDouble(textField_3.getText())));
        diem.setDiemkhac(BigDecimal.valueOf(Double.parseDouble(textField_4.getText())));
        diem.setDiemtong(BigDecimal.valueOf(Double.parseDouble(textField_5.getText())));

        suaDiem(diem);
    }

    public void suaDiem(Diem diem) {

        
        
       Session session = HibernateUtil.getSessionFactory().openSession();
       
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(diem);
            transaction.commit();
        } catch (HibernateException ex) {
            //ex.printStackTrace();
            transaction.rollback();

        } finally {
            session.close();
        }
    }

}
