package com.jzfactory.jd.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jzfactory.jd.bean.Master;

public class TestDao extends BaseHibernateDAO {
	/**
	 * ������ʱ״̬���־�״̬��ת��
	 */
	public void testT2P() {
		Master master = new Master();
		// ��ʱ̬
		master.setName("����ʦ");
		master.setSex(1);
		Session session = getSession();
	
		Transaction trans = getSession().beginTransaction();
		session.save(master);
		trans.commit();
		// �־�̬����session���й����ֺ����ݿ��й�ϵ
		master.setSex(0);
		session.close();
		// ����̬����sessione���й������Ǻ����ݿ��й�ϵ
		master.setName("aaa");
		// ��ʱ̬
		master.setId(1000);
	}

	/**
	 * ������ʱ״̬���־�״̬�󣬸��������ύ��
	 */
	public void testT2P2Update() {
		Master master = new Master();
		master.setName("����ʦ");
		master.setSex(0);
		Session session = getSession();
		Transaction trans = getSession().beginTransaction();
		// �˴�����������
		session.save(master);
		session.save(master);

		master.setName("����ʦ");
		master.setSex(1);
		trans.commit();
		session.close();
	}

	/**
	 * ���Գ־�״̬�޸ĺ��ύ
	 */
	public void testP2Update() {
		Transaction trans = getSession().beginTransaction();
		Session session = getSession();
		Master master = (Master) session.get(Master.class, 4);
		// �˴�����������
		session.save(master);

		master.setSex(1);
		master.setName("С����ʦ");
		trans.commit();
	}

	/**
	 * ���־�̬ת��Ϊ����̬������ύ clear �� evict �� session�ر�
	 */
	public void testP2D2Update() {
		Transaction trans = getSession().beginTransaction();
		Session session = getSession();
		// �־û�״̬
		Master master = (Master) session.get(Master.class, 4);
		// ���־ö���ת��Ϊ�������
		session.evict(master);
		// ����̬
		master.setName("����");
		trans.commit();
		session.close();

	}

	/**
	 * ���־�̬ת��Ϊ��ʱ̬
	 */
	public void testP2T() {
		Transaction trans = getSession().beginTransaction();
		Session session = getSession();
		// �־û�״̬
		Master master = (Master) session.get(Master.class, 4);
		session.delete(master);
		master.setName("���");
		trans.commit();
		session.close();
	}

	/**
	 * ͬ���־û�����
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
	 * ������̬ת���ɳ־�̬
	 */
	public void testD2P() {
		Session session = getSession();
		Transaction trans = session.beginTransaction();
		// ��ʱ̬
		Master master = new Master();
		// ����̬
		master.setId(3);
		master.setName("ͬ��ʦ");
		master.setSex(1);
		// �־�̬
		session.update(master);
		String name = master.getName();
		trans.commit();
	}

	/**
	 * �־�״̬�޸�id(����)
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
	 * ����̬ת��Ϊ��ʱ״̬
	 */
	public void testD2S() {
		Session session = getSession();
		Transaction trans = session.beginTransaction();
		// ��ʱ̬
		Master master = new Master();
		// ����̬
		master.setId(3);
		session.delete(master);
		// ��ʱ̬
		master.setName("kk");
		trans.commit();
		session.close();
	}

	/**
	 * �����ظ��ĳ־û�����(����)
	 */
	public void testDuplicateP() {
		Session session = getSession();
		Transaction trans = session.beginTransaction();
		// �־�̬
		Master master = (Master) session.get(Master.class, 2);
		// ��ʱ̬
		Master master2 = new Master();
		// ����̬
		master2.setId(2);
		master2.setName("����");
		session.update(master2);
		// �־�̬
		System.out.println(master2);
		trans.commit();
		session.close();
	}

	/**
	 * �����ظ��ĳ־û�����
	 */
	public void testRemoveDupli() {
		Session session = getSession();
		Transaction trans = session.beginTransaction();
		// �־�̬
		Master master = (Master) session.get(Master.class, 2);
		// ��ʱ̬
		Master master2 = new Master();
		// ����̬
		master2.setId(2);
		master2.setName("����");
		session.merge(master2);
		// �־�̬
		System.out.println(master);
		System.out.println(master2);
		// master.setName("һ������");
		master2.setName("��������");
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
