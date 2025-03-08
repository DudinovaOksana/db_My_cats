
import java.sql.SQLException;

public class Db1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Conn1 conn1 = new Conn1("jdbc:sqlite:My_cats.s3db");
        conn1.createDB();
        conn1.readDB();
        conn1.closeDB();
    }
}
