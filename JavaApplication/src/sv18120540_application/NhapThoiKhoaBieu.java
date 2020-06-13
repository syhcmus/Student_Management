/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sv18120540_hibernate_pojo.Danhsachlop;
import sv18120540_hibernate_pojo.Lop;
import sv18120540_hibernate_pojo.Tkb;
import sv18120540_hibernate_pojo.TkbId;

/**
 *
 * @author Sy Pham
 */
public class NhapThoiKhoaBieu {

    private JTextField textField;
    private String path;

    public void kichHoat() {
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Mã Lớp");
        lblNewLabel.setBounds(50, 81, 56, 16);
        frame.getContentPane().add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(130, 78, 116, 22);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("File");
        btnNewButton.setBounds(287, 77, 97, 25);
        btnNewButton.addActionListener(new LayDuongDan());
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Tạo Thời Khóa Biểu");
        btnNewButton_1.setBounds(106, 173, 200, 25);
        btnNewButton_1.addActionListener((ActionEvent e) -> {
            new TaoTKB();
            new DanhSachLopChoMonHoc().taoDanhSachLopMonHoc();
        });
        frame.getContentPane().add(btnNewButton_1);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    class LayDuongDan implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser file = new JFileChooser();
            file.showOpenDialog(null);

            if (JFileChooser.APPROVE_OPTION == 0) {
                path = file.getSelectedFile().getPath();
            }
        }

    }

    class TaoTKB implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                taoTKB();

                JOptionPane.showMessageDialog(null, "Tạo TKB thành công");
                textField.setText("");

            } catch (FileNotFoundException ex) {
                Logger.getLogger(NhapThoiKhoaBieu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NhapThoiKhoaBieu.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void taoTKB() throws UnsupportedEncodingException, FileNotFoundException, IOException {
        String malop = textField.getText();
        Danhsachlop dsLop = new Danhsachlop(malop);
        NhapDanhSachLop.themLopVaoDanhSach(dsLop);

        String duongDan = path;
        BufferedReader file = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(duongDan)), "UTF-8"));

        String duLieu = file.readLine();

        while ((duLieu = file.readLine()) != null && duLieu.isEmpty() == false) {

            System.out.println(duLieu);

            String[] cot = duLieu.split(",", 0);

            Tkb tkb = new Tkb();
            tkb.setId(new TkbId(malop, cot[1]));
            tkb.setTenmon(cot[2]);
            tkb.setPhonghoc(cot[3]);
            tkb.setDanhsachlop(dsLop);

            taoTKB(tkb);

        }
    }

    public void taoTKB(Tkb tkb) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        if (kiemTraTKBTonTai(tkb.getId()) == true) {
            return;
        }

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(tkb);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public static boolean kiemTraTKBTonTai(TkbId id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Tkb tkb = null;

        try {
            tkb = (Tkb) session.get(Tkb.class, id);
        } catch (HibernateException e) {
            //e.printStackTrace();
        } finally {
            session.close();
        }

        if (tkb == null) {
            return false;
        }

        return true;
    }

}
