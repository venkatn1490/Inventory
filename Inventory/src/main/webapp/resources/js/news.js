$(document).ready(function(){		
		  $("#newsViewModel").dialog({
		    autoOpen: false,
		    modal: true,	  		  	
		    width: 900,
		    height:600,
		    show:"fade",
          hide: "fold",
          draggable: true
		});	 

		//Notification Remove
		$("#newsRemovebtn").click(function(){
	  		var id = $('input[name=srado]:checked').val(); 
	  		$("#newsId").val(id);
	  		$( "#newsdelform" ).submit();
	  	});  	
			
		
		$(".close").click(function(){
	  		$("#notificationUpdateModel").dialog("close");$("#newsViewModel").dialog("close");
	  		$("#notificationPublishModel").dialog("close");
	  	});
	
		
}); //document ready end



function viewNewsModel(id){
	 $.ajax({
		   url: 'getNewsModelData.do',
		   type: 'GET',
		   data: 'newsId='+id,
		   dataType: "json",
		   success: function(data) { 
			   try{	$("#nvtitle").html(data.title);
			   $("#nvtdesc").html(data.tagDesc);  			  
			   $("#nvdt").html(data.createdOn);
			   $("#nvsource").html(data.sourceName);
			   $("#nvtarea").html(data.therapeuticName);			   
			   $("#nvndesc").html(data.newsDesc); 
			   }catch(err){
			   }
		   },
		   error: function(e) {
		   }
		 });   	 
	
	 
	   $(".ui-dialog-titlebar").hide();
	  $("#newsViewModel").dialog("open");
}





