/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.hibernate.Session;
import sv18120540_hibernate_pojo.Lop;

/**
 *
 * @author Sy Pham
 */
public class SinhVien extends NguoiDung {

    private JTable table;

    public SinhVien(String tenDangNhap, String matKhau) {
        super(tenDangNhap, matKhau);
        kichHoat();
    }

    @Override
    public void kichHoat() {
        /*
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Tai khoan");
        lblNewLabel.setBounds(12, 13, 81, 16);
        frame.getContentPane().add(lblNewLabel);

        JButton btnNewButton = new JButton("Đăng Xuất");
        btnNewButton.setBounds(308, 9, 97, 25);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new DangNhap().kichHoat();
            }
        });
        frame.getContentPane().add(btnNewButton);

        Session sessoin = HibernateUtil.getSessionFactory().openSession();
        Lop l = (Lop) sessoin.get(Lop.class, tenDangNhap);

        JTextField textField = new JTextField(l.getHoten());
        textField.setEditable(false);
        textField.setBounds(116, 10, 162, 22);
        frame.getContentPane().add(textField);
        //textField.setColumns(10);

        JComboBox comboBox = new JComboBox();
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = (String) comboBox.getSelectedItem();
                System.out.println(s);
            }
        });
        comboBox.setBounds(116, 90, 162, 22);
        frame.getContentPane().add(comboBox);

        JLabel lblNewLabel_1 = new JLabel("Môn học");
        lblNewLabel_1.setBounds(12, 93, 56, 16);
        frame.getContentPane().add(lblNewLabel_1);

        JButton btnNewButton_1 = new JButton("Xem Điểm");
        btnNewButton_1.setBounds(308, 89, 97, 25);
        frame.getContentPane().add(btnNewButton_1);

        table = new JTable();
        table.setBounds(12, 155, 392, 85);
        frame.getContentPane().add(table);*/

        frame = new JFrame();
        frame.setBounds(100, 100, 442, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Tai khoan");
        lblNewLabel.setBounds(133, 13, 81, 16);
        frame.getContentPane().add(lblNewLabel);
        
        Session sessoin = HibernateUtil.getSessionFactory().openSession();
        Lop l = (Lop) sessoin.get(Lop.class, tenDangNhap);
        sessoin.close();

        JTextField textField = new JTextField(l.getHoten());
        textField.setEditable(false);
        textField.setBounds(243, 10, 162, 22);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        String[] text = {"a", "b"};
        JComboBox comboBox = new JComboBox(text);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = (String) comboBox.getSelectedItem();
                System.out.println(s);
            }
        });
        comboBox.setBounds(116, 90, 162, 22);
        frame.getContentPane().add(comboBox);

        JLabel lblNewLabel_1 = new JLabel("Môn học");
        lblNewLabel_1.setBounds(12, 93, 56, 16);
        frame.getContentPane().add(lblNewLabel_1);

        JButton btnNewButton_1 = new JButton("Xem Điểm");
        btnNewButton_1.setBounds(308, 89, 97, 25);
        frame.getContentPane().add(btnNewButton_1);

        table = new JTable();
        table.setBounds(12, 127, 392, 85);
        frame.getContentPane().add(table);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("Cài đặt");
        menuBar.add(mnNewMenu);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("Đổi mật khẩu");
        mnNewMenu.add(mntmNewMenuItem_1);

        JMenuItem mntmNewMenuItem = new JMenuItem("Đăng xuất");
        mntmNewMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new DangNhap().kichHoat();
            }
        });
        mnNewMenu.add(mntmNewMenuItem);

        frame.setLocationRelativeTo(null);

    }

}
