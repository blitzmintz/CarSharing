package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;


public class CompanyDaoImpl implements CompanyDao {

    @Override
    public Company selectById(int id, Database database) {
        String query = "SELECT * FROM COMPANY WHERE ID=" + id;
        try (Connection conn = DriverManager.getConnection(database.getURL());
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(query);
        ) { while (resultSet.next()) {
            String companyName = resultSet.getString("NAME");
            return new Company(id, companyName);
        }
    } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createNewCompany(String companyName, Database database) {
        String query = "INSERT INTO COMPANY (NAME) VALUES ('" + companyName + "')";
        database.executeUpdate(query);

    }

    @Override
    public ArrayList<Company> selectAllCompanies(Database database) {
        ArrayList<Company> companies = new ArrayList<>();
        String query = "SELECT * FROM COMPANY";
        try (Connection conn = DriverManager.getConnection(database.getURL());
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(query);
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                companies.add(new Company(id, name));
            }
            return companies;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return companies;
    }
}
