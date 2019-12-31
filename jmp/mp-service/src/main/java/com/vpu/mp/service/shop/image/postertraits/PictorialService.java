package com.vpu.mp.service.shop.image.postertraits;

import com.vpu.mp.db.shop.tables.records.PictorialRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.Tables.PICTORIAL;

/**
 *
 * @author zhaojianqiang
 * 2019年10月17日 下午5:19:04
 */
@Service
public class PictorialService extends ShopBaseService {

    /**
     * 根据过了条件查询指定的记录
     * @param identityId 画报关联的实体ID，如goodsId
     * @param action  画报类型，{@link com.vpu.mp.service.pojo.wxapp.share.PictorialConstant}
     * @param userId 用户Id
     * @return 画报详情
     */
    public PictorialRecord getPictorialDao(Integer identityId, Byte action, Integer userId) {
        return db().selectFrom(PICTORIAL).where(PICTORIAL.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(PICTORIAL.USER_ID.eq(userId)).and(PICTORIAL.IDENTITY_ID.eq(identityId)).and(PICTORIAL.ACTION.eq(action))
            .fetchAny();
    }

    /**
     * 添加记录
     * @param record 画报记录
     */
    public void addPictorialDao(PictorialRecord record) {
        db().executeInsert(record);
    }

    /**
     * 修改记录
     * @param record 画报记录
     */
    public void updatePictorilaDao(PictorialRecord record) {
        db().executeUpdate(record);
    }
}
