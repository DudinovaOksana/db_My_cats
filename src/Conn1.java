import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conn1 {
    private Connection connection;
    private Statement statmt;
    private ResultSet resSet;
    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public Conn1(String dbAddress) throws ClassNotFoundException, SQLException
    {
        connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(dbAddress);
        System.out.println("База Подключена!");
    }
    // --------Создание таблицы--------
    public void createDB() throws ClassNotFoundException, SQLException
    {
        statmt = connection.createStatement();
        statmt.execute("CREATE TABLE if not exists 'cats' ('id' INTEGER PRIMARY KEY UNIQUE not null, " +
                "'name' VARCHAR(20) not null, 'type_id' INTEGER not null, 'age' INTEGER not null, " +
                "'weight' DOUBLE not null, FOREIGN KEY (type_id) REFERENCES types(id));");
        System.out.println("Таблица создана или уже существует.");
    }
    // -------- Вывод таблицы--------
    public void readDB() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM cats");
        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String  type = resSet.getString("type");
            System.out.println( "ID = " + id );
            System.out.println( "type = " + type );
            System.out.println();
        }
        System.out.println("Таблица выведена");
    }

    // --------Закрытие--------
    public void closeDB() throws ClassNotFoundException, SQLException
    {
        this.connection.close();
        this.statmt.close();
        System.out.println("Соединения закрыты");
    }

}
