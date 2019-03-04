package com.project.service;


import java.util.List;
import java.util.Map;

import com.project.bean.CommentBean;
import com.project.bean.DiscountBean;
import com.project.bean.ManageUserBean;
import com.project.bean.UserBean;
import com.project.bean.VIPBean;

/**
 * 业务层
 * 用户登陆、注册接口
 * @author Administrator
 *
 */
public interface IUserService {
	/**
	 * 用户登陆
	 * @param phone
	 * @return UserBean
	 */
	public UserBean findUserByName(String phone);
	/**
	 * 用户注册
	 * @param bean
	 * @return 注册是否成功的信息（String）
	 */
	public String addUser(UserBean bean);
	/**
	 * 管理员用户登陆
	 * @param name
	 * @return ManageUserBean
	 */
	public ManageUserBean findManageUserByName(String name);
	/**
	 * 增加管理员账号
	 * @param bean
	 * @return 添加是否成功的具体信息（String）
	 */
	public String addManageUser(ManageUserBean bean);
	/**
	 * 添加会员信息及将插入信息的主键返回后添加到对应的用户u_vip列
	 * @param bean
	 * @return 插入是否成功（boolean）
	 */
    public boolean addVIP(VIPBean bean);
    /**
     * 修改用户会员等级
     * @param bean（会员等级）
     * @return
     */
    public boolean changeVIPClass(String phone,VIPBean bean);
    /**
     * 前台用户查找用户对应的会员信息
     * @param u_id
     * @return VIPBean
     */
    public VIPBean findVIPInfo();
    /**
     * 根据手机号码查找用户信息(后台查询用户)
     * @param phone
     * @return
     */
    public VIPBean findVIPInfo(String phone);
    /**
     * 查找所有会员用户
     * @return List<UserBean>
     */
    public Map<String, Object> findUserAll(int page);
    /**
     * 查找所有管理员用户
     * @return List<ManageUserBean>
     */
    public Map<String, Object> findManageAll(int page);
    /**
     * 删除管理员用户
     * @param name
     * @return boolean
     */
    public boolean removeManageByName(String name);
    /**
     * 修改管理员用户权限
     * @param bean
     * @return boolean
     */
    public boolean chanageManage(ManageUserBean bean);
    
    /**
     * 修改管理员密码
     * @param bean
     * @return 修改结果
     */
    public String chanageManagePass(ManageUserBean bean);
    /**
     * 添加用户评论
     * @param bean
     * @return boolean
     */
    public boolean addComment(CommentBean bean);
    /**
     * 查找所有评论（分页）
     * @param page
     * @return
     */
    public Map<String, Object> findAllComment(int page);
    /**
     * 前台页面判断是否登录
     * @param token
     * @return
     */
    public String findName(String token);
    
    /**
     * 查找所有折扣信息
     * @return List<DiscountBean>
     */
    public List<DiscountBean> findAllDiscount();
    
    /**
     * 修改会员折扣
     * @param bean
     * @return
     */
    
    public boolean changeDiscount(DiscountBean bean);
    /**
     * 修改用户密码
     * @param bean
     * @return
     */
    public String changeUserPass(UserBean bean);
    /**
     * 前端获取管理员用户权限
     * @param str
     * @return
     */
    public ManageUserBean getManageUserRole(String token);
}
