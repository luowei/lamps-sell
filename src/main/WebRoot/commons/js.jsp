<!-- jquery-form-validate -->
<script src="${ctx }/js/jquery_last.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="${ctx }/js/style/validator.css" />
<script src="${ctx }/js/formValidator.js" type="text/javascript"></script>
<script src="${ctx }/js/formValidatorRegex.js" type="text/javascript"></script>
<!-- jquery-form-date -->
<link rel="stylesheet" href="${ctx }/js/date/ui.datepicker.css" type="text/css" media="screen"  charset="utf-8" />
<script src="${ctx }/js/date/ui.datepicker.js" type="text/javascript" charset="utf-8"></script>	
<script src="${ctx }/js/date/ui.datepicker-zh-CN.js" type="text/javascript" charset="utf-8"></script>
<style type="text/css" media="all">
table{
	font-size: 14px;
}
.rw{background-image: url(${ctx }/images/cs.jpg);}}
</style>
<script type="text/javascript">
function changeCss(i)
{
	var cn=document.getElementById("rw"+i).className;
	if(cn!="")
	{
		document.getElementById("rw"+i).setAttribute("oldclass", cn);
	}
	document.getElementById("rw"+i).className="rw"
}
function recoverCss(i)
{
	var old=document.getElementById("rw"+i).setAttribute("oldclass");
	document.getElementById("rw"+i).className="";
	if(old!="")
	{
		document.getElementById("rw"+i).className=old;
	}
	
}
</script>

