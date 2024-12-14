package carsharing;

class Car {
    private final int id;
    private final String name;
    private final int companyId;

    Car(int id, String name, int companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;

    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getCompanyId() {
        return companyId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
