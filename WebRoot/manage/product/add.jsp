<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="../../commons/head.jsp" %>
<%@ include file="../../commons/js.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>添加产品</TITLE>
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
$("#userName").formValidator({onshow:"请输入用户名",onfocus:"用户名不能为空",oncorrect:"用户名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户名两边不能有空符号"},onerror:"用户名不能为空,请确认"}).ajaxValidator({
	    type : "get",
		url : "${ctx}/manage/user/checkUserName.do",
		datatype : "json",
		success : function(data){	
            if( data == "0" )
			{		
                return true;
			}
            else
			{
                return false;
			}
		},	
		error: function(){alert("服务器没有返回数据，可能服务器忙，请重试");},
		onerror : "该用户名不可用，请更换用户名",
		onwait : "正在对用户名进行合法性校验，请稍候..."
	});

$("#password1").formValidator({onshow:"请输入密码",onfocus:"密码不能为空",oncorrect:"密码合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"密码两边不能有空符号"},onerror:"密码不能为空,请确认"});

$("#password2").formValidator({onshow:"请输入重复密码",onfocus:"两次密码必须一致哦",oncorrect:"密码一致"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"重复密码两边不能有空符号"},onerror:"重复密码不能为空,请确认"}).compareValidator({desid:"password1",operateor:"=",onerror:"2次密码不一致,请确认"});

$("input:radio[name='sex']").formValidator({tipid:"sexTip",onshow:"请选择你的性别",onfocus:"没有第三种性别了，你选一个吧",oncorrect:"输入正确",defaultvalue:[1]}).inputValidator({min:1,max:1,onerror:"性别忘记选了,请确认"});//.defaultPassed();

$("#email").formValidator({onshow:"请输入邮箱",onfocus:"邮箱6-100个字符,输入正确了才能离开焦点",oncorrect:"恭喜你,你输对了",defaultvalue:"@",forcevalid:true}).inputValidator({min:6,max:100,onerror:"你输入的邮箱长度非法,请确认"}).regexValidator({regexp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onerror:"你输入的邮箱格式不正确"});

$("#content").formValidator({onshow:"请输入你的描述",onfocus:"描述至少要输入10个汉字或20个字符",oncorrect:"恭喜你,你输对了",defaultvalue:"这家伙很懒，什么都没有留下。"}).inputValidator({min:20,onerror:"你输入的描述长度不正确,请确认"});	


$("#qq").formValidator({empty:true,onempty:"你真的不想留下QQ号码吗？",onshow:"请输入你的QQ号码",onfocus:"请输入至少5位的QQ号码"}).InputValidator({min:5,max:12,onerror:"你输入的QQ号码长度错误"});	


});	

</script>
	

</HEAD>
<BODY 
style="BACKGROUND-POSITION-Y: -120px; BACKGROUND-IMAGE: url(../images/bg.gif); BACKGROUND-REPEAT: repeat-x">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%">
  <TBODY>
    <TR>
      <TD width=10 height=29><IMG src="${ctx }/manage/index/Left.files/bg_left_tl.gif"></TD>
      <TD 
    style="FONT-SIZE: 18px; BACKGROUND-IMAGE: url(../images/bg_left_tc.gif); COLOR: white; FONT-FAMILY: system">企业资料</TD>
      <TD width=10><IMG src="${ctx }/manage/index/Left.files/bg_left_tr.gif"></TD>
    </TR>
    <TR>
      <TD style="BACKGROUND-IMAGE: url(../images/bg_left_ls.gif)"></TD>
      <TD id=menuTree 
    style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign=top></TD>
      <TD style="BACKGROUND-IMAGE: url(../images/bg_left_rs.gif)"></TD>
    </TR>
    <TR>
      <TD colspan="3"  style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign=top>
    <div style="float: right"><a href="javascript:history.go(-1)">返回</a></div>
      <form name="form1" id="form1" method="post" enctype="multipart/form-data" action="${ctx }/manage/product/add.do">
        <table width="591" height="433" border="0" align="center">
          <tr>
            <td colspan="3"><div align="center"><span class="STYLE2">添加产品</span></div></td>
            </tr>
          <tr>
            <td width="101" align="right">名称:</td> 
     	 <td width="120"><input type="text" id="name" name="name" style="width:120px" value="" /></td> 
     		 <td width="342"><div id="nameTip" style="width:250px"></div></td> 
          </tr>
          <tr>
		  <td align="right">产品分类:</td> 
     	 <td>
<%--     	 	大类：--%>
<%--			<select><c:forEach var="bigCategory" items="${bigCategoryList }">--%>
<%--				<option value="${bigCategory.id }">${bigCategory.name }</option>--%>
<%--			</c:forEach></select>--%>
<%--			小类：--%>
			<select name="categoryId"><c:forEach var="category" items="${categoryList }">
				<option value="${category.id }">${category.name }</option>
			</c:forEach></select>
		</td> 
     	 <td><div id="typeTip" style="width:250px"></div></td> 
		</tr>
          <tr>
         <td align="right">状态:</td> 
	     <td>
	     <select name="status">
	     	<option value="上线">上线</option>
	     	<option value="下线">下线</option>
	     </select>
	     </td> 
     	  <td><div id="statusTip" style="width:250px"></div></td>
          </tr>
          <tr>
         <td align="right">销量:</td> 
	     <td><input type="text" name="salesValume" id="salesValume" style="width:120px" value="0"/></td> 
     	  <td><div id="salesValumeTip" style="width:250px"></div></td>
          </tr>
          <tr>
            <td align="right">生产地:</td> 
    	    <td><input type="text" id="produceArea" name="produceArea" style="width:120px" /></td> 
     	    <td><div id="produceAreaTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td align="right">小图:</td>
            <td>
           	 	本地上传：<br/><input type="file" id="smallFile" name="smallFile"/><br/>
            	链接地址：<br/><input type="text" id="smallImg" name="smallImg"/>
            </td> 
     	    <td><div id="smallFileTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td align="right">大图:</td>
            <td>
            	本地上传：<br/><input type="file" id="bigFile" name="bigFile" /><br/>
            	链接地址：<br/><input type="text" id="smallImg" name="bigImg"/>
            </td> 
     	    <td><div id="bigImgTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td align="right">生产日期:</td>
            <td><input type="text" id="createtime" name="createtime"/></td> 
     	    <td>(格式：2012-01-01)<div id="createtimeTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td align="right">价格:</td>
            <td><input type="text" id="price" name="price" style="width:120px" value="0.0"/></td> 
     	    <td><div id="priceTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td align="right">数量:</td>
            <td><input type="text" id="num" name="num" style="width:120px" value="1"/></td> 
     	    <td><div id="numTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td align="right">最小数量:</td>
            <td><input type="text" id="minNum" name="minNum" style="width:120px" value="1"/></td> 
     	    <td><div id="numTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td align="right">排列序号:</td>
            <td><input type="text" id="orders" name="orders"  style="width:120px" value="1"/></td> 
     	    <td><div id="sortNumTip" style="width:250px"></div></td> 
          </tr>
          <tr>
            <td align="right">详细信息:</td>
            <td><textarea id="detail" name="detail" cols="30" rows="10"></textarea></td> 
     	    <td><div id="detailTip" style="width:250px"></div></td> 
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
      <TD style="BACKGROUND-IMAGE: url(../images/bg_left_bc.gif)"></TD>
      <TD width=10><IMG 
src="${ctx }/manage/index/Left.files/bg_left_br.gif"></TD>
    </TR>
      
  </TBODY>

</TABLE>

</BODY>
</HTML>
