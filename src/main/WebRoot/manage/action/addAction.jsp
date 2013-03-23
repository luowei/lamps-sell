<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../commons/head.jsp"%>
<%@ include file="../../commons/js.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>新增权限</TITLE>
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
	$(document).ready(function() {
		$.formValidator.initConfig( {
			formid : "form1",
			onError : function() {
				alert("具体错误请看提示!")
			}
		});
		//点击获取脚本按钮，获取校验代码

			$("#actionName").formValidator( {
				onshow : "请输入权限描述",
				onfocus : "权限描述不能为空",
				oncorrect : "权限描述合法"
			}).inputValidator( {
				min : 1,
				empty : {
					leftempty : false,
					rightempty : false,
					emptyerror : "权限描述两边不能有空符号"
				},
				onerror : "权限描述不能为空,请确认"
			});
			$("#path").formValidator( {
				onshow : "请输入权限路径",
				onfocus : "权限路径不能为空",
				oncorrect : "权限路径合法"
			}).inputValidator( {
				min : 1,
				empty : {
					leftempty : false,
					rightempty : false,
					emptyerror : "权限路径两边不能有空符号"
				},
				onerror : "权限路径不能为空,请确认"
			});
			$("#orders").formValidator( {
				onshow : "请输入排序值",
				onfocus : "请输入排序值",
				oncorrect : "排序值合法"
			}).inputValidator( {
				type:"number",
				max:1000,
				min : 1,
				onerror : "请输入整数，请确认"
			});
		
		
		

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
						新增权限
					</TD>
					<TD width=10>
						<IMG src="${ctx }/manage/index/Left.files/bg_left_tr.gif">
					</TD>
				</TR>
				<TR>
					<TD style="BACKGROUND-IMAGE: url(${ctx}/manage/images//bg_left_ls.gif)"></TD>
					<TD id=menuTree
						style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white"
						vAlign=top></TD>
					<TD style="BACKGROUND-IMAGE: url(${ctx}/manage/images//bg_left_rs.gif)"></TD>
				</TR>
				<TR>
					<TD colspan="3"
						style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white"
						vAlign=top>
						<form name="form1" id="form1" method="post"
							action="${ctx }/manage/action/new.do">
							<table width="591" height="433" border="0" align="center">
								<tr>
									<td colspan="3">
										<div align="center">
											<span class="STYLE2">新增权限</span>
										</div>
									</td>
								</tr>
							
								<tr>
									<td width="76" align="right">
										权限描述:									</td>
									<td width="212">
										<input type="text" id="actionName" name="actionName"
											style="width: 120px" value="" />
								  </td>
									<td width="289">
										<div id="actionNameTip" style="width: 250px"></div>
								  </td>
								</tr>
								
								<tr>
									<td width="76" align="right">
										排序:									</td>
									<td width="212">
										<input type="text" id="orders" name="orders"
											style="width: 120px" value="" />
								  </td>
									<td width="289">
										<div id="ordersTip" style="width: 250px"></div>
								  </td>
								</tr>
								<tr>
									<td width="76" align="right">
										所属模块:									</td>
									<td width="212">
										<select name="moduleId" id="moduleId">
											<c:forEach items="${moduleList}" var="module" varStatus="s">
												<option value="${module.id }">
													${module.moduleName }
												</option>
											</c:forEach>
										</select>
								  </td>
									<td width="289">
										<div id="moduleIdTip" style="width: 250px"></div>
								  </td>
								</tr>
								<tr>
									<td width="76" align="right">
										权限路径:									</td>
									<td width="212">
										<textarea name="path" cols="200" rows="5" id="path" style="width: 200px"></textarea>
								  </td>
									<td width="289">
										<div id="pathTip" style="width: 250px"></div>
								  </td>
								</tr>
								<tr>
									<td>&nbsp;
										
									</td>
									<td>
										<input type="submit" name="button" id="button" value="提交" />
									</td>
									<td>&nbsp;
										
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
					<TD style="BACKGROUND-IMAGE: url(${ctx}/manage/images//bg_left_bc.gif)"></TD>
					<TD width=10>
						<IMG src="${ctx }/manage/index/Left.files/bg_left_br.gif">
					</TD>
				</TR>

			</TBODY>

		</TABLE>

	</BODY>
</HTML>
