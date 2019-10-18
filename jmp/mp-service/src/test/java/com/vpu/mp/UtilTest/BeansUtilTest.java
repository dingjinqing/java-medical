package com.vpu.mp.UtilTest;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;

import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.mp.goods.OrderGoodsMpVo;
import com.vpu.mp.service.pojo.shop.order.mp.order.OrderListMpVo;
import com.vpu.mp.service.pojo.wxapp.comment.CommentListVo;

public class BeansUtilTest {
	public static void main1(String[] args) {
		OrderListMpVo source = new OrderListMpVo();
		source.setOrderId(1);
		OrderGoodsMpVo one = new OrderGoodsMpVo();
		one.setGoodsId(1);
		List<OrderGoodsMpVo> goods = new ArrayList<OrderGoodsMpVo>();
		goods.add(one);
		source.setGoods(goods);
		OrderListInfoVo orderListInfoVo = new OrderListInfoVo();
		try {
			PropertyUtils.copyProperties(orderListInfoVo,source);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(orderListInfoVo);
	}
	
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		OrderListMpVo source = new OrderListMpVo();
		source.setOrderId(1);
		OrderGoodsMpVo one = new OrderGoodsMpVo();
		one.setGoodsId(1);
		List<CommentListVo> g1 = new ArrayList<CommentListVo>();
		List<OrderGoodsMpVo> g2 = new ArrayList<OrderGoodsMpVo>();
		g2.add(one);
		BeanUtils.copyProperties(g1,g2);
		System.out.println(g1);
		System.out.println(g2);
	}
}
