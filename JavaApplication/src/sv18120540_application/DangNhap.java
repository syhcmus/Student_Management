/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static sv18120540_application.NhapDanhSachLop.kiemTraSVTonTai;
import sv18120540_hibernate_pojo.Lop;
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
        if(isCreatedDefaultAccount == false)
            taoTaiKhoan();
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

        JButton btnNewButton = new JButton("Đăng Nhập");
        btnNewButton.setBounds(298, 82, 97, 56);
        btnNewButton.addActionListener(new DangNhapTK());
        frame.getContentPane().add(btnNewButton);

        mFrame = frame;
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.setLocationRelativeTo(null);
        mFrame.setVisible(true);
    }
    
    class DangNhapTK implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            NguoiDung tk = kiemTraDangNhap();
            if(tk != null){
                mFrame.dispose();
                mFrame = tk.getFrame();
                mFrame.setVisible(true);
            }
        }
        
    }
    
    public NguoiDung kiemTraDangNhap(){
        NguoiDung n = null;
        
        
        if(kiemTraTKTonTai(textField.getText())){
            Session session = HibernateUtil.getSessionFactory().openSession();
            Taikhoan tk = (Taikhoan) session.get(Taikhoan.class, textField.getText());
            String password = new String(passwordField.getPassword());
            System.out.println(password);
            if(tk.getMatkhau().equals(password)){
                String loaiTk = tk.getLoaitk();
                if(loaiTk.equals("0"))
                    n = new SinhVien(tk.getTendangnhap(), tk.getMatkhau());
                else
                    n = new GiaoVu(tk.getTendangnhap(), tk.getMatkhau());
            }
                
            session.close();
        }
        
        return n;
    }
    
    public void taoTaiKhoan(){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        ArrayList<Lop> ds = (ArrayList<Lop>) session.createQuery("from Lop").list();
        session.close();
        
        for(int i=0; i<ds.size(); i++){
            Lop l = ds.get(i);
            Taikhoan tk = new Taikhoan(l.getMssv(), l.getMssv(), "0");
            taoTaiKhoan(tk);
        }
        
        taoTaiKhoan(new Taikhoan("giaovu", "giaovu", "1"));
        
        isCreatedDefaultAccount = true;
        
    }
    
    public void taoTaiKhoan(Taikhoan tk){
        
        if(kiemTraTKTonTai(tk.getTendangnhap())){
            return;
        }
        
        Session session = HibernateUtil.getSessionFactory().openSession();


        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(tk);
            transaction.commit();
        } catch (HibernateException e) {
            //e.printStackTrace();
        } finally {
            session.close();
        }

        
    }
    
    public boolean kiemTraTKTonTai(String id){
        
        Session session = HibernateUtil.getSessionFactory().openSession();

        Taikhoan tk = null;

        try {
            tk = (Taikhoan) session.get(Taikhoan.class, id);
        } catch (HibernateException e) {
            //e.printStackTrace();
        } finally {
            session.close();
        }

        if (tk == null) {
            return false;
        }

        return true;
        
    }

}
