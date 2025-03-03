import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import org.nocrala.tools.texttablefmt.CellStyle.HorizontalAlign;

public class Utils {
    private static final String RESET = Colors.RESET.getCode();
    private static final String RED = Colors.RED.getCode();
    private static final String YELLOW = Colors.YELLOW.getCode();
    private static final String GREEN = Colors.GREEN.getCode();
    private static final String CYAN = Colors.CYAN.getCode();
    private static final String PINK = Colors.PINK.getCode();
    private static Scanner sc = new Scanner(System.in);
    private static final List<StaffMember> employees = new ArrayList<>();
    private static final List<Volunteer> volunteers = new ArrayList<>();
    private static final List<SalariedEmployee> salariedEmp = new ArrayList<>();
    private static final List<HourlySalaryEmployee> hourlyEmp = new ArrayList<>();
    private static int nextId = 11; // cuz there are 10 records in the list

    //    display
    public static void displayMenu() {
        while (true) {
            System.out.println("┌───────────────────────────────────┐");
            System.out.println("│      " + CYAN + "STAFF MANAGEMENT SYSTEM" + RESET + "      │");
            System.out.println("├───────────────────────────────────┤");
            System.out.println("│  " + YELLOW + "1. " + GREEN + "Insert Employee               " + RESET + "│");
            System.out.println("│  " + YELLOW + "2. " + GREEN + "Update Employee               " + RESET + "│");
            System.out.println("│  " + YELLOW + "3. " + GREEN + "Display Employee              " + RESET + "│");
            System.out.println("│  " + YELLOW + "4. " + GREEN + "Remove Employee               " + RESET + "│");
            System.out.println("│  " + YELLOW + "5. " + RED + "Exit                          " + RESET + "│");
            System.out.println("└───────────────────────────────────┘");

            System.out.println("\n-------------------------------------");
            System.out.print(PINK + "Select an option: " + RESET);
            int option = getValidIntegerInput(PINK + "Select an option: " + RESET, 1, 5);
            optionHandler(option);
            if (option == 5) break;
        }
        sc.close();
    }

    // check if input is valid, only int tho
    private static int getValidIntegerInput(String prompt, int min, int max) {
        int input;
        while (true) {

            if (sc.hasNextInt()) {
                input = sc.nextInt();
                sc.nextLine();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println(RED + "Invalid! Please choose between " + min + " and " + max + "." + RESET);
                    System.out.print(PINK + prompt + RESET);
                }
            } else {
                System.out.println(RED + "Invalid input! Please enter a number." + RESET);
                System.out.print(PINK + prompt + RESET);
                sc.next();
            }
        }
    }


    //handle option
    public static void optionHandler(int option) {
        switch (option) {
            case 1 -> {
                insertEmployee();
            }
            case 2 -> {
                updateEmployee();
            }
            case 3 -> {
                displayEmployee();
            }
            case 4 -> {
                removeEmployee();
            }
            case 5 -> System.out.println(GREEN + "Exiting... Have a nice day :3" + RESET);
        }
    }

    private static void removeEmployee() {
        System.out.println(">>>>>>>>> Remove Employee >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.print(PINK + "Enter the ID of the employee you want to remove: " + RESET);
        int id = getValidIntegerInput(PINK + "Enter the ID of the employee you want to remove: " + RESET, 1, employees.size());

        // use id to search
        Optional<StaffMember> employeeToRemove = findEmployeeById(id);

        employeeToRemove.ifPresentOrElse(
                emp -> {
                    employees.remove(emp);
                    System.out.println(GREEN + "Employee with ID " + id + " has been successfully removed." + RESET);
                },
                () -> System.out.println(RED + "Employee with ID " + id + " not found." + RESET)
        );
    }


    private static Optional<StaffMember> findEmployeeById(int id) {
        return employees.stream()
                .filter(emp -> emp.getId() == id)
                .findFirst();  // Returns an Optional<StaffMember>
    }

    private static void updateEmployee() {
        System.out.println(">>>>>>>>> Update Employee >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.print(PINK + "Enter the ID of the employee you want to update: " + RESET);
        int id = getValidIntegerInput(PINK + "Enter the ID of the employee you want to update: " + RESET, 1, employees.size());


        findEmployeeById(id).ifPresentOrElse(
                emp -> {
                    // Create table
                    CellStyle centerStyle = new CellStyle(HorizontalAlign.center);
                    CellStyle boldCenterStyle = new CellStyle(HorizontalAlign.center);


                    String empType = emp.getClass().getSimpleName();


                    updateLoop:
                    while (true) {
                        switch (empType) {
                            case "Volunteer" -> {
                                Table t = new Table(6, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
                                // sizing
                                t.setColumnWidth(0, 20, 30); // Type
                                t.setColumnWidth(1, 8, 12);  // ID
                                t.setColumnWidth(2, 20, 30); // Name
                                t.setColumnWidth(3, 20, 30); // Address
                                t.setColumnWidth(4, 15, 25); // Salary
                                t.setColumnWidth(5, 15, 25); // Pay

                                // table headers
                                t.addCell("Type", boldCenterStyle);
                                t.addCell("ID", boldCenterStyle);
                                t.addCell("Name", boldCenterStyle);
                                t.addCell("Address", boldCenterStyle);
                                t.addCell("Salary", boldCenterStyle);
                                t.addCell("Pay", boldCenterStyle);

                                // data
                                Volunteer volunteer = (Volunteer) emp;

                                // Table Data
                                t.addCell("Volunteer", centerStyle);
                                t.addCell(String.valueOf(volunteer.getId()), centerStyle);
                                t.addCell(volunteer.getName(), centerStyle);
                                t.addCell(volunteer.getAddress(), centerStyle);
                                t.addCell(String.valueOf(volunteer.getSalary()), centerStyle);
                                t.addCell("$" + String.format("%.2f", volunteer.pay()), centerStyle);
                                System.out.println("\n" + t.render());
                                System.out.println("\n" + YELLOW + " 1." + GREEN + " Name     "
                                        + YELLOW + "2." + GREEN + " Address     "
                                        + YELLOW + "3." + GREEN + " Salary    "
                                        + YELLOW + "0." + GREEN + " Cancel" + RESET);
                                System.out.print(PINK + "Select a column to update: " + RESET);
                                int col = getValidIntegerInput(PINK + "\nSelect a column to update: " + RESET, 0, 3);
                                if (col == 0) {
                                    System.out.println("Going back to main menu...");

                                    break updateLoop;
                                }
                                switch (col) {
                                    case 1 -> {
                                        System.out.print("Change name to: ");
                                        String newName = sc.nextLine();
                                        volunteer.setName(newName);
                                        System.out.println(GREEN + "Name has been updated successfully!" + RESET);
                                    }
                                    case 2 -> {
                                        System.out.print("Change address to: ");
                                        String newAddress = sc.nextLine();
                                        volunteer.setAddress(newAddress);
                                        System.out.println(GREEN + "Address has been updated successfully!" + RESET);
                                    }
                                    case 3 -> {
                                        System.out.print("Change salary to: ");
                                        double newSalary = getValidDoubleInput("Change salary to: ");
                                        volunteer.setSalary(newSalary);
                                        System.out.println(GREEN + "Salary has been updated successfully!" + RESET);
                                    }

                                    default -> System.out.println(RED + "Please select a valid option." + RESET);
                                }
                            }
                            case "SalariedEmployee" -> {
                                Table t = new Table(7, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
                                // sizing
                                t.setColumnWidth(0, 20, 30); // Type
                                t.setColumnWidth(1, 8, 12);  // ID
                                t.setColumnWidth(2, 20, 30); // Name
                                t.setColumnWidth(3, 20, 30); // Address
                                t.setColumnWidth(4, 15, 25); // Salary
                                t.setColumnWidth(5, 15, 25); // Bonus
                                t.setColumnWidth(6, 15, 25); // Pay

                                // table headers
                                t.addCell("Type", boldCenterStyle);
                                t.addCell("ID", boldCenterStyle);
                                t.addCell("Name", boldCenterStyle);
                                t.addCell("Address", boldCenterStyle);
                                t.addCell("Salary", boldCenterStyle);
                                t.addCell("Bonus", boldCenterStyle);
                                t.addCell("Pay", boldCenterStyle);

                                //data
                                SalariedEmployee salariedEmployee = (SalariedEmployee) emp;
                                t.addCell("Salaried Employee", centerStyle);
                                t.addCell(String.valueOf(salariedEmployee.getId()), centerStyle);
                                t.addCell(salariedEmployee.getName(), centerStyle);
                                t.addCell(salariedEmployee.getAddress(), centerStyle);
                                t.addCell(String.valueOf(salariedEmployee.getSalary()), centerStyle);
                                t.addCell(String.valueOf(salariedEmployee.getBonus()), centerStyle);
                                t.addCell("$" + String.format("%.2f", salariedEmployee.pay()), centerStyle);
                                System.out.println("\n" + t.render());
                                System.out.println("\n" + YELLOW + " 1." + GREEN + " Name     "
                                        + YELLOW + "2." + GREEN + " Address     "
                                        + YELLOW + "3." + GREEN + " Salary    "
                                        + YELLOW + "4." + GREEN + " Bonus     "
                                        + YELLOW + "0." + GREEN + " Cancel" + RESET);
                                System.out.print(PINK + "Select a column to update: " + RESET);
                                int col = getValidIntegerInput(PINK + "\nSelect a column to update: " + RESET, 0, 4);
                                if (col == 0) {
                                    System.out.println("Going back to main menu...");
                                    break updateLoop;
                                }
                                switch (col) {
                                    case 1 -> {
                                        System.out.print("Change name to: ");
                                        String newName = sc.nextLine();
                                        salariedEmployee.setName(newName);
                                        System.out.println(GREEN + "Name has been updated successfully!" + RESET);
                                    }
                                    case 2 -> {
                                        System.out.print("Change address to: ");
                                        String newAddress = sc.nextLine();
                                        salariedEmployee.setAddress(newAddress);
                                        System.out.println(GREEN + "Address has been updated successfully!" + RESET);
                                    }
                                    case 3 -> {
                                        System.out.print("Change salary to: ");
                                        double newSalary = getValidDoubleInput("Change salary to: ");

                                        salariedEmployee.setSalary(newSalary);
                                        System.out.println(GREEN + "Salary has been updated successfully!" + RESET);
                                    }
                                    case 4 -> {
                                        System.out.print("Change bonus to: ");
                                        double newBonus = getValidDoubleInput("Change bonus to: ");

                                        salariedEmployee.setSalary(newBonus);
                                        System.out.println(GREEN + "Bonus has been updated successfully!" + RESET);
                                    }

                                    default -> System.out.println(RED + "Please select a valid option." + RESET);
                                }
                            }
                            case "HourlySalaryEmployee" -> {
                                Table t = new Table(7, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
                                // sizing
                                t.setColumnWidth(0, 20, 30); // Type
                                t.setColumnWidth(1, 8, 12);  // ID
                                t.setColumnWidth(2, 20, 30); // Name
                                t.setColumnWidth(3, 20, 30); // Address
                                t.setColumnWidth(4, 10, 10); // Hour
                                t.setColumnWidth(5, 15, 15); // Rate
                                t.setColumnWidth(6, 15, 20); // Pay

                                // table headers
                                t.addCell("Type", boldCenterStyle);
                                t.addCell("ID", boldCenterStyle);
                                t.addCell("Name", boldCenterStyle);
                                t.addCell("Address", boldCenterStyle);
                                t.addCell("Hour", boldCenterStyle);
                                t.addCell("Rate", boldCenterStyle);
                                t.addCell("Pay", boldCenterStyle);

                                // data
                                HourlySalaryEmployee hourlySalaryEmployee = (HourlySalaryEmployee) emp;
                                t.addCell("Hourly Employee", centerStyle);
                                t.addCell(String.valueOf(hourlySalaryEmployee.getId()), centerStyle);
                                t.addCell(hourlySalaryEmployee.getName(), centerStyle);
                                t.addCell(hourlySalaryEmployee.getAddress(), centerStyle);
                                t.addCell(String.valueOf(hourlySalaryEmployee.getHoursWorked()), centerStyle);
                                t.addCell(String.valueOf(hourlySalaryEmployee.getRate()), centerStyle);
                                t.addCell("$" + String.format("%.2f", hourlySalaryEmployee.pay()), centerStyle);
                                System.out.println("\n" + t.render());
                                System.out.println("\n" + YELLOW + " 1." + GREEN + " Name     "
                                        + YELLOW + "2." + GREEN + " Address     "
                                        + YELLOW + "3." + GREEN + " Hours    "
                                        + YELLOW + "4." + GREEN + " Rate     "
                                        + YELLOW + "0." + GREEN + " Cancel" + RESET);
                                System.out.print(PINK + "Select a column to update: " + RESET);
                                int col = getValidIntegerInput(PINK + "\nSelect a column to update: " + RESET, 0, 4);
                                if (col == 0) {
                                    System.out.println("Going back to main menu...");
                                    break updateLoop;
                                }
                                switch (col) {
                                    case 1 -> {
                                        System.out.print("Change name to: ");
                                        String newName = sc.nextLine();
                                        hourlySalaryEmployee.setName(newName);
                                        System.out.println(GREEN + "Name has been updated successfully!" + RESET);
                                    }
                                    case 2 -> {
                                        System.out.print("Change address to: ");
                                        String newAddress = sc.nextLine();
                                        hourlySalaryEmployee.setAddress(newAddress);
                                        System.out.println(GREEN + "Address has been updated successfully!" + RESET);
                                    }
                                    case 3 -> {
                                        int newHour = getValidIntegerInput("Change hours to: ", 1, 168);

                                        hourlySalaryEmployee.setHoursWorked(newHour);
                                        System.out.println(GREEN + "Hours worked has been updated successfully!" + RESET);
                                    }
                                    case 4 -> {
                                        System.out.print("Change rate to: ");
                                        double newRate = getValidDoubleInput("Change rate to: ");

                                        hourlySalaryEmployee.setRate(newRate);
                                        System.out.println(GREEN + "Rate has been updated successfully!" + RESET);
                                    }

                                    default -> System.out.println(RED + "Please select a valid option." + RESET);
                                }
                            }
                        }
                    }
                },
                () -> System.out.println("Employee not found.")
        );


    }


    //insert menu
    public static void insertEmployee() {
        System.out.println("\n>>>>>>>>>>> INSERT EMPLOYEE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
        System.out.println("┌────────────────────────────────────────────────────────────────────┐");
        System.out.println("│ " + YELLOW + "1." + GREEN + " Volunteer " + RESET +
                "│ " + YELLOW + "2." + GREEN + " Salaried Employee " + RESET +
                "│ " + YELLOW + "3." + GREEN + " Hourly Employee " + RESET +
                "│ " + YELLOW + "4." + GREEN + " Back " + RESET + "│");
        System.out.println("└────────────────────────────────────────────────────────────────────┘");
        System.out.println("\n----------------------------------------------------------------------");
        System.out.print(PINK + "Select an option: " + RESET);
        int option = getValidIntegerInput(PINK + "Select an option: " + RESET, 1, 4);
        if (option == 4) return;

        switch (option) {
            case 1 -> inputData("volunteer");
            case 2 -> inputData("salaried employee");
            case 3 -> inputData("hourly employee");
        }
    }

    // this is for input
    public static void inputData(String type) {


        int id = nextId++;
        System.out.println("ID: " + id);

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter address: ");
        String address = sc.nextLine();

        StaffMember employee = null;

        switch (type) {
            case "volunteer" -> {
                System.out.print("Enter any pay (or 0 if none): ");
                double salary = getValidDoubleInput("Enter any pay (or 0 if none): ");
                employee = new Volunteer(id, name, address, salary);
                volunteers.add((Volunteer) employee);
            }
            case "salaried employee" -> {
                System.out.print("Enter salary: ");
                double salary = getValidDoubleInput("Enter salary: ");
                System.out.print("Enter bonus: ");
                double bonus = getValidDoubleInput("Enter bonus: ");
                employee = new SalariedEmployee(id, name, address, salary, bonus);
                salariedEmp.add((SalariedEmployee) employee);
            }
            case "hourly employee" -> {
                System.out.print("Enter hours worked: ");
                int hoursWorked = getValidIntegerInput(PINK + "Select an option: " + RESET, 1, 168); // i had to put 168 cuz the method requires min and max, so 168 is basically max hours per week
                System.out.print("Enter hourly rate: ");
                double rate = getValidDoubleInput("Enter hourly rate: ");
                employee = new HourlySalaryEmployee(id, name, address, hoursWorked, rate);
                hourlyEmp.add((HourlySalaryEmployee) employee);
            }
        }

        if (employee != null) {
            employees.add(employee);
            System.out.println(GREEN + "Employee '" + name + "' has been added as '" + type + "'" + RESET);

        } else {
            System.out.println(RED + "Error: Employee could not be added!" + RESET);
        }
        System.out.println("\n");
    }

    //pre existing data
    public static void initializeEmployees() {
        employees.add(new Volunteer(1, "Alice Johnson", "123 Maple St", 500.0));
        employees.add(new Volunteer(2, "Bob Smith", "456 Oak St", 500.0));
        employees.add(new SalariedEmployee(3, "Charlie Brown", "789 Pine St", 6000.0, 5000.0));
        employees.add(new SalariedEmployee(4, "Diana Prince", "321 Cedar St", 7500.0, 7000.0));
        employees.add(new SalariedEmployee(5, "Ethan Hunt", "654 Birch St", 8500.0, 9000.0));
        employees.add(new HourlySalaryEmployee(6, "Fiona Gallagher", "987 Spruce St", 40, 250.0));
        employees.add(new HourlySalaryEmployee(7, "George Lucas", "741 Redwood St", 35, 110.5));
        employees.add(new HourlySalaryEmployee(8, "Hannah Baker", "852 Fir St", 30, 150.0));
        employees.add(new SalariedEmployee(9, "Isaac Newton", "159 Elm St", 9500.0, 1000.0));
        employees.add(new Volunteer(10, "Jack Sparrow", "357 Palm St", 0.0));
    }

    // validate double
    private static double getValidDoubleInput(String prompt) {
        while (true) {
            if (sc.hasNextDouble()) {
                double value = sc.nextDouble();
                sc.nextLine();
                return value;
            } else {
                System.out.println(RED + "Invalid input! Please enter a valid number." + RESET);
                sc.next();
                System.out.print(prompt);
            }
        }
    }


    // Display emp
    public static void displayEmployee() {
        final int PAGE_SIZE = 4; // records per page
        int currentPage = 1;
        int totalPages = (int) Math.ceil((double) employees.size() / PAGE_SIZE);

        while (true) {

            System.out.println("\n\n>>>>>>>>>>>> Display Employee >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            // Create table
            CellStyle centerStyle = new CellStyle(HorizontalAlign.center);
            CellStyle boldCenterStyle = new CellStyle(HorizontalAlign.center);
            Table t = new Table(9, BorderStyle.UNICODE_BOX, ShownBorders.ALL);

            // sizing
            t.setColumnWidth(0, 20, 30); // Type
            t.setColumnWidth(1, 8, 12);  // ID
            t.setColumnWidth(2, 20, 30); // Name
            t.setColumnWidth(3, 20, 30); // Address
            t.setColumnWidth(4, 10, 25); // Salary
            t.setColumnWidth(5, 10, 15); // Bonus
            t.setColumnWidth(6, 10, 10); // Hour
            t.setColumnWidth(7, 10, 15); // Rate
            t.setColumnWidth(8, 10, 20); // Pay

            // table headers
            t.addCell("Type", boldCenterStyle);
            t.addCell("ID", boldCenterStyle);
            t.addCell("Name", boldCenterStyle);
            t.addCell("Address", boldCenterStyle);
            t.addCell("Salary", boldCenterStyle);
            t.addCell("Bonus", boldCenterStyle);
            t.addCell("Hour", boldCenterStyle);
            t.addCell("Rate", boldCenterStyle);
            t.addCell("Pay", boldCenterStyle);

            // range of employees to display on the current page
            int startIdx = (currentPage - 1) * PAGE_SIZE;
            int endIdx = Math.min(startIdx + PAGE_SIZE, employees.size());

            for (int i = startIdx; i < endIdx; i++) {
                StaffMember emp = employees.get(i);
                if (emp instanceof SalariedEmployee salaryEmp) {
                    t.addCell("Salaried Employee", centerStyle);
                    t.addCell(String.valueOf(salaryEmp.id), centerStyle);
                    t.addCell(salaryEmp.name, centerStyle);
                    t.addCell(salaryEmp.address, centerStyle);
                    t.addCell("$" + String.valueOf(salaryEmp.getSalary()), centerStyle);
                    t.addCell("$" + String.valueOf(salaryEmp.getBonus()), centerStyle);
                    t.addCell("-", centerStyle);
                    t.addCell("-", centerStyle);
                    t.addCell("$" + String.valueOf(salaryEmp.pay()), centerStyle);
                } else if (emp instanceof HourlySalaryEmployee hourlyEmp) {
                    t.addCell("Hourly Employee", centerStyle);
                    t.addCell(String.valueOf(hourlyEmp.id), centerStyle);
                    t.addCell(hourlyEmp.name, centerStyle);
                    t.addCell(hourlyEmp.address, centerStyle);
                    t.addCell("-", centerStyle);
                    t.addCell("-", centerStyle);
                    t.addCell(String.valueOf(hourlyEmp.getHoursWorked()), centerStyle);
                    t.addCell("$" + String.valueOf(hourlyEmp.getRate()), centerStyle);
                    t.addCell("$" + String.valueOf(hourlyEmp.pay()), centerStyle);
                } else if (emp instanceof Volunteer volunteer) {
                    t.addCell("Volunteer", centerStyle);
                    t.addCell(String.valueOf(volunteer.id), centerStyle);
                    t.addCell(volunteer.name, centerStyle);
                    t.addCell(volunteer.address, centerStyle);
                    t.addCell("$" + String.valueOf(volunteer.getSalary()), centerStyle);
                    t.addCell("-", centerStyle);
                    t.addCell("-", centerStyle);
                    t.addCell("-", centerStyle);
                    t.addCell("$" + String.valueOf(volunteer.pay()), centerStyle);
                }
            }

            // render
            System.out.println(t.render());
            System.out.println("\nPage: " + currentPage + "/" + totalPages);
            System.out.println(YELLOW + "1. " + GREEN + "First Page    " + YELLOW + "2. " + GREEN + "Next Page    " + YELLOW + "3. " + GREEN + "Previous Page    " + YELLOW + "4. " + GREEN + "Last Page    " + YELLOW + "5. " + GREEN + "Exit" + RESET);
            System.out.println("-------------------------------");
            System.out.print(PINK + "Select an option: " + RESET);
            // control
            int option = getValidIntegerInput(PINK + "Select an option: " + RESET, 1, 5);

            switch (option) {
                case 1 -> currentPage = 1; // First page
                case 2 -> {
                    if (currentPage < totalPages) currentPage++; // Next page
                    else System.out.println(RED + "Already on the last page!" + RESET);
                }
                case 3 -> {
                    if (currentPage > 1) currentPage--; // prev page
                    else System.out.println(RED + "Already on the first page!" + RESET);
                }
                case 4 -> currentPage = totalPages; // Last page
                case 5 -> {
                    System.out.println(GREEN + "Going back to the main menu..." + RESET);
                    return;
                }
                default -> System.out.println(RED + "Invalid Option!" + RESET);
            }
        }
    }


}
