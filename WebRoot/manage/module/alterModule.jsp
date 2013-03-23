<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../commons/head.jsp" %>
<%@ include file="../../commons/js.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>修改模块</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<STYLE type=text/css> 
{
	FONT-SIZE: 12px
}
#menuTree A {
	COLOR: #566984; TEXT-DECORATION: none
}
.STYLE2 {font-size: x-large}
</STYLE>

<style type="text/css" media="all"> 
body,div{font-size:12px;}
</style> 

<META content="MSHTML 6.00.2900.5848" name=GENERATOR>

<script type="text/javascript">
	$(document).ready(function(){
$.formValidator.initConfig({formid:"form1",onError:function(){alert("具体错误请看提示!")}});
		//点击获取脚本按钮，获取校验代码

$("#moduleName").formValidator({onshow:"请输入模块名",onfocus:"模块名不能为空",oncorrect:"模块名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"模块名两边不能有空符号"},onerror:"模块名不能为空,请确认"});


$("#moduleInfo").formValidator({onshow:"请输入模块描述",onfocus:"描述至少要输入2个汉字",oncorrect:"恭喜你,你输对了"}).inputValidator({min:2,onerror:"你输入的描述长度不正确,请确认"});	


});	

</script>
	

</HEAD>
<BODY 
style="BACKGROUND-POSITION-Y: -120px; BACKGROUND-IMAGE: url(${ctx }/manage/images/bg.gif); BACKGROUND-REPEAT: repeat-x">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%">
  <TBODY>
    <TR>
      <TD width=10 height=29><IMG src="${ctx }/manage/index/Left.files/bg_left_tl.gif"></TD>
      <TD 
    style="FONT-SIZE: 18px; BACKGROUND-IMAGE: url(${ctx }/manage/images/bg_left_tc.gif); COLOR: white; FONT-FAMILY: system">修改模块</TD>
      <TD width=10><IMG src="${ctx }/manage/index/Left.files/bg_left_tr.gif"></TD>
    </TR>
    <TR>
      <TD style="BACKGROUND-IMAGE: url(${ctx }/manage/images/bg_left_ls.gif)"></TD>
      <TD id=menuTree 
    style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign=top></TD>
      <TD style="BACKGROUND-IMAGE: url(${ctx }/manage/images/bg_left_rs.gif)"></TD>
    </TR>
    <TR>
      <TD colspan="3"  style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign=top>
      <form name="form1" id="form1" method="post" action="${ctx }/manage/module/update.do">
        <table width="591" height="433" border="0" align="center">
          <tr>
            <td colspan="3"><div align="center"><span class="STYLE2">修改模块</span></div></td>
            </tr>
          <tr>
            <td width="101" align="right">模块名:</td> 
     	 <td width="120"><input type="text" id="moduleName" name="moduleName" style="width:120px" value="${module.moduleName }" /></td> 
     		 <td width="342"><input type="hidden" name="id" value="${module.id }" style="width:120px" /></td> 
          </tr>
    
          <tr>
            <td align="right" valign="top">模块描述:</td> 
    	  <td colspan="2" valign="top"> <textarea id="moduleInfo" name="moduleInfo" cols="50" rows="10">${module.moduleInfo }</textarea>  
     	             
          </tr>
          <tr> 
    		  <td colspan="3"><div id="moduleInfoTip" style="width:250px"></div></td> 
    	</tr> 
          <tr>
            <td>&nbsp;</td>
            <td><input type="submit" name="button" id="button" value="提交" /> </td>
            <td>&nbsp;</td>
          </tr>
  
        </table>
            </form>
      </TD>  
    </TR>
         <TR>
      <TD width=10><IMG src="${ctx }/manage/index/Left.files/bg_left_bl.gif"></TD>
      <TD style="BACKGROUND-IMAGE: url(${ctx }/manage/images/bg_left_bc.gif)"></TD>
      <TD width=10><IMG 
src="${ctx }/manage/index/Left.files/bg_left_br.gif"></TD>
    </TR>
      
  </TBODY>

</TABLE>

</BODY>
</HTML>
