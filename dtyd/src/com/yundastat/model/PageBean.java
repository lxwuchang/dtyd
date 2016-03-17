/**
 * 
 */
package com.yundastat.model;

/**
 * @author Administrator
 *
 */
public class PageBean {
	private int totalRows=0;
	private int pageSize=0;
	private int totalPages=0;
	private int currentPage=-1;
	private int startNum = 0;
	private int nextPage=0;
	private int previousPage=0;
	private boolean hasNextPage=false;
	private boolean hasPrePage=false;
	
	public PageBean(){}
	public PageBean(int pageSize, int currentPage, int totalRows) { 

		this.pageSize = pageSize; 
		this.currentPage = currentPage; 
		this.totalRows = totalRows; 

		if ((totalRows % pageSize) == 0) { 
		totalPages = totalRows / pageSize; 
		} else { 
		totalPages = totalRows / pageSize + 1; 
		} 

		if (currentPage >= totalPages) { 
		hasNextPage = false; 
		currentPage = totalPages; 
		} else { 
		hasNextPage = true; 
		} 

		if (currentPage <= 1) { 
			hasPrePage = false; 
		currentPage = 1; 
		} else { 
			hasPrePage = true; 
		} 

		startNum = (currentPage - 1) * pageSize; 

		nextPage = currentPage + 1; 

		if (nextPage >= totalPages) { 
		nextPage = totalPages; 
		} 

		previousPage = currentPage - 1; 

		if (previousPage <= 1) { 
		previousPage = 1; 
		} 

		} 
	public boolean isHasPreviousPage() { 

		return hasPrePage; 

		} 
	
	public boolean isHasNextPage() { 

		return hasNextPage; 

		} 

		/** 
		* @return the nextPage 
		*/ 
		public int getNextPage() { 
		return nextPage; 
		} 

		/** 
		* @param nextPage 
		*            the nextPage to set 
		*/ 
		public void setNextPage(int nextPage) { 
		this.nextPage = nextPage; 
		} 

		/** 
		* @return the previousPage 
		*/ 
		public int getPreviousPage() { 
		return previousPage; 
		} 

		/** 
		* @param previousPage 
		*            the previousPage to set 
		*/ 
		public void setPreviousPage(int previousPage) { 
		this.previousPage = previousPage; 
		} 

		/** 
		* @return the currentPage 
		*/ 
		public int getCurrentPage() { 
		return currentPage; 
		} 

		/** 
		* @param currentPage 
		*            the currentPage to set 
		*/ 
		public void setCurrentPage(int currentPage) { 
		this.currentPage = currentPage; 
		} 

		/** 
		* @return the pageSize 
		*/ 
		public int getPageSize() { 
		return pageSize; 
		} 

		/** 
		* @param pageSize 
		*            the pageSize to set 
		*/ 
		public void setPageSize(int pageSize) { 
		this.pageSize = pageSize; 
		} 

		/** 
		* @return the totalPages 
		*/ 
		public int getTotalPages() { 
		return totalPages; 
		} 

		/** 
		* @param totalPages 
		*            the totalPages to set 
		*/ 
		public void setTotalPages(int totalPages) { 
		this.totalPages = totalPages; 
		} 

		/** 
		* @return the totalRows 
		*/ 
		public int getTotalRows() { 
		return totalRows; 
		} 

		/** 
		* @param totalRows 
		*            the totalRows to set 
		*/ 
		public void setTotalRows(int totalRows) { 
		this.totalRows = totalRows; 
		} 

		/** 
		* @param hasNextPage 
		*            the hasNextPage to set 
		*/ 
		public void setHasNextPage(boolean hasNextPage) { 
		this.hasNextPage = hasNextPage; 
		} 

		/** 
		* @param hasPreviousPage 
		*            the hasPreviousPage to set 
		*/ 
		public void setHasPreviousPage(boolean hasPreviousPage) { 
		this.hasPrePage = hasPreviousPage; 
		} 

		/** 
		* @return the startNum 
		*/ 
		public int getStartNum() { 
		return startNum; 
		} 

		/** 
		* @param startNum 
		*            the startNum to set 
		*/ 
		public void setStartNum(int startNum) { 
		this.startNum = startNum; 
		} 
		

}
