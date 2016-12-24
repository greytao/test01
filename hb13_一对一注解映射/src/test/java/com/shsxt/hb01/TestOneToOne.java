package com.shsxt.hb01;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Before;
import org.junit.Test;

import com.shsxt.po.IdCard;
import com.shsxt.po.User;

public class TestOneToOne {



	private SessionFactory factory;

	private Session session;

	@Before
	public void init(){
		/**
		 * 1.加载配置
		 * 2.通过注册中心 获取sessionfactory
		 * 3.session 通过sessionfactory 获取
		 * 4.调用api  执行操作
		 * 5.关闭session 
		 */

		Configuration config=new Configuration();

		config.configure("hibernate.cfg.xml");

		ServiceRegistry registry=new StandardServiceRegistryBuilder()
		.applySettings(config.getProperties()).build();

		factory=config.buildSessionFactory(registry);
	}



	@Test
	public void test01(){
		session=factory.openSession();
		Transaction tx=session.beginTransaction();

		User user=new User();

		user.setUserName("admin");
		user.setUserPwd("admin");

		IdCard idCard=new IdCard();
		idCard.setNum("test001");


		//  添加关联关系
		user.setIdCard(idCard);

		idCard.setUser(user);


		// 执行添加
		session.save(user);

		tx.commit();
		session.close();

	}

	@Test
	public void test02(){
		session=factory.openSession();
		User user=(User) session.get(User.class, 1);
		System.out.println(user.getId()+"--"+user.getUserName()+"--"+user.getIdCard().getNum());
		session.close();
	}
	
	
	@Test
	public void test03(){
		session=factory.openSession();
		Transaction tx=session.beginTransaction();
		User user=(User) session.get(User.class, 1);
		user.setUserName("admin02");
		user.setUserPwd("admin02");
		IdCard idCard=user.getIdCard();
		
		idCard.setNum("test02");
		user.setIdCard(idCard);
		
		
		session.update(user);
		
		tx.commit();
		session.close();
		
	}
	
	
	@Test
	public void test04(){
		session=factory.openSession();
		Transaction tx=session.beginTransaction();
		User user=(User) session.get(User.class, 1);
		session.delete(user);
		tx.commit();
		session.close();
		
	}
	




}
