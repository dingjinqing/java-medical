package com.vpu.mp.service.foundation;

import org.jooq.ExecuteContext;
import org.jooq.impl.DefaultExecuteListener;
/**
 * 
 * @author 新国
 *
 */
public class ExceptionTranslator extends DefaultExecuteListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void exception(ExecuteContext context) {
        System.out.println("Access database using jOOQ Exception:"+ context.sql()+", Message:"+context.sqlException().getMessage());
    }
}
