package model.jdbc.impl;

import java.sql.Connection;

class JDBC {
    Connection connection;

    JDBC(Connection connection) {
        this.connection = connection;
    }
}
