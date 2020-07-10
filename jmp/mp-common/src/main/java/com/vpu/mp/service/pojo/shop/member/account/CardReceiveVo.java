package com.vpu.mp.service.pojo.shop.member.account;
import lombok.Data;
@Data
public class CardReceiveVo {
	private Boolean isMostGrade;
	private String cardNo;
	private String isContinue;
	private GradeCardData gradeCard;
}
