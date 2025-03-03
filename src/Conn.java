import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conn {
    private Connection connection;
    private Statement statmt;
    private ResultSet resSet;
    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public Conn(String dbAddress) throws ClassNotFoundException, SQLException
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
        statmt.execute("CREATE TABLE if not exists 'types' ('id' INTEGER AUTO_INCREMENT PRIMARY KEY UNIQUE, 'type' VARCHAR(100) not null);");
        System.out.println("Таблица создана или уже существует.");
    }
    // --------Заполнение таблицы--------
    public void insertType(String type) throws SQLException
    {
        String insertCat = String.format("INSERT INTO 'types' ('type') VALUES ('%s'); ", type);
        statmt.execute(insertCat);
        System.out.println("Таблица заполнена");
    }
    // --------Изменение таблицы--------
    public void update_type(int id, String type) throws SQLException
    {
        String updateType = String.format("UPDATE types SET type = ('%s') where id =('%d');",type,id);
        statmt.execute(updateType);
        System.out.println("Запись с id = "+id+" обновлена");
    }
    // --------Удаление таблицы--------
    public void delete_type(int id) throws SQLException{
        String deleteType = String.format("DELETE FROM types WHERE Id = ('%d');",id);
        statmt.execute(deleteType);
        System.out.println("Запись с id = "+id+" удалена");
    }
    // -------- Вывод таблицы--------
    public void readDB() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM users");
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
