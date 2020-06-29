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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import sv18120540_hibernate_pojo.Diem;
import sv18120540_hibernate_pojo.Lop;
import sv18120540_hibernate_pojo.LopMonhoc;
import sv18120540_hibernate_pojo.Tkb;
import sv18120540_hibernate_pojo.TkbId;

/**
 *
 * @author Sy Pham
 */
class DiemMonHoc extends Thread {

    private JTable table;
    private Diem diem;

    public DiemMonHoc(JTable table, Diem diem) {
        this.table = table;
        this.diem = diem;
        this.start();
    }

    @Override
    public void run() {

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Điểm GK");
        model.addColumn("Điểm CK");
        model.addColumn("Điểm khác");
        model.addColumn("Điểm tổng");

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            model.addRow(new Object[]{
                diem.getDiemGk(),
                diem.getDiemCk(),
                diem.getDiemkhac(),
                diem.getDiemtong()
            });

            table.setModel(model);
            table.setAutoResizeMode(0);
        } finally {
            session.close();
        }

    }

}

public final class SinhVien extends NguoiDung {

    private JTable table;
    private String subject;

    public SinhVien(String username, String matKhau) {
        super(username, matKhau);
        kichHoat();
    }

    

    @Override
    public void kichHoat() {

        frame = new JFrame();
        frame.setBounds(100, 100, 442, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel accountLabel = new JLabel("Tài khoản");
        accountLabel.setBounds(133, 13, 81, 16);
        frame.getContentPane().add(accountLabel);

        Session sessoin = HibernateUtil.getSessionFactory().openSession();
        Lop l = (Lop) sessoin.get(Lop.class, username);
        sessoin.close();

        JTextField classIDField = new JTextField(String.format("%s - %s", l.getHoten(), username));
        classIDField.setEditable(false);
        classIDField.setBounds(243, 10, 162, 22);
        frame.getContentPane().add(classIDField);
        classIDField.setColumns(10);

        String[] subjectArr = monHoc();
        subject = subjectArr[0];
        JComboBox comboBox = new JComboBox(subjectArr);
        comboBox.addActionListener((ActionEvent e) -> {
            subject = (String) comboBox.getSelectedItem();
        });
        comboBox.setBounds(116, 90, 162, 22);
        frame.getContentPane().add(comboBox);
        

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 127, 392, 85);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JLabel subjectLabel = new JLabel("Môn học");
        subjectLabel.setBounds(12, 93, 56, 16);
        frame.getContentPane().add(subjectLabel);

        JButton gradeButton = new JButton("Xem Điểm");
        gradeButton.setBounds(308, 89, 97, 25);
        gradeButton.addActionListener((ActionEvent e) -> {
            Diem diem = timMonHocSinhVien();
            if (diem != null) {
                new DiemMonHoc(table, diem);
            }
            else{
                JOptionPane.showMessageDialog(null, "Điểm Môn " + subject + " chưa cập nhật");
            }
        });
        frame.getContentPane().add(gradeButton);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu setupMenu = new JMenu("Cài đặt");
        menuBar.add(setupMenu);

        JMenuItem changePasswordItem = new JMenuItem("Đổi mật khẩu");
        changePasswordItem.addActionListener((ActionEvent e) -> {
            frame.dispose();
            frame = new DoiMatKhau(username).getFrame();
            frame.setVisible(true);
        });
        setupMenu.add(changePasswordItem);

        JMenuItem logoutItem = new JMenuItem("Đăng xuất");
        logoutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new DangNhap().kichHoat();
            }
        });
        setupMenu.add(logoutItem);

        frame.setLocationRelativeTo(null);

    }

    public String[] monHoc() {
        ArrayList<String> m = new ArrayList<String>();
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            ArrayList<LopMonhoc> ds = (ArrayList<LopMonhoc>) session.createQuery("from LopMonhoc").list();

            for (int i = 0; i < ds.size(); i++) {
                LopMonhoc l = ds.get(i);
                if (l.getId().getMssv().equals(username)) {
                    String maLopMonHoc = l.getDanhsachlopMonhoc().getMalopMonhoc();
                    String maLop = maLopMonHoc.split("-", 0)[0];
                    String maMon = maLopMonHoc.split("-", 0)[1];
                    TkbId id = new TkbId(maLop, maMon);
                    Tkb tkb = (Tkb) session.get(Tkb.class, id);
                    m.add(tkb.getTenmon());

                }
            }

            return m.toArray(new String[m.size()]);
        } finally {
            session.close();
        }

    }

    public Diem timMonHocSinhVien() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Diem diem = null;

        try {
           

            ArrayList<Diem> ds = (ArrayList<Diem>) session.createQuery("from Diem").list();
            for (int i = 0; i < ds.size(); i++) {

                if (ds.get(i).getId().getMssv().equals(username)) {
                    String maLopMaMon = ds.get(i).getDanhsachlopMonhoc().getMalopMonhoc();
                    String maLop = maLopMaMon.split("-", 0)[0];
                    String maMon = maLopMaMon.split("-", 0)[1];
                    TkbId tkbID = new TkbId(maLop, maMon);
                    Tkb tkb = (Tkb) session.get(Tkb.class, tkbID);

                    if (tkb.getTenmon().equals(subject)) {
                        diem = ds.get(i);

                        break;
                    }

                }

            }

        } finally {
            session.close();
        }

        return diem;

    }

}
