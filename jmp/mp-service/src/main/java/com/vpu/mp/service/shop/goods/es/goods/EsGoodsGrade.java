package com.vpu.mp.service.shop.goods.es.goods;

import com.vpu.mp.service.foundation.es.annotation.EsFiled;
import com.vpu.mp.service.foundation.es.annotation.EsFiledTypeConstant;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;

import java.math.BigDecimal;


/**
 * ES 商品中不同会员等级中的各个规格对应的价格
 */
public class EsGoodsGrade {


    @EsFiled(name = EsSearchName.GRADE.PRD_ID,type = EsFiledTypeConstant.INTEGER)
    private Integer prdId;
    @EsFiled(name = EsSearchName.GRADE.GRADE_PRICE,type = EsFiledTypeConstant.SCALED_FLOAT)
    private BigDecimal gradePrice;
    @EsFiled(name = EsSearchName.GRADE.GRADE,type = EsFiledTypeConstant.KEYWORD)
    private String grade;

    public EsGoodsGrade(){}

    public Integer getPrdId() {
        return prdId;
    }

    public void setPrdId(Integer prdId) {
        this.prdId = prdId;
    }

    public BigDecimal getGradePrice() {
        return gradePrice;
    }

    public void setGradePrice(BigDecimal gradePrice) {
        this.gradePrice = gradePrice;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
