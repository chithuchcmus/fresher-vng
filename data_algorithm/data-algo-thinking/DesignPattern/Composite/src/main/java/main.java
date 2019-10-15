public class main {
    public static void main(String[] args) {
        NhanVien CEO = new NhanVien("John", 30000);

        NhanVien headSales = new NhanVien("Robert", 20000);

        NhanVien headMarketing = new NhanVien("Michel", 20000);

        NhanVien clerk1 = new NhanVien("Laura", 10000);
        NhanVien clerk2 = new NhanVien("Bob", 10000);

        NhanVien salesExecutive1 = new NhanVien("Richard", 10000);
        NhanVien salesExecutive2 = new NhanVien("Rob", 10000);

        CEO.add(headSales);
        CEO.add(headMarketing);

        headSales.add(salesExecutive1);
        headSales.add(salesExecutive2);

        headMarketing.add(clerk1);
        headMarketing.add(clerk2);

        System.out.println(CEO);

        for (NhanVien boss : CEO.getDsNhanVien()) {
            System.out.println(boss);
            for (NhanVien nhanVien : boss.getDsNhanVien()) {
                System.out.println(nhanVien);
            }
        }
    }
}
