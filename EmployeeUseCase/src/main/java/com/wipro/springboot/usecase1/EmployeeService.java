package com.wipro.springboot.usecase1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Scanner;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @PostConstruct
    public void initEmployees() {
        if (repository.count() == 0) {
            repository.save(new Employee("Alice", "Developer"));
            repository.save(new Employee("Bob", "Tester"));
            repository.save(new Employee("Charlie", "Architect"));
        }

        printAllEmployees(); // ✅ Show employees in the console on startup

        // ✅ Run console input in a separate thread
        new Thread(this::startConsoleInput).start();
    }

    // ✅ Add employee and show updated list
    public Employee addEmployee(String name, String role) {
        Employee newEmployee = repository.save(new Employee(name, role));

        System.out.println("\n✅ New Employee Added:");
        System.out.println("➡ ID: " + newEmployee.getId() + ", Name: " + newEmployee.getName() + ", Role: " + newEmployee.getRole());
        System.out.println("------------------------------");

        printAllEmployees(); // ✅ Print updated list in the console
        return newEmployee;
    }

    // ✅ Fetch all employees for API calls
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    // ✅ Print all employees in the console
    public void printAllEmployees() {
        List<Employee> employees = repository.findAll();
        System.out.println("\n📌 List of Employees in Database:");
        for (Employee emp : employees) {
            System.out.println("➡ ID: " + emp.getId() + ", Name: " + emp.getName() + ", Role: " + emp.getRole());
        }
        System.out.println("------------------------------\n");
    }

    // ✅ Console-based Employee Addition (Runs separately)
    public void startConsoleInput() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n📌 Do you want to add a new employee? (yes/no): ");
            String response = scanner.nextLine();

            if (response.equalsIgnoreCase("no")) {
                System.out.println("✅ Exiting console input mode.");
                break;
            }

            System.out.print("Enter Employee Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Employee Role (Developer/Tester/Architect): ");
            String role = scanner.nextLine();

            if (!isValidRole(role)) {
                System.out.println("❌ Invalid role! Please enter Developer, Tester, or Architect.");
                continue;
            }

            addEmployee(name, role); // Save to database
        }

        scanner.close();
    }

    // ✅ Validate Role Input
    private boolean isValidRole(String role) {
        return role.equalsIgnoreCase("Developer") || 
               role.equalsIgnoreCase("Tester") || 
               role.equalsIgnoreCase("Architect");
    }
}