package carsharing;

class Customer {
    private int id;
    private String name;
    private int rented_car_id;

    Customer(int id, String name, int rented_car_id) {
        this.id = id;
        this.name = name;
        this.rented_car_id = rented_car_id;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;

    }
    public int getRentedCarId() {
        return rented_car_id;
    }
    public void setId(int rented_car_id) {
        this.rented_car_id = rented_car_id;
    }
    @Override
    public String toString() {
        return this.name;
    }

}
