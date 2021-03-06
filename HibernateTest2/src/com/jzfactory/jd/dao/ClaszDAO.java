package com.jzfactory.jd.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jzfactory.jd.bean.Clasz;
import com.jzfactory.jd.bean.Master;
import com.jzfactory.jd.bean.Student;

/**
 * A data access object (DAO) providing persistence and search support for Clasz
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.jzfactory.jd.bean.Clasz
 * @author MyEclipse Persistence Tools
 */
public class ClaszDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(ClaszDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String LOCAL = "local";

	public void save(Clasz transientInstance) {
		log.debug("saving Clasz instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Clasz persistentInstance) {
		log.debug("deleting Clasz instance");
		try {

			Transaction trans=getSession().beginTransaction();
			getSession().delete(persistentInstance);
			trans.commit();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Clasz findById(java.lang.Integer id) {
		log.debug("getting Clasz instance with id: " + id);
		try {
			Clasz instance = (Clasz) getSession().get(
					"com.jzfactory.jd.bean.Clasz", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Clasz> findByExample(Clasz instance) {
		log.debug("finding Clasz instance by example");
		try {
			List<Clasz> results = (List<Clasz>) getSession()
					.createCriteria("com.jzfactory.jd.bean.Clasz")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Clasz instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Clasz as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Clasz> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Clasz> findByLocal(Object local) {
		return findByProperty(LOCAL, local);
	}

	public List findAll() {
		log.debug("finding all Clasz instances");
		try {
			String queryString = "from Clasz";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Clasz merge(Clasz detachedInstance) {
		log.debug("merging Clasz instance");
		try {
			Clasz result = (Clasz) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Clasz instance) {
		log.debug("attaching dirty Clasz instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Clasz instance) {
		log.debug("attaching clean Clasz instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	/**
	 * 为某个班级添加学生
	 * @param claszId
	 * @param stud
	 */
	public void addStudent(int claszId,Student stud)
	{
		Session session=getSession();
		Clasz cls=(Clasz)session.get(Clasz.class, claszId);
		cls.getStudents().add(stud);
		Transaction trans=getSession().beginTransaction();
		session.update(cls);
		trans.commit();
	}
	public void addStudent(Clasz cls,Student stud)
	{
		Transaction trans=getSession().beginTransaction();
		Session session=getSession();
		cls.getStudents().add(stud);
		session.save(cls);
		trans.commit();
		session.close();
		
	}
	/**
	 * 测试hibnate的加载模式
	 */
		public void testFetch()
		{
			Session session=getSession();
			Clasz cls=(Clasz) session.get(Clasz.class, 1);
			session.close();
			System.out.println(cls.getStudents());
		}
	
	public static void main(String[] args) {
		ClaszDAO cdao=new ClaszDAO();
		Student stud=new Student();
		Clasz cls=new Clasz();
		Clasz clasz=cdao.findById(1);
		System.out.println(clasz.getMaster());
//		System.out.println(clasz.getStudents());
		
//		stud.setName("三丰");
//		stud.setAge(20);
//		cdao.addStudent(1, stud);

//		cls.setName("五年四班");
//		stud.setName("dd");
//		cdao.addStudent(cls, stud);
		
//		Clasz cls1=cdao.findById(2);
//		cdao.delete(cls1);
		cdao.testFetch();
		
		
	}
}