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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import sv18120540_hibernate_pojo.Danhsachlop;
import sv18120540_hibernate_pojo.DanhsachlopMonhoc;
import sv18120540_hibernate_pojo.Lop;
import sv18120540_hibernate_pojo.LopMonhoc;

/**
 *
 * @author Sy Pham
 */
class DanhSachLop extends Thread {

    
    private JTable table;
    private String classID;

    public DanhSachLop(JTable table, String classID) {
        this.table = table;
        this.classID = classID;
        start();
    }

   

   

    @Override
    public void run() {

        String malop = classID;

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("MSSV");
        model.addColumn("Ho Ten");
        model.addColumn("Gioi Tinh");
        model.addColumn("CMND");

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            int count = 0;
            ArrayList<Lop> dsLop = (ArrayList<Lop>) session.createQuery("from Lop").list();
            for (int i = 0; i < dsLop.size(); i++) {

                if (dsLop.get(i).getDanhsachlop().getMalop().equals(malop)) {
                    count++;

                    Lop l = dsLop.get(i);

                    model.addRow(new Object[]{
                        l.getMssv(),
                        l.getHoten(),
                        l.getGioitinh(),
                        l.getCmnnd()
                    });

                }
            }

            if (count == 0) {
                ArrayList<LopMonhoc> dsLopMonhoc = (ArrayList<LopMonhoc>) session.createQuery("from LopMonhoc").list();
                for (int i = 0; i < dsLopMonhoc.size(); i++) {

                    if (dsLopMonhoc.get(i).getDanhsachlopMonhoc().getMalopMonhoc().equals(malop)) {
                        LopMonhoc l = dsLopMonhoc.get(i);

                        model.addRow(new Object[]{
                            l.getId().getMssv(),
                            l.getHoten(),
                            l.getGioitinh(),
                            l.getCmnnd()
                        });

                    }
                }

            }

            table.setModel(model);
            table.setAutoResizeMode(0);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
        } finally {
            session.close();
        }

    }

}

public class XemDanhSach {

    private JTable table;
    private JFrame frame;
    private String classID;

    public void kichHoat() {

        frame = new JFrame("Xem danh sách lớp");
        frame.setBounds(100, 100, 465, 312);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel classIDLabel = new JLabel("Mã Lớp");
        classIDLabel.setBounds(30, 34, 56, 16);
        frame.getContentPane().add(classIDLabel);

       
        String[] classIDArr = dsLop();
        classID = classIDArr[0];
        JComboBox comboBox = new JComboBox(classIDArr);
        comboBox.addActionListener((ActionEvent e) -> {
            classID = (String) comboBox.getSelectedItem();
        });
        comboBox.setBounds(113, 31, 111, 22);
        frame.getContentPane().add(comboBox);

        JButton viewClassButton = new JButton("Xem Danh Sách");
        viewClassButton.setBounds(281, 30, 139, 25);
        viewClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                new DanhSachLop(table, classID);
            }
        });
        frame.getContentPane().add(viewClassButton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 88, 390, 128);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JButton addStudentButton = new JButton("Thêm Sinh Viên");
        addStudentButton.setBounds(126, 227, 150, 25);
        addStudentButton.addActionListener((ActionEvent e) -> {
            //frame.dispose();
            frame = new ThemSinhVien().getFrame();
        });
        frame.getContentPane().add(addStudentButton);

        JButton deleteStudentButton = new JButton("Xóa Sinh Viên");
        deleteStudentButton.setBounds(295, 227, 125, 25);
        deleteStudentButton.addActionListener((ActionEvent e) -> {
            new XoaSinhVien().kichHoat();
        });
        frame.getContentPane().add(deleteStudentButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

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

            ArrayList<DanhsachlopMonhoc> ds1 = (ArrayList<DanhsachlopMonhoc>) session.createQuery("from DanhsachlopMonhoc").list();
            for (int i = 0; i < ds1.size(); i++) {
                DanhsachlopMonhoc l = ds1.get(i);
                lop.add(l.getMalopMonhoc());
            }

        } finally {
            session.close();
        }

        return lop.toArray(new String[lop.size()]);

    }

}
