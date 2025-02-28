

import java.sql.SQLException;

public class db {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Conn conn = new Conn("jdbc:sqlite:My_cats.s3db");
        conn.createDB();
        conn.insert_type("Абиссинская кошка");
        conn.insert_type("Австралийский мист");
        conn.insert_type("Американская жесткошерстная");
        conn.closeDB();

    }
}
