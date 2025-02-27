

import java.sql.SQLException;

public class db {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Conn conn = new Conn("jdbc:sqlite:My_cats.s3db");
        conn.createDB();
        conn.closeDB();

    }
}
