package com.vpu.mp.shell;


import com.github.fonimus.ssh.shell.SshShellHelper;
import com.github.fonimus.ssh.shell.commands.SshShellComponent;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.foundation.util.JsonUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.goods.es.EsTaskParam;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.thread.es.EsThreadConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jline.utils.Log;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Slf4j
@SshShellComponent
@ShellCommandGroup("ElasticSearch Commands")
public class EsCommand {

    @Autowired
    private EsThreadConfig esThreadConfig;

    @Autowired
    private SaasApplication saas;

    @Autowired
    private SshShellHelper helper;

    @ShellMethod("ElasticSearch Index . --all[false|true]default true --shopId<shopId>")
    @ShellMethodAvailability("argsAvailability")
    public void es(@ShellOption(arity = 1, defaultValue = "true") boolean all,@ShellOption( defaultValue = "0")int shopId) {

        List<Integer> shopIdList = null;
        if( all ){
            shopIdList = saas.shop.getAll().stream().map(ShopRecord::getShopId).collect(Collectors.toList());
        }else if( 0 != shopId ){
            shopIdList = Collections.singletonList(shopId);
        }
        if( Objects.requireNonNull(shopIdList).isEmpty() ){
            helper.printError("Please shopId is not null");
        }
        shopIdList.forEach(x->{
            esThreadConfig.doIndexByShopId(x);
        });
    }
    @ShellMethod("ElasticSearch Index . --all[false|true]default true --shopId<shopId>")
    public void ess() {
        for (int i = 0; i < 1000; i++) {
            log.info("\nsend--->【{}】",i);
            saas.getShopApp(674625).esGoodsService.addEsGoodsIndex(Collections.singletonList(2),i);
        }
    }

    public static void main(String[] args) {
        EsTaskParam param = new EsTaskParam();
        param.setGoodsIdList(Collections.singletonList(2));
        param.setShopId(1);
        String jonStr = Util.toJson(param);

        System.out.println(jonStr);
        EsTaskParam p = Util.parseJson(jonStr,EsTaskParam.class);
    }
    private Availability argsAvailability(){
        if( helper.checkAuthorities(Collections.singletonList("admin")) ){
            return Availability.unavailable("admin command is only for an admin users !");
        }
        return Availability.available();
    }
}
