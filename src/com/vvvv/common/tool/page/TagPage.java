package com.vvvv.common.tool.page;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 自定义标签
 * @author arron.huang
 *
 */
public class TagPage extends TagSupport {
	/**
	 * 
	 */
	private Page2 page;
	
	private static final long serialVersionUID = 2362515588307245961L;
	public int doStartTag() throws JspException {
		 StringBuffer sbf = new StringBuffer();
		 StringBuffer sbf2 = new StringBuffer();
		 int curPage; //当前页
		 int pageSize; //页大小
		 int countPage; //总页数
		 String actions = null; //跳转方法
		//当前页如果小于或者等于1的时候。首页和上一页不能用
		curPage = page.getCurPage();
		pageSize = page.getPageSize();
		countPage = page.getCountPage();
		
		//如果action为空的时候,得到Page中的参数
		if(actions == null || "".equals(actions)){
			actions = page.getMethod();
		}
		sbf.append("<span style='font-size: 12px;'>");/*
		sbf2.append("总记录条数:").append(page.getCountNumber());
		sbf2.append(" &nbsp;总页数是:").append(countPage==0?1:countPage);
		sbf2.append("&nbsp;当前页:").append(curPage+1).append("&nbsp;&nbsp;");*/
		sbf2.append((curPage+1)+ "/" + countPage);
		countPage = countPage - 1;
		if(curPage < countPage){
			sbf.append("<a href='"+actions+"&amp;curPage="+(curPage+1)+"&amp;pageSize="+pageSize+"&amp;countPage="+countPage+"'>下一页</a>&nbsp;<a href='"+actions+"&amp;curPage="+countPage+"&amp;pageSize="+countPage+"&amp;countPage="+countPage+"'>尾页</a>&nbsp;");
		}
		if(curPage > 0 ){
			sbf.append("<a href='"+actions+"&amp;curPage=0&amp;pageSize="+pageSize+"&amp;countPage="+countPage+"'>首页</a>&nbsp;<a href='"+actions+"&amp;curPage="+(curPage-1)+"&amp;pageSize="+pageSize+"&amp;countPage="+countPage+"'>上一页</a>&nbsp;");
		}
		if(curPage < countPage || curPage > 0 ){
			sbf.append(sbf2);
		}
		sbf.append("<input type='text' id='pageSize' value='10' style='width:30px;height:16'>>&nbsp;<input type='button' onclick='location.href='"
				+actions+"&amp;curPage="+(curPage+1)+"&amp;countPage="+countPage+"&amp;pageSize=document.getElementById(&quot;pageSize&quot;).value'>");
		sbf.append("</span>");
		JspWriter out = pageContext.getOut();
		try {
			out.println(sbf.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_PAGE;
	}
	
	public Page2 getPage() {
		return page;
	}
	public void setPage(Page2 page) {
		this.page = page;
	}
}
