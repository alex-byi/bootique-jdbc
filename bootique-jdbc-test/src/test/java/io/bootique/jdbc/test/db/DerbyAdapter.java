package io.bootique.jdbc.test.db;


class DerbyAdapter implements DBAdapter {

    private static final String DB_TYPE = "derby";

    @Override
    public String processSQL(String command) {
        return command;
    }

    @Override
    public String getDBType() {
        return DB_TYPE;
    }
}
