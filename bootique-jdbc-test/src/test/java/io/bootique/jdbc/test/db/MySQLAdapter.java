package io.bootique.jdbc.test.db;

import io.bootique.jdbc.test.DefaultDatabaseChannelIT;

class MySQLAdapter implements DBAdapter {

    private static final String QUOTE = "`";
    private static final String DB_TYPE = "mysql";

    public MySQLAdapter() {
        //set MySQL quote for DefaultDatabaseChannelIT
        DefaultDatabaseChannelIT.quote = "`a`";
        //set this property for test MySQL DB because MySQL server need to know client timezone
        // or use default (UTC+0). For starts this tests on various machines we assign timezone to UTC
        System.setProperty("user.timezone", "UTC");
    }

    @Override
    public String processSQL(String command) {

        String process = command.replaceAll("\"", QUOTE);

        if (command.contains("VARCHAR (10) FOR BIT DATA")) {
            process = process.replace("VARCHAR (10) FOR BIT DATA", "VARBINARY(100)");
        }
        if (command.contains("TIMESTAMP")) {
            process = process.replace("TIMESTAMP", "TIMESTAMP NULL");
        }
        return process;
    }

    @Override
    public String getDBType() {
        return DB_TYPE;
    }
}
