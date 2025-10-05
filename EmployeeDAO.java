import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private Connection conn;

    public EmployeeDAO(Connection conn) {
        this.conn = conn;
    }

    // Add employee
    public void addEmployee(Employee emp) throws SQLException {
        String sql = "INSERT INTO employee(name, age, department, salary) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emp.getName());
            pstmt.setInt(2, emp.getAge());
            pstmt.setString(3, emp.getDepartment());
            pstmt.setDouble(4, emp.getSalary());
            pstmt.executeUpdate();
            System.out.println("✅ Employee added successfully!");
        }
    }

    // Get all employees
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                );
                list.add(emp);
            }
        }
        return list;
    }

    // Update employee salary
    public void updateEmployeeSalary(int id, double newSalary) throws SQLException {
        String sql = "UPDATE employee SET salary=? WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newSalary);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Employee salary updated!");
            else System.out.println("❌ Employee ID not found!");
        }
    }

    // Delete employee
    public void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employee WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Employee deleted!");
            else System.out.println("❌ Employee ID not found!");
        }
    }
}
