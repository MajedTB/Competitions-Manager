package code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
	private File excelFile;
	Workbook wb;

	public Excel(String fileName) {
		excelFile = new File(fileName);

		if (!excelFile.exists()) // if the file doesn't exists
			createNewExcelFile();

		try (InputStream inp = new FileInputStream(excelFile)) {
			wb = new XSSFWorkbook(inp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Competition> getCompetitionsFromFile() { // To get the competitions from the excel file
		ArrayList<Competition> compList = new ArrayList<Competition>();
		Competition comp;

		for (Sheet sheet : wb) { // for every sheet (Competition)
			if (sheet.getRow(4).getCell(5) == null) { // To check if it's a TeamCompetition or SoloCompetition
				comp = new SoloCompetition(); // make a new SoloCompetition

				for (int i = 5; i <= sheet.getLastRowNum(); i++) { // To get the Students from the excel file
					Student st = new Student();
					
					DataFormatter dataFormatter = new DataFormatter(); // to format id & rank
					st.setId(dataFormatter.formatCellValue(sheet.getRow(i).getCell(1))); // formating id to be a plain string
					st.setName(sheet.getRow(i).getCell(2).toString());
					st.setMajor(sheet.getRow(i).getCell(3).toString());
					st.setRank((dataFormatter.formatCellValue(sheet.getRow(i).getCell(4)))); // formating rank to be a plain string
					((SoloCompetition) comp).addStudent(st);
				}
				comp.setName(sheet.getRow(0).getCell(1).toString()); // set the competition name
				comp.setLink(sheet.getRow(1).getCell(1).toString()); // set the competition link
				comp.setDate(sheet.getRow(2).getCell(1).getDateCellValue()); // to set the date

			} else {
				comp = new TeamCompetition(); // make a new TeamCompetition
				
				ArrayList<Team> teams = new ArrayList<Team>();
				ArrayList<String> teamNames = new ArrayList<String>();
				
				for (int i = 5; i <= sheet.getLastRowNum(); i++) { // To get the Students from the excel file
					Student st = new Student();
					DataFormatter dataFormatter = new DataFormatter(); // to format student id, team number and rank
					st.setId(dataFormatter.formatCellValue(sheet.getRow(i).getCell(1)));
					st.setName(sheet.getRow(i).getCell(2).toString());
					st.setMajor(sheet.getRow(i).getCell(3).toString());
	
					int teamIndex = teamNames.indexOf(sheet.getRow(i).getCell(5).toString()); // checks if the team exists in the array
					if (teamIndex != -1) { // team exist
						// add the student to the already existing team
						teams.get(teamIndex).addStudent(st);
					} else { // team is new
						// create a team and set all of it's information and add the student to it
						Team team = new Team(); 
						
						team.setTeamName(sheet.getRow(i).getCell(5).toString());
						team.setTeamNumber(dataFormatter.formatCellValue(sheet.getRow(i).getCell(4))); 
						team.setRank(dataFormatter.formatCellValue(sheet.getRow(i).getCell(6)));
						team.addStudent(st);
						teams.add(team);
						teamNames.add(sheet.getRow(i).getCell(5).toString());
						((TeamCompetition) comp).addTeam(team);
					}
				}
				comp.setName(sheet.getRow(0).getCell(1).toString());
				comp.setLink(sheet.getRow(1).getCell(1).toString());
				comp.setDate(sheet.getRow(2).getCell(1).getDateCellValue());
			}
			compList.add(comp);
		}

		return compList;
	}

	public void createNewExcelFile() { // if there is no excel file create a new one
		try (OutputStream out = new FileOutputStream(excelFile)) {
			Workbook wb = new XSSFWorkbook();
			wb.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addSheet(Competition competition) { // add the competition to the excel file
		try (OutputStream out = new FileOutputStream(excelFile)) { // open the excel file for inserting a new shee
			Sheet sheet = wb.createSheet(competition.getName()); // make a new sheet with it's name the same as the competition name
			
			// Add the competition name, link, date, and the structure of the student table
			Row row = sheet.createRow(0);
			row.createCell(0).setCellValue("Competition Name"); 
			row.createCell(1).setCellValue(competition.getName());

			row = sheet.createRow(1);
			row.createCell(0).setCellValue("Competition Link");
			row.createCell(1).setCellValue(competition.getLink());

			row = sheet.createRow(2);
			row.createCell(0).setCellValue("Competition Date");
			row.createCell(1).setCellValue(competition.getDate());
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setDataFormat(wb.createDataFormat().getFormat("d-mmm-yy"));
			row.getCell(1).setCellStyle(cellStyle);

			row = sheet.createRow(4);
			row.createCell(1).setCellValue("Student ID");
			row.createCell(2).setCellValue("Student Name");
			row.createCell(3).setCellValue("Major");

			if (competition instanceof SoloCompetition) { // if it's a SoloCompetition
				row.createCell(4).setCellValue("Rank");
				ArrayList<Student> arr = ((SoloCompetition) competition).getStudents();
				Student st;
				for (int i = 0; i < arr.size(); i++) { // add every Student in the competition to the sheet row by row
					st = arr.get(i);
					row = sheet.createRow(5 + i);
					row.createCell(0).setCellValue(i + 1);
					row.createCell(1).setCellValue(st.getId() + " ");
					row.createCell(2).setCellValue(st.getName());
					row.createCell(3).setCellValue(st.getMajor());
					row.createCell(4).setCellValue(st.getRank());
				}
			} else { // if it's a Team competition
				// set the team number, name, rank of the competition
				row.createCell(4).setCellValue("Team Number");
				row.createCell(5).setCellValue("Team Name");
				row.createCell(6).setCellValue("Rank");
				ArrayList<Team> teamArr = ((TeamCompetition) competition).getTeams(); // get the teams in the compeititon and save them in arrayList
				ArrayList<Student> stuArr;
				String teamNum;
				String teamName;
				int count = 1;
				for (int i = 0; i < teamArr.size(); i++) { // for every team
					stuArr = teamArr.get(i).getStudents(); // get the students
					teamName = teamArr.get(i).getTeamName(); // get the team name
					teamNum = teamArr.get(i).getTeamNumber(); // get the team number
					for (int j = 0; j < stuArr.size(); j++) { // for every student in the team
						// get the information of the student and but it in the sheet row by row
						row = sheet.createRow(4 + count);
						row.createCell(0).setCellValue(count);
						row.createCell(1).setCellValue(stuArr.get(j).getId());
						row.createCell(2).setCellValue(stuArr.get(j).getName());
						row.createCell(3).setCellValue(stuArr.get(j).getMajor());
						row.createCell(4).setCellValue(teamNum);
						row.createCell(5).setCellValue(teamName);
						row.createCell(6).setCellValue(teamArr.get(i).getRank());
						count++;
					}
				}
			}

			wb.write(out);

		} catch (Exception e) {

		}
	}

	public void deleteSheet(Competition competition) { // to delete a sheet from the excel file
		try (OutputStream out = new FileOutputStream(excelFile)) {
			
			int index = 0;
			for (Sheet sheet : wb) {
				if (sheet.getRow(0).getCell(1).toString().equals(competition.getName())) {
					break;
				} 
				index++;
			}
			
			wb.removeSheetAt(index);
			wb.write(out);
		} catch (Exception e) {

		}
	}

	public void updateSheet(Competition competition) { // updates the competition by deleting the old sheet and creating a new one
		deleteSheet(competition);
		addSheet(competition);
	}

}