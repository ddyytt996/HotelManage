package domain;

public class Room{
	private int id;
	private String no;
	private String contain;
	private String workingStatus;

	public Room(){
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getContain() {
		return contain;
	}

	public void setContain(String contain) {
		this.contain = contain;
	}

	public String getWorkingStatus() {
		return workingStatus;
	}

	public void setWorkingStatus(String workingStatus) {
		this.workingStatus = workingStatus;
	}

	public Room(int id, String no, String contain, String workingStatus) {
		this.id = id;
		this.no = no;
		this.contain = contain;
		this.workingStatus = workingStatus;
	}

	public Room(String no, String workingStatus) {
		this.no = no;
		this.contain = contain;
		this.workingStatus = workingStatus;
	}

}

