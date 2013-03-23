<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="commons/head.jsp" %>
<%@ include file="commons/js.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>类别-灯具销售系统</title>
<jsp:include page="top.jsp"></jsp:include>
	<div id="main" class="s_clear">
		<div class="rows box01-wrap">
        	<div class="box-blue list-table"">
        		<div class="title01">
                	<div class="tab01">
                    	<ul><li class="hover"><a>灯具类别</a></li></ul>
                    </div>
                </div>
                <div class="sortbox">
                   	<c:forEach  items="${pCategoryList}" var="pCategory" varStatus="s">
                   	<div class="row">
                           <h1><a href="${ctx }/head/product.php?categoryId=${pCategory.id }" target=_blank>${pCategory.name }</a></h1>
                           <div class="sort">
                           <c:forEach items="${subCategoryList}" var="subCategory" varStatus="s">
			 				<c:if test="${subCategory.parentId eq pCategory.id && subCategory.id > 1 }">
      						  <a href="${ctx }/head/product.php?categoryId=${subCategory.id }" target=_blank>${subCategory.name }</a>
      						  <span class="line"></span>
      						</c:if></c:forEach>
						</div><br/>
                   </div></c:forEach>
              </div>
            </div>
        </div>
	</div>
	
<jsp:include page="foot.jsp"></jsp:include></body>
</html>