
import java.sql.SQLException;

public class Db1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Conn1 conn1 = new Conn1("jdbc:sqlite:My_cats.s3db");
        conn1.createDB();
        conn1.insertCat("Август", "Гавана",3,5.5);
        conn1.insertCat("Вася","Ориентальная кошка",1, 4.3);
        conn1.insertCat("Нафоня","Сококе",2,4.1);
        conn1.add_more_cats(5000);
        conn1.readDB();
        conn1.delete_type(10);
        String where = "age = 1";
        String set = "age =3";
        conn1.delete_cat(where);
        conn1.update_cat(12,set,where);
        conn1.closeDB();
    }
}
