package carsharing;

import java.sql.*;

    public class Main {

        public static void main(String[] args) throws ClassNotFoundException {
            Database database = new Database();
            Menu menu = new Menu(database);
            menu.startMainMenu();

        }
    }
