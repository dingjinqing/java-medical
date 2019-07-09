package com.vpu.mp.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vpu.mp.service.foundation.JedisManager;

import redis.clients.jedis.Jedis;

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
	public String test() throws InterruptedException {
		System.out.println("test------------------: ");
		for (int i = 0; i < 100; i++) {
			MyThread t = new MyThread();
			t.start();
		}
		Thread.sleep(100000);
		return "ok";
	}
}
