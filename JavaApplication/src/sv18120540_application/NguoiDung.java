/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv18120540_application;

import javax.swing.JFrame;

/**
 *
 * @author Sy Pham
 */
public abstract class NguoiDung {
    
    protected String username;
    protected String matKhau;
    protected JFrame frame;

   
    public NguoiDung(String username, String matKhau) {
        this.username = username;
        this.matKhau = matKhau;
    }
    
    
   
    public void doiMK(){
        
    }
    
   public abstract void kichHoat();

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
   
   
    
    
}
