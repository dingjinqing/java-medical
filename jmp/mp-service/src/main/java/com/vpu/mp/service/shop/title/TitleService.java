package com.vpu.mp.service.shop.title;

import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.dao.shop.title.TitleDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.title.TitleListParam;
import com.vpu.mp.service.pojo.shop.title.TitleOneParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TitleService extends ShopBaseService{
    @Autowired
    protected TitleDao titleDao;
    public static final int ZERO = 0;

    public PageResult<TitleOneParam> getTitleList(TitleListParam param) {
        PageResult<TitleOneParam> titleList = titleDao.getTitleList(param);
        return titleList;
    }

    public boolean isNameExist(Integer titleId,String name) {
        boolean flag = titleDao.isNameExist(titleId,name);
        return flag;
    }

    public Integer insertTitle(TitleOneParam param) {
        transaction(() -> {
            int titleId = titleDao.insertTitle(param);
            param.setId(titleId);
        });
        return param.getId();
    }

    public Integer updateTitle(TitleOneParam param) {
        transaction(() -> {
            int titleId = titleDao.updateTitle(param);
        });
        return param.getId();
    }

    public TitleOneParam getOneInfo(Integer titleId){
        TitleOneParam titleInfo = titleDao.getOneInfo(titleId);
        return titleInfo;
    }

    public int deleteTitle(Integer titleId){
        int id = titleDao.deleteTitle(titleId);
        return id;
    }

    public List<TitleOneParam> listTitles(){
        List<TitleOneParam> titleList = titleDao.listTitles();
        return titleList;
    }
}
