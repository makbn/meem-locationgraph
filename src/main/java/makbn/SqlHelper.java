package makbn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class SqlHelper {

    private static Connection connection;


    public enum DBRM{

        MS_SQL_SERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver","jdbc:sqlserver://{MYSERVER};databaseName={MYDATABASE}"),

        MySQL("org.gjt.mm.mysql.Driver","jdbc:mysql://{MYSERVER}/{MYDATABASE}");



        public String className;
        public String connectionQuery;

        DBRM(String className, String connectionQuery) {
            this.className=className;
            this.connectionQuery=connectionQuery;
        }
    }

    /**
     *
     * @param dbrm
     * @param host a database url of the form
     * @param dbName database name
     * @param username the database user on whose behalf the connection is being made
     * @param password the user's password
     */
    public static void init(DBRM dbrm,String host,String dbName, String username, String password) throws SQLException, ClassNotFoundException {

        String url=dbrm.connectionQuery.replace("{MYSERVER}",host);

        if(dbName!=null)
            url=url.replace("{MYDATABASE}",dbName);


        switch (dbrm){
            case MS_SQL_SERVER:
            case MySQL:
                Class.forName(dbrm.className);
                connection= DriverManager.getConnection(url,username,password);
                break;
        }

    }

    /**
     *
     * @return connection to db is established or not!
     * @throws SQLException
     */
    public static boolean isConnected() throws SQLException {
        if(connection!=null)
            return !connection.isClosed();
        return false;
    }


    /**
     *
     * @return a <code>Statement</code> object for sending
     * SQL statements to the database.
     * @throws SQLException
     */
    public static Statement getStatement() throws SQLException {
        if(isConnected()){
            return connection.createStatement();
        }
        return null;
    }
}
