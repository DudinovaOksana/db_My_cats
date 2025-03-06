

import java.sql.SQLException;

public class Db {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Conn conn = new Conn("jdbc:sqlite:My_cats.s3db");
        conn.createDB();
        int id = 7;
          String type = conn.get_type(id);
        System.out.printf("У id %d тип %s%n", id, type);

        String where = "type LIKE '%а'";
        conn.get_type_where(where);
        System.out.println();
        conn.get_all_types();
        conn.closeDB();


    }
}
