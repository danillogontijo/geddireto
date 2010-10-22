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
}

	