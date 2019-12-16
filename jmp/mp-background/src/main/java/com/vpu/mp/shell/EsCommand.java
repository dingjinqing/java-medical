package com.vpu.mp.shell;


import com.github.fonimus.ssh.shell.SshShellHelper;
import com.github.fonimus.ssh.shell.commands.SshShellComponent;
import com.vpu.mp.db.main.tables.records.ShopRecord;
import com.vpu.mp.service.saas.SaasApplication;
import com.vpu.mp.thread.es.EsThreadConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

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



    @ShellMethod("ElasticSearch goods Index . --all[false|true]default true --shop-id<shopId>")
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
    @ShellMethod("ElasticSearch lanbel Index . --all[false|true]default true --shopId<shopId>")
    public void esl(@ShellOption(arity = 1, defaultValue = "true") boolean all,@ShellOption( defaultValue = "0")int shopId) {
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
            esThreadConfig.doLabelIndexByShopId(x);
        });
    }


    private Availability argsAvailability(){
        if( helper.checkAuthorities(Collections.singletonList("admin")) ){
            return Availability.unavailable("admin command is only for an admin users !");
        }
        return Availability.available();
    }
}
