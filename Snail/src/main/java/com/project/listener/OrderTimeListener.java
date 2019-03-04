package com.project.listener;




import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import com.project.bean.OrderBean;
import com.project.controller.OrderController;
import com.project.service.IOrderService;





public class OrderTimeListener  implements MessageListener{

	
	@Autowired
	private IOrderService oservice;
	@Override
	public void onMessage(Message arg0, byte[] arg1) {

		byte[] body=arg0.getBody();
		String some=new String(body);
		
		System.out.println("body:"+some);
		if(some.substring(0, 6).equals("order_")) {
			System.out.println(some.substring(6));
			OrderBean oldorder=oservice.findOrderOneByOid(some.substring(6));
			//判断是否线下支付
			if(oldorder!=null) {
				if(oldorder.getO_state()==0) {
					Map<String, Object> map=oservice.quitOrderAllOnline(oldorder);
					System.out.println("自动取消订单:"+map.get("message"));
				}
			}

			}
		}	
		
	}  





