package com.vvvv.common.tool.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @className:Page2.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-8下午04:32:15 
 */
public class Page2 {

	private int curPage = 0; // 当前页 默认为第一页
	@SuppressWarnings("unused")
	private int countPage; // 总页数
	private int pageSize = 20; // 页大小,默认每页大小
	private int countNumber; // 总记录数
	private String method; //请求的方法
	private List<?> list;

	public void setCountNumber(int countNumber) {
		this.countNumber = countNumber;
	}
	public int getCountNumber() {
		return countNumber;
	}
	public Page2(String method) {
		this.method = method;
		
	}
	protected void page() {
	}
	public  Page2(HttpServletRequest request,String method) {
		this.method = method;
		String cpage = request.getParameter("curPage");
		String totalPage = request.getParameter("countPage");
		//判断是否得到值
		if(cpage != null && !"".equals(cpage)){
			curPage = Integer.parseInt(cpage);
		}
		if(totalPage != null && !"".equals(totalPage)){
			countPage = Integer.parseInt(totalPage);
		}
	}
	
	public Page2() {
	}
	public void setMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	//计算出总页数
	public int getCountPage() {

		return 	countNumber % pageSize > 0 ? countNumber / pageSize + 1: countNumber /pageSize;

	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public List<?> getList() {
		return list;
	}
	
	public static void main(String[] args) {
		System.out.println(0 % 2 > 0 ? 0 / 2 : 0 / 2 + 1);
	}
}
