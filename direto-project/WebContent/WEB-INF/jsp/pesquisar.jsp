
    

<%@ include file="include_head.jsp" %>
<style type="text/css" title="currentStyle"> 
			@import "css/dataTable/demo_table.css";
</style> 
<script src="<%=request.getContextPath() %>/js/dataTable/jquery.dataTables.min.js"></script>

<script type="text/javascript" charset="ISO-8859-1">
var asInitVals = new Array();

function ini(){
	//alert("");
	$j('#example tbody tr').each( function(i) {
		/*var nTr = $j(this.nTr);
		var nTds = $j('td', nTr);*/

		var nTds = $j('td', this);
		var nrProtocolo = $j(nTds[2]).text();
		var id = nrProtocolo.substring(6);

		/* Caso queira usar a chave primaria do Documento como ID */
		//var id = $j(nTds[0]).text();
		
		$j(nTds).each( function(i) {

			var text = $j(nTds[i]).text();;
			
			if(i == 0){
				$j(nTds[0]).attr('id',text);
				
			} else {
				$j(nTds[i]).html('<a href="documento.html?id='+id+'">'+text+'</a>');
			}

				
		});	
		
		$j(nTds[0]).html(i+1);
		//alert($j(nTds[0]).text());
	});



	$j("#example_paginate span").click(function(event) {
		
		setTimeout("ini();",1000);		

	});	
}

$j(function(){

	//alert('${total}');
	
	var oTable = $j('#example').dataTable( {
		"iDisplayLength": 5,
		"bProcessing": true,
		"bServerSide": true,
		"oLanguage": {
			"sUrl": "js/dataTable/pt_BR.txt"
		},

		"aoColumns": [ 
					/* ID */   { "bSearchable": false,
			              			"bVisible":    true },
		  			/* Tipo */   null,
		  			/* Protocolo */  null,
		  			/* NrDoc */ 	null,
		  			/* Assunto */  null,
		  			/* Data */    null
		  		], 

		 "aLengthMenu": [[5, 20, -1], [5, 20, "All"]],
		  		      		 		
		
		"sPaginationType": "full_numbers",
		"sAjaxSource": "http://localhost:8080/direto-project/resultado.html?total=${total}&bServerSide=${bServerSide}"

	} );



	//function inicializa(){
	//$j("#example tbody").each( function (i) {

		

		//alert($j(this).text());

		//setTimeout("ini();",3000);

		
		
//	});
	
	/*$j('#example').dataTable( {
		"bProcessing": true,
		//"bServerSide": true,
		"oLanguage": {
			"sUrl": "js/dataTable/pt_BR.txt"
		},
		"sPaginationType": "full_numbers",
		"sAjaxSource": "http://localhost:8080/direto-project/resultado.html"
	} );*/

	//$j('#example').dataTable();

	$j("tfoot input").keyup( function () {
		/* Filter on the column (the index) of this element */
		oTable.fnFilter( this.value, $j("tfoot input").index(this) );
	} );
	
	
	
	/*
	 * Support functions to provide a little bit of 'user friendlyness' to the textboxes in 
	 * the footer
	 */
	$j("tfoot input").each( function (i) {
		
			asInitVals[i] = this.value;
	});
	
	$j("tfoot input").focus( function () {
		if ( this.className == "search_init" )
		{
			this.className = "";
			this.value = "";
		}
	} );
	
	$j("tfoot input").blur( function (i) {
		if ( this.value == "" )
		{
			this.className = "search_init";
			this.value = asInitVals[$j("tfoot input").index(this)];
		}
	} );


	
	
	/*$j('#example tbody tr').each( function() {
		var sTitle;
		var nTds = $j('td', this);

		var sBrowser = $j(nTds[1]).text();
		var sGrade = $j(nTds[4]).text();

		//alert($j(this.nTr).children().eq(0).text());

		//$j(this.nTr).children().eq(0).add('href');
		
		//alert($j(nTds[0]).text());
		//append
		
		//this.setAttribute( 'title', sTitle );
	} );*/
	
	//$j("#example tbody").hover(function(event) {
		/*$j(oTable.fnSettings().aoData).each(function (){
			//alert($j(this.nTd).text());
			//$j(this.nTd).append("<b>danillo</b>");
			var nTr = $j(this.nTr);
			var nTds = $j('td', nTr);
			alert($j(nTds[0]).text());
			
		});*/
	//});	

	


	

	$j("#example tbody").click(function(event) {
		$j(oTable.fnSettings().aoData).each(function (){
			//$(this.nTr).removeClass('row_selected');

			//alert($j(this.nTr).text());
			//alert($j(this.nTr).children().eq(0).text());

			//$j(this.nTr).children().eq(0).add('href');
			
		});
		//$j(event.target.parentNode).addClass('row_selected');

		//alert($j(event.target).text());


		alert($j(event.target.parentNode).children().eq(1).text());
		
	});
	
	//$j( "span", "#corpo" ).button();
	

} );




</script>

<style type="text/css">

#example input{
	text-align: center;
	width: 100%;
}

.conf_id{
	width: 5px;
}

.conf_tipo{
	width: 105px;
}

.conf_protocolo{
	width: 50px;
}

.conf_nrDoc{
	width: 60px;
}

.conf_assunto{
	width: 300px; 
}

.conf_data{
	width: 122px;
}

</style>
		
		
<!-- INICIO CORPO DE LISTA DOCUMENTOS E PESQUISA-->
<div id="corpo" style="width:100%; text-align:center; float: left; position: static; width: 822px; vertical-align: middle;">
		
		
<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"> 
	<thead> 
		<tr>
			<th class="conf_id">ID</th>  
			<th class="conf_tipo">Tipo</th> 
			<th class="conf_protocolo">Protocolo</th> 
			<th class="conf_nrDoc">Nr Doc</th> 
			<th class="conf_assunto">Assunto</th> 
			<th class="conf_data">Data</th> 
		</tr> 
	</thead> 
	
	<tbody> 
		
	</tbody> 
	
	<tfoot> 
		<tr>
			<th><input type="hidden" name="search_id" value="Id" class="search_init" /></th>
			<th><input type="text" name="search_tipo" value="Tipos" class="search_init" /></th> 
			<th><input type="text" name="search_protocolo" value="Protocolo" class="search_init" /></th> 
			<th><input type="text" name="search_nrdoc" value="Nr. Doc" class="search_init"/></th> 
			<th><input type="text" name="search_assunto" value="Assunto" class="search_init" /></th> 
			<th><input type="text" name="search_data" value="Data" class="search_init" /></th> 
		</tr> 
	</tfoot> 
</table> 
		
		<form action="http://localhost:8080/direto-project/resultado.html"
			method="get">
		<input type="text" value="Ofí" name="sSearch" />
		<input type="hidden" value="9" name="total">
		<input type="hidden" value="true" name="bServerSide">
		<input type="submit" value="enviar">
		</form>
			
		
		
</div>
<!-- FIM CORPO DE LISTA DOCUMENTOS E PESQUISA-->
		
<%@ include file="include_foot.jsp" %>	