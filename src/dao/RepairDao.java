package dao;

import domain.Repair;
import service.CustomerService;
import util.JdbcHelper;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class RepairDao {

    private static RepairDao repairDao=new RepairDao();
    private RepairDao(){}
    public static RepairDao getInstance(){return repairDao;}
    //xie
    public Set<Repair> findByCustomer(int customerId) throws SQLException {
        //创建保修记录集合用于存放从数据库读取的该用户的保修记录
        Set<Repair> repairs = new HashSet<>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        String repairsByCustomer_sql = "SELECT * FROM Repair WHERE customer_id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(repairsByCustomer_sql);
        preparedStatement.setInt(1,customerId);
        ResultSet resultSet=preparedStatement.executeQuery(repairsByCustomer_sql);
        while (resultSet.next()){
            repairs.add(
                    new Repair(resultSet.getInt("id"),
                    RoomService.getInstance().findById(resultSet.getString("room_id")),
                    CustomerService.getInstance().find(resultSet.getInt("customer_id")),
                    resultSet.getString("remarks"),
                    resultSet.getString("repairStatus"),
                    AdminUserService.getInstance().find(resultSet.getString("repairman_id"))
                    ));
        }
        return repairs;
    }

    //xie
    public boolean addByCustomer(Repair repair) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        String addByCustomer_sql = "INSERT INTO Repair (room_id,customer_id,remarks,repairStatus)VALUES"
                + " (?,?,?,?)";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(addByCustomer_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1, repair.getRoom().getId());
        preparedStatement.setInt(2, repair.getCustomer().getId());
        preparedStatement.setString(3,repair.getRemarks());
        preparedStatement.setString(4,"未修理");
        //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
        int affectedRowNum = preparedStatement.executeUpdate();
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRowNum > 0;
    }

   public boolean update(Repair repair)throws SQLException{
        Connection connection=JdbcHelper.getConn();
        String updateRepair_sql = "Update Repair set room_id=?,remarks=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(updateRepair_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1, repair.getRoom().getId());
        preparedStatement.setString(2, repair.getRemarks());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRows > 0;
   }
   //zhuang
   public Set<Repair> findByRepairAdmin() throws SQLException{
       //创建保修记录集合用于存放从数据库读取所有报修记录
       Set<Repair> repairs = new HashSet<>();
       //获得连接对象
       Connection connection = JdbcHelper.getConn();
       String allRepairs_sql = "SELECT * FROM Repair";
       //创建语句盒子对象
       Statement statement = connection.createStatement();
       ResultSet resultSet = statement.executeQuery(allRepairs_sql);
       while (resultSet.next()){
           repairs.add(
                   new Repair(resultSet.getInt("id"),
                           RoomService.getInstance().findById(resultSet.getInt("room_id")),
                           CustomerService.getInstance().find(resultSet.getInt("customer_id")),
                           resultSet.getString("remarks"),
                           resultSet.getString("repairStatus"),
                           AdminUserService.getInstance().find(resultSet.getInt("repairman_id"))
                   ));
       }
       return repairs;
   }
   //zhuang
   public Set<Repair> findByRoomNo(String roomNo) throws SQLException{
       //创建保修记录集合用于存放从数据库读取所有报修记录
       Set<Repair> repairs = new HashSet<>();
       //获得连接对象
       Connection connection = JdbcHelper.getConn();
       int roomId=RoomService.getInstance().findByRoomNo(roomNo).getId();
       String repairsByRoomId_sql = "SELECT * FROM Repair WHERE room_id=?";
       //在该连接上创建预编译语句对象
       PreparedStatement preparedStatement = connection.prepareStatement(repairsByRoomId_sql);
       preparedStatement.setInt(1,roomId);
       ResultSet resultSet=preparedStatement.executeQuery(repairsByRoomId_sql);
       while (resultSet.next()){
           repairs.add(
                   new Repair(resultSet.getInt("id"),
                           RoomService.getInstance().findById(resultSet.getInt("room_id")),
                           CustomerService.getInstance().find(resultSet.getInt("customer_id")),
                           resultSet.getString("remarks"),
                           resultSet.getString("repairStatus"),
                           AdminUserService.getInstance().find(resultSet.getInt("repairman_id"))
                   ));
       }
       return repairs;
   }
   //zhuang
    public boolean addByAdminUser(Repair repair) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        String addByCustomer_sql = "INSERT INTO Repair (room_id,repairman_id,remarks,repairStatus)VALUES"
                + " (?,?,?,?)";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(addByCustomer_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1, repair.getRoom().getId());
        preparedStatement.setInt(2, repair.getRepairAdmin().getId());
        preparedStatement.setString(3,repair.getRemarks());
        preparedStatement.setString(4,"已修理");
        //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
        int affectedRowNum = preparedStatement.executeUpdate();
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRowNum > 0;
    }
    public boolean deleteByAdminUser(int id) throws SQLException{
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String deleteRepair_sql = "DELETE FROM Repair WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(deleteRepair_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1, id);
        //执行预编译语句，获取删除记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRows > 0;
    }
    public boolean commit(Repair repair) throws SQLException{
        Connection connection=JdbcHelper.getConn();
        String commitRepair_sql = "Update Repair set repairStatus=?,repairman_id=? where id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(commitRepair_sql);
        //为预编译参数赋值
        preparedStatement.setString(1, "已提交");
        preparedStatement.setInt(2, repair.getRepairAdmin().getId());
        preparedStatement.setInt(3,repair.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRows > 0;
    }
}
