package carsharing;

import java.util.ArrayList;

interface CustomerDao {
    Customer selectById(int id, Database database);
    void createNewCustomer(String customerName,Database database);
    ArrayList<Customer> getAllCustomers(Database database);
    void updateCustomer(int id, int customerId, Database database);

    void returnRentalCar(int customerId, Database database);
}
