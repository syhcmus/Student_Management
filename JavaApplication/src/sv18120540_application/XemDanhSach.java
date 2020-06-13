/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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

    
    private JTable table_2;
    private String maLop;

    public DanhSachLop(JTable table_2, String maLop) {
        this.table_2 = table_2;
        this.maLop = maLop;
        this.start();
    }

   

    @Override
    public void run() {

        String malop = maLop;

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

            table_2.setModel(model);
            table_2.setAutoResizeMode(0);
            table_2.getColumnModel().getColumn(1).setPreferredWidth(150);
        } finally {
            session.close();
        }

    }

}

public class XemDanhSach {

    private JTable table;
    private JFrame frame;
    private JTextField textField;
    private String maLop;

    public void kichHoat() {

        frame = new JFrame("Xem danh sách lớp");
        frame.setBounds(100, 100, 465, 312);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Mã Lớp");
        lblNewLabel.setBounds(30, 34, 56, 16);
        frame.getContentPane().add(lblNewLabel);

       
        String[] lop = dsLop();
        maLop = lop[0];
        JComboBox comboBox = new JComboBox(lop);
        comboBox.addActionListener((ActionEvent e) -> {
            maLop = (String) comboBox.getSelectedItem();
        });
        comboBox.setBounds(113, 31, 111, 22);
        frame.getContentPane().add(comboBox);

        JButton btnNewButton = new JButton("Xem Danh Sách");
        btnNewButton.setBounds(281, 30, 139, 25);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                new DanhSachLop(table, maLop);
            }
        });
        frame.getContentPane().add(btnNewButton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 88, 390, 128);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JButton btnNewButton_1 = new JButton("Thêm Sinh Viên");
        btnNewButton_1.setBounds(126, 227, 150, 25);
        btnNewButton_1.addActionListener((ActionEvent e) -> {
            //frame.dispose();
            frame = new ThemSinhVien().getFrame();
        });
        frame.getContentPane().add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Xóa Sinh Viên");
        btnNewButton_2.setBounds(295, 227, 125, 25);
        btnNewButton_2.addActionListener((ActionEvent e) -> {
            new XoaSinhVien().kichHoat();
        });
        frame.getContentPane().add(btnNewButton_2);

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
