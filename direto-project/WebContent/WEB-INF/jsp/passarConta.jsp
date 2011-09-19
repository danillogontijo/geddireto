<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

<%@ include file="include_head.jsp" %>

<script type="text/javascript" src="<%=request.getContextPath() %>/dwr/interface/contasJS.js"></script>

<script type="text/javascript">

$j(function(){

	//$j.ui.dialog.defaults.bgiframe = true;

	$j("#usuLogin").focus(function() {
        $j("#btPassarContas").fadeOut(1000);
	});
	
	$j("#usuLogin").bind('blur',function(){
	
		validateUser($j(this).val());
	
		}
	);
	
	$j('input:checkbox').bind('click',function(){
		
		validateUser($j("#usuLogin").val());
	
	});

	$j("#conta_principal").bind('click',function(){
		if($j(this).is(':checked')){
			var message = "Você selecionou sua conta principal.\n"+
					"Todas as outras contas também serão\nautomaticamente transferidas.\n"+
					"Para trocar sua conta principal\ncontate o administrador do sistema.";

					alertMessage("ATENÇÃO!",message,false);

			$j('input:checkbox[name=contas]').attr('checked','checked');
			$j('input:checkbox[name=contas]').attr('disabled','disabled');
			$j("#conta_principal").attr('disabled','');
			
		}else{
			$j('input:checkbox[name=contas]').attr('checked','');
			$j('input:checkbox[name=contas]').attr('disabled','');
		}
	});

	$j("#btPassarContas").click(function(){
		
		var sContasTransferidas = "";

		$j('input:checked[name=contas]').each(function(index) {
		    //alert($j(this).val());
		    var usuLogin = $j('#usuLogin').val();
		    contasJS.transferAccount($j(this).val(),usuLogin,{
				callback:function(sRetorno) {

				var arRetorno = sRetorno.split(',');

		    	if(eval(arRetorno[1]))
		    		sContasTransferidas += arRetorno[0]+' <span style="color:green;">(transferida)</span><br/>';
		    	else
		    		sContasTransferidas += arRetorno[0]+' <span style="color:red;">(não transferida)</span><br/>';

		    	dialogMessage('Transferindo contas...','<p style="text-align: center;">'+sContasTransferidas+'</p>',true);

		    	$j( "#dialog-message" ).dialog( "option", "buttons", { "Ok": function() { 

			    	//$(this).dialog("close");

			    	//alert('fazendo logoff-j_security_rememberMe_logout');
			    	window.location.href = 'j_security_rememberMe_logout';

			    	} 
		    	});

		    	$j( '.ui-icon' ).click(function() { 

			    	//$(this).dialog("close");

			    	//alert('fazendo logoff'); 
			    	
			    	window.location.href = 'j_security_rememberMe_logout';

			    	});
		    	
		    	}
		    });


		});

	});
	
});

function validateUser(usuLogin)
{
	usuarioJS.validateUser(usuLogin, {
		callback:function(exist) {
			//alert(exist);
			if (eval(exist)){
				$j('#validacao').hide();
				
				$j('#validacao').css('color','green');
				$j('#validacao').html('Usuário encontrado!');
				$j('#btPassarContas').val('Transferir as contas acima seleciondas para '+usuLogin);
				$j('#btPassarContas').show();

				$j('#validacao').fadeIn(1000);
			}else{
				$j('#validacao').hide();
				
				$j('#btPassarContas').hide();
				$j('#validacao').css('color','red');
				$j('#validacao').html('Usuário inexistente ou inválido!');

				$j('#validacao').fadeIn(1000);
				
			}
		}
  	});
 	  	
}

</script>

<!-- INICIO SUBMENU 2 DO CORPO PRINCIPAL -->
			<div style="background-color: #B8C9DD;" class="ui_main_body ui_subMenu _width_main_body _font_normal">
				<span style="font-weight: bold;">Transferência de contas</span>
			</div>
			
			<!-- INICIO CORPO PRINCIPAL -->
			<div class="ui_main_body _width_main_body" style="background-color: #fff; min-height: 640px;">
				<table width="100%">
			
				<tr>
					<td align="center">
					
					<fieldset>
						<label>Digite o login do usuário para o qual deseja trasferir as contas abaixo:</label>
						<input type="text" id="usuLogin" name="usuLogin">
					
					
					<p>
					
						<label style="">Selecione as contas:</label>
						<c:forEach var="conta" items="${usuario.contas}">
			    		     <c:choose>
						      <c:when test="${conta.carteira.idCarteira == contaAtual}">
						     	<input type="checkbox" name="contas" value="${conta.idConta}" <c:if test="${conta.contaPrincipal == 1}">id="conta_principal"</c:if> /><span style="font-weight: bold;">${conta.carteira.cartAbr}</span>
						      </c:when>
						
						      <c:otherwise>
						      	<input type="checkbox" name="contas" value="${conta.idConta}"" <c:if test="${conta.contaPrincipal == 1}">id="conta_principal"</c:if> />${conta.carteira.cartAbr}
						      </c:otherwise>
						    </c:choose>
						    <c:if test="${conta.contaPrincipal == 1}"><br></c:if>
						</c:forEach>
					</p>
					
					<p>
						<span id="validacao" style="font-weight: bold;"></span>
					
					</p>	
					
					<p>
						<span>	
							<input type="button" id="btPassarContas" style="display: none; width: 50%;" />
						</span>
						
					</p>
					
							
					</fieldset>
					
					 </td>
				</tr>
			
			</table>
		</div>
	</div>
</div>
	
<%@ include file="include_foot.jsp" %>

