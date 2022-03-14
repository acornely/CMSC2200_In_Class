package edu.ben.project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GameLog {
	private File logFile = new File("zombie_log.txt");
	private FileWriter fileWriter;
	private BufferedWriter buffWriter;
	private Scanner file;

	public GameLog() {
		try {
			if (!logFile.exists()) {
				logFile.createNewFile();
			} else {
				logFile.delete();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean gameLogs(String events) {
		try {
			fileWriter = new FileWriter(logFile.getAbsoluteFile(), true);
			buffWriter = new BufferedWriter(fileWriter);
			file = new Scanner(logFile);
			
//			checkLastLog(events);
			buffWriter.write(events);
			buffWriter.newLine();
			buffWriter.close();
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
//	
//	public String checkLastLog(String event) {
//		String lastLine = " ";
//		
//		
//		while (file.hasNextLine()) {
//			
//		}
//		
//		
//		
//	}

}
