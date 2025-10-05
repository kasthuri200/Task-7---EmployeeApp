import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class EmployeeApp {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/employee_db?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "MySQL@2004";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to database successfully!");

            EmployeeDAO dao = new EmployeeDAO(conn);
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- Employee Management ---");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Update Employee Salary");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Choose option: ");

                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Name: "); String name = sc.nextLine();
                        if (name.isEmpty()) {
                            System.out.println("❌ Name cannot be empty!");
                            break;
                        }
                        System.out.print("Age: "); int age = sc.nextInt(); sc.nextLine();
                        System.out.print("Department: "); String dept = sc.nextLine();
                        System.out.print("Salary: "); double salary = sc.nextDouble(); sc.nextLine();
                        dao.addEmployee(new Employee(name, age, dept, salary));
                        break;

                    case 2:
                        List<Employee> list = dao.getAllEmployees();
                        System.out.println("ID | Name | Age | Department | Salary");
                        for (Employee e : list) {
                            System.out.println(e.getId() + " | " + e.getName() + " | " + e.getAge() + " | " + e.getDepartment() + " | " + e.getSalary());
                        }
                        break;

                    case 3:
                        System.out.print("Employee ID: "); int idU = sc.nextInt();
                        System.out.print("New Salary: "); double newSal = sc.nextDouble();
                        dao.updateEmployeeSalary(idU, newSal);
                        break;

                    case 4:
                        System.out.print("Employee ID: "); int idD = sc.nextInt();
                        dao.deleteEmployee(idD);
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        conn.close();
                        sc.close();
                        return;

                    default:
                        System.out.println("❌ Invalid choice!");
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Driver class not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error!");
            e.printStackTrace();
        }
    }
}
