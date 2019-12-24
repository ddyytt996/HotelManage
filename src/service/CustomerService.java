package service;

import dao.CustomerDao;
import domain.Customer;

import java.sql.SQLException;

public class CustomerService {
    private static CustomerDao customerDao= CustomerDao.getInstance();
    private static CustomerService customerService=new CustomerService();
    private CustomerService(){}
    public static CustomerService getInstance(){
        return customerService;
    }

    public Customer login(Customer logincustomer) throws SQLException {
       return customerDao.login(logincustomer);
    }

    public boolean register(Customer customer) throws SQLException {
        return customerDao.register(customer);
    }

    public boolean findPassword(Customer customer) throws SQLException {
        return customerDao.findPassword(customer);
    }

    //修改密码的方法，获取user对象的新的密码的值进行修改
    public boolean changePassword(Customer customer) throws SQLException {
        return customerDao.changePassword(customer);
    }

    public boolean changeName(Customer customer)throws SQLException {
        return customerDao.changeName(customer);
    }

    public Customer findByAccount(String account)throws SQLException{
        return customerDao.findByAccount(account);
    }

    public Customer find(int id)throws SQLException{
        return customerDao.find(id);
    }
}
