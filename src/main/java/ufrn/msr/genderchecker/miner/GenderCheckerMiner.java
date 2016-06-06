package ufrn.msr.genderchecker.miner;

import java.util.ArrayList;
import java.util.List;

import ufrn.msr.genderchecker.dao.BrazilianUserDAO;
import ufrn.msr.genderchecker.dao.hibernate.HibernateBrazilianUserDAO;
import ufrn.msr.genderchecker.exceptions.GenderCheckerException;
import ufrn.msr.genderchecker.models.BrazilianUser;
import ufrn.msr.genderchecker.util.GenderCheckerUtil;

public class GenderCheckerMiner {
	
	private static BrazilianUserDAO userDAO = new HibernateBrazilianUserDAO();

	public static void main(String[] args) {
		
		List<BrazilianUser> usersAux = userDAO.listAll();
		List<BrazilianUser> users = new ArrayList<BrazilianUser>();
		
		for(BrazilianUser user: usersAux)
			if(user.getGender() == null && user.getName() != null && !user.getName().equals(""))
				users.add(user);	
		
		int errorSolveMaxAttempts = 1;		
		int errorSolveCount = 0;
		
		do{
			users = genderCheckerAux(users);
			errorSolveCount++;
		}while(!users.isEmpty() && errorSolveCount < errorSolveMaxAttempts);
		
		System.exit(0);
	}

	private static List<BrazilianUser> genderCheckerAux(List<BrazilianUser> users){
		
		List<BrazilianUser> errors = new ArrayList<BrazilianUser>();		
				
		int processedUsers = 0;
		while(processedUsers < users.size()){			
			BrazilianUser user = users.get(processedUsers++);
			
			System.out.print((processedUsers)+"/"+users.size() + " --- ");
			
			if(user.getName() == null || user.getName().equals("")){
				System.out.println(user + " --- NO NAME --- FAIL!");
				continue;
			}
			try {
				String gender = GenderCheckerUtil.checkGenderFromName(user.getName().split(" ")[0]);
				user.setGender(gender);
				
				System.out.println(user + " --- DONE!");
				
				userDAO.beginTransaction();
				userDAO.save(user);
				userDAO.commitTransaction();
			} catch (GenderCheckerException e) {
				errors.add(user);
				System.out.println(user + " --- FAIL!");
				e.printStackTrace();				
			}						
		}
		return errors;		
	}
}