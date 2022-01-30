package code;

import java.util.ArrayList;
import java.util.Date;

public class SoloCompetition extends Competition {
	private ArrayList<Student> students = new ArrayList<Student>();;

	public SoloCompetition() {

	}

	public SoloCompetition(String name, String link, Date date) {
		super(name, link, date);
	}

	public SoloCompetition(String name, String link, Date date, ArrayList<Student> students) {
		super(name, link, date);
		this.students = students;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public void addStudent(Student student) {
		students.add(student);
	}

	public void removeStudent(Student student) {
		students.remove(student);
	}
	
	public SoloCompetition clone() {
        SoloCompetition cloneComp = new SoloCompetition();
        cloneComp.setName(this.getName());
        cloneComp.setLink(this.getLink());
        cloneComp.setDate(this.getDate());
        for(Student i : this.students) {
            cloneComp.students.add(i.clone());
        }
        
        return cloneComp;
    }

}