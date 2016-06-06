package ufrn.msr.genderchecker.importter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import ufrn.msr.genderchecker.dao.BrazilianUserDAO;
import ufrn.msr.genderchecker.dao.hibernate.HibernateBrazilianUserDAO;
import ufrn.msr.genderchecker.models.BrazilianUser;

public class GHTorrentImportter {
		
	public static void brazilianUserImportter(String filepath){			
		
		Reader in;
		int count = 0;
		
		try {
			
			BrazilianUserDAO brazilianUserDAO = new HibernateBrazilianUserDAO();			
			brazilianUserDAO.beginTransaction();
						
			in = new FileReader(filepath);
			
			System.out.println(" _____________________________________________________________");
			System.out.println("| #     | id       |     login               | followers      |");
			System.out.println("|_______|__________|_________________________|________________|");
			
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().withNullString("").parse(in);			

			for (CSVRecord record : records) {				
				BrazilianUser user = new BrazilianUser();
					    
				user.setId(Integer.parseInt(record.get("id")));
				user.setLogin(record.get("login"));
				user.setLocation(record.get("location"));								
				user.setFollowers(Integer.parseInt(record.get("followers")));
				
				brazilianUserDAO.save(user);							
				
				System.out.println(String.format("| %-5s |  %-7s |  %-22s | %-14s |" , ++count, 
							user.getId(), user.getLogin(), user.getFollowers()));
			}
			
			brazilianUserDAO.commitTransaction();
						
			System.out.println(" _____________________________________________________________");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){			
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {		
		
//		String path = "/home/joaohelis/Desktop/gender_oss_research/brazilian_ghusers.csv";		
//		brazilianUserImportter(path);
		
	}
}
