package carsharing;

import java.util.ArrayList;

interface CarDao {
    Car selectCarByID(int id, Database database);
    void createNewcar(String carName, int companyID, Database database);
    ArrayList<Car> selectAllCars(int companyid, Database database);
    ArrayList<Car> selectRentableCars(int companyid, Database database);
}
