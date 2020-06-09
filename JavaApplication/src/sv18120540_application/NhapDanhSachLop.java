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
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sv18120540_hibernate_pojo.Danhsachlop;
import sv18120540_hibernate_pojo.Lop;

/**
 *
 * @author Sy Pham
 */
public class NhapDanhSachLop {

    public void kichHoat(){
        JFrame frame = new JFrame();

        JButton upClassFile = new JButton("load class file");
        upClassFile.addActionListener(new nhapFile());
        frame.getContentPane().add(upClassFile);

        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    class nhapFile implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser file = new JFileChooser();
            file.showOpenDialog(null);

            if (JFileChooser.APPROVE_OPTION == 0) {
                String duongDan = file.getSelectedFile().getPath();
                //String malop = tachDuongDan(duongDan);
                try {
                    themSinhVienVaoLop(duongDan);
                } catch (UnsupportedEncodingException ex) {
                } catch (IOException ex) {
                }
            }
        }

    }
    
    public String tachDuongDan(String duongDan){
        String result = null;
        for(int i=duongDan.length()-1; i>=0; i--){
            if((int)duongDan.charAt(i) == 92){
                result = duongDan.substring(i+1);
                result = result.substring(0, result.length()-4);
                break;
            }
        }
        
        return result;
    }

    public static boolean kiemTraSVTonTai(String maSinhVien) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Lop lop = null;

        try {
            lop = (Lop) session.get(Lop.class, maSinhVien);
        } catch (HibernateException e) {
            //e.printStackTrace();
        } finally {
            session.close();
        }

        if (lop == null) {
            return false;
        }

        return true;
    }
    
    public boolean kiemTraLopTonTai(String maLop){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Danhsachlop dsLop = null;

        try {
            dsLop = (Danhsachlop) session.get(Danhsachlop.class, maLop);
        } catch (HibernateException e) {
            //e.printStackTrace();
        } finally {
            session.close();
        }

        if (dsLop == null) {
            return false;
        }

        return true;
    }

    public static boolean themSinhVienVaoLop(Lop lop) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        if (kiemTraSVTonTai(lop.getMssv()) == true) {
            return false;
        }

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(lop);
            transaction.commit();
        } catch (HibernateException e) {
            //e.printStackTrace();
        } finally {
            session.close();
        }
        
        return true;
    }

    public void themSinhVienVaoLop(String duongDan) throws FileNotFoundException, UnsupportedEncodingException, IOException {
      
        String malop = tachDuongDan(duongDan);
        Danhsachlop dsLop = new Danhsachlop(malop);
        themLopVaoDanhSach(dsLop);

        BufferedReader file = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File(duongDan)), "UTF-8"));
        
        String duLieu = file.readLine();
        
        
        while((duLieu = file.readLine()) != null && duLieu.isEmpty() == false){
            
            System.out.println(duLieu);
            
            String[] cot = duLieu.split(",", 0);
            
            Lop lop = new Lop();
            lop.setMssv(cot[1]);
            lop.setHoten(cot[2]);
            lop.setGioitinh(cot[3]);
            lop.setCmnnd(cot[4]);
            lop.setDanhsachlop(dsLop);
            
            themSinhVienVaoLop(lop);
            
        }

    }

    public void themLopVaoDanhSach(Danhsachlop lop) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        if(kiemTraLopTonTai(lop.getMalop()) == true)
            return;

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(lop);
            transaction.commit();
        } catch (HibernateException e) {
            //e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
