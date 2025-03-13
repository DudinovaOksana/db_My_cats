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
        statmt.execute(sql);
        System.out.println("Таблица создана или уже существует.");
    }
    // --------Добавить кошку--------
    public int findCatByType (String type) throws SQLException {
        statmt = connection.createStatement();
        String select = String.format("select id from types where type='%s'",type);
        ResultSet resultSet = statmt.executeQuery(select);
        try  {
            return resultSet.getInt("id");
        } catch (SQLException e) {
            statmt.execute(String.format("insert or ignore into types values (null, '%s')",type));
            statmt.executeQuery(select);
            return resultSet.getInt("id");
        }
    }
    public void insertCat(String name, String type, int age, Double weight) throws SQLException {
        int typeId = findCatByType(type);
        String insertCat = String.format(Locale.US, "INSERT or ignore INTO cats VALUES (null, '%s', %d, %d, %.1f)", name,typeId,age,weight);
        statmt.execute(insertCat);
        System.out.println("Котик добавлен");
    }
    // --------Добавить много котиков--------
    public void add_more_cats(int n) throws SQLException{
    for (int i=0; i<n; i++){
        insertCat(randomCatName(), randomCatType(), randomCatAge(), randomCatWeight());
    }
    }

    private String randomCatName(){
        double seed = Math.random();
        String[] names = {"Гарфилд","Том","Гудвин","Рокки","Ленивец","Пушок","Спорти","Бегемот","Пират","Гудини","Зорро","Саймон","Альбус","Базилио","Леопольд","Нарцисс","Атос","Каспер","Валлито","Оксфорд","Бисквит","Соня","Клеопатра","Цунами","Забияка","Матильда","Кнопка","Масяня","Царапка","Серсея","Ворсинка","Амели","Наоми","Маркиза","Изольда","Вальс","Несквик","Златан","Баскет","Изюм","Цукат","Мокко","Месси","Кокос","Адидас","Бейлиз","Тайгер","Зефир","Мохи","Валенсия","Баунти","Свити","Текила","Ириска","Карамель","Виски","Кукуруза","Гренка","Фасолька","Льдинка","Китана","Офелия","Дайкири","Брусника","Аватар","Космос","Призрак","Изумруд","Плинтус","Яндекс","Крисмас","Метеор","Оптимус","Смайлик","Цельсий","Краска","Дейзи","Пенка","Веста","Астра","Эйприл","Среда","Бусинка","Гайка","Елка","Золушка","Мята","Радость","Сиам","Оскар","Феликс","Гарри","Байрон","Чарли","Симба","Тао","Абу","Ватсон","Енисей","Измир","Кайзер","Васаби","Байкал","Багира","Айрис","Диана","Мими","Сакура","Индия","Тиффани","Скарлетт","Пикси","Лиззи","Алиса","Лило","Ямайка","Пэрис","Мальта","Аляска"};
        int randomNameIndex = (int) (seed * names.length);
        return names[randomNameIndex];
    }

    private String randomCatType() throws SQLException {
        double seed = Math.random();
        int typesCount = getAllTypesCount();
        int randomNameIndex = (int) (seed * typesCount);
        return getType(randomNameIndex);
    }

    public int getAllTypesCount() throws SQLException{
        statmt = connection.createStatement();
        String getAllTypes = String.format("SELECT COUNT(id) FROM types;");
        ResultSet resultSet = statmt.executeQuery(getAllTypes);
        int res = resultSet.getInt("COUNT(id)");
        return res-1;
    }

    private int randomCatAge(){
        double seed = Math.random();
        return (int)(seed * 20);
    }

    private double randomCatWeight(){
        double seed = Math.random();
        double randomCatWeight = seed * 10;
        return randomCatWeight;
    }

    public String getType(int id) throws SQLException{
        String getType = String.format("Select type from types where id=%d",id);
        ResultSet resultSet = statmt.executeQuery(getType);
        String res;
        try {
            res = resultSet.getString("type");
        } catch (SQLException e) {
            res = "ERROR";
        }
        return res;

    }
    // --------Удаление кота--------
    public void delete_type(int id) throws SQLException{
        String deleteType = String.format("DELETE FROM cats WHERE Id = %d",id);
        statmt.execute(deleteType);
        System.out.println("Запись с id = "+id+" удалена");
    }
    // --------Удаление кота по условию--------
    public void delete_cat(String where) throws SQLException{
        String deleteCat = String.format("DELETE FROM cats WHERE %s",where);
        statmt.execute(deleteCat);
        System.out.println("Кошки с именем, оканчивающимся на 'а', возраст которых равен"+where+" удалены");
    }
    // --------Изменить запись--------
    public void update_cat(int id, String set, String where) throws SQLException
    {
        String updateCat = String.format("UPDATE cats SET %s where id = %d and %s",set,id,where);
        statmt.execute(updateCat);
        System.out.println("Запись с id = "+id+" обновлена");
    }
    // --------Возвратить котика по id--------
    public String get_cat(int id) throws SQLException{
        String getCat = String.format("Select * from cats where id=%d",id);
        statmt.execute(getCat);
        ResultSet resultSet = statmt.getResultSet();
        return resultSet.getString("name");
    }
    // --------Вывести котов, которые подходят под запрос where--------
    public void get_cat_where(String where) throws SQLException{
        String getCatWhere = String.format("Select * from cats where %s",where);
        statmt.execute(getCatWhere);
        ResultSet resultSet = statmt.getResultSet();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }
    }
    // --------Вывести всех котов--------
    public void get_all_cats() throws SQLException{
        String getAllCats = String.format("Select * from cats");
        statmt.execute(getAllCats);
        ResultSet resultSet = statmt.getResultSet();
        while (resultSet.next()){
            System.out.println(resultSet.getString("name"));
        }
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
