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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import sv18120540_hibernate_pojo.Tkb;

/**
 *
 * @author Sy Pham
 */
class DSThoiKhoaBieu extends Thread {

    private JTable table;
    private String maLop;

    public DSThoiKhoaBieu(JTable table, String maLop) {
        this.table = table;
        this.maLop = maLop;
        start();
    }

    @Override
    public void run() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Mã Môn");
        model.addColumn("Tên Môn");
        model.addColumn("Phòng Học");

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            ArrayList<Tkb> ds = (ArrayList<Tkb>) session.createQuery("from Tkb").list();

            for (int i = 0; i < ds.size(); i++) {
                if (ds.get(i).getDanhsachlop().getMalop().equals(maLop)) {
                    Tkb tkb = ds.get(i);
                    model.addRow(new Object[]{
                        tkb.getId().getMamon(),
                        tkb.getTenmon(),
                        tkb.getPhonghoc()
                    });
                }
            }

            table.setModel(model);
            table.setAutoResizeMode(0);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(150);

        } finally {
            session.close();
        }

    }

}

public class XemTKB {

    private JTextField textField;
    private JTable table;
    private String maLop;

    public void kichHoat() {
        JFrame frame = new JFrame("Xem Thời Khóa Biểu");
        frame.setBounds(100, 100, 450, 300);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Mã Lớp");
        lblNewLabel.setBounds(42, 54, 56, 16);
        frame.getContentPane().add(lblNewLabel);

        /*
        textField = new JTextField();
        textField.setBounds(146, 51, 116, 22);
        frame.getContentPane().add(textField);
        textField.setColumns(10);*/
        String[] lop = new NhapThoiKhoaBieu().dsLop();
        maLop = lop[0];
        JComboBox comboBox = new JComboBox(lop);
        comboBox.addActionListener((ActionEvent e) -> {
            maLop = (String) comboBox.getSelectedItem();
        });
        comboBox.setBounds(115, 51, 116, 22);
        frame.getContentPane().add(comboBox);

        JButton btnNewButton = new JButton("Xem TKB");
        btnNewButton.setBounds(297, 50, 97, 25);

        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                

                new DSThoiKhoaBieu(table, maLop);
            }
        });
        frame.getContentPane().add(btnNewButton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(41, 99, 353, 127);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
