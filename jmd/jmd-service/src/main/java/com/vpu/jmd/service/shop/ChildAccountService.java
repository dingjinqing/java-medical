package com.vpu.jmd.service.shop;


import cn.hutool.core.collection.CollUtil;
import com.vpu.jmd.table.main.tables.records.SystemChildAccountRecord;
import com.vpu.jmd.service.base.MainBaseService;
import com.vpu.jmd.service.base.bo.PageResult;
import org.jooq.Record;
import org.jooq.SelectSeekStep1;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.vpu.jmd.table.main.Tables.SYSTEM_CHILD_ACCOUNT;
import static com.vpu.jmd.table.main.Tables.SYSTEM_ROLE;


/**
 *
 * @author 新国
 *
 */
@Service
public class ChildAccountService extends MainBaseService {

	public SystemChildAccountRecord verify(String username, String password) {
		SystemChildAccountRecord user = db().selectFrom(SYSTEM_CHILD_ACCOUNT)
				.where(SYSTEM_CHILD_ACCOUNT.ACCOUNT_NAME.eq(username)).or(SYSTEM_CHILD_ACCOUNT.MOBILE.eq(username))
				.fetchAny();
		if (user != null) {
			if (user.getAccountPwd().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
				return user;
			}
		}

		// db().selectFrom(SYSTEM_CHILD_ACCOUNT).limit(0, 20).fetch();
		return null;
	}

	public SystemChildAccountRecord checkByIdAndNameOnChild(Integer accountId, String username, Integer sysId) {
		SystemChildAccountRecord user = db().selectFrom(SYSTEM_CHILD_ACCOUNT)
				.where(SYSTEM_CHILD_ACCOUNT.ACCOUNT_NAME.eq(username))
				.and(SYSTEM_CHILD_ACCOUNT.ACCOUNT_ID.eq(accountId)).and(SYSTEM_CHILD_ACCOUNT.SYSTEM_USER_ID.eq(sysId))
				.fetchAny();
		if (user != null) {
			return user;
		}
		return null;
	}

	public SystemChildAccountRecord getUserFromAccountName(String username) {
		return db().selectFrom(SYSTEM_CHILD_ACCOUNT)
				.where(SYSTEM_CHILD_ACCOUNT.ACCOUNT_NAME.eq(username))
				.fetchAny();
	}
	public SystemChildAccountRecord getUserFromAccountId(Integer accountId) {
		return db().selectFrom(SYSTEM_CHILD_ACCOUNT)
				.where(SYSTEM_CHILD_ACCOUNT.ACCOUNT_ID.eq(accountId))
				.fetchAny();
	}

	public SystemChildAccountRecord getUserFromMobile(String mobile) {
		return db().selectFrom(SYSTEM_CHILD_ACCOUNT)
				.where(SYSTEM_CHILD_ACCOUNT.MOBILE.eq(mobile))
				.fetchAny();
	}

	public PageResult<SystemChildAccountVo> getPageList(Integer currentPage, Integer pageRows) {
		SelectSeekStep1<Record, Integer> select = db().select(SYSTEM_CHILD_ACCOUNT.fields())
				.select(SYSTEM_ROLE.ROLE_NAME).from(SYSTEM_CHILD_ACCOUNT).leftJoin(SYSTEM_ROLE)
				.on(SYSTEM_CHILD_ACCOUNT.ROLE_ID.eq(SYSTEM_ROLE.ROLE_ID))
				.orderBy(SYSTEM_CHILD_ACCOUNT.ACCOUNT_ID.desc());
		return this.getPageResult(select, currentPage, pageRows, SystemChildAccountVo.class);
	}

    public List<SystemChildAccountRecord> getByAccountIds(List<Integer> accountIds) {
        if (CollUtil.isEmpty(accountIds)) {
            return Collections.emptyList();
        }
        return db().selectFrom(SYSTEM_CHILD_ACCOUNT).where(SYSTEM_CHILD_ACCOUNT.ACCOUNT_ID.in(accountIds)).fetch();
    }

	public SystemManagerVo getInfoById(Integer accountId) {
		return db().select(SYSTEM_CHILD_ACCOUNT.ACCOUNT_ID, SYSTEM_CHILD_ACCOUNT.ACCOUNT_NAME)
				.from(SYSTEM_CHILD_ACCOUNT).where(SYSTEM_CHILD_ACCOUNT.ACCOUNT_ID.eq(accountId))
				.fetchAnyInto(SystemManagerVo.class);
	}

	public List<SystemManagerVo> getInfoByIds(List<String> accountIds) {
		return db().select(SYSTEM_CHILD_ACCOUNT.ACCOUNT_ID, SYSTEM_CHILD_ACCOUNT.ACCOUNT_NAME)
				.from(SYSTEM_CHILD_ACCOUNT).where(SYSTEM_CHILD_ACCOUNT.ACCOUNT_ID.in(accountIds))
				.fetchInto(SystemManagerVo.class);
	}

	public List<SystemManagerVo> getChildAccountList() {
		return db().select(SYSTEM_CHILD_ACCOUNT.ACCOUNT_ID, SYSTEM_CHILD_ACCOUNT.ACCOUNT_NAME)
				.from(SYSTEM_CHILD_ACCOUNT).fetchInto(SystemManagerVo.class);
	}

	public List<SystemManagerVo> setManager(String value) {
		List<SystemManagerVo> list = new ArrayList<SystemManagerVo>();
		if (null != value && !value.equals("")) {
			String[] split = value.split(",");
			list = getInfoByIds(Arrays.asList(split));
		}
		return list;
	}

}
