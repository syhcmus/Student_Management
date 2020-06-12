/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sv18120540_hibernate_pojo.Danhsachlop;
import sv18120540_hibernate_pojo.DanhsachlopMonhoc;
import sv18120540_hibernate_pojo.Lop;
import sv18120540_hibernate_pojo.LopMonhoc;
import sv18120540_hibernate_pojo.LopMonhocId;
import sv18120540_hibernate_pojo.Tkb;

/**
 *
 * @author Sy Pham
 */
public class DanhSachLopChoMonHoc {

    public void taoDanhSachLopMonHoc() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        String hql = "from Tkb";
        Query query = session.createQuery(hql);
        List<Tkb> ds = query.list();

        String maLop = null;
        String maMon = null;
        String maSinhVien = null;
        String hoTen = null;
        String gioiTinh = null;
        String cmnd = null;

        Iterator it = ds.iterator();
        while (it.hasNext()) {
            Tkb tkb = (Tkb) it.next();
            System.out.println(tkb.getTenmon());

            maLop = tkb.getId().getMalop();
            maMon = tkb.getId().getMamon();
            String malop_monhoc = String.format("%s-%s", maLop, maMon);
            DanhsachlopMonhoc dsLopMonhoc = new DanhsachlopMonhoc(malop_monhoc);
            themLopVaoDanhSach(dsLopMonhoc);

            Query q = session.createQuery("from Lop");
            ArrayList<Lop> dsLop = (ArrayList<Lop>) q.list();

            for (int i = 0; i < dsLop.size(); i++) {
                if(!dsLop.get(i).getDanhsachlop().getMalop().equals(maLop)) continue;
                
                Lop lop = (Lop) session.get(Lop.class, dsLop.get(i).getMssv());
                if (lop != null) {
                    maSinhVien = lop.getMssv();
                    hoTen = lop.getHoten();
                    gioiTinh = lop.getGioitinh();
                    cmnd = lop.getCmnnd();
                }

                LopMonhoc lopMonhoc = new LopMonhoc();
                lopMonhoc.setId(new LopMonhocId(maSinhVien, malop_monhoc));
                lopMonhoc.setHoten(hoTen);
                lopMonhoc.setCmnnd(cmnd);
                lopMonhoc.setGioitinh(gioiTinh);
                lopMonhoc.setLop(lop);
                lopMonhoc.setDanhsachlopMonhoc(dsLopMonhoc);

                taoDsLopMonHoc(lopMonhoc);
            }

        }
    }

    public void taoDsLopMonHoc(LopMonhoc lop) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        if (kiemTraSVTonTai(lop.getId()) == true) {
            return;
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

    }

    public static boolean kiemTraSVTonTai(LopMonhocId id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        LopMonhoc lop = null;

        try {
            lop = (LopMonhoc) session.get(LopMonhoc.class, id);
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

    public static boolean kiemTraLopTonTai(String maLop) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        DanhsachlopMonhoc dsLop = null;

        try {
            dsLop = (DanhsachlopMonhoc) session.get(DanhsachlopMonhoc.class, maLop);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        if (dsLop == null) {
            return false;
        }

        return true;
    }

    public static void themLopVaoDanhSach(DanhsachlopMonhoc lop) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        if (kiemTraLopTonTai(lop.getMalopMonhoc()) == true) {
            return;
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
    }
}
