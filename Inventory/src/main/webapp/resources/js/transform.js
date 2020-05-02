$(document).ready(function(){		
		  $("#transformViewModel").dialog({
		    autoOpen: false,
		    modal: true,	  		  	
		    width: 900,
		    height:600,
		    show:"fade",
          hide: "fold",
          draggable: true
		});	 

		//Notification Remove
		$("#transformRemovebtn").click(function(){
	  		var id = $('input[name=srado]:checked').val(); 
	  		$("#transformId").val(id);
	  		$( "#transformdelform" ).submit();
	  	});  	
			
		
		$(".close").click(function(){
	  		$("#notificationUpdateModel").dialog("close");$("#transformViewModel").dialog("close");
	  		$("#notificationPublishModel").dialog("close");
	  	});
	
		
}); //document ready end



function viewTransformModel(id){
	//alert("kkk");
	 $.ajax({
		   url: 'getTransformModelData.do',
		   type: 'GET',
		   data: 'transformId='+id,
		   dataType: "json",
		   success: function(data) { 
			   try{	$("#tvtitle").html(data.title);
			   $("#tvcategory").html(data.categoryName);
			   $("#tvtdesc").html(data.tagDesc);  			  
			   $("#tvdt").html(data.createdOn);
			   $("#tvsource").html(data.sourceName);
			   $("#tvtarea").html(data.therapeuticName);			   
			   $("#tvndesc").html(data.newsDesc); 
			   }catch(err){
			   }
		   },
		   error: function(e) {
		   }
		 });   	 
	
	 
	   $(".ui-dialog-titlebar").hide();
	  $("#transformViewModel").dialog("open");
}





