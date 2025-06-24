import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlExample {
    public static void main(String[] args) throws ClassNotFoundException {
        // Konfigurasi koneksi langsung (tanpa argumen)
        String host = "tictactoe-fahyi-f2cb.f.aivencloud.com";
        String port = "13904";
        String databaseName = "defaultdb";
        String userName = "avnadmin";
        String password = "AVNS_EGlGpkJhi4bZqiE2AY9";

        // URL JDBC lengkap dengan SSL
        String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslMode=REQUIRED";

        // Load driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Koneksi dan eksekusi
        try (Connection connection = DriverManager.getConnection(jdbcUrl, userName, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT version() AS version")) {

            System.out.println("✅ Connected to MySQL successfully!");
            while (resultSet.next()) {
                System.out.println("MySQL Version: " + resultSet.getString("version"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Connection failure.");
            e.printStackTrace();
        }
    }
}
