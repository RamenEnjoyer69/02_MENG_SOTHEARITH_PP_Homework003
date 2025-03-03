abstract class StaffMember {
    protected String type;
    protected int id;
    protected String name;
    protected String address;

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public StaffMember(int id, String name, String address) {

        this.id = id;
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "StaffMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public abstract double pay();

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
