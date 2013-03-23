package com.vvvv.common.tool.page;

import java.util.ArrayList;
import java.util.List;

/**
 * @className:PageUtil.java
 * @classDescription:实现分页接口
 * @author:xiayingjie
 * @createTime:2010-7-12
 */
public class PageUtil{

	/**
	 * 获取分页显示标签
	 * 
	 * @param page
	 *            page对象
	 * @return
	 */
	public static String getTag(Page page) {
		return getTagByGoogle(page);
//		StringBuffer tag=new StringBuffer();
//		
//		tag.append("  <a href='" + page.getForwordName()
//				+ 1 + "'>首页</a>&nbsp;");
//		tag.append("  <a href='" + page.getForwordName()
//				+ page.getPrePage() + "'>上页</a>&nbsp;");
//		tag.append("  <a href='" + page.getForwordName()
//				+ page.getNextPage() + "'>下页</a>&nbsp;");
//		tag.append("  <a href='" + page.getForwordName()
//				+ page.getTotalPages() + "'>尾页</a>&nbsp;");
//		
//
//		tag.append("<br/>共" + page.getTotalCount() + "条  每页" + page.getPageSize() + "条  总共"
//				+ page.getTotalPages() + "页 " + page.getPageNo() + "/" + page.getTotalPages() + "  ");
//		
//		return tag.toString();

	}
	/**
	 * 获取分页显示标签
	 * 
	 * @param page
	 *            page对象
	 * @return
	 */
	public static String getTagByGoogle(Page page) {
		StringBuffer tag=new StringBuffer();
		tag.append("  <a href='" + page.getForwordName()+ 1 + "&pageSize="+page.getPageSize()+"'>首页</a>&nbsp;");
		tag.append("  <a href='" + page.getForwordName()+ page.getPrePage() + "&pageSize="+page.getPageSize()+"'>上页</a>&nbsp;");
		
		//-----------
		int start=(page.getPageNo()-1);
		
		if(page.getPageNo()>page.getLeftCount()){
			start=page.getLeftCount();
		}	
		for(int i=start;i>0;i--){
			tag.append("  <a href='" + (page.getForwordName()+(page.getPageNo()-i)) + "&pageSize="+page.getPageSize()+"'>"+(page.getPageNo()-i)+"</a>&nbsp;");
		}
		
		tag.append("  "+page.getPageNo()+"&nbsp;");
		
		//------------
		int end=(int)page.getTotalPages()-page.getPageNo();
		if(end>page.getRigthCount()){
			end=page.getRigthCount();
		}
		for(int i=1;i<=end;i++){
			tag.append("  <a href='" + (page.getForwordName()+(page.getPageNo()+i)) + "&pageSize="+page.getPageSize()+"'>"+(page.getPageNo()+i)+"</a>&nbsp;");
		}
		
		tag.append("  <a href='" + page.getForwordName()+ page.getNextPage() + "&pageSize="+page.getPageSize()+"'>下页</a>&nbsp;");
		tag.append("  <a href='" + page.getForwordName()+ page.getTotalPages() + "&pageSize="+page.getPageSize()+"'>尾页</a>&nbsp;");
		tag.append("跳到第<input type='text' id='pageTo' value='"+page.getPageNo()+"' style='width:30px;height:16'>页");
		tag.append("<input type='button' onclick='location.href=&apos;"
				+page.getForwordName()+"&apos;+document.getElementById(&apos;pageTo&apos;).value+&apos;&amp;curPage="+(page.getPageNo()+1)+"&amp;countPage="
				+page.getTotalCount()+"&amp;pageSize="+page.getPageSize()+"&apos;' value='Go'>&nbsp;");
		tag.append("<br/>每页显示<input type='text' id='pageSize' value='"+page.getPageSize()+"' style='width:30px;height:16'>条");
		tag.append("<input type='button' onclick='location.href=&apos;"
				+page.getForwordName()+page.getPageNo()+"&amp;curPage="+(page.getPageNo()+1)+"&amp;countPage="
				+page.getTotalCount()+"&amp;pageSize=&apos;+document.getElementById(&apos;pageSize&apos;).value+&apos;&apos;' value='确定'>&nbsp;");
		tag.append("共" + page.getTotalCount() + "条  每页" + page.getPageSize() + "条  总共"
				+ page.getTotalPages() + "页 " + page.getPageNo() + "/" + page.getTotalPages() + "  ");
		return tag.toString();

	}
	
	/**
	 * 前台分页控件
	 * @author wei.luo
	 * @createTime 2012-3-30
	 * @param page
	 * @return
	 */
	public static String getFrontTag(Page page){
		StringBuffer tag=new StringBuffer();
		tag.append("  <a  class='nobg' href='" + page.getForwordName()+ 1 + "&pageSize="+page.getPageSize()+"'>首页</a>");
		tag.append("  <a  class='nobg' href='" + page.getForwordName()+ page.getPrePage() + "&pageSize="+page.getPageSize()+"'>上页</a>");
		
		//-----------
		int start=(page.getPageNo()-1);
		
		if(page.getPageNo()>page.getLeftCount()){
			start=page.getLeftCount();
		}	
		for(int i=start;i>0;i--){
			tag.append("  <a class='nobg' href='" + (page.getForwordName()+(page.getPageNo()-i)) + "&pageSize="+page.getPageSize()+"'>"+(page.getPageNo()-i)+"</a>&nbsp;");
		}
		
		tag.append("  <a  class='hover' href='#'> "+page.getPageNo()+"</a>");
		
		//------------
		int end=(int)page.getTotalPages()-page.getPageNo();
		if(end>page.getRigthCount()){
			end=page.getRigthCount();
		}
		for(int i=1;i<=end;i++){
			tag.append("  <a class='nobg' href='" + (page.getForwordName()+(page.getPageNo()+i)) + "&pageSize="+page.getPageSize()+"'>"+(page.getPageNo()+i)+"</a>&nbsp;");
		}
		
		tag.append("  <a class='nobg' href='" + page.getForwordName()+ page.getNextPage() + "&pageSize="+page.getPageSize()+"'>下页</a>&nbsp;");
		tag.append("  <a class='nobg' href='" + page.getForwordName()+ page.getTotalPages() + "&pageSize="+page.getPageSize()+"'>尾页</a>&nbsp;");
//		tag.append("跳到第<input type='text' id='pageTo' value='"+page.getPageNo()+"' style='width:30px;height:16'>页");
//		tag.append("<input type='button' onclick='location.href=&apos;"
//				+page.getForwordName()+"&apos;+document.getElementById(&apos;pageTo&apos;).value+&apos;&amp;curPage="+(page.getPageNo()+1)+"&amp;countPage="
//				+page.getTotalCount()+"&amp;pageSize="+page.getPageSize()+"&apos;' value='Go'>&nbsp;");
//		tag.append("<br/>每页显示<input type='text' id='pageSize' value='"+page.getPageSize()+"' style='width:30px;height:16'>条");
//		tag.append("<input type='button' onclick='location.href=&apos;"
//				+page.getForwordName()+page.getPageNo()+"&amp;curPage="+(page.getPageNo()+1)+"&amp;countPage="
//				+page.getTotalCount()+"&amp;pageSize=&apos;+document.getElementById(&apos;pageSize&apos;).value+&apos;&apos;' value='确定'>&nbsp;");
//		tag.append("共" + page.getTotalCount() + "条  每页" + page.getPageSize() + "条  总共"
//				+ page.getTotalPages() + "页 " + page.getPageNo() + "/" + page.getTotalPages() + "  ");
		return tag.toString();
	}
	
	/**
	 * 获取跳转的条件
	 * @param map
	 * @return
	 */
	public static  String getForwardCondition(List<Condition> list){
		StringBuffer fragment=new StringBuffer(getCondition(list,null));

		if("".equals(fragment.toString())){
			fragment.append("?pageNo=");
		}else{
			fragment.append("&pageNo=");
		}
		return fragment.toString();
	}
	/**
	 * 获取排序URL
	 * @param map
	 * @return
	 */
	public static  String getOrderCondition(List<Condition> list){
		
		StringBuffer fragment=new StringBuffer(getCondition(list,"order"));

		if("".equals(fragment.toString())){
			fragment.append("?order=");
		}else{
			fragment.append("&order=");
		}
		return fragment.toString();
	}
	/**
	 * 获取最基本的条件URL
	 * @param list
	 * @return
	 */
	public static String getCondition(List<Condition> list,String removeName){
		boolean flag=true;
		StringBuffer fragment=new StringBuffer("");
		for(Condition condition:list){
			if(!condition.getName().equals(removeName)){
				if(null!=condition.getValue()&&!"".equals(condition.getValue())){
					if(flag){
						fragment.append("?");
						flag=false;
					}else{
						fragment.append("&");
					}
					fragment.append(condition.getName()+"="+condition.getValue());
				}
			}
		}
		return fragment.toString();
	}
	/**
	 * 获取过滤条件查询
	 * @param list
	 * @return
	 */
	public static List<Condition> getFilterConditions(List<Condition> list){
		List<Condition> fragmentList=new ArrayList();
		for(Condition condition:list){
			if(!"".equals(condition.getValue())&&null!=condition.getValue()){
				//暂时屏蔽排序显示
				if(condition.isFlag()){
					condition.setUrl(getCondition(list,condition.getName()));
					fragmentList.add(condition);
				}
			}
		}
		return fragmentList;
		
	}
//
//	/**
//	 * 获取分页显示标签
//	 * 
//	 * @param page
//	 *            page对象
//	 * @return
//	 */
//	public static String[] getWMLTag(Page page) {
//		// 总页数
//		int allCount = page.getAllCount();
//		// 显示的条数
//		int count = page.getCount();
//		// 当前的页数
//		int currentPage = page.getPage();
//		// 左边显示的条数
//		int leftCount = page.getLeftCount();
//		// 右边显示的条数
//		int rightCount = page.getRigthCount();
//		// 如果显示为0的话则默认为三条
//		if (leftCount == 0 || rightCount == 0) {
//			leftCount = 3;
//			rightCount = 3;
//		}
//		// 总页数
//		int allPage;
//		if (allCount % count == 0) {
//			allPage = allCount / count;
//		} else {
//			allPage = allCount / count + 1;
//		}
//
//		String arg1 = "";
//		// 如果当前页小于首页，将当前页设为首页
//		StringBuffer tag = new StringBuffer();
//		if (currentPage < 1) {
//			currentPage = 1;
//		}
//		// 如果中当前页大约末页，则将当前页设置为末页
//		if (currentPage > allPage) {
//			currentPage = allPage;
//		}
//		if (currentPage < allPage) {
//			tag.append("  <a href='" + page.getForwordName()
//					+ (currentPage + 1) + "'>下页</a>&nbsp;");
//		} 
//		if (currentPage > 1) {
//			tag.append("<a href='" + page.getForwordName() + (currentPage - 1)
//					+ "'>上页</a>&nbsp;<br/>");
//		}
//
//		// 显示条数
//		String[] args = new String[5];
//		args[0] = "共找到" + allCount + "条结果  ";
//		args[1] = tag.toString();
//		args[2]="第" + currentPage + "页 " + "共"+allPage+"页";
//		args[3]=String.valueOf((currentPage-1)*count);
//		
//		return args;
//	}
//
//	public static String getTagByChoose(Page page) {
//		// 总页数
//		int allCount = page.getAllCount();
//		// 显示的条数
//		int count = page.getCount();
//		// 当前的页数
//		int currentPage = page.getPage();
//		// 左边显示的条数
//		int leftCount = page.getLeftCount();
//		// 右边显示的条数
//		int rightCount = page.getRigthCount();
//		// 如果显示为0的话则默认为三条
//		if (leftCount == 0 || rightCount == 0) {
//			leftCount = 3;
//			rightCount = 3;
//		}
//
//		// 总页数
//		int allPage;
//		if (allCount % count == 0) {
//			allPage = allCount / count;
//		} else {
//			allPage = allCount / count + 1;
//		}
//		// 如果当前页小于首页，将当前页设为首页
//		StringBuffer tag = new StringBuffer();
//		if (currentPage < 1) {
//			currentPage = 1;
//		}
//		// 如果中当前页大约末页，则将当前页设置为末页
//		if (currentPage > allPage) {
//			currentPage = allPage;
//		}
//		tag.append("共" + allCount + "条 每页" + page.getCount() + "条  总共"
//				+ allPage + "页 " + currentPage + "/" + allPage + "  ");
//		if (allPage > 1) {
//			tag.append("<a href='" + page.getForwordName() + 1
//					+ "'>首页</a>&nbsp;");
//			tag.append("<a href='" + page.getForwordName() + (currentPage - 1)
//					+ "'>上页</a>&nbsp;");
//
//			if (currentPage != allPage) {
//				tag.append("  <a href='" + page.getForwordName()
//						+ (currentPage + 1) + "'>下页</a>&nbsp;");
//			} else {
//				tag.append("<a href='" + page.getForwordName() + currentPage
//						+ "'>下页</a>&nbsp;");
//			}
//
//			tag.append("<a href='" + page.getForwordName() + allPage
//					+ "'>末页</a>&nbsp;");
//			// tag.append("<input name=\"pageCountName\" type=\"text\"
//			// size=\"5\" />");
//			// tag.append("<a href=\"javascript:var
//			// count=document.getElementById('pageCountName').value;location.href='"+page.getForwordName()+"'+count+'&flag=1';\">跳转</a>");
//
//			// tag.append("<form name=\"dispatchForm\" method=\"post\" action=\"
//			// \">");
//			// tag.append("<input name=\"pageCountName\" type=\"text\"
//			// size=\"5\" />");
//			// tag.append("<a href=\"javascript:var
//			// count=document.getElementById('pageCountName').value;document.dispatchForm.action='"+page.getForwordName()+"'+count;document.dispatchForm.submit();\">跳转</a>");
//			// tag.append("</form>");
//			// tag.append("<a
//			// href=\"javascript:MM_jumpMenu('"+page.getForwordName()+"');\">跳转</a>");
//			// tag.append("<script language=\"javascript\">");
//			// tag.append(" function MM_jumpMenu(count){ ");
//			// tag.append(" var
//			// i=document.getElementById('pageCountName').value; var
//			// url=count+i;");
//			// tag.append(" window.location.href=url;");
//			// tag.append(" }");
//			// tag.append("</script>");
//		}
//		return tag.toString();
//	}
}
