package code;

public class Student {

	private String name, id, major, rank;

	public Student() {

	}

	public Student(String name, String id, String major) {
		this.name = name;
		this.id = id;
		this.major = major;
	}

	public Student(String name, String id, String major, String rank) {
		this.name = name;
		this.id = id;
		this.major = major;
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getMajor() {
		return major;
	}

	public String getRank() {
		return rank;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return name;
	}

	public Student clone() {
		return new Student(this.getName(), this.getId(), this.getMajor(), this.getRank());
	}
}