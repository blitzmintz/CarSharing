package carsharing;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

class Database {
    private String URL = "jdbc:h2:./src/carsharing/db/carsharing";

    Database() {
        try {
            Class.forName("org.h2.Driver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String CREATE_COMPANY_TABLE = "CREATE TABLE IF NOT EXISTS COMPANY " +
                "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                " name VARCHAR_IGNORECASE(255) NOT NULL UNIQUE);" +
                "ALTER TABLE company ALTER COLUMN id RESTART WITH 1";

        String CREATE_CAR_TABLE = "CREATE TABLE IF NOT EXISTS CAR " +
                "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                " name VARCHAR_IGNORECASE(255) NOT NULL UNIQUE, " +
                " company_id INTEGER NOT NULL ," +
                " FOREIGN KEY (company_id) REFERENCES COMPANY(id));"
                + "ALTER TABLE car ALTER COLUMN id RESTART WITH 1";

        String CREATE_CUSTOMER_TABLE = "CREATE TABLE IF NOT EXISTS CUSTOMER " +
                "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                " name VARCHAR_IGNORECASE(255) NOT NULL UNIQUE, " +
                " rented_car_id INTEGER, " +
                "FOREIGN KEY (rented_car_id) REFERENCES CAR(id));" +
                "ALTER TABLE customer ALTER COLUMN id RESTART WITH 1";


        executeUpdate(CREATE_COMPANY_TABLE);
        executeUpdate(CREATE_CAR_TABLE);
        executeUpdate(CREATE_CUSTOMER_TABLE);
    }



    void executeUpdate(String query) {


        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            conn.setAutoCommit(true);
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getURL() {
        return URL;
    }
}
