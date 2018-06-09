package ztf.dao;
import ztf.po.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author:ZTF
 * @Date:2018/6/6 13:51
 */
public class UserDao {
    String TABLE_NAME="user";
    String ID="account";
    String CREATE_SQL ="(account varchar(254),password varchar(254),user_name varchar(254))";
    String INSERT_SQL = "insert into "+TABLE_NAME+" values(?,?,?)" ;
    String SELECT_SQL = "select * from "+TABLE_NAME ;
    String SELECT_SQL_ID = "select * from "+TABLE_NAME+" where "+ID+"=?" ;
    String DELETE_SQL="delete from "+TABLE_NAME+" where "+ID+"=?";
    private UserDao() {}
    private static UserDao single=null;
    public static UserDao getInstance() {
        if (single == null) {
            single = new UserDao();
        }
        return single;
    }

    public void createData(){
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try(Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:aname", "sa", "");
            Statement stmt = connection.createStatement();)
        {
            String sql1 = "create table IF NOT EXISTS "+TABLE_NAME+CREATE_SQL;
            stmt.executeUpdate(sql1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insert(User user) {
        try {
            createData();
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try(Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:aname", "sa", "");
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL))
        {
            ps.setString(1,user.getAccount());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getUserName());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User selectByAccount(String account) {
        try {
            createData();
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user=null;
        try(Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:aname", "sa", "");
            PreparedStatement state = connection.prepareStatement(SELECT_SQL_ID))
        {
            state.setString(1, account);
            ResultSet resultSet=state.executeQuery();
            if(resultSet.next()){
                user=new User(resultSet.getString(1),
                        resultSet.getString(2),resultSet.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public List<User> selectAll() {
        try {
            createData();
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<User> userList=new ArrayList<>();
        try(Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:aname", "sa", "");
            PreparedStatement state = connection.prepareStatement(SELECT_SQL))
        {
            ResultSet resultSet=state.executeQuery();
            while(resultSet.next()){
                User user=new User(resultSet.getString(1),
                        resultSet.getString(2),resultSet.getString(3));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}
