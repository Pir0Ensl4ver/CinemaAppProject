/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.dal.SQL;

import hr.dal.Repo;
import hr.model.User;
import hr.model.UserType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author User
 */
public class UserRepo implements Repo<User> {
    
    private static final String ID_USER = "IDUser";
    private static final String NAME = "Name";
    private static final String PASSWORD = "Password";
    private static final String TYPE = "Type";

    private static final String CREATE_USER = "{ CALL createUser (?,?,?,?) }";
    private static final String UPDATE_USER = "{ CALL updateUser (?,?,?,?) }";
    private static final String DELETE_USER = "{ CALL deleteUser (?) }";
    private static final String SELECT_USER = "{ CALL selectUser (?) }";
    private static final String SELECT_USERS = "{ CALL selectUsers }";
    
    @Override
    public int create(User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_USER)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getType().getType());
            
            stmt.registerOutParameter(4, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(4);
        }
    }

    @Override
    public void create(List<User> users) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_USER)) {
            
            for (User user : users) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getPassword());
                stmt.setInt(3, user.getType().getType());

                stmt.registerOutParameter(4, Types.INTEGER);
                stmt.executeUpdate();
            } 
        }
    }

    @Override
    public void update(int id, User data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_USER)) {
            
            stmt.setString(1, data.getName());
            stmt.setString(2, data.getPassword());
            stmt.setInt(3, data.getType().getType());
            
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_USER)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<User> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_USER)) {

            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(new User(
                            rs.getInt(ID_USER),
                            rs.getString(NAME),
                            rs.getString(PASSWORD),
                            UserType.from(rs.getInt(TYPE))));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> select() throws Exception {
        List<User> users = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try(Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_USERS);
                ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                users.add(new User(
                        rs.getInt(ID_USER), 
                        rs.getString(NAME),
                        rs.getString(PASSWORD),
                        UserType.from(rs.getInt(TYPE))));
            }
        }
        return users;
    }
    
}
