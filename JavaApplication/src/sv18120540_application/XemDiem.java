/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import sv18120540_hibernate_pojo.Diem;
import sv18120540_hibernate_pojo.DiemId;

/**
 *
 * @author Sy Pham
 */
class DiemLop extends Thread {

    
    private JTable table;
    private String maLop;

    public DiemLop(JTable table, String maLop) {
        this.table = table;
        this.maLop = maLop;
        this.start();
    }

    

    @Override
    public void run() {
        

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("MSSV");
        model.addColumn("Họ tên");
        model.addColumn("Điểm GK");
        model.addColumn("Điểm CK");
        model.addColumn("Điểm khác");
        model.addColumn("Điểm tổng");

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {

            ArrayList<Diem> ds = (ArrayList<Diem>) session.createQuery("from Diem").list();
            for (int i = 0; i < ds.size(); i++) {
                if (ds.get(i).getId().getMalopMonhoc().equals(maLop)) {
                    Diem d = ds.get(i);
                    model.addRow(new Object[]{
                        d.getId().getMssv(),
                        d.getHoten(),
                        d.getDiemGk(),
                        d.getDiemCk(),
                        d.getDiemkhac(),
                        d.getDiemtong()
                    });
                }

            }

            table.setModel(model);
            table.setAutoResizeMode(0);
        } finally {
            session.close();
        }

    }
}

class KetQua extends Thread {

   
    private JTable table;
    private String maLop;
    private static final double score = 5;

    public KetQua(JTable table, String maLop) {
        this.table = table;
        this.maLop = maLop;
        this.start();
    }

   

    @Override
    public void run() {


        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("MSSV");
        model.addColumn("Họ tên");
        model.addColumn("Điểm tổng");
        model.addColumn("Kết quả");

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            ArrayList<Diem> ds = (ArrayList<Diem>) session.createQuery("from Diem").list();
            for (int i = 0; i < ds.size(); i++) {
                if (ds.get(i).getId().getMalopMonhoc().equals(maLop)) {
                    Diem d = ds.get(i);
                    String kq = null;
                    if (Double.compare(d.getDiemtong().doubleValue(), score) >= 0) {
                        kq = "Đậu";
                    } else {
                        kq = "Rớt";
                    }

                    model.addRow(new Object[]{
                        d.getId().getMssv(),
                        d.getHoten(),
                        d.getDiemtong(),
                        kq
                    });

                }

                table.setModel(model);
                table.setAutoResizeMode(0);
                table.getColumnModel().getColumn(1).setPreferredWidth(150);

            }
        } finally {
            session.close();
        }

    }
}

class ThongKe extends Thread {

   
    private JTable table;
    private String maLop;
    private static final double score = 5;

    public ThongKe(JTable table, String maLop) {
        this.table = table;
        this.maLop = maLop;
        this.start();
    }

    

    @Override
    public void run() {
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kết quả");
        model.addColumn("Số Lượng");
        model.addColumn("Phần Trăm(%)");

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            ArrayList<Diem> ds = (ArrayList<Diem>) session.createQuery("from Diem").list();
            float passed = 0;
            float failed = 0;

            for (int i = 0; i < ds.size(); i++) {
                if (ds.get(i).getId().getMalopMonhoc().equals(maLop)) {
                    Diem d = ds.get(i);
                    if (Double.compare(d.getDiemtong().doubleValue(), score) >= 0) {
                        passed++;
                    } else {
                        failed++;
                    }
                }
            }

            float passedRate = (passed / (passed + failed)) * 100;
            passedRate = (float) Math.round(passedRate * 100) / 100;
            float failedRate = 100 - passedRate;

            model.addRow(new Object[]{
                "Đâu", passed, passedRate
            });
            model.addRow(new Object[]{
                "Rớt", failed, failedRate
            });

            table.setModel(model);
            table.setAutoResizeMode(0);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(150);
        } finally {
            session.close();
        }

    }

}

public class XemDiem {

    //private JTextField textField;
    private JTable table;
    private JTextField textField_1;
    private String maLop;

    public void kichHoat() {
        JFrame frame = new JFrame("Xem Điểm");
        frame.setBounds(100, 100, 450, 348);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Mã Lớp");
        lblNewLabel.setBounds(30, 36, 56, 16);
        frame.getContentPane().add(lblNewLabel);

        String[] lop = new NhapDiem().dsLop();
        maLop = lop[0];
        JComboBox comboBox = new JComboBox(lop);
        comboBox.addActionListener((ActionEvent e) -> {
            maLop = (String) comboBox.getSelectedItem();
        });
        comboBox.setBounds(135, 33, 116, 22);
        frame.getContentPane().add(comboBox);

        JButton btnNewButton = new JButton("Xem Điểm");
        btnNewButton.setBounds(303, 32, 97, 25);
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!maLop.isEmpty()) {
                    new DiemLop(table, maLop);
                    
                }
            }
        });
        frame.getContentPane().add(btnNewButton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 121, 368, 119);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JButton btnNewButton_1 = new JButton("Kết Quả");
        btnNewButton_1.setBounds(53, 84, 97, 25);
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!maLop.isEmpty()) {
                    new KetQua(table, maLop);
                }
            }
        });
        frame.getContentPane().add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Thống Kê");
        btnNewButton_2.setBounds(238, 84, 97, 25);
        btnNewButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!maLop.isEmpty()) {
                    new ThongKe(table, maLop);
                }
            }
        });
        frame.getContentPane().add(btnNewButton_2);

        JLabel lblNewLabel_1 = new JLabel("Mã Sinh Viên");
        lblNewLabel_1.setBounds(30, 260, 97, 16);
        frame.getContentPane().add(lblNewLabel_1);

        textField_1 = new JTextField();
        textField_1.setBounds(135, 253, 116, 22);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        JButton btnNewButton_3 = new JButton("Sửa Điểm");
        btnNewButton_3.setBounds(303, 253, 97, 25);
        btnNewButton_3.addActionListener((ActionEvent e) -> {
            String mssv = textField_1.getText();
            
            
            if (mssv.isEmpty() || maLop.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nhập Thông tin Sinh Viên");
                return;
            }
            
            new SuaDiem(new DiemId(mssv, maLop), table);
            textField_1.setText("");
            //frame.dispose();
        });
        frame.getContentPane().add(btnNewButton_3);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
