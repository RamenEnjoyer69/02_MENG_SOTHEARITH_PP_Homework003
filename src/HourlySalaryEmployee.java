class HourlySalaryEmployee extends StaffMember {
    private int hoursWorked;
    private double rate;

    public HourlySalaryEmployee(int id, String name, String address, int hoursWorked, double rate) {
        super(id, name, address);
        this.hoursWorked = hoursWorked;
        this.rate = rate;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public double getRate() {
        return rate;
    }

    public double pay() {
        return hoursWorked * rate;
    }
}