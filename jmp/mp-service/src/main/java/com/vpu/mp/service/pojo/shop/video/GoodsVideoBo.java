package com.vpu.mp.service.pojo.shop.video;


import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.UploadedVideo.UPLOADED_VIDEO;
public class GoodsVideoBo {

    private Integer id;
    private String url;
    private String imgUrl;
    private Integer size;
    private Integer width;
    private Integer height;

    public GoodsVideoBo(){}

    public GoodsVideoBo(Record record){
        this.id = record.get(UPLOADED_VIDEO.VIDEO_ID);
        this.size = record.get(GOODS.GOODS_VIDEO_SIZE);
        this.url = record.get(GOODS.GOODS_VIDEO);
        this.imgUrl = record.get(GOODS.GOODS_VIDEO_IMG);
        this.width = record.get(UPLOADED_VIDEO.VIDEO_WIDTH);
        this.height = record.get(UPLOADED_VIDEO.VIDEO_HEIGHT);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
