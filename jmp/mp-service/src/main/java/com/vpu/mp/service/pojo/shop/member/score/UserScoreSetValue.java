package com.vpu.mp.service.pojo.shop.member.score;

import java.util.Arrays;

/**
 * 用于生成Json存储
 * @author 黄壮壮
 * 2019-07-16 11:35
 */
public class UserScoreSetValue {
	Byte enable;
	String[] score;
	public UserScoreSetValue() {}
	public UserScoreSetValue(Byte enable,String[] score){
		this.enable = enable;
		this.score = score;
	}
	public Byte getEnable() {
		return enable;
	}
	public void setEnable(Byte enable) {
		this.enable = enable;
	}
	public String[] getScore() {
		return score;
	}
	public void setScore(String[] score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "UserScoreSetValue [enable=" + enable + ", score=" + Arrays.toString(score) + "]";
	}
}
