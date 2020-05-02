$(document).ready(function(){		
		  $("#healthtipViewModel").dialog({
		    autoOpen: false,
		    modal: true,	  		  	
		    width: 900,
		    height:600,
		    show:"fade",
          hide: "fold",
          draggable: true
		});	 

		//Notification Remove
		$("#healthtipRemovebtn").click(function(){
	  		var id = $('input[name=srado]:checked').val(); 
	  		$("#healthTipId").val(id);
	  		$( "#healthtipdelform" ).submit();
	  	});  	
			
		
		$(".close").click(function(){
	  		$("#notificationUpdateModel").dialog("close");$("#healthtipViewModel").dialog("close");
	  		$("#notificationPublishModel").dialog("close");
	  	});
	
		
}); //document ready end



function viewHealthtipModel(id){
	//alert("kkk");
	 $.ajax({
		   url: 'getHealthTipData.do',
		   type: 'GET',
		   data: 'healthtipId='+id,
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
	  $("#healthtipViewModel").dialog("open");
}





