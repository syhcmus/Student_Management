/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sv18120540_hibernate_pojo.Taikhoan;

/**
 *
 * @author Sy Pham
 */
public class DoiMatKhau {

    private JFrame frame;
    private JPasswordField currentPassword;
    private String tenDangNhap;
    private JPasswordField newPassword;

    public DoiMatKhau(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
        kichHoat();
        
    }

    public JFrame getFrame() {
        return frame;
    }
    
    
    

    public void kichHoat() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Mật Khẩu cũ");
        lblNewLabel.setBounds(56, 73, 81, 16);
        frame.getContentPane().add(lblNewLabel);

        currentPassword = new JPasswordField();
        currentPassword.setBounds(216, 70, 137, 22);
        frame.getContentPane().add(currentPassword);

        JLabel lblNewLabel_1 = new JLabel("Mật khẩu mới");
        lblNewLabel_1.setBounds(56, 128, 81, 16);
        frame.getContentPane().add(lblNewLabel_1);

        newPassword = new JPasswordField();
        newPassword.setBounds(216, 125, 137, 22);
        frame.getContentPane().add(newPassword);

        JButton btnNewButton = new JButton("Xác Nhận");
        btnNewButton.addActionListener((ActionEvent e) -> {
            capNhatMK();
            frame.dispose();
            frame = taiKhoanCu().getFrame();
            frame.setVisible(true);
        });
        btnNewButton.setBounds(145, 178, 97, 25);
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Quay Lại");
        btnNewButton_1.addActionListener((ActionEvent e) -> {
            frame.dispose();
            frame = taiKhoanCu().getFrame();
            frame.setVisible(true);
        });
        btnNewButton_1.setBounds(305, 215, 97, 25);
        frame.getContentPane().add(btnNewButton_1);

        frame.setLocationRelativeTo(null);
    }

    public void capNhatMK(Taikhoan t) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
            transaction.rollback();

        } finally {
            session.close();
        }

    }

    public void capNhatMK() {

        Taikhoan tk = xacNhanTKCu();
        if (tk == null) {
            JOptionPane.showMessageDialog(null, "Mật khẩu cũ không đúng");
            return;
        }
        
        tk.setMatkhau(new String(newPassword.getPassword()));

        capNhatMK(tk);

    }

    public Taikhoan xacNhanTKCu() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Taikhoan tk = null;

        try {

            Taikhoan t = (Taikhoan) session.get(Taikhoan.class, tenDangNhap);
            String mk = new String(currentPassword.getPassword());

            if (t.getMatkhau().equals(mk)) {
                tk = t;

            }
        } finally {
            session.close();
        }

        return tk;
    }

    public NguoiDung taiKhoanCu() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Taikhoan tk;
        NguoiDung n;

        try {

            tk = (Taikhoan) session.get(Taikhoan.class, tenDangNhap);
           
        } finally {
            session.close();
        }
        
        if(tk.getLoaitk().equals("0"))
            n = new SinhVien(tk.getTendangnhap(), tk.getMatkhau());
        else
            n = new GiaoVu(tk.getTendangnhap(), tk.getMatkhau());
            
        
        return n;
    }

}
