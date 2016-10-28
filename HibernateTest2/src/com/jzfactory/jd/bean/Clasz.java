package com.jzfactory.jd.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Clasz entity. @author MyEclipse Persistence Tools
 */
@Entity
//代表那个数据库里的那张表
@Table(name = "clasz", catalog = "school_hibernate",
uniqueConstraints = @UniqueConstraint(columnNames = "master_id"))
//Serializable代表序列化和反序列化，序列化可以存入，反序列化可提取
public class Clasz implements java.io.Serializable {

	// Fields

	private Integer id;
	private Master master;
	private String name;
	private String local;
	private Set<Student> students = new HashSet<Student>(0);

	// Constructors

	/** default constructor */
	public Clasz() {
	}

	/** full constructor */
	public Clasz(Master master, String name, String local, Set<Student> students) {
		this.master = master;
		this.name = name;
		this.local = local;
		this.students = students;
	}

	// Property accessors
	@Id
	@GeneratedValue
//	代表主键 唯一不嫩为空
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY)
	public Master getMaster() {
		return this.master;
	}

	public void setMaster(Master master) {
		this.master = master;
	}

	@Column(name = "name", length = 10)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "local", length = 30)
	public String getLocal() {
		return this.local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
//代表的是一对多的关系   mappedBy有谁进行维护
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "clasz")
	@Cascade({CascadeType.ALL})

	public Set<Student> getStudents() {
		return this.students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

}