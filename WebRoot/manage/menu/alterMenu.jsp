<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../commons/head.jsp"%>
<%@ include file="../../commons/js.jsp"%>
<%@ include file="../../commons/dtree.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>修改菜单</TITLE>
		<META http-equiv=Content-Type content="text/html; charset=utf-8">
		<STYLE type=text/css>
{
FONT-SIZE








:




 




12
px










}
#menuTree A {
	COLOR: #566984;
	TEXT-DECORATION: none
}

.STYLE2 {
	font-size: x-large
}
</STYLE>

		<style type="text/css" media="all">
body,div {
	font-size: 12px;
}
</style>

		<META content="MSHTML 6.00.2900.5848" name=GENERATOR>

		<script type="text/javascript">
	$(document).ready(function(){
$.formValidator.initConfig({formid:"form1",onError:function(){alert("具体错误请看提示!")}});
		//点击获取脚本按钮，获取校验代码

$("#menuName").formValidator({
	onshow:"请输入菜单名",
	onfocus:"菜单名不能为空",
	oncorrect:"菜单名合法"}).inputValidator(
	{min:1,empty:{leftempty:false,rightempty:false,emptyerror:"菜单名两边不能有空符号"},
	onerror:"菜单名不能为空,请确认"});
$("#orders").formValidator({
	onshow:"请输入排序 .格式：1,1.2,1.2.1",
	onfocus:"排序不能为空",
	oncorrect:"排序名合法"}).inputValidator(
	{min:1,empty:{leftempty:false,rightempty:false},
	onerror:"排序不能为空,请确认"});
$("#url").formValidator({
	onshow:"请输入路径",
	oncorrect:"路径合法"});
$("#imageUrl").formValidator({
	onshow:"请输入图片路径",
	oncorrect:"图片路径合法"});
});	

</script>


	</HEAD>
	<BODY
		style="BACKGROUND-POSITION-Y: -120px; BACKGROUND-IMAGE: url(${ctx}/manage/images/bg.gif); BACKGROUND-REPEAT: repeat-x">
		<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%">
			<TBODY>
				<TR>
					<TD width=10 height=29>
						<IMG src="${ctx }/manage/index/Left.files/bg_left_tl.gif">
					</TD>
					<TD
						style="FONT-SIZE: 18px; BACKGROUND-IMAGE: url(${ctx}/manage/images/bg_left_tc.gif); COLOR: white; FONT-FAMILY: system">
						修改菜单
					</TD>
					<TD width=10>
						<IMG src="${ctx }/manage/index/Left.files/bg_left_tr.gif">
					</TD>
				</TR>
				<TR>
					<TD style="BACKGROUND-IMAGE: url(${ctx}/manage/images/bg_left_ls.gif)"></TD>
					<TD id=menuTree
						style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white"
						vAlign=top></TD>
					<TD style="BACKGROUND-IMAGE: url(${ctx}/manage/images/bg_left_rs.gif)"></TD>
				</TR>
				<TR>
					<TD colspan="3"
						style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white"
						vAlign=top>
						<form name="form1" id="form1" method="post"
							action="${ctx }/manage/menu/update.do">
							<table width="591" height="433" border="0" align="center">
								<tr>
									<td colspan="3">
										<div align="center">
											<span class="STYLE2">修改菜单</span>
										</div>
									</td>
								</tr>
								<tr>
									<td width="103" align="right">
										菜单名:
									</td>
									<td width="190">
										<input name="menuName" type="text" id="menuName"
											style="width: 120px" value="${menuInfo.menuName}" size="50" />
											<input type="hidden" name="id" value="${menuInfo.id}"/>
									</td>
									<td width="284">
										<div id="menuNameTip" style="width: 250px"></div>
									</td>
								</tr>

								<tr>
									<td align="right" valign="top">
										排序：
									</td>
									<td valign="top">
										<input name="orders" type="text" id="orders"
											style="width: 120px" value="${menuInfo.orders}" size="50" />
										<td width="284">
											<div id="ordersTip" style="width: 250px"></div>
										</td>
								</tr>
								<tr>
									<td align="right" valign="top">
										路径：:
									</td>
									<td valign="top">
										<input name="url" type="text" id="url" style="width: 200px"
											value="${menuInfo.url}" size="50" />
										<td width="284">
											<div id="urlTip" style="width: 250px"></div>
										</td>
								</tr>
								<tr>
									<td align="right" valign="top">
										图片路径：
									</td>
									<td valign="top">
										<input type="text" id="imageUrl" name="imageUrl"
											style="width: 200px" value="${menuInfo.imageUrl}" />
										<td width="284">
											<div id="imageUrlTip" style="width: 250px"></div>
										</td>
								</tr>
								<tr>
									<td align="right" valign="top">
										父菜单：
									</td>
									<td colspan="2" valign="top">

										<div class="dtree">

											<p>
												<a href="javascript: d.openAll();">展开</a> |
												<a href="javascript: d.closeAll();">关闭</a>
											</p>

											<script type="text/javascript">
		<!--
 		
		d = new dTree('d');
		d.add(0,-1,"<input type='radio' name='parentId' value='0' checked >系统菜单");
		<c:forEach items="${menuList}" var="menu" varStatus="s">
			
		   d.add(${menu.id}, ${menu.parentId}, "<input type='radio' name='parentId' value='${menu.id}'<c:if test="${menu.id==menuInfo.parentId}">checked</c:if>>${menu.menuName }");       	
		</c:forEach>
	  	 
		document.write(d);

		//-->

	</script>

										</div>


									</td>
								</tr>
								<tr>
									<td colspan="3">
										<div id="roleInfoTip" style="width: 250px"></div>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td>
										<input type="submit" name="button" id="button" value="提交" />
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
							</table>
						</form>
					</TD>
				</TR>
				<TR>
					<TD width=10>
						<IMG src="${ctx }/manage/index/Left.files/bg_left_bl.gif">
					</TD>
					<TD style="BACKGROUND-IMAGE: url(${ctx}/manage/images/bg_left_bc.gif)"></TD>
					<TD width=10>
						<IMG src="${ctx }/manage/index/Left.files/bg_left_br.gif">
					</TD>
				</TR>

			</TBODY>

		</TABLE>

	</BODY>
</HTML>
