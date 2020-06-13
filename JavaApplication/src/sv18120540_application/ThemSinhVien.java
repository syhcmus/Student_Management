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
import sv18120540_hibernate_pojo.Danhsachlop;
import sv18120540_hibernate_pojo.Lop;

/**
 *
 * @author Sy Pham
 */
public class ThemSinhVien {

    private JLabel lblNewLabel;
    private JTextField textField;
    private JLabel lblNewLabel_1;
    private JTextField textField_1;
    private JLabel lblNewLabel_2;
    private JTextField textField_2;
    private JLabel lblNewLabel_3;
    private JTextField textField_3;
    private JLabel lblNewLabel_4;
    private JTextField textField_4;
    private JButton btnNewButton;
    private JFrame frame;

    public JFrame getFrame() {
        return frame;
    }

    public ThemSinhVien() {
        kichHoat();
    }
    
    

    public void kichHoat() {
        frame = new JFrame("Thêm Sinh Viên");
        frame.setBounds(100, 100, 450, 390);
        frame.setLocationRelativeTo(null);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        lblNewLabel = new JLabel("Họ và Tên");
        lblNewLabel.setBounds(52, 72, 83, 16);
        frame.getContentPane().add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(212, 21, 137, 22);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        lblNewLabel_1 = new JLabel("MSSV");
        lblNewLabel_1.setBounds(52, 24, 56, 16);
        frame.getContentPane().add(lblNewLabel_1);

        textField_1 = new JTextField();
        textField_1.setBounds(212, 69, 137, 22);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        lblNewLabel_2 = new JLabel("Giới Tính");
        lblNewLabel_2.setBounds(52, 121, 56, 16);
        frame.getContentPane().add(lblNewLabel_2);

        textField_2 = new JTextField();
        textField_2.setBounds(212, 118, 137, 22);
        frame.getContentPane().add(textField_2);
        textField_2.setColumns(10);

        lblNewLabel_3 = new JLabel("CMND");
        lblNewLabel_3.setBounds(52, 169, 56, 16);
        frame.getContentPane().add(lblNewLabel_3);

        textField_3 = new JTextField();
        textField_3.setBounds(212, 166, 137, 22);
        frame.getContentPane().add(textField_3);
        textField_3.setColumns(10);

        lblNewLabel_4 = new JLabel("Lớp");
        lblNewLabel_4.setBounds(52, 230, 56, 16);
        frame.getContentPane().add(lblNewLabel_4);

        textField_4 = new JTextField();
        textField_4.setBounds(212, 227, 137, 22);
        frame.getContentPane().add(textField_4);
        textField_4.setColumns(10);

        btnNewButton = new JButton("Thêm Sinh Viên");
        btnNewButton.addActionListener(new themSinhVien());
        btnNewButton.setBounds(140, 292, 123, 25);
        frame.getContentPane().add(btnNewButton);

        frame.setVisible(true);

    }

    public class themSinhVien implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Lop lop = new Lop();
            lop.setMssv(textField.getText());
            lop.setHoten(textField_1.getText());
            lop.setGioitinh(textField_2.getText());
            lop.setCmnnd(textField_3.getText());
            lop.setDanhsachlop(new Danhsachlop(textField_4.getText()));

            if (NhapDanhSachLop.themSinhVienVaoLop(lop) == true) {
                JOptionPane.showMessageDialog(null, "Thêm Sinh Viên Thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Thêm Sinh Viên  Không Thành công");
            }

            textField.setText("");
            textField_1.setText("");
            textField_2.setText("");
            textField_3.setText("");
            textField_4.setText("");

        }

    }
}
