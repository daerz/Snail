package com.project.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bean.CommentBean;
import com.project.bean.DiscountBean;
import com.project.bean.ManageUserBean;
import com.project.bean.UserBean;
import com.project.bean.VIPBean;
import com.project.dao.IUserDao;
import com.project.service.IUserService;
import com.project.util.RegexUtil;
/**
 * 业务层
 * 用户登录、注册实现类
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao dao;
	
	@Override
	public UserBean findUserByName(String phone) {
		if(!phone.matches(RegexUtil.REGEX_MOBILE)){
			return null;
		}
		UserBean user = dao.selectByPhone(phone);
		
		
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		if (user == null) {
			//用户折扣
			session.setAttribute("discount", 1);
			return null;
		}
		VIPBean vipBean = dao.selectVIPInfo(user.getU_id());
		DiscountBean discountBean  = dao.selectDiscont(vipBean.getV_code());
		//将用手机号与用户id存入shiro的session中
		
		//用户id
		session.setAttribute("u_id", user.getU_id());
		//用户手机号
		session.setAttribute("phone", user.getU_phone());
		//用户折扣
		session.setAttribute("discount", discountBean.getD_dis());
		//用户会员等级
		session.setAttribute("vip", vipBean.getV_code());
		System.out.println("user"+user);
		return user;
	}
	
	public String findName(String token){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		if ("login".equals(token)) {
			String phone = (String) session.getAttribute("phone");
			session.setAttribute("name", phone);
			return phone;
		}
		String phone = (String) session.getAttribute("name");
		if (phone == null) {
			return "null";
		}
		return phone;
		
	}
	@Override
	public String addUser(UserBean bean) {
		if (bean.getU_phone() == null || !bean.getU_phone().matches(RegexUtil.REGEX_MOBILE)) {
			return "用户名必须为手机号";
		}
		if (!bean.getU_pass().equals(bean.getPass_two())) {
			return "前后密码不一致";
		}
		if ( !bean.getU_pass().matches(RegexUtil.REGEX_PASSWORD)) {
			return "密码必须为6-20位";
		}
		UserBean userBean = dao.selectByPhone(bean.getU_phone());
		if (userBean != null) {
			return "手机号码已注册";
		}
		//比对成功对密码进行加密
		String pass = new SimpleHash("MD5",bean.getU_pass() , bean.getU_phone(), 1024).toString();
		bean.setU_pass(pass);
		int num = dao.insertUser(bean);
		if (num > 0) {
			//获取插入用户的主键id
			int id = bean.getU_id();
			//注册成功自动为用户创建用户信息表
			VIPBean vipBean = new VIPBean();
			vipBean.setU_id(id);
			System.out.println(vipBean);
			dao.insertVIP(vipBean);
			return "注册成功";
		}
		return "注册失败，请重新注册";
	}

	@Override
	public ManageUserBean findManageUserByName(String name) {
		if (name == null || !name.matches(RegexUtil.REGEX_PASSWORD)) {
			return null;
		}
		
		ManageUserBean manageUserBean = dao.selectByManageName(name);
		//将用户名与权限保存在session中
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute("m_name", manageUserBean.getM_name());
		session.setAttribute("m_role", manageUserBean.getRole());

		return manageUserBean;
	}

	@Override
	public String addManageUser(ManageUserBean bean) {
		if (bean.getM_name()==null || bean.getM_pass()==null) {
			return "用户名或密码不能为空";
		}
		if (!bean.getM_name().matches(RegexUtil.REGEX_PASSWORD)) {
			return "用户名必须为6-20位";
		}
		if (!bean.getM_pass().matches(RegexUtil.REGEX_PASSWORD)) {
			return "密码名必须为6-20位";
		}
		ManageUserBean manageUserBean = dao.selectByManageName(bean.getM_name());
		if (manageUserBean != null) {
			return "用户已存在";
		}
		//将密码加密
		String pass = new SimpleHash("MD5",bean.getM_pass(),bean.getM_name(),1024).toString();
		bean.setM_pass(pass);
		int num = dao.insertManageUser(bean);
		if (num > 0) {
			return "注册成功";
		}
		return "注册失败，请重新注册";
	}

	@Override
	public boolean addVIP(VIPBean bean) {
		if (bean == null) {
			return false;
		}
		if (bean.getV_name().length()>20) {
			return false;
		}
		//获取用户id
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		int u_id = (int) session.getAttribute("u_id");
		bean.setU_id(u_id);
		int num = dao.updateVIPInfo(bean);
		if (num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean changeVIPClass(String phone,VIPBean bean) {
		if (bean.getV_code()>4 || bean.getV_code()<1) {
			return false;
		}
		UserBean userBean = dao.selectByPhone(phone);
		bean.setU_id(userBean.getU_id());
		int num = dao.updateVIPClass(bean);
		if (num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public VIPBean findVIPInfo() {
		int u_id = getU_id(); 
		System.out.println(u_id);
		if (u_id == 0) {
			return null;
		}
		VIPBean bean = dao.selectVIPInfo(u_id);
		return bean;
	}
	/**
	 * 获取用户id
	 * @return u_id
	 */
	public static int getU_id(){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		if (session.getAttribute("u_id") == null) {
			return 0;
		}
		int u_id = (int) session.getAttribute("u_id");
		return u_id;
	}
	/**
	 * 获取用户手机号码
	 * @return Phone
	 */
	public static String getPhone(){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String phone = (String) session.getAttribute("phone");
		return phone;
	}

	@Override
	public VIPBean findVIPInfo(String phone) {
		if (phone == null || !phone.matches(RegexUtil.REGEX_MOBILE)) {
			return null;
		}
		UserBean bean = dao.selectByPhone(phone);
		if (bean != null) {
			int u_id = bean.getU_id();
			VIPBean vipBean = dao.selectVIPInfo(u_id);
			return vipBean;
		}
		return null;
	}

	@Override
	public Map<String, Object> findUserAll(int page) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		if (page < 1) {
			return null;
		}
		int begin = 10 * (page-1);
		List<UserBean> list = dao.selectUserAll(begin, 10);
		int num = dao.CountUserSum();
		int countPage = 0;
		if (num % 10 == 0) {
			countPage = num / 10;
		}
		if (num % 10 != 0) {
			countPage = num / 10 + 1;
		}
		map.put("list", list);
		map.put("page", countPage);
		return map;
	}

	@Override
	public Map<String, Object> findManageAll(int page) {
		Map<String, Object> map = new HashMap<String,Object>();
		if (page < 1) {
			return null;
		}
		int new_page = 10 * (page - 1);
		List<ManageUserBean> list = dao.selectManageAll(new_page, 10);
		int num = dao.CountManageSum();
		int countPage = num/10;
		if (num % 10 != 0) {
			countPage = num/10+1;
		}
		map.put("list", list);
		map.put("page", countPage);
		return map;
	}

	@Override
	public boolean removeManageByName(String name) {
		if (name == null) {
			return false;
		}
		int num = dao.deleteManageByName(name);
		if (num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean chanageManage(ManageUserBean bean) {
		if (bean == null) {
			return false;
		}
		int num = dao.updateManageByName(bean);
		if (num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public String chanageManagePass(ManageUserBean bean) {
		if (bean == null || !bean.getM_pass().matches(RegexUtil.REGEX_PASSWORD)) {
			return "密码必须为6-20位";
		}
		if (!bean.getM_pass().equals(bean.getPass_two())) {
			return "前后密码不一致";
		}
		//比对成功对密码进行加密
		String pass = new SimpleHash("MD5",bean.getM_pass() , bean.getM_name(), 1024).toString();
		bean.setM_pass(pass);
		int num = dao.updateManagePass(bean);
		if (num > 0) {
			return "修改成功";
		}
		return "修改失败";
	}

	@Override
	public boolean addComment(CommentBean bean) {
		if (bean == null) {
			return false;
		}
		int num = dao.insertComment(bean);
		if (num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> findAllComment(int page) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		if (page < 1) {
			return null;
		}
		int new_page = 15 * (page - 1);
		int num = dao.countComment();
		int countPage = num/15;
		if (num%15 != 0) {
			countPage = num/15 +1;
		}
		List<CommentBean> list = dao.selectAllComment(new_page, 15);
		map.put("page", countPage);
		map.put("list", list);
		return map;
	}

	@Override
	public List<DiscountBean> findAllDiscount() {
		List<DiscountBean> list = dao.selectAllDiscount();
		
		return list;
	}

	@Override
	public boolean changeDiscount(DiscountBean bean) {
		if (bean.getD_class() < 1 || bean.getD_dis() < 0.1) {
			return false;
		}
		int num = dao.updateDiscount(bean);
		if (num > 0) {
			return true;
		}
		return false;
	}

	@Override
	public String changeUserPass(UserBean bean) {
		if (bean.getU_phone() == null || !bean.getU_phone().matches(RegexUtil.REGEX_MOBILE)) {
			return "用户名必须为手机号";
		}
		if (!bean.getU_pass().equals(bean.getPass_two())) {
			return "前后密码不一致";
		}
		if ( !bean.getU_pass().matches(RegexUtil.REGEX_PASSWORD)) {
			return "密码必须为6-20位";
		}
		
		//比对成功对密码进行加密
		String pass = new SimpleHash("MD5",bean.getU_pass() , bean.getU_phone(), 1024).toString();
		bean.setU_pass(pass);
		int num = dao.updateUserPass(bean);
		if (num > 0) {
			return "修改成功";
		}
		return "修改失败，请重新修改";
	}

	@Override
	public ManageUserBean getManageUserRole(String token) {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			if ("login".equals(token)) {
				String name = (String) session.getAttribute("m_name");
				String role = (String) session.getAttribute("m_role");
				session.setAttribute("manageName", name);
				session.setAttribute("role", role);
			}
			String name = (String) session.getAttribute("manageName");
			String role = (String) session.getAttribute("role");
			
			if (name != null ) {
				ManageUserBean manage = new ManageUserBean();
				manage.setM_name(name);
				manage.setRole(role);
				return manage;
			}
			return null;
			
	}

}
