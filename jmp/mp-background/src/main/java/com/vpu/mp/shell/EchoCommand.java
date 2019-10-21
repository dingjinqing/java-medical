package com.vpu.mp.shell;

import com.vpu.mp.service.saas.SaasApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sshd.shell.springboot.autoconfiguration.SshdShellCommand;

@Component
@SshdShellCommand(value = "db", description = "DB Manager")
public class EchoCommand {

    private static final String ONLY_CHECK = "only_check";

    private static final String ALL = "all";

    private static final String SHOP_ID = "shop";

    @Autowired
    protected SaasApplication saas;

    @SshdShellCommand(value = "test", description = "1234")
    public String test( String arg ){
        return "Hello,word"+arg;
    }


    @SshdShellCommand(value = "repair main", description = "main DB --only_check ")
    public void repairMain( String arg ){
        boolean onlyCheck = false ;
        if( ONLY_CHECK.equals(arg) ){
            onlyCheck = true;
        }
        saas.repairDb.repairMainDb(onlyCheck);
    }
    @SshdShellCommand(value = "repair shop", description = "shop DB --only_check --all --shop [shopId]")
    public void repairShop( String args ){
        String[] params = args.split("--");
        boolean onlyCheck = false ;
        boolean all = false ;
        Integer shopId = null;
        for( String param: params  ){
            if( ONLY_CHECK.equals(param.trim()) ){
                onlyCheck = true;
            }
            if( ALL.equals(param) ){
                all = true;
            }
            if( param.contains(SHOP_ID) ){
                shopId = Integer.parseInt(param.split(" ")[1]);
            }
        }
        if( all ){
            saas.repairDb.repairAllShopDb(onlyCheck);
        }else if( shopId != null ){
            saas.repairDb.repairShopDb(onlyCheck,shopId);
        }
    }

}
