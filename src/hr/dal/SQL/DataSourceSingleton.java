/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.dal.SQL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javax.sql.DataSource;

/**
 *
 * @author User
 */
public class DataSourceSingleton {
    
    private static final String PASSWORD = "sql";
    private static final String USERNAME = "sa";
    private static final String DATABASE = "BlitzCineStarMovies";
    private static final String SERVER = "GEPARD";
    
    private static DataSource instance;
    
    private static DataSource CreateInstance() {
        SQLServerDataSource datasource = new SQLServerDataSource();
        datasource.setServerName(SERVER);
        datasource.setDatabaseName(DATABASE);
        datasource.setUser(USERNAME);
        datasource.setPassword(PASSWORD);
        return datasource;
    }
    

    private DataSourceSingleton() {
    }
    

    public static DataSource getInstance() {
        if(instance == null){
            instance = CreateInstance();
        }
        return instance;
    }
    
}
