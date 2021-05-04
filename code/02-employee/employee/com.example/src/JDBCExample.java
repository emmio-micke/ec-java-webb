import java.sql.*;

public class JDBCExample {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:10034/jdbc_tutorial";

    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args) throws Exception {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected successfully to database.");

            stmt = conn.createStatement();

            String sql = "SELECT id, first_name, last_name, address FROM employee";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String first = rs.getString("first_name");
                String last = rs.getString("last_name");
                String address = rs.getString("address");

                System.out.println("ID: " + id);
                System.out.println("First: " + first);
                System.out.println("Last: " + last);
                System.out.println("Address: " + address);
                System.out.println("---------");
            }
            rs.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        System.out.println("Done!");
    }
}
