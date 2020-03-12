package io.bootique.jdbc.test.db;


class PostgreSQLAdapter implements DBAdapter {

    private static final String DB_TYPE = "postgresql";

    @Override
    public String getDBType() {
        return DB_TYPE;
    }

    @Override
    public String processSQL(String command) {
        if (command.contains("DATA")) {
            return command
                    .replace("VARCHAR (10) FOR BIT DATA", "BYTEA");
        }
        return command;
    }
}
