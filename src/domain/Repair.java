package domain;


import domain.manageUser.AdminUser;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Repair
{

	private int id;

	private String remarks;
	
	private String repairStatus;

	private Customer customer;
	
	private AdminUser repairAdmin;
	
	private Room room;

	//从数据库读取记录使用
	public Repair(int id, Room room, Customer customer,String remarks, String repairStatus, AdminUser repairAdmin) {
		this.id = id;
		this.room = room;
		this.customer = customer;
		this.remarks = remarks;
		this.repairStatus = repairStatus;
		this.repairAdmin = repairAdmin;
	}

	//管理员增加报修记录使用
	public Repair(Room room, String remarks, AdminUser repairAdmin) {
		this.room = room;
		this.remarks = remarks;
		this.repairAdmin = repairAdmin;
	}

	//客户增加报修记录使用
	public Repair(Room room, String remarks, Customer customer) {
		this.room = room;
		this.remarks = remarks;
		this.customer = customer;
	}

	//用户和管理员修改报修记录使用
	public Repair(int id,Room room,String remarks){
		this.id=id;
		this.room=room;
		this.remarks=remarks;
	}

	//管理员提交报修
	public Repair(int id,AdminUser repairAdmin){
		this.id=id;
		this.repairAdmin=repairAdmin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public AdminUser getRepairAdmin() {
		return repairAdmin;
	}

	public void setRepairAdmin(AdminUser repairAdmin) {
		this.repairAdmin = repairAdmin;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}

