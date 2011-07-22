<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="include_taglibs.jsp" %>

<html>
<body onload="sistema()">

<c:set var="p" value="file:\\\\\\\\10.133.108.111\\\\temp\\\\${path}"></c:set>
<c:set var="pFirefox" value="file://///10.133.108.111/temp/${path}"></c:set>
<c:set var="pFirefoxLinux" value="file:////home/direto/work_direto/temp/${path}"></c:set>


<html>

<script type="text/javascript">


function redirect(so){


if (document.all)
{
 	 window.location.href('${p}'); //ie nao funciona
}
else
{
 	
 	var firefox = document.getElementById("firefox"); 
 	
 	if (so == 1){
 		firefox.innerHTML = "<a href='${pFirefoxLinux}'>${pFirefoxLinux}</a><br><b>No Firefox ou Chrome clique ou copie este link e coloque-o na barra de endere&ccedil;o de seu browser.</b>";
 		document.location = "${pFirefoxLinux}";
 	}else{	 
 		firefox.innerHTML = "<a href='file://///10.133.108.111/temp/${path}'>file://///10.133.108.111/temp/${path}</a><br><b>No Firefox ou Chrome clique ou copie este link e coloque-o na barra de endere&ccedil;o de seu browser.</b>";
 		document.location = "${pFirefox}";
 	}
 	
 	
}
 
}


function fechar(){
	if (!document.all)
	{
		window.close(0);
	}
}


function init(so){
	redirect(so);
	//setTimeout("fechar();",3000);
}


</script>


<script language="javascript">
function sistema()
{
if(navigator.userAgent.indexOf('Linux') != -1)
{ var so = 1; }

else if((navigator.userAgent.indexOf('Win')!=
-1)&&(navigator.userAgent.indexOf('95')!= -1))
{ var so = "Windows 95"; }

else if((navigator.userAgent.indexOf('Win')!=
-1)&&(navigator.userAgent.indexOf('98')!= -1))
{ var so = "Windows 98"; }

else if((navigator.userAgent.indexOf('Win')!=
-1)&&(navigator.userAgent.indexOf('NT')!= -1))
{ var so = "Windows NT/XP"; }

else
if((navigator.userAgent.indexOf('Win')!=-1)&&(navigator.userAgent.indexOf('2000')!=-1))
{ var so = "Windows 2000"; }

else if(navigator.userAgent.indexOf('Mac') != -1)
{ var so = "Macintosh"; }

else if(navigator.userAgent.toLowerCase().indexOf('unix') != -1)
{ var so = "Unix"; }

else { var so = "Outro"; }

if (so == 1){
	init(1);
}else{
	init(0);
}

}
</script>


<body onload="sistema();">

<span id="firefox"></span>

</body>

</html>




</body>
</html>