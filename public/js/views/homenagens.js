
 $(function () {
	 
	$('#pais_id').change(function() {
		var paisId = $(this).val();
		
		//Remove todos os options, menos o vazio
		$('#estado_id option[value!=""]').remove();
    
		if(paisId != "") {
			myJsRoutes.controllers.Homenagens.listarEstados(paisId).ajax({
				success : function(result) {
					//$('#estado_id').removeAttr("disabled");
					
					$.each(result, function(index, data) {
						$('#estado_id').append(
							$('<option></option>').val(data.id).html(data.nome)
						);
					});
				}
			});
		} else {
			//$('#estado_id').attr('disabled','disabled');
		}
		
		return false;
	});
	
	$('#estado_id').change(function() {
		var estadoId = $(this).val();
		
	    //Remove todos os options, menos o vazio
		$('#cidade_id option[value!=""]').remove();
	    
		if(estadoId != "") {
			myJsRoutes.controllers.Homenagens.listarCidades(estadoId).ajax({
				success : function(result) {
					//$('#cidade_id').removeAttr("disabled");
					
					$.each(result, function(index, data) {
						$('#cidade_id').append(
							$('<option></option>').val(data.id).html(data.nome)
						);
					});
				}
			});
		}else {
			//$('#cidade_id').attr('disabled','disabled');
		}
	    
		return false;
	});

});