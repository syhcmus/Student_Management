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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import sv18120540_hibernate_pojo.Lop;
import sv18120540_hibernate_pojo.LopMonhoc;

/**
 *
 * @author Sy Pham
 */
public class XemDanhSach {

    private JTextField textField_2;
    JTable table_2;

    public void kichHoat() {
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Mã Lớp");
        lblNewLabel.setBounds(21, 27, 56, 16);
        frame.getContentPane().add(lblNewLabel);

        textField_2 = new JTextField();
        textField_2.setBounds(94, 24, 116, 22);
        frame.getContentPane().add(textField_2);
        textField_2.setColumns(10);

        JButton btnNewButton = new JButton("Xem Danh Sách");
        btnNewButton.setBounds(279, 23, 129, 25);
        btnNewButton.addActionListener(new DanhSachLop());
        frame.getContentPane().add(btnNewButton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 76, 387, 164);
        frame.getContentPane().add(scrollPane);

        table_2 = new JTable();
        scrollPane.setViewportView(table_2);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    class DanhSachLop implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            String malop = textField_2.getText();
            ArrayList<ArrayList<String>> data = new ArrayList<>();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("MSSV");
            model.addColumn("Ho Ten");
            model.addColumn("Gioi Tinh");
            model.addColumn("CMND");

            Session session = HibernateUtil.getSessionFactory().openSession();

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

        }

    }
}
