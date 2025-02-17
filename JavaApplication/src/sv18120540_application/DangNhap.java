/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.hibernate.Session;
import sv18120540_hibernate_pojo.Taikhoan;

/**
 *
 * @author Sy Pham
 */
public class DangNhap {

    JFrame mFrame;
    private JTextField textField;
    private JPasswordField passwordField;
    JFrame frame;
    private static boolean isCreatedDefaultAccount;

    public DangNhap() {
        if (isCreatedDefaultAccount == false) {
            TaoTaiKhoan.taoTaiKhoan(new Taikhoan("giaovu", "giaovu", "1"));
            isCreatedDefaultAccount = true;
        }
    }

    void kichHoat() {

        frame = new JFrame("Đăng Nhập");
        frame.setBounds(100, 100, 450, 266);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("User");
        lblNewLabel.setBounds(51, 70, 56, 16);
        frame.getContentPane().add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(146, 67, 116, 22);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Password");
        lblNewLabel_1.setBounds(51, 137, 66, 16);
        frame.getContentPane().add(lblNewLabel_1);

        passwordField = new JPasswordField();
        passwordField.setBounds(146, 134, 116, 22);
        frame.getContentPane().add(passwordField);

        JCheckBox chckbxNewCheckBox = new JCheckBox("Show Password");
        chckbxNewCheckBox.addActionListener((ActionEvent e) -> {
            if (chckbxNewCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });
        chckbxNewCheckBox.setBounds(146, 165, 124, 25);
        frame.getContentPane().add(chckbxNewCheckBox);

        JButton btnNewButton = new JButton("Đăng Nhập");
        btnNewButton.setBounds(298, 82, 97, 56);
        btnNewButton.addActionListener(new DangNhapTK());
        frame.getContentPane().add(btnNewButton);

        mFrame = frame;
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.setLocationRelativeTo(null);
        mFrame.setVisible(true);
    }

    class DangNhapTK implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            NguoiDung tk = kiemTraDangNhap();
            if (tk != null) {
                mFrame.dispose();
                mFrame = tk.getFrame();
                mFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Thông tin đăng nhập không chính xác");
            }
        }

    }

    public NguoiDung kiemTraDangNhap() {
        NguoiDung n = null;

        if (TaoTaiKhoan.kiemTraTKTonTai(textField.getText())) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Taikhoan tk = (Taikhoan) session.get(Taikhoan.class, textField.getText());
            String password = new String(passwordField.getPassword());

            if (tk.getMatkhau().equals(password)) {
                String loaiTk = tk.getLoaitk();
                if (loaiTk.equals("0")) {
                    n = new SinhVien(tk.getTendangnhap(), tk.getMatkhau());
                } else {
                    n = new GiaoVu(tk.getTendangnhap(), tk.getMatkhau());
                }
            }

            session.close();
        }

        return n;
    }

    
}
