package com.vpu.mp.service.pojo.shop.member;

import java.util.Arrays;

/**
 * 用于生成Json存储
 * @author 黄壮壮
 * 2019-07-16 11:35
 */
public class UserScoreSetValue {
	String enable;
	String[] score;
	public UserScoreSetValue() {}
	public UserScoreSetValue(String enable,String[] score){
		this.enable = enable;
		this.score = score;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
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
