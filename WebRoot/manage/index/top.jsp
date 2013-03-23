<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../commons/head.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD id=Head1>
<TITLE>无标题页</TITLE>
<STYLE type=text/css> 
*{
	FONT-SIZE: 12px; COLOR: white
}
#logo {
	COLOR: white
}
#logo A {
	COLOR: white
}
FORM {
	MARGIN: 0px
}
</STYLE>
<META content="MSHTML 6.00.2900.5848" name=GENERATOR>
<script type="text/javascript" language="javascript">
    function setTime() {
    
     var tempDate = new Date();
	 
	 
  	 year= tempDate.getFullYear();
  	
     month= tempDate.getMonth() + 1 ;
    
     day= tempDate.getDate(); 
   
    var currentTime= year+"年"+month+"月"+day+"日  ";
    hour=tempDate.getHours();     
    minute=tempDate.getMinutes();    
    second=tempDate.getSeconds();    
	currentTime += hour+":"+minute+":"+second+" ";
	
     myArray=new Array(6);
     myArray[0]="星期日"
     myArray[1]="星期一"
     myArray[2]="星期二"
     myArray[3]="星期三"
     myArray[4]="星期四"
     myArray[5]="星期五"
     myArray[6]="星期六"
     weekday=tempDate.getDay();
    
     currentTime += myArray[weekday];
     

        document.getElementById("htmer_time").innerHTML=currentTime;
    }
    setInterval(setTime,1000);
</script>
</HEAD>
<body 
style="BACKGROUND-IMAGE: url('../images/bg.gif'); MARGIN: 0px; BACKGROUND-REPEAT: repeat-x">
<form id="form1">
  <DIV id=logo 
style="BACKGROUND-IMAGE: url('../images/logo.png'); BACKGROUND-REPEAT: no-repeat">
    <DIV 
style="PADDING-RIGHT: 50px; BACKGROUND-POSITION: right 50%; DISPLAY: block; PADDING-LEFT: 0px; BACKGROUND-IMAGE: url(../images/bg_banner_menu.gif); PADDING-BOTTOM: 0px; PADDING-TOP: 3px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 30px; TEXT-ALIGN: right"><A 
href="#"></A> 
<div id="htmer_time"></div>

 </DIV>
    <DIV style="DISPLAY: block; HEIGHT: 54px"></DIV>
    <DIV 
style="BACKGROUND-IMAGE: url(../images/bg_nav.gif); BACKGROUND-REPEAT: repeat-x; HEIGHT: 30px">
      <TABLE cellSpacing=0 cellPadding=0 width="100%">
        <TBODY>
          <TR>
            <TD>
              <DIV><IMG src="Top.files/nav_pre.gif" align=absMiddle> &quot;${userAdmin.userName}&quot;欢迎进入后台管理</DIV>
            </TD>
            <TD align=right width="70%"><SPAN style="PADDING-RIGHT: 50px"><A 
      href="javascript:history.go(-1);"><IMG src="Top.files/nav_back.gif" 
      align=absMiddle border=0>后退</A> <A href="javascript:history.go(1);"><IMG 
      src="Top.files/nav_forward.gif" align=absMiddle border=0>前进</A> <A 
      href="${ctx}/manage/user/loginOut.do" target=_parent><IMG 
      src="Top.files/nav_changePassword.gif" align=absMiddle border=0>重新登录</A><IMG 
      src="Top.files/menu_seprator.gif" align=absMiddle> <SPAN 
      id="Clock"></SPAN></SPAN></TD>
          </TR>
        </TBODY>
      </TABLE>
    </DIV>
  </DIV>
  <SCRIPT type=text/javascript>
    var clock = new Clock();
    clock.display(document.getElementById("clock"));
</SCRIPT>
</form>
</body>
</HTML>
