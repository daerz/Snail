package com.project.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.project.bean.OrderBean;
import com.project.service.IOrderService;
import com.project.util.AlipayUtil;

@Controller
@RequestMapping(value = "/snail")
public class PayController {
	@Autowired
	private IOrderService oservice;

	/**
	 * 付款
	 * 
	 * @param o_id
	 * @return
	 */
	@RequestMapping(value = "/aliPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> aliPay(String o_id) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		System.out.println(o_id);
		OrderBean ob = oservice.findOrderOneByOid(o_id);

		if (ob == null) {

			map.put("result", "订单错误 ");
			map.put("paystate", 400);
			return map;
		}
		if(ob.getO_state()==1) {
			map.put("result", "订单已支付");
			map.put("paystate", 400);
			return map;
		}
		// 获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayUtil.getValue("serverUrl"),
				AlipayUtil.getValue("appId"), AlipayUtil.getValue("privateKey"), AlipayUtil.getValue("format"),
				AlipayUtil.getValue("charset"), AlipayUtil.getValue("alipayPublicKey"),
				AlipayUtil.getValue("signType"));

		// 设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayUtil.getValue("return_url"));
		alipayRequest.setNotifyUrl(AlipayUtil.getValue("notify_url"));
		String out_trade_no = null;
		String total_amount = null;
		String subject = null;
		String body = null;
		try {
			// 商户订单号，商户网站订单系统中唯一订单号，必填
			out_trade_no = new String(ob.getO_id().getBytes("ISO-8859-1"), "UTF-8");
			// 付款金额，必填
			total_amount = new String(String.valueOf(ob.getO_r_price()).getBytes("UTF-8"), "UTF-8");
			// 订单名称，必填
			subject = new String("snail".getBytes("UTF-8"), "UTF-8");
			// 商品描述，可空
			body = new String("CHESHI".getBytes("UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\"" + total_amount
				+ "\"," + "\"subject\":\"" + subject + "\"," + "\"body\":\"" + body + "\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		// 若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		// alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
		// + "\"total_amount\":\""+ total_amount +"\","
		// + "\"subject\":\""+ subject +"\","
		// + "\"body\":\""+ body +"\","
		// + "\"timeout_express\":\"10m\","
		// + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		// 请求
		String result = null;
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
		} catch (AlipayApiException e) {

			e.printStackTrace();
		}

		map.put("result", result);
		map.put("paystate", 200);
		return map;

	}

	
}
