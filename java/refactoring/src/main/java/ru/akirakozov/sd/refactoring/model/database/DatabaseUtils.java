package ru.akirakozov.sd.refactoring.model.database;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtils {
    private DatabaseUtils() {}

    public static DataSource getDataSource() {
        return DataSourceHolder.INSTANCE;
    }

    private static final class DataSourceHolder {
        private static final DataSource INSTANCE;

        static {
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl("jdbc:sqlite:test.db");
            INSTANCE = dataSource;

            try (Connection connection = INSTANCE.getConnection()) {
                if (connection == null) {
                    throw new RuntimeException("Can't get testing connection from DB.");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Can't get testing connection from DB.", e);
            }

        }
    }
}
