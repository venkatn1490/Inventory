$(document).ready(function(){	
	 $('#ptareaselectid').multipleSelect();
	 $('#pdocsselectid').multipleSelect();
		  $("#notificationViewModel,#notificationUpdateModel,#notificationPublishModel").dialog({
		    autoOpen: false,
		    modal: true,	  		  	
		    width: 900,
		    height:600,
		    show:"fade",
          hide: "fold",
          draggable: true
		});	 

		//Notification Remove
		$("#notificationRemovebtn").click(function(){
	  		var id = $('input[name=srado]:checked').val(); 
	  		$("#notificationDelId").val(id);
	  		$( "#notificationdelform" ).submit();
	  	});  	
			
		
		$(".close").click(function(){
	  		$("#notificationUpdateModel").dialog("close");$("#notificationViewModel").dialog("close");
	  		$("#notificationPublishModel").dialog("close");
	  	});
		
		$("#notificationdownloadbtn").click(function(){
			var id = $('input[name=srado]:checked').val(); 
			if(id == '' || id === undefined) {
				alert("Please select a Notification");
			}else {
				$("#notificationStatId").val(id);			
				$("#notificationstat").submit();
			}
	  	});  
	
		
}); //document ready end



function viewNotificationModel(id){
	slide = [];
	index = 1;
	var imgId = 1;
	 $.ajax({
		   url: 'getNotificationModelData.do',
		   type: 'GET',
		   data: 'notificationId='+id,
		   dataType: "json",
		   success: function(data) { 
			   try{		  		
				   
			   $("#nvtitle").html(data.notificationName);
			   $("#nvdesc").html(data.notificationDesc);  			  
			   $("#nvdt").html(data.createdOn);
			   $("#nvcompany").html(data.companyName);
			   $("#nvtarea").html(data.therapeuticName);
			   
			   $("#nvsent").html(data.totalSentNotification);
			   $("#nvview").html(data.totalViewedNotifcation);
			   $("#nvpending").html(data.totalPendingNotifcation);
			   $("#nvappoints").html(data.totalConvertedToAppointment);
			  /* $("#nvremainders").html(data.totalnotificationRemainders);
			   $("#nvfavorites").html(data.totalCntFavouriteNotification);*/
			    
			   var ddata = data.notificationDetails;
			   var ht = '';
			    for(var i in ddata)
			    {
			       var dtitle = ddata[i].detailTitle;
			       var ddesc = ddata[i].detailDesc;
			       ht = ht + '<dt> Notification Detail Name </dt> <dd> '+ dtitle +'</dd> <dt> Notification Detail Description </dt><dd>'+ddesc +'</dd>';
			       imgId = ddata[0].detailId;
			     slide.push({ 
			        "src" : 'getNotificationContent.do/'+ddata[i].detailId+'/',
			        "des"  : ddata[i].detailDesc
			        
			    });
			     
			     }
			    
			    var fulr ='getNotificationContent.do/'+imgId+'/';
				$("#slider").attr("src",fulr);
			   
			    $("#detailsViewId").html(ht);
			   }catch(err){
			   }
		   },
		   error: function(e) {
		   }
		 });   	 
	
	 
	   $(".ui-dialog-titlebar").hide();
	  $("#notificationViewModel").dialog("open");
}

function getAllTherapeuticData(type){
	 $("#therapeuticAreaId").html('');
	 $("#nutharea").html('');    
	 var id ='0';
	 if(type == 'create'){
		 id = document.getElementById("companySelectedId").value;
	 }else{
		 id = document.getElementById("nucompany").value;
	 }   	
	 
	 $.ajax({
		   url: 'getAllTherapeutics.do',
		   type: 'GET',
		   data: 'companyId='+id,    		   
		   success: function(data) { 
			   if(type == 'create'){
				   $("#therapeuticAreaId").append(data);
			   }else{
				   $("#nutharea").append(data);
			   }
			  
		   },
		   error: function(e) {
		   }
		 });   	 
}

function validateForm(){
	var uname=document.getElementById("userName-v").value;
	var cny=document.getElementById("companySelectedId").value;
	var tarea=document.getElementById("therapeuticAreaId").value;
	var st=document.getElementById("ncstat").value;
	var unamesub=document.getElementById("userName-v-sub").value;
	if(uname == ''){
		alert("Please select a Notification Name");
		return false;
	}
	if(cny == '' || cny == '0'){
		alert("Please select Company");
		document.getElementById("companySelectedId").focus();
		return false;
	}
	
	if(tarea =='' || tarea =='0'){
		alert("Please select Therapeutic Area");
		document.getElementById("therapeuticAreaId").focus();
		return false;
	}
	if(st.indexOf('Select')>0){
		 alert("Please select Status");
		 document.getElementById("ncstat").focus();
		 return false;
	}	
	if(unamesub == ''){
		alert("Please select a Detail Name");
		document.getElementById("userName-v-sub").focus();
		return false;
	}
	return true;
}

function validateUpdateForm(){
	var uname=document.getElementById("nuname").value;
	var cny=document.getElementById("nucompany").value;
	var tarea=document.getElementById("nutharea").value;
	var st=document.getElementById("nustauts").value;
	
	if(uname == ''){
		alert("Please select a Notification Name");
		return false;
	}
	if(cny == '' || cny == '0'){
		alert("Please select Company");
		document.getElementById("companySelectedId").focus();
		return false;
	}
	
	if(tarea =='' || tarea =='0'){
		alert("Please select Therapeutic Area");
		document.getElementById("therapeuticAreaId").focus();
		return false;
	}
	if(st.indexOf('Select')>0){
		 alert("Please select Status");
		 document.getElementById("ncstat").focus();
		 return false;
	}	
	
	return true;
}

