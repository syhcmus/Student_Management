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
public class SuaDiem //extends Thread 
{

    private DiemId id;
    private Diem diem;
    private JTable table;
    private JTextField studentIDText;
    private JTextField classIDText;
    private JTextField immediateGradeText;
    private JTextField finalGradeText;
    private JTextField otherGradeText;
    private JTextField resultGradeText;

    public SuaDiem(DiemId id, JTable table) {
        this.id = id;
        this.table = table;
        if(kiemTraThongTinSinhVien())
            kichHoat();

    }

    
    public boolean kiemTraThongTinSinhVien(){
        
         if (NhapDiem.kiemTraDiemTonTai(id) == false) {
            JOptionPane.showMessageDialog(null, "MSSV Không Đúng Hoặc Không Tồn Tại Trong Lớp");
            return false;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();

        diem = (Diem) session.get(Diem.class, id);
        
        session.close();
        
        return true;
        
    }
    

    public void kichHoat() {
        JFrame frame = new JFrame("Sửa Điểm");
        frame.setBounds(100, 100, 450, 419);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel studentIDLabel = new JLabel("MSSV");
        studentIDLabel.setBounds(74, 39, 56, 16);
        frame.getContentPane().add(studentIDLabel);

        studentIDText = new JTextField();
        studentIDText.setBounds(237, 36, 116, 22);
        frame.getContentPane().add(studentIDText);
        studentIDText.setColumns(10);

        JLabel classIDLabel = new JLabel("Lớp");
        classIDLabel.setBounds(74, 81, 56, 16);
        frame.getContentPane().add(classIDLabel);

        classIDText = new JTextField();
        classIDText.setBounds(237, 78, 116, 22);
        frame.getContentPane().add(classIDText);
        classIDText.setColumns(10);

        JLabel immediateGradeLabel = new JLabel("Điểm GK");
        immediateGradeLabel.setBounds(74, 130, 56, 16);
        frame.getContentPane().add(immediateGradeLabel);

        JLabel finalGradeLabel = new JLabel("Điểm CK");
        finalGradeLabel.setBounds(74, 176, 56, 16);
        frame.getContentPane().add(finalGradeLabel);

        JLabel otherGradeLabel = new JLabel("Điểm khác");
        otherGradeLabel.setBounds(74, 230, 86, 16);
        frame.getContentPane().add(otherGradeLabel);

        JLabel resultGradeLabel = new JLabel("Điểm tổng");
        resultGradeLabel.setBounds(74, 278, 116, 16);
        frame.getContentPane().add(resultGradeLabel);

        JButton confirmButton = new JButton("Xác nhận");
        confirmButton.setBounds(154, 321, 97, 25);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                suaDiem();

                JOptionPane.showMessageDialog(null, "Cập Nhật Điểm thành công");
                frame.dispose();
            }
        });
        frame.getContentPane().add(confirmButton);

        immediateGradeText = new JTextField();
        immediateGradeText.setBounds(237, 127, 116, 22);
        frame.getContentPane().add(immediateGradeText);
        immediateGradeText.setColumns(10);

        finalGradeText = new JTextField();
        finalGradeText.setBounds(237, 173, 116, 22);
        frame.getContentPane().add(finalGradeText);
        finalGradeText.setColumns(10);

        otherGradeText = new JTextField();
        otherGradeText.setBounds(237, 227, 116, 22);
        frame.getContentPane().add(otherGradeText);
        otherGradeText.setColumns(10);

        resultGradeText = new JTextField();
        resultGradeText.setBounds(237, 275, 116, 22);
        frame.getContentPane().add(resultGradeText);
        resultGradeText.setColumns(10);

        studentIDText.setText(diem.getId().getMssv());
        studentIDText.setEditable(false);
        classIDText.setText(diem.getId().getMalopMonhoc());
        classIDText.setEditable(false);
        immediateGradeText.setText(diem.getDiemGk().toString());
        finalGradeText.setText(diem.getDiemCk().toString());
        otherGradeText.setText(diem.getDiemkhac().toString());
        resultGradeText.setText(diem.getDiemtong().toString());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void suaDiem() {
        diem.setDiemGk(BigDecimal.valueOf(Double.parseDouble(immediateGradeText.getText())));
        diem.setDiemCk(BigDecimal.valueOf(Double.parseDouble(finalGradeText.getText())));
        diem.setDiemkhac(BigDecimal.valueOf(Double.parseDouble(otherGradeText.getText())));
        diem.setDiemtong(BigDecimal.valueOf(Double.parseDouble(resultGradeText.getText())));

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
