package com.project.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.bean.CommentBean;
import com.project.bean.DiscountBean;
import com.project.bean.ManageUserBean;
import com.project.bean.UserBean;
import com.project.bean.VIPBean;
import com.project.dao.IUserDao;

/**
 * 持久层
 * 用户登录、注册实现类
 * @author Administrator
 *
 */
@Component
public class UserDaoImpl implements IUserDao{
	@Autowired
	SqlSessionFactory fa;
	@Override
	public UserBean selectByPhone(String phone) {
		SqlSession session = fa.openSession();
		UserBean userBean = null;
		try {
			IUserDao dao = session.getMapper(IUserDao.class);
			System.out.println("dao");
			userBean = dao.selectByPhone(phone);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		session.close();
		return userBean;
	}
	
	@Override
	public int insertUser(UserBean bean) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		int num = dao.insertUser(bean);
		session.commit();
		session.close();
		return num;
	}
	
	@Override
	public ManageUserBean selectByManageName(String name) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		ManageUserBean bean = dao.selectByManageName(name);
		session.close();
		return bean ;
	}

	@Override
	public int insertManageUser(ManageUserBean bean) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		int num = dao.insertManageUser(bean);
		session.commit();
		session.close();
		return num;
	}

	@Override
	public int insertVIP(VIPBean bean) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		int num = dao.insertVIP(bean);
		session.commit();
		session.close();
		return num;
	}

	@Override
	public int updateVIPClass(VIPBean bean) {
		SqlSession session = fa.openSession();
	    IUserDao dao = session.getMapper(IUserDao.class);
		int num = dao.updateVIPClass(bean);
		session.commit();
		session.close();
		return num;
	}

	@Override
	public VIPBean selectVIPInfo(int u_id) {
		SqlSession session = fa.openSession();
	    IUserDao dao = session.getMapper(IUserDao.class);
	    VIPBean bean = dao.selectVIPInfo(u_id);
		return bean;
	}

	@Override
	public DiscountBean selectDiscont(int v_code) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		DiscountBean bean = dao.selectDiscont(v_code);
		
		return bean;
	}

	@Override
	public List<UserBean> selectUserAll(int begin,int total) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		List<UserBean> list = dao.selectUserAll(begin,total);
		return list;
	}

	@Override
	public List<ManageUserBean> selectManageAll(int begin,int total) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		List<ManageUserBean> list = dao.selectManageAll(begin,total);
		return list;
	}

	@Override
	public int deleteManageByName(String name) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		int num = dao.deleteManageByName(name);
		session.commit();
		session.close();
		return num;
	}

	@Override
	public int updateManageByName(ManageUserBean bean) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		int num = dao.updateManageByName(bean);
		session.commit();
		session.close();
		return num;
	}

	@Override
	public int CountUserSum() {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		int num = dao.CountUserSum();
		return num;
	}

	@Override
	public int CountManageSum() {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		int num = dao.CountManageSum();
		return num;
	}

	@Override
	public int updateManagePass(ManageUserBean bean) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		int num = dao.updateManagePass(bean);
		session.commit();
		session.close();
		return num;
	}

	@Override
	public int insertComment(CommentBean bean) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		int num = dao.insertComment(bean);
		session.commit();
		session.close();
		return num;
	}

	@Override
	public List<CommentBean> selectAllComment(int begin, int total) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		List<CommentBean> list = dao.selectAllComment(begin, total);
		session.close();
		return list;
	}

	@Override
	public int countComment() {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		int num = dao.countComment();
		session.close();
		return num;
	}

	@Override
	public int updateVIPInfo(VIPBean bean) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		int num = dao.updateVIPInfo(bean);
		session.close();
		return num;
	}

	@Override
	public List<DiscountBean> selectAllDiscount() {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		List<DiscountBean> list = dao.selectAllDiscount();
		session.close();
		return list;
	}

	@Override
	public int updateDiscount(DiscountBean bean) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		int num = dao.updateDiscount(bean);
		session.commit();
		session.close();
		return num;
	}

	@Override
	public int updateUserPass(UserBean bean) {
		SqlSession session = fa.openSession();
		IUserDao dao = session.getMapper(IUserDao.class);
		int num = dao.updateUserPass(bean);
		session.commit();
		session.close();
		return num;
	}

	

	

	
	

}
