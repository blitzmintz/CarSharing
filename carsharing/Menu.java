package carsharing;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

class Menu {
    //toggles for what menu is active/inactive
    private boolean mainMenuActive = true;
    private boolean managerMenu;
    private boolean companyMenuActive;
    private boolean carMenuActive;
    private boolean customerMenuActive;
    private boolean rentalCarMenuActive;


    //instantiate a bunch of shit here
    private final Scanner sc;
    private final Database database;
    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;

    //constructor that receives database instance
    Menu(Database database) {
        this.database = database;
        sc = new Scanner(System.in);
        companyDao = new CompanyDaoImpl();
        carDao = new CarDaoImpl();
        customerDao = new CustomerDaoImpl();
    }

    //start method that runs while initial menu is true, this prints the menu
    void startMainMenu() {
        int choice;
        while (mainMenuActive) {
            System.out.println("1. Log in as a manager\n" +
                    "2. Log in as a customer\n" +
                    "3. Create a customer\n" +
                    "0. Exit");
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    managerMenu = true;
                    managerMenu();

                case 2:
                    customerMenuActive = true;
                    customerMenu();

                case 3:
                    System.out.println("Enter the customer name:");
                    String customerName = sc.nextLine();
                    customerDao.createNewCustomer(customerName, database);
                    System.out.println("The customer was added!\n");
                    startMainMenu();

                case 0:
                    mainMenuActive = false;
            }
        }
    }
    //manager method that runs while manager menu is true (i.e. if 1 in start)

    private void managerMenu() {
        int choice;
        while (managerMenu) {
            System.out.println("\n1. Company list\n" +
                    "2. Create a company\n" +
                    "0. Back");
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    companyMenuActive = true;
                    companyMenu();

                }
                case 2 -> {
                    System.out.println("Enter the company name: ");
                    String newCompanyName = sc.nextLine();
                    companyDao.createNewCompany(newCompanyName, database);
                    System.out.println("The company was created!");
                }
                case 0 -> {
                    managerMenu = false;
                    startMainMenu();
                }
                //default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private void companyMenu() {
        int choice;
        while (companyMenuActive) {
            ArrayList<Company> companies = companyDao.selectAllCompanies(database);
            if (companies.isEmpty()) {
                System.out.println("The company list is empty!");
                companyMenuActive = false;
            } else {
                System.out.println("\nChoose a company:");
                int listNo = 1;
                for (Company company : companies) {
                    System.out.println(listNo + ". " + company.getName());
                    listNo++;
                }
                System.out.println("0. Back");
                choice = Integer.parseInt(sc.nextLine());
                if (choice == 0) {
                    companyMenuActive = false;
                } else {
                    Company company = companies.get(choice - 1);
                    carMenuActive = true;
                    carMenu(company);
                }


            }
        }
    }

    private void carMenu(Company company) {
        System.out.println("'" + company.getName() + "'" + " company:");
        int choice;
        int companyId = company.getId();
        while (carMenuActive) {
            System.out.println("1. Car list\n" +
                    "2. Create a car\n" +
                    "0. Back");
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    //print list of cars for that company, else print list empty
                    ArrayList<Car> cars = carDao.selectAllCars(company.getId(), database);
                    if (cars.isEmpty()) {
                        System.out.println("The car list is empty!");
                    } else {
                        int listNo = 1;
                        System.out.println("'" + company.getName() + "' cars:");
                        for (Car car : cars) {
                            System.out.println(listNo + ". " + car.getName());
                            listNo++;
                        }
                        System.out.println();
                    }
                }
                case 2 -> {
                    //create a car for that company using company ID
                    System.out.println("Enter the car name:");
                    String newCarName = sc.nextLine();
                    carDao.createNewcar(newCarName, companyId, database);
                    System.out.println("The car was added!");
                }
                case 0 -> {
                    carMenuActive = false;
                    managerMenu = true;
                    managerMenu();
                }
            }

        }
    }

    private void customerMenu() {
        while (customerMenuActive) {
            System.out.println("Choose a customer:");
            ArrayList<Customer> customers = customerDao.getAllCustomers(database);
            if (customers.isEmpty()) {
                System.out.println("The customer list is empty!");
                customerMenuActive = false;
                mainMenuActive = true;
                startMainMenu();
            } else {
                int listNo = 1;
                for (Customer customer : customers) {
                    System.out.println(listNo + ". " + customer.getName());
                    listNo++;
                }
                System.out.println("0. Back");
                System.out.println();
            }
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 0) {
                customerMenuActive = false;
                mainMenuActive = true;
                startMainMenu();
            } else {
                Customer customer = customers.get(choice - 1);
                rentalCarMenuActive = true;
                rentalCarMenu(customer);
            }
        }
    }

    private void rentalCarMenu(Customer customer) {
        while (rentalCarMenuActive) {
            System.out.println("1. Rent a car\n" +
                    "2. Return a rented car\n" +
                    "3. My rented car\n" +
                    "0. Back");

            boolean hasRentalCar = customer.getRentedCarId() > 0;
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> {
                    if(hasRentalCar) {
                        System.out.println("You've already rented a car!\n");
                    } else {
                        ArrayList<Company> companies = companyDao.selectAllCompanies(database);
                        int listNo = 1;
                        System.out.println("Choose a company:");
                        for (Company company : companies) {
                            System.out.println(listNo + ". " + company.getName());
                            listNo++;

                        }
                        System.out.println("0. Back");
                        choice = Integer.parseInt(sc.nextLine());
                        if (choice == 0) {
                            rentalCarMenuActive = false;
                        } else {
                            Company company = companies.get(choice - 1);
                            ArrayList<Car> cars = carDao.selectRentableCars(company.getId(), database);

                            if (cars.isEmpty()) {
                                System.out.println("No available cars in the '" +
                                        company.getName() + "' company");
                            } else {
                                int carListNo = 1;
                                System.out.println("Choose a car:");
                                for (Car car : cars) {
                                    System.out.println(carListNo + ". " + car.getName());
                                    carListNo++;
                                }
                                System.out.println("0. Back");
                            }

                            int carChoice = Integer.parseInt(sc.nextLine());
                            if (carChoice == 0) {
                                customerMenuActive = true;
                                customerMenu();
                            } else {
                                Car car = cars.get(carChoice - 1);
                                int id = car.getId();
                                int customerId = customer.getId();
                                customerDao.updateCustomer(id, customerId, database);
                                customer.setId(id);
                                System.out.println("You rented '" + car.getName() + "'");
                        }
                    }
                }}
                case 2 -> {
                    if (customer.getRentedCarId() > 0) {
                        int customerId = customer.getId();
                        customerDao.returnRentalCar(customerId, database);
                        System.out.println("You've returned a rented car!");

                    } else {
                        System.out.println("You didn't rent a car!");
                        System.out.println();
                    }
                }
                case 3 -> {
                    if (hasRentalCar) {
                        Car car = carDao.selectCarByID(customer.getRentedCarId(), database);
                        Company company = companyDao.selectById(car.getCompanyId(), database);
                        String carName = car.toString();
                        String companyName = company.toString();
                        System.out.println("Your rented car:\n" + carName + "\n Company: \n" + companyName + "\n");

                    } else {
                        System.out.println("You didn't rent a car!");
                    }
                }
                case 0 -> {
                    rentalCarMenuActive = false;
                    mainMenuActive = true;
                    startMainMenu();
                }
            }

        }
    }

}

