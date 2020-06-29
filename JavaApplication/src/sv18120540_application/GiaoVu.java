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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

/**
 *
 * @author Sy Pham
 */
public final class GiaoVu extends NguoiDung {

    public GiaoVu(String username, String matKhau) {
        super(username, matKhau);
        kichHoat();
    }

   

    @Override
    public void kichHoat() {
        frame = new JFrame();
        frame.setBounds(100, 100, 420, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel accountLabel = new JLabel("Tài khoản");
        accountLabel.setBounds(88, 13, 92, 16);
        frame.getContentPane().add(accountLabel);

        JTextField nameAccountLabel = new JTextField();
        nameAccountLabel.setText("Giáo vụ");
        nameAccountLabel.setEditable(false);
        nameAccountLabel.setBounds(229, 10, 161, 22);
        frame.getContentPane().add(nameAccountLabel);
        nameAccountLabel.setColumns(10);

        JButton enterClassButton = new JButton("Nhập Danh Sách Lớp");
        enterClassButton.setBounds(12, 61, 168, 25);
        enterClassButton.addActionListener((ActionEvent e) -> {
            new NhapDanhSachLop().kichHoat();
        });
        frame.getContentPane().add(enterClassButton);

        JButton enterSheduleButton = new JButton("Nhập TKB");
        enterSheduleButton.addActionListener((ActionEvent e) -> {
            new NhapThoiKhoaBieu().kichHoat();
        });
        enterSheduleButton.setBounds(12, 115, 168, 25);
        frame.getContentPane().add(enterSheduleButton);

        JButton enterGradeButton = new JButton("Nhập Điểm");
        enterGradeButton.addActionListener((ActionEvent e) -> {
            new NhapDiem().kichHoat();
        });
        enterGradeButton.setBounds(12, 163, 168, 25);
        frame.getContentPane().add(enterGradeButton);

        JButton viewClassButton = new JButton("Danh Sách Lớp");
        viewClassButton.addActionListener((ActionEvent e) -> {
            new XemDanhSach().kichHoat();
        });
        viewClassButton.setBounds(229, 61, 161, 25);
        frame.getContentPane().add(viewClassButton);

        JButton viewSheduleButton = new JButton("Thời Khóa Biểu");
        viewSheduleButton.addActionListener((ActionEvent e) -> {
            new XemTKB().kichHoat();
        });
        viewSheduleButton.setBounds(229, 115, 161, 25);
        frame.getContentPane().add(viewSheduleButton);

        JButton viewGradeButton = new JButton("Điểm");
        viewGradeButton.addActionListener((ActionEvent e) -> {
            new XemDiem().kichHoat();
        });
        viewGradeButton.setBounds(229, 163, 161, 25);
        frame.getContentPane().add(viewGradeButton);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("Cài đặt");
        menuBar.add(mnNewMenu);

        JMenuItem changePasswordItem = new JMenuItem("Đổi mật khẩu");
        changePasswordItem.addActionListener((ActionEvent e) -> {
            frame.dispose();
            frame = new DoiMatKhau(username).getFrame();
            frame.setVisible(true);
        });
        mnNewMenu.add(changePasswordItem);

        JMenuItem logoutItem = new JMenuItem("Đăng xuất");
        logoutItem.addActionListener((ActionEvent e) -> {
            frame.dispose();
            new DangNhap().kichHoat();
        });
        mnNewMenu.add(logoutItem);

        frame.setLocationRelativeTo(null);
        //frame.setVisible(true);
    }

}
