package lab7;

import lab6.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class DBManager implements AutoCloseable {
    private final static String PROPERTIES_PATH = "database.properties";
    private Connection connection;

    public DBManager() {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(PROPERTIES_PATH))) {
            props.load(in);
        } catch (IOException exc) {
            System.out.println("Cannot read properties.");
        }

        System.setProperty("jdbc.drivers", props.getProperty("jdbc.drivers"));
        try {
            connection = DriverManager.getConnection(
                    props.getProperty("jdbc.url"),
                    Server.dbLogin,
                    Server.dbPassword
            );
        } catch (SQLException exc) {
            System.out.println("Cannot connect to database.");
        }
    }

    public ResultSet getQuery(String sql, Object... preps) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            fillStatement(statement, preps);
            return statement.executeQuery();
        } catch (SQLException exc) {
            System.out.println("Cannot get query from database.");
            return null;
        }
    }

    public int update(String sql, Object... preps) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            fillStatement(statement, preps);
            return statement.executeUpdate();
        } catch (SQLException exc) {
            System.out.println("Cannot insert into database.");
            return 0;
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException exc) {
            System.out.println("Caught exception during work with database.");
        }
    }

    private void fillStatement(PreparedStatement statement, Object[] preps) throws SQLException {
        for (int i = 0; i < preps.length; i++) {
            if (preps[i] instanceof Integer) {
                statement.setInt(i + 1, (Integer) preps[i]);
                continue;
            }
            if (preps[i] instanceof Double) {
                statement.setDouble(i + 1, (Double) preps[i]);
                continue;
            }
            if (preps[i] instanceof Boolean) {
                statement.setBoolean(i + 1, (Boolean) preps[i]);
            }
            statement.setString(i + 1, preps[i].toString());
        }
    }
}
