

import java.sql.SQLException;

public class Db {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Conn conn = new Conn("jdbc:sqlite:My_cats.s3db");
        conn.createDB();

        for (String type : Const.TYPES){
            conn.insertType(type);
        }
        conn.update_type(5,"Сибирская двухцветная");
        conn.delete_type(6);
        conn.closeDB();

    }
}
