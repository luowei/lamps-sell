<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="commons/head.jsp" %>
<%@ include file="commons/js.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="x-ua-compatible" content="ie=7"/>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>灯具销售系统</title>
<script src="${ctx }/js/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="${ctx }/js/scroll.js" type="text/javascript"></script>
<jsp:include page="top.jsp"></jsp:include>
	<div id="main">
        <div class="rows s_clear">
        	<div class="cols-left01">
                <div class="rows box01-wrap">
                	<div class="box-blue listbox">
                    	<div class="title01"><div class="title01-wrap title">
                        	<h1>产品分类</h1>
                        </div></div>
                        <div class="sortbox">
                        	<c:forEach  items="${pCategoryList}" var="pCategory" varStatus="s">
                        	<div class="row">
                                <h1><a href="${ctx }/head/product.php?categoryId=${pCategory.id }" target=_blank>${pCategory.name }</a></h1>
                                <div class="sort">
                                <c:forEach items="${subCategoryList}" var="subCategory" varStatus="s">
								 <c:if test="${subCategory.parentId eq pCategory.id && subCategory.id > 1 }">
           						  <a href="${ctx }/head/product.php?categoryId=${subCategory.id }" target=_blank>${subCategory.name }</a><span class="line"></span>
           						 </c:if></c:forEach>
								</div>
                            </div></c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <div class="cols-right01">
            	<div class="rows s_clear">
                	<div class="cols-left02">
                    	<div class="box01-wrap rows">
                            <div class="box-blue listbox">
                                <div class="title01">
                                    <div class="tab01">
                                        <ul>
                                            <li class="hover"><a>产品报价</a></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="tab02">
<%--                                	<a class="right" href="${ctx }/price.php" target="_blank">更多价格</a>--%>
                                	<ul>
                               	    <li id="five1" class="hover" onmouseover="setTab('five',1,4)">室内灯具价格</li>
                                        <li id="five2" onmouseover="setTab('five',2,4)">户外灯具价格</li>
                                        <li id="five3" onmouseover="setTab('five',3,4)">灯具配件价格</li>
                                        <li id="five4" onmouseover="setTab('five',4,4)">电光源类价格</li>
                                    </ul>
                                </div>
                                <div id="con_five_1" class="putbox">
                                	<div class="hor-list">
                                    	<ul class="lister01 s_clear">
                                    	<c:forEach  items="${roomProductList}" var="rProduct" varStatus="s" begin="0" step="1" end="13">
                                        	<li><div><a href="${ctx }/head/product_detail.php?id=${rProduct.id }" target="_blank">${rProduct.name }
                                        	<span class="orange">${rProduct.price }￥/台</span></a></div></li>
                                        </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <div id="con_five_2" class="putbox" style="display:none">
                                	<div class="hor-list">
                                    	<ul class="lister01 s_clear">
                                    	<c:forEach  items="${outdoorsProductList}" var="oProduct" varStatus="s" begin="0" step="1" end="13">
                                        	<li><div><a href="${ctx }/head/product_detail.php?id=${oProduct.id }" target="_blank">${oProduct.name } 
                                        	<span class="orange">${oProduct.price }￥/台</span></a></div></li>
                                        </c:forEach>	
                                        </ul>
                                    </div>
                                </div>
                                <div id="con_five_3" class="putbox" style="display:none">
                                	<div class="hor-list">
                                    	<ul class="lister01 s_clear">
                                    	<c:forEach  items="${partsProductList}" var="pProduct" varStatus="s" begin="0" step="1" end="13">
                                        	<li><div><a href="${ctx }/head/product_detail.php?id=${pProduct.id }" target="_blank">${pProduct.name }
                                        	<span class="orange">${pProduct.price }￥/台</span></a></div></li>
                                        </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <div id="con_five_4" class="putbox" style="display:none">
                                	<div class="hor-list">
                                    	<ul class="lister01 s_clear">
                                    	<c:forEach  items="${e_sourceProductList}" var="eProduct" varStatus="s" begin="0" step="1" end="13">
                                        	<li><div><a href="${ctx }/head/product_detail.php?id=${eProduct.id }" target="_blank">${eProduct.name } 
                                        	<span class="orange">${eProduct.price }￥/台</span></a></div></li>
                                        </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                        	</div>
                        </div>
                        <div class="box01-wrap rows">
                            <div class="box-blue listbox">
                                <div class="title01">
                                    <div class="tab01">
                                        <ul>
                                            <li id="seven1" class="hover" onmouseover="setTab('seven',1,4)"><a href="${ctx }/head/tradinfo.php?type='求购'">求购信息</a></li>
                                            <li id="seven2" onmouseover="setTab('seven',2,4)"><a href="${ctx }/head/tradinfo.php?type='供应'">供应信息</a></li>
                                            <li id="seven3" onmouseover="setTab('seven',3,4)"><a href="${ctx }/head/tradinfo.php?type='合作'">合作信息</a></li>
                                            <li id="seven4" onmouseover="setTab('seven',4,4)"><a href="${ctx }/head/tradinfo.php?type='代理'">代理信息</a></li>
                                        </ul>
                                    </div>
                                </div>
                                <div id="con_seven_1" class="putbox">
                                	<div class="hor-list">
                                    	<ul class="s_clear">
                                    		<c:forEach  items="${lastBuyTradinfoList}" var="bTradinfo" varStatus="s" begin="0" step="1" end="13">
                                                 	<li><div>[${bTradinfo.infoType }]&nbsp;<a href="${ctx }/head/tradinfo/detail.php?id=${bTradinfo.id }" target=_blank>${bTradinfo.name }</a></div></li>
                                             </c:forEach>    	
                                        </ul>          
                                    </div>
                                </div>
                                <div id="con_seven_2" class="putbox" style="display:none">
                                	<div class="hor-list">
                                     	<ul class="s_clear">
                                        	<c:forEach  items="${lastsupplyTradinfoList}" var="sTradinfo" varStatus="s" begin="0" step="1" end="13">
                                                 	<li><div>[${sTradinfo.infoType }]&nbsp;<a href="${ctx }/head/tradinfo/detail.php?id=${sTradinfo.id }" target=_blank>${sTradinfo.name }</a></div></li>
                                             </c:forEach>  
                                        </ul>       
                                    </div>
                                </div>
                                <div id="con_seven_3" class="putbox" style="display:none">
                                	<div class="hor-list">
                                    	<ul class="s_clear">
                                        	<c:forEach  items="${lastCoopTradinfoList}" var="cTradinfo" varStatus="s" begin="0" step="1" end="13">
                                                 	<li><div>[${cTradinfo.infoType }]&nbsp;<a href="${ctx }/head/tradinfo/detail.php?id=${cTradinfo.id }" target=_blank>${cTradinfo.name }</a></div></li>
                                             </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <div id="con_seven_4" class="putbox" style="display:none">
                                	<div class="hor-list">
                                    	<ul class="s_clear">
                                        	<c:forEach  items="${lastProxyTradinfoList}" var="pTradinfo" varStatus="s" begin="0" step="1" end="13">
                                                 	<li><div>[${pTradinfo.infoType }]&nbsp;<a href="${ctx }/head/tradinfo/detail.php?id=${pTradinfo.id }" target=_blank>${pTradinfo.name }</a></div></li>
                                             </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cols-right02">
                    	<div class="box02-wrap rows"><div class="box-ashen helpbox">
                        	<div class="title01 title01-ashen title">
                            	<h1>会员信息</h1>
                            </div>
                            <div class="help">
                            <c:if test="${sessionScope.user ne null }">
                                <div class="btns s_clear">
                                    <a href="${ctx }/head/user_detail.php" target="_blank"><b><span style="font-size: 30px">${sessionScope.user.userName }</span></b>
                                    	<span>积分：${sessionScope.user.score }</span>
                                    </a>
                                </div>
                             </c:if>
                             <c:if test="${sessionScope.user eq null }">
                             	<div class="regist"><a href="javascript:" onclick="login()"><img src="${ctx }/images/skin/login.png" alt="登录" /></a></div>
                             </c:if>
                                <div class="regist"><a href="javascript:" onclick="register()"><img src="${ctx }/images/skin/regist.png" alt="注册" /></a></div>
                            </div>
                            <script type="text/javascript">
                            function login(){
								var msg="<div style='text-align: center;padding-top: 20px;'>"+
									"<form action='${ctx }/head/login.php'method='post'>"+
									"用户名:<input name='userName' style='width: 150px'/><br/>"+
									"密码:&nbsp;&nbsp;<input type='password' name='userPassword' style='width: 150px'/><br/><br/>"+
									"<input type='submit' value='确定'/>"+
									"</form></div>";
								alertWin("用户登录", msg, 400, 160);
							}

                            function register(){
                            	var msg = "<form name='regForm' action='${ctx }/head/reg.php' method='post'>"+
                            	"<table align='center' cellpadding='0' >"+
                            	"<tr ><td align='right'>用户名：</td><td align='left'><input type='text' name='userName' style='width:160px;'/></td></tr>"+
                            	"<tr ><td align='right'>密码：</td><td align='left'><input type='password' name='userPassword' style='width:160px;'/></td></tr>"+
                            	"<tr ><td align='right'>确定密码：</td><td align='left'><input type='password' name='repassword' style='width:160px;'/></td></tr>"+
                            	"<tr ><td align='right'>性别：</td><td align='left'><select name='sex'><option value='男'>男</option>"+
                            	"<option value='女'>女</option></select></td></tr>"+
                            	"<tr ><td align='right'>email：</td><td align='left'><input type='text' name='email' style='width:160px;;'/></td></tr>"+
                            	"<tr ><td align='right'>qq：</td><td align='left'><input type='text' name='qq' style='width:160px;'/></td></tr>"+
                            	"<tr ><td align='right'>简介：</td  style='width:160px;'>"+
                            	"<td align='left'><textarea name='content' style='width:160px;height=100px;' rows='6'></textarea></td></tr>"+
                            	"<td colspan='2' align='center'><input type='submit' value='提交'/></td>"+
                            	"</table></form>";
                            	alertWin("用户注册",msg,300,300);
                            }
							</script>
							<script type="text/javascript" language="javascript" src="${ctx }/js/window.js"></script>

                        </div></div>
                        <div class="box01-wrap rows">
                            <div class="box-blue listbox">
                                <div class="tab">
                                	<ul>
                                    	<li id="three1" class="hover" onmouseover="setTab('three',1,2)"><b>行业资讯</b></li>
                                        <li id="three2" onmouseover="setTab('three',2,2)"><b class="tab-right">企业新闻</b></li>
                                    </ul>
                                </div>
                                <div id="con_three_1">
                                    <div class="newsbox">
                                        <h3>${lastIndustryNewsList[0].title }</h3>
                                        <p ><div style="text-overflow: ellipsis; overflow: hidden;height: 50px">
                                         ${lastIndustryNewsList[0].detail }</div></p>
                                        <div><a href="${ctx }head/news_detail.php?id=${lastIndustryNewsList[0].id }" target="_blank">详情</a></div>
                                    </div>
                                    <div class="list02">
                                        <ul class="lister02">
                                        <c:forEach  items="${lastIndustryNewsList}" var="iNews" varStatus="s" begin="0" step="1" end="6">
                                            <li><A href="${ctx }head/news_detail.php?id=${iNews.id }" target="_blank">${iNews.title }</A></li>
                                        </c:forEach>
                                        </ul>
                                        <p class="more"><a href="${ctx }/head/news.php?type='行业新闻'" target="_blank">更多&gt;&gt;</a></p>
                                    </div>
                              </div>
                                 <div id="con_three_2" style="display:none">
                                    <div class="newsbox">
                                        <h3>${lastComplanyNewsList[0].title }</h3>
                                        <p><div style="text-overflow: ellipsis; overflow: hidden;height: 50px">
                                        ${lastComplanyNewsList[0].detail }</div></p>
                                        <div><a href="${ctx }head/news_detail.php?id=${lastComplanyNewsList[0].id }" target="_blank">详情</a></div>
                                    </div>
                                    <div class="list02">
                                        <ul class="lister02">
                                        <c:forEach  items="${lastComplanyNewsList}" var="cNews" varStatus="s" begin="0" step="1" end="6">
                                            <li><A href="${ctx }head/news_detail.php?id=${cNews.id }" target=_blank>${cNews.title }</A></li>
                                        </c:forEach>
                                        </ul>
                                        <p class="more"><a href="${ctx }/head/news.php?type='企业新闻'" target="_blank">更多&gt;&gt;</a></p>
                                    </div>
                                 </div>
                            </div>
                		</div>
                    </div>
                </div>
                <div class="box01-wrap rows">
                    <div class="box-blue listbox">
                        <div class="title01">
                            <div class="tab01">
                            	<div class="right">
                                    <a class="blue" href="${ctx }/head/product.php" target="_blank">更多产品</a>
                                </div>
                                <ul>
                                    <li class="hover"><a>产品推荐</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="rollBox">
     <div class="LeftBotton" onmousedown="ISL_GoUp()" onmouseup="ISL_StopUp()" onmouseout="ISL_StopUp()"></div>
     <div class="Cont" id="ISL_Cont">
      <div class="ScrCont">
       <div id="List1">
        <!-- 图片列表 begin -->
        <c:forEach  items="${hotProductList}" var="hProduct" varStatus="s" begin="0" step="1" end="19">
         <div class="pic">
          <a href="${ctx }/head/product_detail.php?id=${hProduct.id}" target=_blank>
          <img src="${hProduct.bigImg }" alt="" />
          </a><p><a href="${ctx }/head/product_detail.php?id=${hProduct.id}" target=_blank>${hProduct.name }</a></p>
         </div>       
        </c:forEach>
        <!-- 图片列表 end -->
       </div>
       <div id="List2"></div>
      </div>
     </div>
     <div class="RightBotton" onmousedown="ISL_GoDown()" onmouseup="ISL_StopDown()" onmouseout="ISL_StopDown()"></div>
    </div>
<script type="text/javascript" language="javascript" src="../css/scroll_pic.js"></script>
                    </div>
                </div>
                <div class="box01-wrap row">
                    <div class="box-blue listbox">
                        <div class="title01">
                            <div class="tab01">
                            	<div class="right">
                                    <a class="blue" href="${ctx }/head/complany.php" target="_blank">更多&gt;&gt;</a>
                                </div>
                                <ul>
                                    <li class="hover"><a>最新加盟企业</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="hor-list">
                            <ul class="lister01 hor3 s_clear">
                            <c:forEach  items="${lastComplanyList}" var="lComplany" varStatus="s" begin="0" step="1" end="8">
                                <li><div><a target="_blank" href="${ctx }/head/complany_detail.php?id=${lcomplany.id }">${lComplany.name }</a></div></li>
                            </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="box01-wrap row">
                    <div class="box-blue listbox">
                        <div class="title01">
                            <div class="tab01">
                            	<div class="right">
                                    <a class="blue" href="${ctx }/head/message.php" target="_blank">更多&gt;&gt;</a>
                                </div>
                                <ul>
                                    <li class="hover"><a>客户好评</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="hor-list2">
                            <ul class="lister01 hor3 s_clear">
                            <c:forEach  items="${lastMessageList}" var="lMessage" varStatus="s" begin="0" step="1" end="3">
                                <li><div><a target="_blank" href="${ctx }/head/message_detail.php?id=${lMessage.id }">
                                	<div style="display: inline;text-overflow: ellipsis; overflow: hidden; white-space: nowrap; margin-left: 10px; width: 300px">${lMessage.detail }</div>
                                	</a><span>admin</span></div>
                                </li>
                            </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="rows">
        	<div class="box01-wrap">
            	<div class="box-blue">
                	<div class="title01">
                    	<div class="tab01">
                        	<div class="right">
<%--                            	<a class="orange" href="${ctx }/about/link.jsp" target="_blank">更多&gt;&gt;</a>--%>
<%--                                <a class="blue" href="${ctx }/about/apply_Contact.jsp" target="_blank">申请链接&gt;&gt;</a>--%>
                            </div>
                        	<ul>
                            	<li class="hover"><a>友情链接</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="link-box">
                    <a href="http://www.icyougou.com/" target="_blank">电子元件采购网</a> 
                    <a href="http://www.jmzstx.com" target="_blank">江门装饰网</a> 
                    <a href="http://www.jsb2c.com" target="_blank">岳阳装饰网</a> 
                    <a href="http://www.aliwulian.com" target="_blank">阿里物联网</a> 
                    <a href="http://www.zmbid.com" target="_blank">灯具采购网</a> 
                    <a href="http://www.3dcoo.com" target="_blank">聊城建筑装饰网</a> 
                    <a href="http://www.5ilight.com" target="_blank">照明设计网 </a> 
                    <a href="http://www.chazuo.com" target="_blank">插座网</a> 
                    <a href="http://www.851zs.com/" target="_blank">贵州装饰网</a> 
                </div>
            </div>
        </div>
    </div>
<jsp:include page="foot.jsp"></jsp:include></body>
</html>

