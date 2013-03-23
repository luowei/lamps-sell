<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="commons/head.jsp" %>
<%@ include file="commons/js.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>企业库_灯具销售系统</title>
<jsp:include page="top.jsp"></jsp:include>
	<div id="main" class="s_clear">
   		
   		<div class="rows box01-wrap">
        	<div class="box-blue listbox">
            	<div class="title01">
                	<center><h1>${complany.name }</h1></center>
                </div>
                <div class="productbox s_clear">
                	<c:if test="${rComplany eq null }"><c:set var="complany" value="${complanyList[0]}"/></c:if>
                	<c:if test="${rComplany ne null }"><c:set var="complany" value="${rComplany }"/></c:if>
                	<div class="linkman box02-wrap">
                    	<div class="box-ashen">
                        	<div class="title01 title01-ashen"><center><h1>${complany.name }</h1></center></div>
                            <div class="content">
                            	<c:if test="${complany.contact ne null }">
	                            	<p>联系人：${complany.contact.name }</p>
	                                <p>电话：${complany.contact.tel }</p>
	                                <p>手机：${complany.contact.phone }</p>
	                                <p>传真：${complany.contact.fax }</p>
	                                <p>邮编：${complany.contact.zip }</p>
	                                <p>地址：${complany.contact.addr }</p>
                                </c:if>
                                <c:if test="${complany.contact eq null }">
                                	<p><b style="color:#E15F00;font-size: 16px;">此企业没有添加联系人信息</b></p>
                                </c:if>
                                <div class="btn">
                                    <table class="center"><tr><td>
                                    	<a href="${ctx }/head/product.php?complanyId=${complany.id}" target="_blank" class="icon-btn icon-com-btn"></a>
                                    </td></tr></table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="product">
                    	<div class="img">
                        	<a class="pd" href="${complany.img }" title="${complany.name }" target="_blank">
                        		<img src="${complany.img }" alt="${complany.name }" style="height: 180px;width: 180px"/></a>
                            <div><table class="center"><tr><td>
                            	<a class="view" href="${complany.img }" target="_blank">放大图片</a>
                            </td></tr></table></div>
                        </div>
                        <c:if test="${complany.shortName ne null ||complany.shortName ne '' }">
                        <p>简称： ${complany.shortName }</p></c:if>
                        <p>企业性质:${complany.type }</p>
						<p>经营模式:${complany.businessMode }</p>
                        <p>主营业务:${company.primaryBusiness }</p>
                        <p>企业地址:${complany.address }</p>
                        <p>成立日期: ${complany.createtime }</p>
                        <div class="btns">
                        	详细介绍：${complany.detail }	
                        </div>
                    </div>
                </div>
            </div>
        </div>
   		
   		
   		
   		<div class="rows box01-wrap">
        	<div class="box-blue list-table"">
        		<div class="title01">
                	<div class="tab01">
                    	<ul><li class="hover"><a>企业列表</a></li></ul>
                    </div>
                </div>
                <table class="table" cellpadding="0">
                	<tr >
                    	<th class="show">名称</th>
                        <th class="price">logo</th>
                        <th class="index">产品</th>
                        <th class="index">地址</th>
                    </tr>
                <c:set var="i" value="1" />
                <c:forEach items="${complanyList}" var="lComplany" varStatus="s">
                    <tr  <c:if test="${i%2 eq 0 }"> class="bluebg"</c:if> >
                    	<td class="show" style="width: 150px;">
                        	<div id="bj_cp">
                        		<a href="${ctx }/head/complany.php?id=${lComplany.id }">${lComplany.name}</a>
                            </div>
                        </td>
                        <td class="price">
                        <a href="${ctx }/head/product.php?complanyId=${lComplany.id}" target="_blank"">
                        	<img src="${lComplany.logo }" width="100px" height="100px"/></a>
                        </td>
                        <td class="index">
                        <c:if test="${lComplany.shortName ne null && lComplany.shortName ne ''}">
                        	<b style="color:#E15F00;"><a href="${ctx }/head/product.php?complanyId=${lComplany.id }">${lComplany.shortName}的产品</a></b>
                        </c:if>
                        <c:if test="${lComplany.shortName eq null || lComplany.shortName eq ''}">
                        	<b style="color:#E15F00;"><a href="${ctx }/head/product.php?complanyId=${lComplany.id }">产品</a></b>
                        </c:if>
                        </td>
                        <td class="index">${lComplany.address }</td>
                    </tr>
                    <c:set var="i" value="${i+1}" />
                 </c:forEach>
                </table>
                <div class="pages">
					<table class="center"><tr><td>
					${frontTag }
		            </td></tr></table>
                </div>
            </div>
        </div>
   		
    </div>
<jsp:include page="foot.jsp"></jsp:include></body>
</html>
