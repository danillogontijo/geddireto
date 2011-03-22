<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

$j(function() {
	$j( "#accordion" ).accordion();
});
</script>

<style type="text/css">
.menu {
	width: 200px;
}

</style>

<table width="1024" cellpadding="0" cellspacing="0" height="800">
	<tr>
		<td width="200">
		<div class="menu">
			<div id="accordion">
				<h3><a href="#">Usuários</a></h3>
				<div>
					<ul>
						<li><a href="usuarios.html" target="palco">Listar usuários</a></li>
						<li><a href="index.html" target="palco">Cadastro</a></li>
						<li>Edição</li>
						<li>Vincular carteira</li>
					</ul>
				</div>
				<h3><a href="#">Documentos</a></h3>
				<div>
					
				</div>
				<h3><a href="#">Carteiras</a></h3>
				<div>
					<ul>
						<li>Cadastro</li>
						<li>Edição</li>
						<li>Grupos</li>
					</ul>
				</div>
				<h3><a href="#">Outros</a></h3>
				<div>
					<ul>
						<li>Postro/Graduação</li>
						<li>Seção</li>
						<li>OM</li>
						<li>Função</li>
					</ul>
				</div>
			</div>
		</div>
		</td>
		<td width="824">
		
			<IFRAME name="palco" src="blank.html" frameBorder="0" width="824px" height="300px" scrolling=no></IFRAME>
			
		</td>
	</tr>
</table>

</body>
</html>