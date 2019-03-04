package com.project.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.bean.CommentBean;
import com.project.bean.DiscountBean;
import com.project.bean.ManageUserBean;
import com.project.bean.UserBean;
import com.project.bean.VIPBean;
import com.project.service.ILoginLogService;
import com.project.service.IUserService;
import com.project.tools.LoginEnum;
import com.project.tools.UserNamePasswordTelphoneToken;
import com.project.util.RegexUtil;

import com.project.util.SMSUtil;




/**
 * 控制层
 * 用户登陆、注册
 * @author Administrator
 *
*/
@Controller
@RequestMapping(value="/users",produces={"text/html;charset=utf-8"})
public class UserController {
	@Autowired
	private IUserService service;
	@Autowired
	private ILoginLogService logService;
	/**
	 * 用户密码登录
	 * @param bean
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public String userPassLogin(UserBean bean){
		System.out.println(bean);
		Subject subject = SecurityUtils.getSubject();
		//使用shiro验证
		UserNamePasswordTelphoneToken token = new UserNamePasswordTelphoneToken(bean.getU_phone(),bean.getU_pass());
		token.setLoginType(LoginEnum.USER);
		try {
			subject.login(token);
			service.findName("login");
			return "登录成功";
			
		} catch (Exception e) {
			return "登录失败";
		}
	}
	/**
	 * 用户验证码登陆
	 * @param phone
	 * @param code
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="/user/sms",method=RequestMethod.POST)
	@ResponseBody
	public String SMSLogin(String phone,String code){
		System.out.println(phone+code);
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		System.out.println("SMSLogin"+session);
		//将session中的手机号码和验证码与前台做比对
		String sessionCode = (String) session.getAttribute("code");
		String sessionPhone = (String) session.getAttribute("phone");
		//删除session中的验证码
		session.removeAttribute("code");
		session.removeAttribute("phone");
		if (sessionCode==null || sessionPhone==null) {
			return "验证码不正确";
		}
		//比对成功就进入shiro验证
		if (sessionCode.equals(code) && sessionPhone.equals(phone)) {
			
			UserNamePasswordTelphoneToken token = new UserNamePasswordTelphoneToken(phone);
			token.setLoginType(LoginEnum.USER);
			try {
				subject.login(token);
				service.findName("login");
				return "登录成功";
			} catch (Exception e) {
				return "用户名或密码错误";
			}
		}
		return "用户名或验证码不正确";
		
	}
	/**
	 * 用户注册
	 * @param bean
	 * @return 注册信息
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="/user",method=RequestMethod.POST)
	@ResponseBody
	public String userRegister(UserBean bean){
		//验证码比对
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(); 
		String sessionCode = (String)session.getAttribute("code");
		String sessionPhone = (String) session.getAttribute("phone");
		if (bean.getCode() == null) {
			return "验证码不能为空";
		}
		System.out.println(bean);
		session.removeAttribute("code");
		session.removeAttribute("phone");
		if (bean.getCode().equals(sessionCode)&& bean.getU_phone().equals(sessionPhone)) {
			String result = service.addUser(bean);
			return result;
		}
		return "验证码错误";
	}
	/**
	 * 用户修改密码
	 * @param bean
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="/user",method=RequestMethod.PUT)
	@ResponseBody
	public String userChangePass(UserBean bean){
		//验证码比对
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(); 
		String sessionCode = (String)session.getAttribute("code");
		String sessionPhone = (String) session.getAttribute("phone");
		if (bean.getCode() == null) {
			return "验证码不能为空";
		}
		session.removeAttribute("code");
		session.removeAttribute("phone");
		if (bean.getCode().equals(sessionCode)&& bean.getU_phone().equals(sessionPhone)) {
			String result = service.changeUserPass(bean);
			return result;
		}
		return "验证码错误";
	}
	/**
	 * 管理员登录
	 * @return 登陆信息
	 */
	@RequestMapping(value="/manageUser/login",method=RequestMethod.POST)
	@ResponseBody
	public String manageLogin(ManageUserBean bean){
		Subject subject = SecurityUtils.getSubject();
		if (bean == null) {
			return "";
		}
		UserNamePasswordTelphoneToken token = new UserNamePasswordTelphoneToken(bean.getM_name(), bean.getM_pass());
		token.setLoginType(LoginEnum.ADMIN);
		try {
			subject.login(token);
			service.getManageUserRole("login");
			return "登录成功";
		} catch (Exception e) {
			return "登录失败";
		}
		
	}
	/**
	 * 获取用户登陆后的权限
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/manageUser/{token}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public ManageUserBean getManageRole(@PathVariable(value="token")String token){
		ManageUserBean manageUserBean = service.getManageUserRole(token);
		return manageUserBean;
	}
	/**
	 * 管理员注册
	 * @param bean
	 * @return 注册结果
	 */
	@RequestMapping(value="/manageUser",method=RequestMethod.POST)
	@ResponseBody
	public String manageRegister(ManageUserBean bean){
		if (bean.getM_pass().equals(bean.getPass_two())) {
			String result = service.addManageUser(bean);
			return result;
		}
		return "前后密码不一致";
	}
	/**
	 * 分页查找所有会员用户（每页8行）
	 * @param page
	 * @return Map<String, Object>  map键：值(list:用户信息;page:总页数)
	 */
	@RequestMapping(value="/user",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> findAllUser(int page){
		Map<String, Object> map = service.findUserAll(page);
		return map;
	}
	/**
	 * 分页查找所有管理员用户（每页8行）
	 * @param page
	 * @return Map<String, Object>  map键：值(list:用户信息;page:总页数)
	 */
	@RequestMapping(value="/manageUser",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> findAllManage(int page){
		Map<String, Object> map = service.findManageAll(page);
		return map;
		
	}
	/**
	 * 修改管理员权限
	 * @param bean
	 * @return boolean
	 */
	@RequestMapping(value="/manageUser",method=RequestMethod.PUT,produces="application/json;charset=UTF-8")
	@ResponseBody
	public boolean changeManageRole(ManageUserBean bean ){
		boolean boo = service.chanageManage(bean);
		return boo;
		
	}
	/**
	 * 修改管理员用户密码
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="/manageUser/pass",method=RequestMethod.PUT,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String chanageManagePass(ManageUserBean bean){
		String result = service.chanageManagePass(bean);
		return result;
	}
	/**
	 * 删除管理员用户
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/manageUser",method=RequestMethod.DELETE,produces="application/json;charset=UTF-8")
	@ResponseBody
	public boolean removeManage(String name){
		boolean boo = service.removeManageByName(name);
		
		return boo;
		
	}
	/**
	 * 填写用户信息表
	 * @param bean
	 * @return boolean
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="/vip",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public boolean addVIPInfo(VIPBean bean){
		boolean result = service.addVIP(bean);
		
		return result;
	}
	/**
	 * 获取用户会员信息
	 * @return VIPBean
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="/vip",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public VIPBean findVIPInfo(){
		VIPBean bean = service.findVIPInfo();
		System.out.println(bean);
		return bean;
	}
	/**
	 * 根据手机号码查找用户信息（后台人员）
	 * @param phone
	 * @return VIPBean
	 */
	@RequestMapping(value="/vip/{phone}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public VIPBean findVIPInfo(@PathVariable(value="phone")String phone){
		VIPBean bean = service.findVIPInfo(phone);
		System.out.println(phone);
		return bean;
	}
	/**
	 * 修改会员等级
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="/vip",method=RequestMethod.PUT,produces="application/json;charset=UTF-8")
	@ResponseBody
	public boolean changeVIPInfo(String phone,VIPBean bean){
		boolean boo = service.changeVIPClass(phone,bean);
		return boo;
		
	}
	/**
	 * 增加评论
	 * @param bean
	 * @return boolean
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="/comment",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public boolean addComment(CommentBean bean){
		System.out.println(bean);
		boolean boo = service.addComment(bean);
		return boo;
		
	}
	/**
	 * 查找所有评论信息
	 * @param page
	 * @return Map<String, Object>
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="/comment",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> findAllComment(int page){
		Map<String, Object> map = service.findAllComment(page);
		
		return map;
	}
	/**
	 * 获取所有会员折扣信息
	 * @return List<DiscountBean>
	 */
	@RequestMapping(value="/dis",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public List<DiscountBean> findAllDiscount(){
		List<DiscountBean> list = service.findAllDiscount();
		return list;
	}
	
	/**
	 * 修改会员折扣信息
	 * @param bean
	 * @return boolean
	 */
	@RequestMapping(value="/dis",method=RequestMethod.PUT,produces="application/json;charset=UTF-8")
	@ResponseBody
	public boolean changeDiscount(DiscountBean bean){
		System.out.println(bean);
		boolean boo = service.changeDiscount(bean);
		return boo;
	}
	/**
	 * 用户获取验证码信息
	 * @param phone
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="/sms",method=RequestMethod.POST)
	@ResponseBody
	public String getCode(String phone){
		if (!phone.matches(RegexUtil.REGEX_MOBILE)) {
			return "手机号码不正确";
		}
		Subject subject = SecurityUtils.getSubject();
		
		Session session = subject.getSession();
		System.out.println("getCode"+session);
		String code = SMSUtil.execute(phone);
		session.setAttribute("code", code);
		session.setAttribute("phone", phone);
		return "验证码获取成功";
	}
	/**
	 * 获取用户是否已登录
	 * @param token
	 * @return 用户账号
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="/user/{token}",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getUserName(@PathVariable(value="token")String token){
		String name = service.findName(token);
		return name;
	}
	
}
