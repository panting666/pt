package com.jzfactory.jd.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jzfactory.jd.bean.Master;

public class TestDao extends BaseHibernateDAO {
	/**
	 * 测试临时状态到持久状态的转变
	 */
	public void testT2P() {
		Master master = new Master();
		// 临时态
		master.setName("陈老师");
		master.setSex(1);
		Session session = getSession();
	
		Transaction trans = getSession().beginTransaction();
		session.save(master);
		trans.commit();
		// 持久态即在session的托管区又和数据库有关系
		master.setSex(0);
		session.close();
		// 游离态不在sessione的托管区但是和数据库有关系
		master.setName("aaa");
		// 临时态
		master.setId(1000);
	}

	/**
	 * 测试临时状态到持久状态后，更改属性提交。
	 */
	public void testT2P2Update() {
		Master master = new Master();
		master.setName("孙老师");
		master.setSex(0);
		Session session = getSession();
		Transaction trans = getSession().beginTransaction();
		// 此处保存无意义
		session.save(master);
		session.save(master);

		master.setName("孙老师");
		master.setSex(1);
		trans.commit();
		session.close();
	}

	/**
	 * 测试持久状态修改后提交
	 */
	public void testP2Update() {
		Transaction trans = getSession().beginTransaction();
		Session session = getSession();
		Master master = (Master) session.get(Master.class, 4);
		// 此处保存无意义
		session.save(master);

		master.setSex(1);
		master.setName("小孙老师");
		trans.commit();
	}

	/**
	 * 将持久态转换为游离态后更新提交 clear 或 evict 或 session关闭
	 */
	public void testP2D2Update() {
		Transaction trans = getSession().beginTransaction();
		Session session = getSession();
		// 持久化状态
		Master master = (Master) session.get(Master.class, 4);
		// 将持久对象转换为游离对象
		session.evict(master);
		// 游离态
		master.setName("老张");
		trans.commit();
		session.close();

	}

	/**
	 * 将持久态转换为临时态
	 */
	public void testP2T() {
		Transaction trans = getSession().beginTransaction();
		Session session = getSession();
		// 持久化状态
		Master master = (Master) session.get(Master.class, 4);
		session.delete(master);
		master.setName("你好");
		trans.commit();
		session.close();
	}

	/**
	 * 同步持久化对象
	 */
	public void testRefresh() {
		Transaction trans = getSession().beginTransaction();
		Session session = getSession();
		Master master = (Master) session.get(Master.class, 2);
		System.out.println("before" + master.getName());
		trans.commit();
		session.refresh(master);
		System.out.println("after" + master.getName());
		session.close();
	}

	/**
	 * 将游离态转换成持久态
	 */
	public void testD2P() {
		Session session = getSession();
		Transaction trans = session.beginTransaction();
		// 临时态
		Master master = new Master();
		// 游离态
		master.setId(3);
		master.setName("同老师");
		master.setSex(1);
		// 持久态
		session.update(master);
		String name = master.getName();
		trans.commit();
	}

	/**
	 * 持久状态修改id(报错)
	 */
	public void testP2EditId() {
		Session session = getSession();
		Transaction trans = session.beginTransaction();
		Master master = (Master) session.get(Master.class, 2);
		master.setId(100);
		trans.commit();
		session.close();
	}

	/**
	 * 游离态转换为临时状态
	 */
	public void testD2S() {
		Session session = getSession();
		Transaction trans = session.beginTransaction();
		// 临时态
		Master master = new Master();
		// 游离态
		master.setId(3);
		session.delete(master);
		// 临时态
		master.setName("kk");
		trans.commit();
		session.close();
	}

	/**
	 * 测试重复的持久化对象(报错)
	 */
	public void testDuplicateP() {
		Session session = getSession();
		Transaction trans = session.beginTransaction();
		// 持久态
		Master master = (Master) session.get(Master.class, 2);
		// 临时态
		Master master2 = new Master();
		// 游离态
		master2.setId(2);
		master2.setName("游离");
		session.update(master2);
		// 持久态
		System.out.println(master2);
		trans.commit();
		session.close();
	}

	/**
	 * 消除重复的持久化对象
	 */
	public void testRemoveDupli() {
		Session session = getSession();
		Transaction trans = session.beginTransaction();
		// 持久态
		Master master = (Master) session.get(Master.class, 2);
		// 临时态
		Master master2 = new Master();
		// 游离态
		master2.setId(2);
		master2.setName("游离");
		session.merge(master2);
		// 持久态
		System.out.println(master);
		System.out.println(master2);
		// master.setName("一个游离");
		master2.setName("两个游离");
		trans.commit();

		session.close();

	}

	public static void main(String[] args) {
		TestDao dao = new TestDao();
		// dao.testD2P();
		// dao.testT2P2Update();
		// dao.testP2Update();
		// dao.testP2D2Update();
		// dao.testP2T();
		// dao.testRefresh();
		// dao.testD2P();
		// dao.testP2EditId();
		// dao.testD2S();
		// dao.testDuplicateP();
		dao.testRemoveDupli();
	}
}
