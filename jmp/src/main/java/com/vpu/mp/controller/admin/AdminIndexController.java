package com.vpu.mp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vpu.mp.service.foundation.JedisManager;
import com.vpu.mp.service.foundation.JsonResult;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminIndexController extends AdminBaseController {

	JedisManager jedis = JedisManager.instance();

	public class MyThread extends Thread {
		@Override
		public void run() {
				jedis.set("k", "v");
				String message = String.format("a:%d,waiter:%d", jedis.getJedisPool().getNumActive(),
						jedis.getJedisPool().getNumWaiters());
				System.out.println("Jedisp: " +message);
		}
	};

	@RequestMapping(value = "/admin/test")
	@ResponseBody
	public JsonResult test() throws InterruptedException {
		System.out.println("@Controller id:"+Thread.currentThread().getId());
		return success( );
	}
}
