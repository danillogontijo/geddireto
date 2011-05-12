  

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

			var nrProtocolo = $j('td:eq(1)', nRow).text();
			var id = nrProtocolo.substring(6);
			var text = $j('td:eq(1)', nRow).text();
			
			$j('td:eq(1)', nRow).html('<a href="view.html?id='+id+'">'+text+'</a>');
			
			return nRow;
		},

		"aoColumns": [ 
					/* ID */   { "bSearchable": false,
			              			"bVisible":    false },
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



	//function inicializa(){
	//$j("#example tbody").each( function (i) {

		

		//alert($j(this).text());
		

	//addLinks();

	/* Apply the tooltips */
	


	

	function addLinks(){

		setTimeout(function (){
			//alert('links');
			$j(oTable.fnSettings().aoData).each(function (){
				var nTr = $j(this.nTr);
				var nTds = $j('td', nTr);
	
				var nrProtocolo = $j(nTds[1]).text();
				var id = nrProtocolo.substring(6);
	
				$j(nTds).each( function(i) {
					var text = $j(nTds[i]).text();
					$j(nTds[i]).html('<a href="view.html?id='+id+'">'+text+'</a>');
				});	
				
			});

			$j("#pesquisaDataTables_paginate span").click(function() {
				setTimeout(function (){addLinks();},100);
			});

			$j('select[name=pesquisaDataTables_length]').click(function() {
				setTimeout(function (){addLinks();},100);
			});

		},5000);

	};
		
		
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


		//alert($j(event.target.parentNode).children().eq(1).text());
		
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








