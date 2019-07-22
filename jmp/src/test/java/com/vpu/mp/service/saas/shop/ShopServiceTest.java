package com.vpu.mp.service.saas.shop;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vpu.mp.MpApplication;
import com.vpu.mp.service.foundation.BaseServiceTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MpApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = BaseServiceTest.CustomerLoader.class)
public class ShopServiceTest extends BaseServiceTest {


	@Test
	public void testGetPageList() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuildOptions() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddShop() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCanUseShopId() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateShop() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasMobile() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetShopById() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetShopByMobile() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetShopAccessRoleId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRoleShopList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetShopBaseInfoById() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateShopBaseInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetShopList() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckExpire() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertUserLoginRecord() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateConfig() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateOperation() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryOldRcord() {
		fail("Not yet implemented");
	}

	@Test
	public void testDiffEdit() {
		fail("Not yet implemented");
	}

}
