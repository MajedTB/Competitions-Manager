package code;

import java.util.ArrayList;

public class Team {

    private String teamName, teamNumber, rank;
    private ArrayList<Student> students = new ArrayList<Student>();

    public Team() {

    }
    public Team(String teamName, String teamNumber) {
        this.teamName = teamName;
        this.teamNumber = teamNumber;
    }

    public Team(String teamName, String teamNumber, String rank) {
        this.teamName = teamName;
        this.teamNumber = teamNumber;
        this.rank = rank;
    }

    public Team(String teamName, String teamNumber, String rank, ArrayList<Student> students) {
        this.teamName = teamName;
        this.teamNumber = teamNumber;
        this.rank = rank;
        this.students = students;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamNumber() {
        return teamNumber;
    }

    public String getRank() {
        return rank;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    public void setRank(String string) {
        this.rank = string;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void deleteStudent(Student student) {
        students.remove(student);
    }

    @Override
    public String toString() {
        return teamName;
    }
    
    public Team clone() {
		Team clonedTeam = new Team(teamName, teamNumber, rank);
		for(Student i : this.students) {
			clonedTeam.students.add(i.clone());
        }
		return clonedTeam;
	}
    

}