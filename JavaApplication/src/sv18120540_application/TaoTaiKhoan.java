/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sv18120540_hibernate_pojo.Lop;
import sv18120540_hibernate_pojo.Taikhoan;

/**
 *
 * @author Sy Pham
 */
public class TaoTaiKhoan {

    public static void taoTaiKhoan() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        ArrayList<Lop> ds = (ArrayList<Lop>) session.createQuery("from Lop").list();
        session.close();

        for (int i = 0; i < ds.size(); i++) {
            Lop l = ds.get(i);
            Taikhoan tk = new Taikhoan(l.getMssv(), l.getMssv(), "0");
            taoTaiKhoan(tk);
        }

    }

    public static void taoTaiKhoan(String maLop) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        ArrayList<Lop> ds = (ArrayList<Lop>) session.createQuery("from Lop").list();
        session.close();

        for (int i = 0; i < ds.size(); i++) {
            Lop l = ds.get(i);
            if (l.getDanhsachlop().getMalop().equals(maLop)) {
                Taikhoan tk = new Taikhoan(l.getMssv(), l.getMssv(), "0");
                taoTaiKhoan(tk);
            }
        }

    }

    public static void taoTaiKhoan(Taikhoan tk) {

        if (kiemTraTKTonTai(tk.getTendangnhap())) {
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

    public static boolean kiemTraTKTonTai(String id) {

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
