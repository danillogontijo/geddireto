<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../include_taglibs.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Administração - Direto</title>
<link href="<%=request.getContextPath() %>/css/estilos.css" rel="stylesheet" type="text/css" />

<link href="<%=request.getContextPath() %>/css/custom-theme-jquery/jquery-ui-1.8.10.custom.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/css/custom-theme-jquery/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />

<script src="<%=request.getContextPath() %>/js/custom/jquery-1.4.4.min.js"></script>
<script src="<%=request.getContextPath() %>/js/custom/jquery-ui-1.8.10.custom.min.js"></script> 

</head>
<body>

<script>
var $j = jQuery.noConflict();

jQuery(document).ready(function() {

	var sPesquisa = "";
	var table = $j('#table_users');
	
	show();
	
	function show(){
		
		$j.getJSON("../buscaUsuarios.html", function(data){
	
			 $j.each(data.users, function(i,user){
	
					table.append('<tr><td>'+user[0]+' - '+user[1]+'</td></tr>');
					sPesquisa += user[1]+';';
					//alert(user[0]+" - "+user[1]);
			       
			 });
	
		});
	};


	$j("#pesquisar").bind('keyup',function(){


		$j('#table_users tr').each(function(i){
			
			if (i != 0)
				$j(this).remove();
		});
		
		//show();

		//alert('');

		setTimeout(function(){search();},100);
		
		
	});


	function search(){
		var search = $j('#pesquisar').val();


		 var p = sPesquisa.split(';');
		 alert(sPesquisa);
		 
			for (i=0;i<p.length-1;i++){
			 var dado = p[i];

			 var text = (p[i]).toLowerCase();
		    //alert(p[i]);

		     if ((text.indexOf(search) == -1))
				 	table.append('<tr><td>'+text+'</td></tr>');	
			 
		 }

		
		

	};
});



</script>

<table id="table_users">
<tr>
<td colspan="10">Pesquisar: <input type="text" id="pesquisar"></input></td>
</tr>

  
</table>

</body>
</html>