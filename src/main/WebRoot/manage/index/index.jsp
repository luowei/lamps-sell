<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../commons/head.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset//EN">
<HTML><HEAD><TITLE>后台管理 </TITLE>
<META content="MSHTML 6.00.2900.5848" name=GENERATOR></HEAD>
<FRAMESET id=index border=0 frameSpacing=0 rows=120,* frameBorder=no>
	<FRAME id=topFrame name=topFrame src="${ctx }/manage/index/top.jsp" noResize scrolling=no>
	
	<FRAMESET border=0 frameSpacing=0 frameBorder=no cols=20%,*>
		<FRAME id=leftFrame name=leftFrame src="${ctx }/manage/index/left.jsp" noResize scrolling=no>
		<FRAME id=mainFrame name=mainFrame src="${ctx }/manage/index/main.jsp" noResize scrolling=yes>
	</FRAMESET>
</FRAMESET>
<noframes></noframes>
</HTML>
 