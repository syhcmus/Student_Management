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
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sv18120540_hibernate_pojo.Danhsachlop;
import sv18120540_hibernate_pojo.Tkb;
import sv18120540_hibernate_pojo.TkbId;

/**
 *
 * @author Sy Pham
 */
public class NhapThoiKhoaBieu {

    private JTextField textField;
    private String path;
    String lop;

    public void kichHoat() {
        
        JFrame frame = new JFrame("Nhập TKB");
        frame.setBounds(100, 100, 450, 300);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Mã lớp");
        lblNewLabel.setBounds(54, 70, 56, 16);
        frame.getContentPane().add(lblNewLabel);

        String[] maLop = dsLop();
        lop = maLop[0];
        JComboBox comboBox = new JComboBox(maLop);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lop = (String) comboBox.getSelectedItem();
            }
        });
        comboBox.setBounds(145, 67, 122, 22);
        frame.getContentPane().add(comboBox);

        JButton btnNewButton = new JButton("File");
        btnNewButton.addActionListener(new LayDuongDan());
        btnNewButton.setBounds(301, 66, 97, 25);
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Tạo TKB");
        btnNewButton_1.addActionListener((ActionEvent e) -> {
            try {
                boolean isSuccess = taoTKB();
                new DanhSachLopChoMonHoc().taoDanhSachLopMonHoc();
                
                String message = isSuccess ? "Tạo TKB thành công" : "Tạo TKB Thất bại";
                JOptionPane.showMessageDialog(null, message);
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(NhapThoiKhoaBieu.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Tạo TKB Thất bại");
            } catch (IOException ex) {
                //Logger.getLogger(NhapThoiKhoaBieu.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Tạo TKB Thất bại");
            }
        });
        btnNewButton_1.setBounds(158, 159, 97, 25);
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

   

    public boolean taoTKB() throws UnsupportedEncodingException, FileNotFoundException, IOException {
        String malop = lop;
        if (malop == null) {
            return false;
        }
        
        boolean isSuccess = false;

        try {

            Danhsachlop dsLop = new Danhsachlop(malop);
            //NhapDanhSachLop.themLopVaoDanhSach(dsLop);

            String duongDan = path;
            
            if(path == null)
                return false;
            
            BufferedReader file = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    new File(duongDan)), "UTF-8"));

            String duLieu = file.readLine();

            while ((duLieu = file.readLine()) != null && duLieu.isEmpty() == false) {

                String[] cot = duLieu.split(",", 0);

                Tkb tkb = new Tkb();
                tkb.setId(new TkbId(malop, cot[1]));
                tkb.setTenmon(cot[2]);
                tkb.setPhonghoc(cot[3]);
                tkb.setDanhsachlop(dsLop);

                taoTKB(tkb);

            }
            
            isSuccess = true;

        } finally {           
            return isSuccess;
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
            //e.printStackTrace();
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

    public String[] dsLop() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<String> lop = new ArrayList<>();

        try {

            ArrayList<Danhsachlop> ds = (ArrayList<Danhsachlop>) session.createQuery("from Danhsachlop").list();
            for (int i = 0; i < ds.size(); i++) {
                Danhsachlop l = ds.get(i);
                lop.add(l.getMalop());
            }

        } finally {
            session.close();
        }

        return lop.toArray(new String[lop.size()]);

    }

}
