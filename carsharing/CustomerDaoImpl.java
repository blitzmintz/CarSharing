package carsharing;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public Customer selectById(int id, Database database) {
        String query = "SELECT * FROM customer WHERE id = " + id;
        try (Connection conn = DriverManager.getConnection(database.getURL());
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(query);
        ) { while (resultSet.next()) {
            String customerName = resultSet.getString("NAME");
            int rentedCarId = resultSet.getInt("RENTED_CAR_ID");
            return new Customer(id, customerName, rentedCarId);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createNewCustomer(String customerName, Database database) {
        String query = "INSERT INTO CUSTOMER (NAME, RENTED_CAR_ID) VALUES ('" + customerName + "', NULL)";
        database.executeUpdate(query);

    }

    @Override
    public ArrayList<Customer> getAllCustomers(Database database) {
        ArrayList<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM CUSTOMER";
        try (Connection conn = DriverManager.getConnection(database.getURL());
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(query);
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int rentedCarId = resultSet.getInt("RENTED_CAR_ID");
                customers.add(new Customer(id, name, rentedCarId));
            }
            return customers;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void updateCustomer(int id, int customerId, Database database) {
        String query = "UPDATE CUSTOMER SET RENTED_CAR_ID = " + id + " WHERE ID = " + customerId;
        database.executeUpdate(query);
    }

    @Override
    public void returnRentalCar(int customerId, Database database) {
        String query = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = " + customerId;
        database.executeUpdate(query);
    }
}
