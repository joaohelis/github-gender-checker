package ufrn.msr.genderchecker.dao.hibernate;

import ufrn.msr.genderchecker.dao.BrazilianUserDAO;
import ufrn.msr.genderchecker.models.BrazilianUser;

public class HibernateBrazilianUserDAO extends HibernateDAO<BrazilianUser, Integer> implements BrazilianUserDAO {

	public HibernateBrazilianUserDAO(){
		// we passing the entity for super class
		super(BrazilianUser.class);
	}
}