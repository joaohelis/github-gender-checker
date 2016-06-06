package ufrn.msr.genderchecker.miner;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.kohsuke.github.GHRateLimit;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import ufrn.msr.genderchecker.dao.BrazilianUserDAO;
import ufrn.msr.genderchecker.dao.hibernate.HibernateBrazilianUserDAO;
import ufrn.msr.genderchecker.models.BrazilianUser;

public class BrazilianUserMiner {
	
	private static String OAUTH_ACCESS_TOKEN = "YOUR_OAUTH_TOKEN";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		BrazilianUserDAO userDAO = new HibernateBrazilianUserDAO();		
		
		try {
			GitHub github = GitHub.connectUsingOAuth(OAUTH_ACCESS_TOKEN);
			
			List<BrazilianUser> users = userDAO.listAll();					
			
			GHRateLimit rateLimit = github.getRateLimit();
						
			int remaining = rateLimit.remaining;
			int usersTotal = users.size();
			int processedUsers = 1;
			while(processedUsers <= usersTotal){
				
				if(remaining == 0){										
					try {
						Date timeToReset = new Date(rateLimit.getResetDate().getTime() - new Date().getTime() + 5000);
						System.out.println("-------------------------------------------------------------------------------------------------");
						System.out.println("GitHubMiner are going to wait " + timeToReset.getMinutes()+" minutes and "+ 
								timeToReset.getSeconds() + " seconds to reset limit rate! Please, be a bit patient.");
						System.out.println("GitHub Rate Limit Reset Date: " + new Date(rateLimit.getResetDate().getTime() + 5000));
						System.out.println("-------------------------------------------------------------------------------");
						Thread.sleep(timeToReset.getTime());						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					rateLimit = github.getRateLimit();
					remaining = rateLimit.remaining;
					
					System.out.println("GHRateLimit: {remaing: "+ remaining +"}");
					if(remaining == 0) continue;
					System.out.println("GitHub Rate Limit was successfully reseted!");
					System.out.println("-------------------------------------------------------------------------------------------------");
				}
				
				BrazilianUser user = users.get(processedUsers - 1);				
				System.out.print((processedUsers) + " --- ");
				processedUsers++;

				try{
					GHUser ghuser = github.getUser(user.getLogin());
					remaining--;					
					user.setCompany(ghuser.getCompany());
					user.setEmail(ghuser.getEmail());
					user.setName(ghuser.getName());
					System.out.println(user + " --- DONE!");
					
					userDAO.beginTransaction();
					userDAO.save(user);
					userDAO.commitTransaction();
				}catch(Exception e){
					remaining--;
					System.out.println(user + " --- FAIL!");
					continue;
				}																													
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(0);
	}
}
