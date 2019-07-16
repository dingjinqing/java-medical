package com.vpu.mp.service.pojo.shop.summary.visit;

import com.vpu.mp.db.shop.tables.records.MpVisitPageRecord;
import com.vpu.mp.service.foundation.JsonResultMessage;
import lombok.Data;
import org.jooq.SortField;
import org.jooq.TableField;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import static com.vpu.mp.db.shop.tables.MpVisitPage.MP_VISIT_PAGE;

/**
 * 访问页面统计入参
 *
 * @author 郑保乐
 */
@Data
public class VisitPageParam {

    public static final int ASC = 1;
    public static final int DESC = 2;

    @NotBlank(message = JsonResultMessage.MSG_PARAM_ERROR)
    private String startDate;
    @NotBlank(message = JsonResultMessage.MSG_PARAM_ERROR)
    private String endDate;

    /**
     * 按第几个字段排序
     */
    @NotBlank(message = JsonResultMessage.MSG_PARAM_ERROR)
    @Min(1)
    @Max(7)
    private Integer action;

    /**
     * 排序方式（1: ASC 2: DESC）
     */
    @NotBlank(message = JsonResultMessage.MSG_PARAM_ERROR)
    @Min(1)
    @Max(2)
    private Integer asc;

    /**
     * action 和字段的对应关系
     */
    public enum Actions {

        PV(MP_VISIT_PAGE.PAGE_VISIT_PV),
        UV(MP_VISIT_PAGE.PAGE_VISIT_UV),
        STAY_TIME(MP_VISIT_PAGE.PAGE_STAYTIME_PV),
        ENTRY_PV(MP_VISIT_PAGE.ENTRYPAGE_PV),
        EXIT_PV(MP_VISIT_PAGE.EXITPAGE_PV),
        SHARE_PV(MP_VISIT_PAGE.PAGE_SHARE_PV),
        SHARE_UV(MP_VISIT_PAGE.PAGE_SHARE_UV);

        private TableField<MpVisitPageRecord, ?> field;

        Actions(TableField<MpVisitPageRecord, ?> field) {
            this.field = field;
        }

        public TableField<MpVisitPageRecord, ?> getField() {
            return field;
        }
    }

    /**
     * 取字段及排序方式
     */
    public SortField<?> getSortField() {
        Integer action = getAction();
        Integer asc = getAsc();
        Actions[] fields = Actions.values();
        int length = fields.length;
        if (0 > action || length < action) {
            throw new IllegalStateException("Unexpected action: " + action);
        }
        TableField<MpVisitPageRecord, ?> field = fields[action - 1].getField();
        switch (asc) {
            case ASC:
                return field.asc();
            case DESC:
                return field.desc();
            default:
                throw new IllegalStateException("Unexpected asc: " + asc);
        }
    }
}
