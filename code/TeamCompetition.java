package code;

import java.util.ArrayList;
import java.util.Date;

public class TeamCompetition extends Competition {

	private ArrayList<Team> teams = new ArrayList<Team>();;

	public TeamCompetition() {

	}

	public TeamCompetition(String name, String link, Date date) {
		super(name, link, date);
	}

	public TeamCompetition(String name, String link, Date date, ArrayList<Team> teams) {
		super(name, link, date);
		this.teams = teams;
	}

	public ArrayList<Team> getTeams() {
		return teams;
	}

	public ArrayList<String> getAllTeamNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (Team t : teams) {
			names.add(t.getTeamName());
		}
		return names;
	}

	public void addTeam(Team team) {
		teams.add(team);
	}

	public void deleteTeam(Team team) {
		teams.remove(team);
	}

	public TeamCompetition clone() {
		TeamCompetition cloneComp = new TeamCompetition();
		cloneComp.setName(this.getName());
		cloneComp.setLink(this.getLink());
		cloneComp.setDate(this.getDate());
		for (Team i : this.teams) {
			cloneComp.teams.add(i.clone());
		}
		return cloneComp;
	}

}