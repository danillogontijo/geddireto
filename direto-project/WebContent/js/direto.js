/**
 * Declara um objeto para poder adicionar as funcoes
 */
if (window['dojo']) dojo.provide('js.direto');
if (typeof window['js'] == 'undefined') window.js = {};
if (typeof dwr['direto'] == 'undefined') js.direto = {};


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
					alert("Usuário já incluído na lista de destinatários.");
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

	