package sv18120540_hibernate_pojo;
// Generated Jun 8, 2020 10:30:35 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Lop generated by hbm2java
 */
public class Lop  implements java.io.Serializable {


     private String mssv;
     private Danhsachlop danhsachlop;
     private String hoten;
     private String gioitinh;
     private String cmnnd;
     private Set diems = new HashSet(0);
     private Set lopMonhocs = new HashSet(0);

    public Lop() {
    }

	
    public Lop(String mssv) {
        this.mssv = mssv;
    }
    public Lop(String mssv, Danhsachlop danhsachlop, String hoten, String gioitinh, String cmnnd, Set diems, Set lopMonhocs) {
       this.mssv = mssv;
       this.danhsachlop = danhsachlop;
       this.hoten = hoten;
       this.gioitinh = gioitinh;
       this.cmnnd = cmnnd;
       this.diems = diems;
       this.lopMonhocs = lopMonhocs;
    }
    
    public Lop(String mssv, Danhsachlop danhsachlop, String hoten, String gioitinh, String cmnnd, Set lopMonhocs) {
       this.mssv = mssv;
       this.danhsachlop = danhsachlop;
       this.hoten = hoten;
       this.gioitinh = gioitinh;
       this.cmnnd = cmnnd;
       this.lopMonhocs = lopMonhocs;
    }
   
    public String getMssv() {
        return this.mssv;
    }
    
    public void setMssv(String mssv) {
        this.mssv = mssv;
    }
    public Danhsachlop getDanhsachlop() {
        return this.danhsachlop;
    }
    
    public void setDanhsachlop(Danhsachlop danhsachlop) {
        this.danhsachlop = danhsachlop;
    }
    public String getHoten() {
        return this.hoten;
    }
    
    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
    public String getGioitinh() {
        return this.gioitinh;
    }
    
    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }
    public String getCmnnd() {
        return this.cmnnd;
    }
    
    public void setCmnnd(String cmnnd) {
        this.cmnnd = cmnnd;
    }
    public Set getDiems() {
        return this.diems;
    }
    
    public void setDiems(Set diems) {
        this.diems = diems;
    }
    public Set getLopMonhocs() {
        return this.lopMonhocs;
    }
    
    public void setLopMonhocs(Set lopMonhocs) {
        this.lopMonhocs = lopMonhocs;
    }




}


