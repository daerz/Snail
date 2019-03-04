package com.project.dao.impl;


import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.bean.LiveBean;
import com.project.bean.OrderBean;
import com.project.bean.OrderConditionBean;
import com.project.bean.OrderInfoBean;
import com.project.dao.IOrderDao;
/**
 * 持久层
 * 订单业务实现类
 * @author Administrator
 *
 */
@Component
public class OrderDaoImpl implements IOrderDao {

	@Autowired
	private SqlSessionFactory fa;
	
	@Override
	public int insertLive(LiveBean liveBean) {
		SqlSession session=fa.openSession();
		int num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.insertLive(liveBean);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}

	
	@Override
	public int insertOrder(OrderBean ob) {
		SqlSession session=fa.openSession();
		int num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.insertOrder(ob);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}
	
	@Override
	public List<LiveBean> selectLive(LiveBean bean) {
		SqlSession session=fa.openSession();
		IOrderDao dao=session.getMapper(IOrderDao.class);
		List<LiveBean> list=null;
		try {
			list=dao.selectLive(bean);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return list;
	}

	@Override
	public int deleteOrderByOid(String o_id) {
		SqlSession session=fa.openSession();
		int num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.deleteOrderByOid(o_id);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}
	
	@Override
	public int updateOrderByOid(OrderBean ob) {
		SqlSession session=fa.openSession();
		int num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.updateOrderByOid(ob);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}

	@Override
	public OrderBean selectOrderOneByOid(String o_id) {
		SqlSession session=fa.openSession();
		OrderBean ob=null;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			ob=dao.selectOrderOneByOid(o_id);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return ob;
	}

	@Override
	public List<OrderBean> selectOrderAllByPhone(String o_u_phone) {
		SqlSession session=fa.openSession();
		List<OrderBean> ob=null;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			ob=dao.selectOrderAllByPhone(o_u_phone);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return ob;
	}

	@Override
	public List<OrderBean> selectOrderOneByPhone(String o_phone) {
		SqlSession session=fa.openSession();
		List<OrderBean> ob=null;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			ob=dao.selectOrderOneByPhone(o_phone);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return ob;
	}


	@Override
	public List<OrderInfoBean> selectOrderInfoOneByRid(int o_r_id) {
		SqlSession session=fa.openSession();
		List<OrderInfoBean> oib=null;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			oib=dao.selectOrderInfoOneByRid(o_r_id);
			
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return oib;
	}


	@Override
	public List<OrderBean> selectOrderAllByIntime(String intime) {
		SqlSession session=fa.openSession();
		List<OrderBean> ob=null;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			ob=dao.selectOrderAllByIntime(intime);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return ob;
	}


	@Override
	public List<OrderBean> selectOrderAllByOuttime(String outtime) {
		SqlSession session=fa.openSession();
		List<OrderBean> list=null;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			list=dao.selectOrderAllByOuttime(outtime);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return list;
	}


	@Override
	public int updateLive(int l_r_num) {
		SqlSession session=fa.openSession();
		int num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.updateLive(l_r_num);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}



	@Override
	public int insertOrderInfo(List<OrderInfoBean> list) {
		SqlSession session=fa.openSession();
		int num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.insertOrderInfo(list);
			
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}


	@Override
	public String selectLiveOrder(int l_r_num) {
		SqlSession session=fa.openSession();
		String num=null;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.selectLiveOrder(l_r_num);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}


	@Override
	public int updateOrderInfo(OrderInfoBean rib) {
		SqlSession session=fa.openSession();
		int num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.updateOrderInfo(rib);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}


	@Override
	public List<OrderInfoBean> selectOrderInfoByOid(String i_o_id) {
		SqlSession session=fa.openSession();
		List<OrderInfoBean> list=null;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			list=dao.selectOrderInfoByOid(i_o_id);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return list;
	}


	@Override
	public double selectOneOrderInfo(String i_o_id, int i_r_id) {
		SqlSession session=fa.openSession();
		double num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.selectOneOrderInfo(i_o_id, i_r_id);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}


	@Override
	public List<OrderBean> findOrderAllAndAll(OrderBean order) {
		SqlSession session=fa.openSession();
		List<OrderBean> list=null;
		try {
			IOrderDao dao = session.getMapper(IOrderDao.class);
			list=dao.findOrderAllAndAll(order);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return list;
	}


	@Override
	public int updateRoom(String l_uid, int l_r_num) {
		SqlSession session=fa.openSession();
		int num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.updateRoom(l_uid, l_r_num);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}


	@Override
	public int deleteOrderInfoByFlag(String i_o_id) {
		SqlSession session=fa.openSession();
		int num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.deleteOrderInfoByFlag(i_o_id);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}

	@Override
	public int selectCountPage(String o_u_phone) {
		SqlSession session=fa.openSession();
		int num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.selectCountPage(o_u_phone);
			session.commit();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return num;
	}



	@Override
	public List<OrderBean> selectOrderByPage(OrderConditionBean condition) {
		SqlSession session=fa.openSession();
		List<OrderBean> list=null;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			list=dao.selectOrderByPage(condition);
			session.commit();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return list;
	}







	@Override
	public int updateO_stateByO_id(String o_id) {
		SqlSession session=fa.openSession();
		int num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.updateO_stateByO_id(o_id);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}


	@Override
	public List<OrderBean> selectFindAllOrder(int currentPage, String timeNow) {
		SqlSession session=fa.openSession();
		List<OrderBean> list=null;
		try {
			IOrderDao dao = session.getMapper(IOrderDao.class);
			list=dao.selectFindAllOrder(currentPage,timeNow);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return list;
	}


	@Override
	public List<LiveBean> selectAllLive(int nowPage) {
		SqlSession session=fa.openSession();
		List<LiveBean> list=null;
		try {
			IOrderDao dao = session.getMapper(IOrderDao.class);
			list=dao.selectAllLive(nowPage);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return list;
	}


	@Override
	public int selectAllLiveNum() {
		SqlSession session=fa.openSession();
		int num = 0;
		try {
			IOrderDao dao = session.getMapper(IOrderDao.class);
			num=dao.selectAllLiveNum();
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}


	@Override
	public int selectLiveNum(LiveBean bean) {
		SqlSession session=fa.openSession();
		IOrderDao dao=session.getMapper(IOrderDao.class);
		int num = 0;
		try {
			num=dao.selectLiveNum(bean);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}


	@Override
	public int selectCountALLPage(String timeNow) {
		SqlSession session=fa.openSession();
		int num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.selectCountALLPage(timeNow);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}


	@Override
	public int updateEdit(LiveBean liveBean) {
		SqlSession session=fa.openSession();
		int num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.updateEdit(liveBean);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}


	@Override
	public List<Double> selectMoneyByMon(String yearMon) {
		SqlSession session=fa.openSession();
		List<Double> list=null;
		try {
			IOrderDao dao = session.getMapper(IOrderDao.class);
			list=dao.selectMoneyByMon(yearMon);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return list;
	}


	@Override
	public int removeOrderInfoByFlagAndirid(String o_id, int i_r_id) {
		SqlSession session=fa.openSession();
		int num=0;
		try {
			IOrderDao dao=session.getMapper(IOrderDao.class);
			num=dao.removeOrderInfoByFlagAndirid(o_id,i_r_id);
			session.commit();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			session.close();
		}
		return num;
	}

}
