package code;

import java.util.ArrayList;

public class CompetitionManager {
	private static ArrayList<Competition> competitions = new ArrayList<Competition>();
	private static Excel excelFile;

	public static void setup() {
		excelFile = new Excel("Competitions Participations.xlsx");
		competitions = excelFile.getCompetitionsFromFile();
	}

	public static ArrayList<Competition> getCompetitions() {
		return competitions;
	}

	public static ArrayList<String> getCompetitionNames() {
		ArrayList<String> compNames = new ArrayList<String>();
		for (Competition comp : competitions) {
			compNames.add(comp.getName());
		}
		return compNames;
	}

	public static void addCompetition(Competition competition) {
		System.out.println(competition.getName() + competition.getDate());
		competitions.add(competition);
		excelFile.addSheet(competition);
		
	}

	public static void deleteCompetition(Competition competition) {
		competitions.remove(competition);
		excelFile.deleteSheet(competition);
	}

	public static void addStudentToComp(SoloCompetition comp, Student student) {
		comp.addStudent(student);
		excelFile.updateSheet(comp);
	}

	public static void addStudentToComp(TeamCompetition comp, Student student, Team team) {
		team.addStudent(student);
		if (!comp.getTeams().contains(team))
			comp.addTeam(team);
		excelFile.updateSheet(comp);
	}
	
	public static void updateCompetition(Competition oldComp, Competition newComp) { 
        excelFile.deleteSheet(oldComp);
        competitions.remove(oldComp);
        excelFile.addSheet(newComp);
        competitions.add(newComp);
      }

}