package com.project.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.project.bean.OrderBean;
import com.project.service.IOrderService;
import com.project.util.AlipayUtil;

import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;

@Controller
@RequestMapping("/snail")
public class AlipayNotifyController {
	@Autowired
	private IOrderService oservice;
	
	@RequestMapping(value="/notifyInfo",method=RequestMethod.POST)
	public void notifyInfo(HttpServletRequest request) {
		System.out.println("支付宝通知");
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			try {
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			}
			params.put(name, valueStr);
		}
		
		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(params, AlipayUtil.getValue("alipayPublicKey"), AlipayUtil.getValue("charset"), AlipayUtil.getValue("signType"));
		} catch (AlipayApiException e) {
			
			e.printStackTrace();
		} //调用SDK验证签名

		//——请在这里编写您的程序（以下代码仅作参考）——
		
		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
		if(signVerified) {//验证成功
			//商户订单号
			String out_trade_no=null;
			String trade_no=null;
			//交易状态
			String trade_status = null;
			
			String total_amount=null;
			try {
				 out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

				//支付宝交易号
				 trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

				 trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
				 total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				
				 e.printStackTrace();
			}
			
/*			System.out.println("商户订单号："+out_trade_no);
			System.out.println("支付宝订单号："+trade_no);
			System.out.println("订单状态："+trade_status);
			System.out.println("总金额："+total_amount);*/
			
			
			
			OrderBean ob=oservice.findOrderOneByOid(out_trade_no);
//			if(!String.valueOf(ob.getO_r_price()).equals("total_amount")) {
//				System.out.println("金额有误");
//			}
			
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
//				System.out.println("TRADE_FINISHED");
				ob.setO_state(4);
				boolean bool=oservice.changeOrderByOid(ob);
				//注意：
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
			}else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				
//					System.out.println("修改支付状态");
					ob.setO_state(1);
					boolean bool=oservice.changeOrderByOid(ob);
//					System.out.println("订单支付状态修改："+bool);
				//注意：
				//付款完成后，支付宝系统发送该交易状态通知
					
				//付款完成后向后台发送消息
				GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io","BC-22d9da6f0d0146f98a7aebf2420c8c69");
				goEasy.publish("my_channel","1",new PublishListener() {
					@Override
					public void onSuccess() {
						System.out.println("Publish success");
					}
					@Override
					public void onFailed(GoEasyError error) {
						System.out.println("Error code:"+ error.getCode() +"; error content:"+error.getContent());
					}
				});
			}
			
			System.out.println("success");
			
		}else {//验证失败
			System.out.println("fail");
		
			//调试用，写文本函数记录程序运行情况是否正常
			//String sWord = AlipaySignature.getSignCheckContentV1(params);
			//AlipayConfig.logResult(sWord);
		}
		
	}
}
