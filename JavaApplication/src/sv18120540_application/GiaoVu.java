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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

/**
 *
 * @author Sy Pham
 */
public class GiaoVu extends NguoiDung {

    public GiaoVu(String tenDangNhap, String matKhau) {
        super(tenDangNhap, matKhau);
        kichHoat();
    }

    @Override
    public void kichHoat() {
        frame = new JFrame();
        frame.setBounds(100, 100, 420, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Tài khoản");
        lblNewLabel.setBounds(88, 13, 92, 16);
        frame.getContentPane().add(lblNewLabel);

        JTextField txtGioV = new JTextField();
        txtGioV.setText("Giáo vụ");
        txtGioV.setEditable(false);
        txtGioV.setBounds(229, 10, 161, 22);
        frame.getContentPane().add(txtGioV);
        txtGioV.setColumns(10);

        JButton btnNewButton = new JButton("Nhập Danh Sách Lớp");
        btnNewButton.setBounds(12, 61, 168, 25);
        btnNewButton.addActionListener((ActionEvent e) -> {
            new NhapDanhSachLop().kichHoat();
        });
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_2 = new JButton("Nhập TKB");
        btnNewButton_2.addActionListener((ActionEvent e) -> {
            new NhapThoiKhoaBieu().kichHoat();
        });
        btnNewButton_2.setBounds(12, 115, 168, 25);
        frame.getContentPane().add(btnNewButton_2);

        JButton btnNewButton_1 = new JButton("Nhập Điểm");
        btnNewButton_1.addActionListener((ActionEvent e) -> {
            new NhapDiem().kichHoat();
        });
        btnNewButton_1.setBounds(12, 163, 168, 25);
        frame.getContentPane().add(btnNewButton_1);

        JButton btnNewButton_3 = new JButton("Danh Sách Lớp");
        btnNewButton_3.addActionListener((ActionEvent e) -> {
            new XemDanhSach().kichHoat();
        });
        btnNewButton_3.setBounds(229, 61, 161, 25);
        frame.getContentPane().add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("Thời Khóa Biểu");
        btnNewButton_4.addActionListener((ActionEvent e) -> {
            new XemTKB().kichHoat();
        });
        btnNewButton_4.setBounds(229, 115, 161, 25);
        frame.getContentPane().add(btnNewButton_4);

        JButton btnNewButton_5 = new JButton("Điểm");
        btnNewButton_5.addActionListener((ActionEvent e) -> {
            new XemDiem().kichHoat();
        });
        btnNewButton_5.setBounds(229, 163, 161, 25);
        frame.getContentPane().add(btnNewButton_5);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("Cài đặt");
        menuBar.add(mnNewMenu);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("Đổi mật khẩu");
        mntmNewMenuItem_1.addActionListener((ActionEvent e) -> {
            frame.dispose();
            frame = new DoiMatKhau(tenDangNhap).getFrame();
            frame.setVisible(true);
        });
        mnNewMenu.add(mntmNewMenuItem_1);

        JMenuItem mntmNewMenuItem = new JMenuItem("Đăng xuất");
        mntmNewMenuItem.addActionListener((ActionEvent e) -> {
            frame.dispose();
            new DangNhap().kichHoat();
        });
        mnNewMenu.add(mntmNewMenuItem);

        frame.setLocationRelativeTo(null);
        //frame.setVisible(true);
    }

}
