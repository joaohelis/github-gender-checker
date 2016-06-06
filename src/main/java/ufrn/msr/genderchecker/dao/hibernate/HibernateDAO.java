package ufrn.msr.genderchecker.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;

import ufrn.msr.genderchecker.dao.GenericDAO;

public abstract class HibernateDAO<T, Type extends Serializable> implements GenericDAO<T, Type>{

	private Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public HibernateDAO(Class<?> persistentClass) {
		super();
		this.persistentClass = (Class<T>) persistentClass;
	}
	
	public void beginTransaction() {
		HibernateUtil.beginTransaction();
	}
	
	public void commitTransaction() {
		HibernateUtil.commitTransaction();
	}
	
	public void save(T entity) {
		HibernateUtil.getSession().saveOrUpdate(entity);
	}
	
	public void delete(T entity) {
		HibernateUtil.getSession().delete(entity);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listAll() {
		HibernateUtil.beginTransaction();
		Criteria criteria = HibernateUtil.getSession().createCriteria(persistentClass);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public T get(Type pk) {		
		Query query = HibernateUtil.getSession().createQuery("from "+getTypeClass().getName()+" where id = :pk");
		query.setParameter("pk", pk);
		return (T) query.uniqueResult();
	}
	
	private Class<?> getTypeClass() {
        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return clazz;
    }
	
}