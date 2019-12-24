package service;

import dao.RepairDao;
import domain.Repair;
import util.JdbcHelper;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class RepairService {

    private static RepairDao repairDao= RepairDao.getInstance();
    private static RepairService repairService=new RepairService();
    private RepairService(){}
    public static RepairService getInstance(){
        return repairService;
    }

    public Set<Repair> findByCustomer(int customerId) throws SQLException {
       return repairDao.findByCustomer(customerId);
    }

    public boolean addByCustomer(Repair repair) throws SQLException {
       return repairDao.addByCustomer(repair);
    }

    public boolean update(Repair repair)throws SQLException{
        return repairDao.update(repair);
    }

    //zhuang
    public Set<Repair> findByRepairAdmin() throws SQLException{
      return repairDao.findByRepairAdmin();
    }
    //zhuang
    public Set<Repair> findByRoomNo(String roomNo) throws SQLException{
      return repairDao.findByRoomNo(roomNo);
    }
    //zhuang
    public boolean addByAdminUser(Repair repair) throws SQLException {
       return repairDao.addByAdminUser(repair);
    }
    public boolean deleteByAdminUser(int id) throws SQLException{
        return repairDao.deleteByAdminUser(id);
    }
    public boolean commit(Repair repair) throws SQLException{
        return repairDao.commit(repair);
    }
}
