package carsharing;

import java.util.ArrayList;

interface CompanyDao {
    Company selectById(int id, Database database);
    void createNewCompany(String companyName, Database database);
    ArrayList<Company> selectAllCompanies(Database database);

}
