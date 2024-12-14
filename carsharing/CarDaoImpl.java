package carsharing;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;


public class CarDaoImpl implements CarDao {
    @Override
    public Car selectCarByID(int id, Database database) {
        String query = "SELECT * FROM CAR WHERE ID=" + id;
        try (Connection conn = DriverManager.getConnection(database.getURL());
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(query);
        ) { while (resultSet.next()) {
            String carName = resultSet.getString("NAME");
            int companyID = resultSet.getInt("COMPANY_ID");
            return new Car(id, carName, companyID);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createNewcar(String carName, int companyID, Database database) {
        String query = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES('" + carName + "', '" + companyID + "')";
        database.executeUpdate(query);

    }

    @Override
    public ArrayList<Car> selectAllCars(int companyid, Database database) {
        ArrayList<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM CAR WHERE COMPANY_ID=" + companyid;
        try (Connection conn = DriverManager.getConnection(database.getURL());
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(query);
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String carName = resultSet.getString("NAME");
                int companyID = resultSet.getInt("COMPANY_ID");
                cars.add(new Car(id, carName, companyID));
            }
            return cars;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return cars;
    }

    public ArrayList<Car> selectRentableCars(int companyid, Database database) {
        ArrayList<Car> cars = new ArrayList<>();
        String query = "SELECT CAR.* FROM CAR " +
                "LEFT JOIN CUSTOMER ON CAR.ID = CUSTOMER.RENTED_CAR_ID " +
                "WHERE CUSTOMER.NAME IS NULL " +
                "AND CAR.COMPANY_ID= " + companyid + ";";

        try (Connection conn = DriverManager.getConnection(database.getURL());
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(query);
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String carName = resultSet.getString("NAME");
                int companyID = resultSet.getInt("COMPANY_ID");
                cars.add(new Car(id, carName, companyID));
            }
            return cars;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return cars;
    }
}
