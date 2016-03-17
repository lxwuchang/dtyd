/**
 * 
 */
package com.yundastat.service;

import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author hp
 *
 */
public class BaseService {
	
	/**
	 * ����ģ��
	 * <PRE>
	 * ʹ��ʾ��1��
	 * transactionTemplate.execute(new TransactionCallback() {
	 *		public Object doInTransaction(TransactionStatus status) {
	 *			if (Ҫ�ع�) {
	 *				throw new RuntimeException or its subexception;
	 *			}
	 *			
	 *			return result;
	 *		}
	 *	});
	 *
	 * ʹ��ʾ��2��
	 * transactionTemplate.execute(new TransactionCallback() {
	 *		public Object doInTransaction(TransactionStatus status) {
	 *			if (Ҫ�ع�) {
	 *				status.setRollbackOnly();
	 *				return null;
	 *			}
	 *			
	 *			return result;
	 *		}
	 *	});
	 * </PRE>
	 */
	protected TransactionTemplate transactionTemplate;
	
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	
}
