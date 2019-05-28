package com.vpu.mp.service.shop.image;

import com.vpu.mp.service.foundation.BaseService;
import static com.vpu.mp.db.shop.tables.UploadedImage.UPLOADED_IMAGE;

import java.util.List;

import org.jooq.impl.DSL;
import org.jooq.types.UInteger;;

public class ImageService extends BaseService {

	public int removeRow(Integer imageId) {
		Byte delFlag = 1;
		return db().update(UPLOADED_IMAGE)
				.set(UPLOADED_IMAGE.DEL_FLAG, delFlag)
				.where(UPLOADED_IMAGE.IMG_ID.eq(DSL.cast(imageId, UInteger.class)))
				.execute();
	}
	
	public int removeRow(List<UInteger> imageId) {
		Byte delFlag = 1;
		return db().update(UPLOADED_IMAGE)
				.set(UPLOADED_IMAGE.DEL_FLAG, delFlag)
				.where(UPLOADED_IMAGE.IMG_ID.eq(DSL.cast(imageId, UInteger.class)))
				.execute();
	}
	
//
//    /**
//     * 删除多行
//     *
//     * @param array $ids
//     * @return int
//     */
//    public function removeRows(array $ids)
//    {
//        return $this->getTableBuilder()->whereIn($this->keyIdName, $ids)->update(['del_flag' => 1]);
//    }
}
