package com.vpu.mp.controller.admin;

import com.vpu.mp.MpApplication;
import com.vpu.mp.controller.BaseController;
import com.vpu.mp.controller.BaseControllerTest;
import com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyListParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author 孔德成
 * @date 2019/7/22 17:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MpApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = BaseControllerTest.CustomerLoader.class)
public class GroupBuyControllerTest extends AdminBaseControllerTest {

    @Override
    public void initSession() {
        this.loginAccount = true;
        super.initSession();
    }

    @Test
    public void getListGroupBuy() throws Exception {

        GroupBuyListParam param= new GroupBuyListParam();
        param.setPageRows(20);
        param.setCurrentPage(1);
        param.setType(1);
        this.expectSuccess(post("/api/admin/market/groupbuy/list", param)).andReturn();

    }
}