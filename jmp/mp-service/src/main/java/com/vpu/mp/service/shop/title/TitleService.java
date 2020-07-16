package com.vpu.mp.service.shop.title;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.dao.shop.title.TitleDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.title.TitleFetchOneParam;
import com.vpu.mp.service.pojo.shop.title.TitleListParam;
import com.vpu.mp.service.pojo.shop.title.TitleOneParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        titleDao.insertTitle(param);
        return param.getId();
    }

    public Integer updateTitle(TitleOneParam param) {
        int titleId = titleDao.updateTitle(param);
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

    public Integer getTitleByCode(String code) {
        return titleDao.getTitleByCode(code);
    }

    /**
     * 更新/新增职称
     * @param title
     */
    public void synchroTitle(TitleOneParam title) {
        if(getTitleByCode(title.getCode()) == null) {
            insertTitle(title);
        } else {
            title.setId(getTitleByCode(title.getCode()));
            updateTitle(title);
        }
    }

    public void fetchTitles(String json) {
        List<TitleFetchOneParam> titleFetchList = Util.parseJson(json, new TypeReference<List<TitleFetchOneParam>>() {
        });
        for (TitleFetchOneParam list : titleFetchList) {
            TitleOneParam title = new TitleOneParam();
            title.setName(list.getName());
            title.setCode(list.getPositionCode());
            if (list.getState() > 1) title.setIsDelete((byte) 1);
            synchroTitle(title);
        }
    }

    public Integer getTitleIdNew(String code) {
        Integer titleId = getTitleByCode(code);
        if(titleId == null) {
            TitleOneParam titleTemp = new TitleOneParam();
            titleTemp.setCode(code);
            insertTitle(titleTemp);
            return titleTemp.getId();
        } else {
            return titleId;
        }
    }
}
