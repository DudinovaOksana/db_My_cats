import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

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
        String sql = "CREATE TABLE if not exists cats (id INTEGER PRIMARY KEY," +
                "name VARCHAR(20) not null, type_id INTEGER not null, age INTEGER not null, " +
                "weight DOUBLE not null, FOREIGN KEY (type_id) REFERENCES types(id));";
        statmt.executeQuery(sql);
        System.out.println("Таблица создана или уже существует.");
    }
    // --------Добавить кошку--------
    public int findCatByType (String type) throws SQLException {
        //найти typeId
        statmt = connection.createStatement();
        String select = String.format("select id from types where type='%s'",type);
        ResultSet resultSet = statmt.executeQuery(select);
        try  {
            return resultSet.getInt("id");
        } catch (SQLException e) {
            //Создать typeid, type для новой кошки
            statmt.execute(String.format("insert or ignore into types values (null, '%s')",type));
            statmt.executeQuery(select);
            return resultSet.getInt("id"); //вернуть typeId НОВЫЙ
        }
    }
    public void insertCat(String name, String type, int age, Double weight) throws SQLException {
        int typeId = findCatByType(type);
        String insertCat = String.format(Locale.US, "INSERT or ignore INTO cats VALUES (null, '%s', %d, %d, %.1f)", name,typeId,age,weight);
        statmt.execute(insertCat);
        System.out.println("Котик добавлен");
    }
    // --------Вывод таблицы--------
    public void readDB() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM cats");
        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String  name = resSet.getString("name");
            int  type_id = resSet.getInt("type_id");
            int age = resSet.getInt("age");
            double weight = resSet.getDouble("weight");
            System.out.println( "ID = " + id );
            System.out.println( "name = " + name );
            System.out.println( "type_id = " + type_id );
            System.out.println( "age = " + age );
            System.out.println( "weight = " + weight);
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
