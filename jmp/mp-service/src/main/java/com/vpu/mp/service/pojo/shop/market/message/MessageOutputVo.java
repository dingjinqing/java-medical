package com.vpu.mp.service.pojo.shop.market.message;

import lombok.Data;

import java.sql.Timestamp;
/**
 * send record Vo
 * @author 卢光耀
 * @date 2019-09-09 15:09
 *
*/
@Data
public class MessageOutputVo {

    private String userName;

    private Byte templatePlatfrom;

    private Byte sendStatus;

    private Byte isVisit;

    private Timestamp visitTime;
}
