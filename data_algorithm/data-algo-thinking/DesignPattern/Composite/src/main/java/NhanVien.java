import java.util.List;
import java.util.ArrayList;

public class NhanVien {
    private String ten;
    private int luong;
    private List<NhanVien> dsNhanVien;

    public NhanVien(String ten, int luong) {
        this.ten = ten;
        this.luong = luong;
        dsNhanVien = new ArrayList<NhanVien>();
    }

    public void add(NhanVien e) {
        dsNhanVien.add(e);
    }

    public void remove(NhanVien e) {
        dsNhanVien.remove(e);
    }

    public List<NhanVien> getDsNhanVien(){
        return dsNhanVien;
    }

    public String toString(){
        return ("NhanVien :[ ten : " + ten + ", luong :" + luong+" ]");
    }
}