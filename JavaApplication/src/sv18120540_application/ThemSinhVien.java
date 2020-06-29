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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static sv18120540_application.DanhSachLopChoMonHoc.taoDsLopMonHoc;
import sv18120540_hibernate_pojo.Danhsachlop;
import sv18120540_hibernate_pojo.DanhsachlopMonhoc;
import sv18120540_hibernate_pojo.Lop;
import sv18120540_hibernate_pojo.LopMonhoc;
import sv18120540_hibernate_pojo.LopMonhocId;
import sv18120540_hibernate_pojo.Taikhoan;

/**
 *
 * @author Sy Pham
 */
public class ThemSinhVien {

    private JFrame frame;
    private JTextField studentNameText;
    private JTextField studentIdText;
    private JTextField genderText;
    private JTextField personIDText;
    private String classID;

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

        JLabel studentNameLabel = new JLabel("Họ và Tên");
        studentNameLabel.setBounds(52, 72, 83, 16);
        frame.getContentPane().add(studentNameLabel);

        studentNameText = new JTextField();
        studentNameText.setBounds(212, 69, 137, 22);
        frame.getContentPane().add(studentNameText);
        studentNameText.setColumns(10);

        JLabel studentIdLabel = new JLabel("MSSV");
        studentIdLabel.setBounds(52, 24, 56, 16);
        frame.getContentPane().add(studentIdLabel);

        studentIdText = new JTextField();
        studentIdText.setBounds(212, 21, 137, 22);
        frame.getContentPane().add(studentIdText);
        studentIdText.setColumns(10);

        JLabel genderLabel = new JLabel("Giới Tính");
        genderLabel.setBounds(52, 121, 56, 16);
        frame.getContentPane().add(genderLabel);

        genderText = new JTextField();
        genderText.setBounds(212, 118, 137, 22);
        frame.getContentPane().add(genderText);
        genderText.setColumns(10);

        JLabel personIDLabel = new JLabel("CMND");
        personIDLabel.setBounds(52, 169, 56, 16);
        frame.getContentPane().add(personIDLabel);

        personIDText = new JTextField();
        personIDText.setBounds(212, 166, 137, 22);
        frame.getContentPane().add(personIDText);
        personIDText.setColumns(10);

        JLabel classIDLabel = new JLabel("Lớp");
        classIDLabel.setBounds(52, 230, 56, 16);
        frame.getContentPane().add(classIDLabel);

        String[] classIDArr = new XemDanhSach().dsLop();
        classID = classIDArr[0];
        JComboBox comboBox = new JComboBox(classIDArr);
        comboBox.addActionListener((ActionEvent e) -> {
            classID = (String) comboBox.getSelectedItem();
        });
        comboBox.setBounds(212, 227, 137, 22);
        frame.getContentPane().add(comboBox);

        JButton addStudentButton = new JButton("Thêm Sinh Viên");
        addStudentButton.addActionListener(new themSinhVien());
        addStudentButton.setBounds(140, 292, 123, 25);
        frame.getContentPane().add(addStudentButton);

        frame.setVisible(true);

    }

    public class themSinhVien implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Lop lop = new Lop();
            lop.setMssv(studentIdText.getText());
            lop.setHoten(studentNameText.getText());
            lop.setGioitinh(genderText.getText());
            lop.setCmnnd(personIDText.getText());
            lop.setDanhsachlop(new Danhsachlop(classID));

            if (NhapDanhSachLop.themSinhVienVaoLop(lop) == true) {
                //TaoTaiKhoan.taoTaiKhoan(studentIdText.getText());
                Taikhoan tk = new Taikhoan(studentIdText.getText(), studentIdText.getText(), "0");
                TaoTaiKhoan.taoTaiKhoan(tk);
                JOptionPane.showMessageDialog(null, "Thêm Sinh Viên Thành công");
            } else {
                LopMonhoc lopMonhoc = new LopMonhoc();
                lopMonhoc.setId(new LopMonhocId(studentIdText.getText(), classID));
                lopMonhoc.setHoten(studentNameText.getText());
                lopMonhoc.setGioitinh(genderText.getText());
                lopMonhoc.setCmnnd(personIDText.getText());
                lopMonhoc.setLop(lop);

                DanhsachlopMonhoc dsLopMonhoc = new DanhsachlopMonhoc(classID);
                lopMonhoc.setDanhsachlopMonhoc(dsLopMonhoc);

                if (taoDsLopMonHoc(lopMonhoc)) {
                    Taikhoan tk = new Taikhoan(studentIdText.getText(), studentIdText.getText(), "0");
                    TaoTaiKhoan.taoTaiKhoan(tk);
                    JOptionPane.showMessageDialog(null, "Thêm Sinh Viên Thành công");
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm Sinh Viên  Không Thành công");
                }
            }

            frame.dispose();

        }

    }
}
