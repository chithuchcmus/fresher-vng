public class main {
    public static void main(String args[])
    {
        HashTable<String,Integer> table = new HashTable<String, Integer>(new DoubleHashing());
        System.out.println("maxsize: " + table.getMaxSize());
        System.out.println("currentsize: " + table.getCurrentSize());
        table.put("thuc1",12);
        table.put("thuc2",13);
        table.put("thuc3",15);
        table.put("thuc4",12);
        table.put("thuc5",13);
        table.put("goku",15);
        table.put("goku2",12);
        table.put("goku1",13);
        System.out.println("count: " + table.getCount());
        table.put("goku3",15);
        table.put("goku5",12);
        System.out.println("count: " + table.getCount());
        System.out.println("maxsize: " + table.getMaxSize());
        System.out.println("currentsize: " + table.getCurrentSize());
        table.remove("goku");
        table.remove("goku1");
        table.remove("goku2");
        table.remove("goku3");
        table.remove("goku4");
        table.remove("goku5");
        table.remove("thuc1");
        table.remove("thuc2");
        System.out.println("count: " + table.getCount());
        System.out.println("maxsize: " + table.getMaxSize());

    }
}
