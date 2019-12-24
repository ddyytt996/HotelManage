package dao;

import domain.Customer;
import util.JdbcHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDao {

    private static CustomerDao customerDao=new CustomerDao();
    private CustomerDao(){}
    public static CustomerDao getInstance(){return customerDao;}

    public Customer login(Customer logincustomer) throws SQLException {
            Customer customer=null;
            Connection connection= JdbcHelper.getConn();
            String login_sql = "SELECT * FROM Customer WHERE account=? and password=?";
            //在该连接上创建预编译语句对象
            PreparedStatement preparedStatement = connection.prepareStatement(login_sql);
            //为预编译参数赋值
            preparedStatement.setString(1, logincustomer.getAccount());
            preparedStatement.setString(2, logincustomer.getPassword());
            //执行预编译语句获取结果集对象
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                customer=new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("account"),
                        resultSet.getString("password"),
                        resultSet.getString("name"));
            }
            //关闭资源
            JdbcHelper.close(resultSet, preparedStatement, connection);
            return customer;
    }

    public boolean register(Customer customer) throws SQLException {
        Connection connection=JdbcHelper.getConn();
        String register_sql = "INSERT INTO User (account,password,name) VALUES (?,?,?)";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(register_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,customer.getAccount());
        preparedStatement.setString(2,customer.getPassword());
        preparedStatement.setString(3,customer.getName());
        //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
        int affectedRowNum = preparedStatement.executeUpdate();
        preparedStatement.close();
        return affectedRowNum > 0;
    }

    public boolean findPassword(Customer customer) throws SQLException {
        String password=null;
        Connection connection= JdbcHelper.getConn();
        String user_sql = "UPDATE Customer set password WHERE account=? and name=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(user_sql);
        preparedStatement.setString(1,customer.getPassword());
        preparedStatement.setString(2,customer.getAccount());
        preparedStatement.setString(3,customer.getName());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRows > 0;
    }

    //修改密码的方法，获取user对象的新的密码的值进行修改
    public boolean changePassword(Customer customer) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String changePassword_sql = " update Custmoer set password=? where id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(changePassword_sql);
        preparedStatement.setString(1,customer.getPassword());
        preparedStatement.setInt(2, customer.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRows>0;
    }

    public boolean changeName(Customer customer)throws SQLException{
        Connection connection=JdbcHelper.getConn();
        //写sql语句
        String changeName_sql = "update Custmoer set name=? where id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(changeName_sql);
        preparedStatement.setString(1,customer.getName());
        preparedStatement.setInt(2, customer.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRows>0;
    }

    public Customer findByAccount(String account) throws SQLException {
        Customer customer=null;
        Connection connection= JdbcHelper.getConn();
        String user_sql = "SELECT * FROM Customer WHERE account=? ";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(user_sql);
        preparedStatement.setString(1,account);
        //执行预编译语句获取结果集对象
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            customer=new Customer(
                    resultSet.getInt("id"),
                    resultSet.getString("account"),
                    resultSet.getString("password"),
                    resultSet.getString("name")
            );
        }
        return customer;
    }

    public Customer find(int id) throws SQLException {
        Customer customer=null;
        Connection connection= JdbcHelper.getConn();
        String user_sql = "SELECT * FROM Customer WHERE id=? ";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(user_sql);
        preparedStatement.setInt(1,id);
        //执行预编译语句获取结果集对象
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            customer=new Customer(
                    resultSet.getInt("id"),
                    resultSet.getString("account"),
                    resultSet.getString("password"),
                    resultSet.getString("name")
            );
        }
        return customer;
    }
}
