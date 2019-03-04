package com.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.project.bean.CommentBean;
import com.project.bean.DiscountBean;
import com.project.bean.ManageUserBean;
import com.project.bean.UserBean;
import com.project.bean.VIPBean;

/**
 * 持久层
 * 用户登录、注册业务接口
 * @author Administrator
 *
 */
public interface IUserDao {
	/**
	 * 用户登陆
	 * @return UserBean
	 */
	@Select("select * from users where u_phone = #{phone}")
	public UserBean selectByPhone(String phone);
	/**
	 * 用户注册
	 * @return 插入影响的行数
	 */
	@Insert("insert into users(u_phone,u_pass) value(#{u_phone},#{u_pass})")
	@Options(useGeneratedKeys=true, keyProperty="u_id")
	public int insertUser(UserBean bean);
	/**
	 * 管理员登录
	 * @return ManageUserBean
	 */
	@Select("select * from manageuser where m_name = #{name}")
	public ManageUserBean selectByManageName(String name); 
	/**
	 * 添加管理员用户
	 * @return 插入影响的行数
	 */
	@Insert("insert into manageuser(m_name,m_pass,role) value(#{m_name},#{m_pass},#{role})")
	public int insertManageUser(ManageUserBean bean);
	
	/**
	 * 增加用户会员信息
	 * @return 影响的行数
	 */
	@Insert("insert into vip(v_birthday,u_id) value(now(),#{u_id})")
	public int insertVIP(VIPBean bean);
	/**
	 * 更新用户会员信息
	 * @param bean
	 * @return
	 */
	@Update("update vip set v_name = #{v_name},v_sex = #{v_sex} where u_id = #{u_id}")
	public int updateVIPInfo(VIPBean bean);
	/**
	 * 更改用户信息表中的会员等级
	 * @param bean
	 * @return 影响的行数
	 */
	@Update("update vip set v_code = #{v_code} where u_id = #{u_id}")
	public int updateVIPClass(VIPBean bean);
	/**
	 * 查找用户的会员信息
	 * @param u_id
	 * @return VIPBean
	 */
	@Select("select * from vip where u_id = #{u_id}")
	public VIPBean selectVIPInfo(int u_id);
	/**
	 * 查找会员折扣
	 * @param v_code
	 * @return
	 */
	@Select("select * from discount where d_class = #{v_code}")
	public DiscountBean selectDiscont(int v_code);
	/**
	 * 获取所有会员用户
	 * @return List<UserBean>
	 */
	@Select("select * from users join vip on users.u_id = vip.u_id limit #{begin},#{total}")
	@Results({
			@Result(property="u_id",column="u_id"),
			@Result(property="u_phone",column="u_phone"),
			@Result(property="code",column="v_code")
	})
	public List<UserBean> selectUserAll(@Param("begin")int begin,@Param("total")int total);
	/**
	 * 统计会员用户总数
	 * @return int(用户总数)
	 */
	@Select("select count(*) from users")
	public int CountUserSum();
	/**
	 * 获取所有管理员用户
	 * @return List<ManageUserBean>
	 */
	@Select("select * from manageuser limit #{begin},#{total}")
	public List<ManageUserBean> selectManageAll(@Param("begin")int begin,@Param("total")int total);
	/**
	 * 统计管理员总数
	 * @return
	 */
	@Select("select count(*) from manageuser")
	public int CountManageSum();
	/**
	 * 删除管理员账号
	 * @param name
	 * @return int
	 */
	@Delete("delete from manageuser where m_name = #{name}")
	public int deleteManageByName(String name);
	/**
	 * 修改管理员权限
	 * @param bean
	 * @return int
	 */
	@Update("update manageuser set role = #{role} where m_name = #{m_name}")
	public int updateManageByName(ManageUserBean bean);
	
	/**
	 * 修改管理员密码
	 * @param bean
	 * @return
	 */
	@Update("update manageuser set m_pass = #{m_pass} where m_name = #{m_name}")
	public int updateManagePass(ManageUserBean bean);
	/**
	 * 用户评论
	 * @param bean
	 * @return
	 */
	@Insert("insert into comment(c_name,c_content,c_time,c_score) value(#{c_name},#{c_content},now(),#{c_score})")
	public int insertComment(CommentBean bean);
	/**
	 * 查询所有评论（分页）
	 * @param begin
	 * @param total
	 * @return
	 */
	@Select("select * from comment order by c_time desc limit #{begin},#{total}")
	public List<CommentBean> selectAllComment(@Param("begin")int begin,@Param("total")int total);
	/**
	 * 统计评论的总条数
	 * @return
	 */
	@Select("select count(*) from comment")
	public int countComment();
	/**
	 * 查找所有会员折扣信息
	 * @return
	 */
	@Select("select * from discount")
	public List<DiscountBean> selectAllDiscount();
	/**
	 * 修改会员折扣
	 * @param bean
	 * @return
	 */
	@Update("update discount set d_dis = #{d_dis} where d_class = #{d_class}")
	public int updateDiscount(DiscountBean bean);
	/**
	 * 修改密码
	 * @param bean
	 * @return
	 */
	@Update("update users set u_pass = #{u_pass} where u_phone = #{u_phone}")
	public int updateUserPass(UserBean bean);
}
