  

<%@ include file="include_head.jsp" %>
<style type="text/css" title="currentStyle"> 
			@import "css/dataTable/demo_table.css";
</style> 
<script src="<%=request.getContextPath() %>/js/dataTable/jquery.dataTables.min.js"></script>

<script type="text/javascript" charset="utf-8">
var asInitVals = new Array();

$j(function(){

	//alert('${total}');
	
	var oTable = $j('#pesquisaDataTables').dataTable( {
		"iDisplayLength": 10,
		"bProcessing": true,
		"bServerSide": ${bServerSide},
		"oLanguage": {
			"sUrl": "js/dataTable/pt_BR.txt"
		},

		"fnRowCallback": function( nRow, aData, iDisplayIndex ) {

			var id = $j('td:eq(0)', nRow).text();
			
			$j('td', nRow).each(function (){
				var text = $j(this).text();
				$j(this).html('<a href="view.html?id='+id+'">'+text+'</a>');
			});
			
			
			return nRow;
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

		 //"aLengthMenu": [[10, 15, -1], [10, 15, "All"]],
		 "aLengthMenu": [10, 15, 20],
		  		      		 		
		
		"sPaginationType": "full_numbers",
		"sAjaxSource": "/direto-project/resultado.html?total=${total}&bServerSide=${bServerSide}"

	} );


	$j("tfoot input").keyup( function () {
		var subtrai = 0;
		if(${bServerSide})
			subtrai = 1;
		/* Filter on the column (the index) of this element */
		oTable.fnFilter( this.value, eval($j("tfoot input").index(this)-subtrai) ); //subtraia -1 caso exista um input nao searchable
	} );
	
	
	
	/*
	 * Support functions to provide a little bit of 'user friendlyness' to the textboxes in 
	 * the footer
	 */
	$j("tfoot input").focus( function () {
		if ( this.className == "field_inputs search_init field_inputs_shadow" )
		{
			this.className = "field_inputs search_init field_inputs_active";
			this.value = "";
		}
	} );
	
	$j("tfoot input").blur( function (i) {
		if ( this.value == "" )
		{
			this.className = "field_inputs search_init field_inputs_shadow";
			this.value = asInitVals[$j("tfoot input").index(this)];
		}
	} );

	$j("tfoot input").each( function (i) {
		
		asInitVals[i] = this.value;
		$j(this).addClass("field_inputs_shadow");
	});
	

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
		
		
<table cellpadding="0" cellspacing="0" border="0" class="display" id="pesquisaDataTables"> 
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
			<th><input type="hidden" name="search_id" value="Id" class="field_inputs search_init" /></th>
			<th><input type="text" name="search_tipo" value="Tipos" class="field_inputs search_init" /></th> 
			<th><input type="text" name="search_protocolo" value="Protocolo" class="field_inputs search_init" /></th> 
			<th><input type="text" name="search_nrdoc" value="Nr. Doc" class="field_inputs search_init"/></th> 
			<th><input type="text" name="search_assunto" value="Assunto" class="field_inputs search_init" /></th> 
			<th><input type="text" name="search_data" value="Data" class="field_inputs search_init" /></th> 
		</tr> 
	</tfoot> 
</table> 
		
		
			
		
		
</div>
<!-- FIM CORPO DE LISTA DOCUMENTOS E PESQUISA-->
		
<%@ include file="include_foot.jsp" %>	








