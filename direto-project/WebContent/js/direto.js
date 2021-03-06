/**
 * Declara um objeto para poder adicionar as funcoes
 */
if (window['dojo']) dojo.provide('js.direto');
if (typeof window['js'] == 'undefined') window.js = {};
if (typeof dwr['direto'] == 'undefined') js.direto = {};

js.direto.charProibido = function (e){
    var tecla=(window.event)?event.keyCode:e.which;
    //alert(tecla);
    if(tecla == 124 || tecla == 168) { alert("Caracter proibido"); return false;}
    else{ return true; }
};

js.direto.enviarPara = function(){
	DESTINATARIOS = null; //Limpa o Array primeiro;
	DESTINATARIOS = new Array();
	
	$j("#ListaPARA option").each(function () {
          var id = $j(this).val();
		  var user = $j(this).text();
		  DESTINATARIOS.push({
	        	  id : id,
	        	  user : user
	      });
     });

};


js.direto.atualiza = function(page){
	var list = "";
	for (var i = 0; i < DESTINATARIOS.length ; i++) {
			var item = DESTINATARIOS[i];
			list += item.user+",";
	}
	
	if(page=="principal"){
		if (DESTINATARIOS.length == 0)
 			return;
		
		//alert(list);
		
		dialogMessage('Enviando documento...','<p style="text-align: center"><img src="imagens/ajax-loader.gif" /></p>',true);
		encaminharSelecionados(list); //Função encontra-se na principal.jsp
 	}else if(page=="documento"){
 		//alert(list+'documento'+DESTINATARIOS.length);
 		
 		if (DESTINATARIOS.length == 0)
 			return;
 		
 		var sDestinatarios = "";
 		for (var i = 0; i < DESTINATARIOS.length ; i++) {
			var item = DESTINATARIOS[i];
			sDestinatarios += item.id+",";
		}
 		
 		documentosJS.encaminharDocumento(sDestinatarios,ID_DOCUMENTO);
 		var txtHistorico = "(Encaminhado)-Do:";
		txtHistorico += USER_NAME;
		txtHistorico += "-Para:"+list;
		historicoJS.save(ID_DOCUMENTO,txtHistorico);
		
		dialogMessage('Documento encaminhado','Documento encaminhado com sucesso!',false);
		
		//setTimeout("document.location.reload(true)",1000);
		
		if(page=="documento"){
			setTimeout(function(){
				document.location = 'principal.html?box=1&pr=0&filtro=todas';
			},1000);
	 	}else{
	 		setTimeout("document.location.reload(true)",1000);
	 	}
		
 		
 	}else{
 		$j('#destinatarios').val(list);
 	}	
	
};


js.direto.close_mask = function(){
	$j('#mask').hide();
	$j('.window').hide();
};

js.direto.parseDate = function(txt_date) {
	var dia =  txt_date.substr(0,2);
	var mes = txt_date.substr(3,2);
	var ano = txt_date.substr(6,4);
	var hora = txt_date.substr(11,2);
	var min = txt_date.substr(14,2);
	var seg = txt_date.substr(17,2);
	
	if(dia.substring(0,1) == "0")
		dia = dia.substring(1);
	
	if(mes.substring(0,1) == "0")
		mes = mes.substring(1);
	
	var date = new Date();
	//date.setFullYear(parseInt(ano), parseInt(mes)-1, parseInt(dia));
	date.setFullYear(eval(ano), (eval(mes)-1), eval(dia));
	//date.setMonth(parseInt(mes)-1);
	//date.setFullYear(ano);
	date.setHours(hora,min);
	date.setMilliseconds(0);
	date.setSeconds(seg);
	  
	return date;
};

js.direto.compareDate = function(data1,data2){
	var milliSegundos1 = data1.getTime();
    var milliSegundos2 = data2.getTime();
   
    if(milliSegundos1 == milliSegundos2){
        return 0;
    } else if(milliSegundos1 > milliSegundos2){
        return 1;
    } else if(milliSegundos1 < milliSegundos2){
        return -1;
    } else return -1;
    
    
};


/*Função Update histórico,anotaçoes e despachos */
js.direto.show_updates = function(id,type){
	
	var singular = type;
	

	if (type == "anotacoes") {
		singular = "anotacao";
	} else if (type == "despachos") {
		singular = "despacho";
	} else if (type == "historico"){
		singular = "historico";
	} else {
		singular = "unknow";
	}

	var txt_date = $j('#'+type+' span:last').text();
	//alert(txt_date);
	var last_date = js.direto.parseDate(txt_date);
	//alert(last_date);
	var retorno = 0;
		$j.getJSON(type+".html?id="+id, function(data) {
			
	    $j.each(data.dados, function(i,d){
			//alert(i);
	        //$j("#anotacoes div:last").after('div');
	        var date_json_return = js.direto.parseDate(d.dataHora);
	        //alert(date_json_return);
	        
			retorno = js.direto.compareDate(date_json_return,last_date);
			//alert(retorno);
	        if( retorno == 1){
	        	
	        	var arAcao = d.acao.split(" ");
	        	//alert(arAcao.length+" - "+arAcao[0]);
	        	if(arAcao[0].length<50){
	        	
	        	//alert(d.acao.length);
	        	
					var txt = "";
					txt = txt + "<strong>["+d.carteira+"] ["+d.usuNGuerra+"]</strong> - ";
					txt = txt + d.acao;
					txt = txt + " - <span id='data_despacho'>"+d.dataHora+"</span>";
			        
			        $j('#'+type+' div:last').after("<div id='div_despachos'></div>");
			        $j('#'+type+' div').last().hide();
			        $j('#'+type+' div').last().html(txt);
			        $j('#'+type+' div').last().addClass('celula '+singular);
			        //$j('#'+type+' div').last().remove();
			        $j('#'+type+' div').last().fadeIn("slow");
		        
	        	}
	       }
			//$j('<div>teste</div>').insertAfter($('#div_anotacoes div:last'));
	      });
	    
	    setTimeout(function(){
	    	IS_UPDATES_ACTIONS = false;
			},300);
	    
	    });
};


js.direto.sel_chkbox_doc = function(id) {
	
	if (id < 16){
	
		var chkid = "chk"+id;
		var div_doc_id = "div_doc"+id;
		var sel = document.getElementById(chkid);
		var div = document.getElementById(div_doc_id);
			if (sel.checked) {
				div.style.backgroundColor = "#FFFFCC";
			}else{
				div.style.backgroundColor = "";
			}
	}else{
		var sel_tudo = document.getElementById("sel_tudo");
		var chk;
		var total = id;
		
		if (sel_tudo.checked){
			for (i=0;i<total;i++){
				chk = "chk"+i;
				
				chkbox_doc = document.getElementById(chk);
				
				if (chkbox_doc != null){
					chkbox_doc.checked = "yes";
					document.getElementById("div_doc"+i).style.backgroundColor = "#FFFFCC";
				}
			}
		}else{
			for (i=0;i<total;i++){
				chk = "chk"+i;
				
				chkbox_doc = document.getElementById(chk);
				
				if (chkbox_doc != null){
					document.getElementById(chk).checked = "";
					document.getElementById("div_doc"+i).style.backgroundColor = "";
				}
			}

		}

	}	
};


var lbUsuarios_len = "";
var lbUsuariosSel_len = "";
var ListaDE_len = "";
var ListaPARA_len = "";

function mover (ListaDE,ListaPARA) {
	
	ListaDE_len = ListaDE.options.length;
	ListaPARA_len = ListaPARA.options.length;

	for(var i=0; i<ListaDE.options.length; i++) {
		if (ListaDE.options[i].selected && ListaDE.options[i].value != "-1") {
			var no = new Option();
			no.value = ListaDE.options[i].value;
			no.text = ListaDE.options[i].text;
			
			
			if(ListaPARA.options.length>0){
			
				var exists = false;
				var count = 0;
				
				//alert("Tamanho da lista"+ListaPARA.options.length);
				
				while((count < ListaPARA.options.length) && (!exists)){
					if(no.value == ListaPARA.options[count].value){
						exists = true;
					}	
					count++;
				}
				
							
				if (!exists){
					ListaPARA.options[ListaPARA.options.length] = no;
					ListaDE.options[i].value = "";
					ListaDE.options[i].text = "";
					LimpaVazios(ListaDE, ListaDE.options.length);
					LimpaVazios(ListaPARA, ListaPARA.options.length);
				}else{
					alert("Os docs. pertencem agora a carteira e não mais ao usuário,\nsendo que esta já está incluída na lista de destinatários.");
				}
		
			}else{
				ListaPARA.options[ListaPARA.options.length] = no;
				ListaDE.options[i].value = "";
				ListaDE.options[i].text = "";
				LimpaVazios(ListaDE, ListaDE.options.length);
				LimpaVazios(ListaPARA, ListaPARA.options.length);
			}
			
		}
	}
	
}


function remover(ListaDE,ListaPARA) {
	ListaDE_len = ListaDE.options.length;
	ListaPARA_len = ListaPARA.options.length;

	for(var i=0; i<ListaDE.options.length; i++) {
		if (ListaDE.options[i].selected && ListaDE.options[i].value != "-1") {
			var no = new Option();
			no.value = ListaDE.options[i].value
			no.text = ListaDE.options[i].text
			ListaPARA.options[ListaPARA.options.length] = no;
			ListaDE.options[i].value = ""
			ListaDE.options[i].text = ""
		}
	}
	LimpaVazios(ListaDE, ListaDE.options.length);
	LimpaVazios(ListaPARA, ListaPARA.options.length);
}

// Limpa Vazios
function LimpaVazios(box, box_len) {
	for(var i=0; i<box_len; i++)  {
		if(box.options[i].value == "")  {
			var ln = i;
			box.options[i] = null;
			break;
		}
	}

	if(ln < box_len)  {
		box_len -= 1;
		LimpaVazios(box, box_len);
	}
}


/** MODAL */
js.direto.modal = function(obj){

	var winW = 1002;

	var id = $j(obj).attr('href');
	
	if(id == "#wgrupos"){
		carregaGrupos();
	}
	
	if (id == "#wcontas"){
		carregaCarteiras("wcontas");
		var principal = 0;
		var contas = '';
		setTimeout(function(){
			$j('#table_users input:checkbox').each(function(i){
				
				if (i==0){
					principal = $j(this).attr('id');
					
					$j('#slContas option').each(function(){
						if ($j(this).val() == principal)
							$j(this).attr('selected','selected');
							
					});
					
				}else{
					contas += $j(this).attr('id')+',';
				}
				
			});
			
			var arContas = contas.split(',');
			
			$j('#ListaDE option').each(function(){
				
				if( $j(this).val() == principal ){
					$j(this).remove();
				}else{
					
					for (var i = 0; i<arContas.length-1;i++){
						
						if( $j(this).val() == arContas[i]){
							var oODeTxt = $j(this).text();
							var oODeVal = $j(this).val();
							$j(this).remove();
							$j('#ListaPARA').append('<option value="'+oODeVal+'">'+oODeTxt+'</option>');
							//mover($('ListaDE'), $('ListaPARA'));
							
						}	
					}
				}
			
			});
			
		},5000);
		
		setTimeout(function(){$j('#ListaPARA').attr('disabled','');
		$j('#ListaDE').attr('disabled','');},2500);
	}
	
	if (id == "#waddcarteira"){
		carregaFuncoes('slFuncao');
		carregaSecoes('slSecao');
		carregaOm('slOm');
	}
	
	if (id == "#weditcarteira"){
		carregaCarteiras("slCarteira");
	}
	
	if (id == "#wgercarteira"){
		carregaCarteiras("CarteirasListaAll");
	}

	var maskHeight = $j(document).height();
	var maskWidth = $j(window).width();


	$j('#mask').css({'width':maskWidth,'height':maskHeight});

		
	$j('#mask').fadeIn(1000);	
	$j('#mask').fadeTo("slow",0.8);	

	var pos = $j(obj).offset();
	$j(id).css('top',  pos.top-20);
	$j(id).css('left', winW/2-$j(id).width()/2);

	$j(id).fadeIn(100); 

};


function errorAlert(error){

	$j( "#error-message" ).html('<span style="position: absolute; top: 37px;" class="ui-icon ui-icon-alert"></span>'+
			'<p style="float: left; padding-left: 30px; padding-top: 15px;">'+error+
			'</p>');

	$j( "#error-message" ).dialog({
		modal: true,
		buttons: {
			Ok: function() {
				$j( this ).dialog( "close" );
			}
		}
	});
	
}

function alertMessage(title,message,isOwnFormat){
	
	var ele = $j( "#dialog-message" );

	if (isOwnFormat){
		ele.html(message);
	}else{
		ele.html('<p style="text-align:center;" class="ui-state-error ui-corner-all"><span class="ui-icon ui-icon-alert" '+ 
				'style="float:left; margin:0 7px 50px 0;"></span>'+
				message+'</p>');
	}
	
	ele.dialog({
		modal: true,
		buttons: {
			Ok: function() {
				Notifications.checkForPermission();
				$j( this ).dialog( "close" );
			}
		}
	});
	
	ele.dialog( "option", "title", title );
}


function dialogMessage(title,message,isOwnFormat){
	
	var ele = $j( "#dialog-message" );
	
	
	if (isOwnFormat){
		ele.html(message);
	}else{
		ele.html('<p><span class="ui-icon ui-icon-circle-check" '+ 
				'style="float:left; margin:0 7px 50px 0;"></span>'+
				message+'</p>');
	}
	
	/*ele.dialog({
		modal: true
	});*/
	ele.dialog();
	
	ele.dialog( "option", "title", title );
}

function teclaEnter(e){
	
	var evento = (window.event ? e.keyCode : e.which);

	if(evento == 13) {
		alert("Clique no botão OK ou Validar.");
		return false;
	}

//alert(e);
}


