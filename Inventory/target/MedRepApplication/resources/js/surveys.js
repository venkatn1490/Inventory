$(document).ready(function(){

	 $('#ptareaselectid').multipleSelect();
	 $('#pdocsselectid').multipleSelect();

	 $("#surveyViewModel,#surveyUpdateModel").dialog({
		    autoOpen: false,
		    modal: true,
		    width: 900,
		    height:600,
		    show:"fade",
            hide: "fold",
            draggable: true
		});


		$("#surveyUpdatebtn").click(function(){
	  		var id = $('input[name=srado]:checked').val();
	  		if(typeof id === 'undefined'){
	  			alert("Please select any one of the Survey");
	  			return;
	  		}
	  		 $.ajax({
	  		   url: 'getSurveyModelData.do',
	  		   type: 'GET',
	  		   data: 'surveyId='+id,
	  		   dataType: "json",
	  		   success: function(data) {
	  			   try{
	  				 $("#smid").val(data.surveyId);
	  			   $("#smname").val(data.surveyTitle);
	  			   $("#smthe").append(data.therapeuticDropDownValues);
	  			   $("#smurl").attr("href", data.surveyUrl);
	  			   $("#smurl").html(data.surveyUrl);
	  			  $("#smcompany").val(data.companyId);
	  			   $("#smdesc").val(data.surveyDescription);
	  			   $("#smthe").val(data.therapeuticId);
	  			 $("#smstatus").val(data.status);

	  			   }catch(err){

	  			   }
	  		   },
	  		   error: function(e) {

	  		   }
	  		 });
	  		 $(".ui-dialog-titlebar").hide();
	  		$("#surveyUpdateModel").dialog("open");
	  	});

		$("#surveyRemovebtn").click(function(){
	  		var id = $('input[name=srado]:checked').val();
	  		$("#surveyDelId").val(id);
	  		$( "#surveydelform" ).submit();
	  	});

		$(".close").click(function(){
	  		$("#surveyUpdateModel").dialog("close");$("#surveyViewModel").dialog("close");
	  	});

}); //document ready end




function getAllSurveyTherapeuticData(){
	$("#smthe").html('');
	var id = document.getElementById("smcompany").value;

	 $.ajax({
		   url: 'getAllTherapeutics.do',
		   type: 'GET',
		   data: 'companyId='+id,
		   success: function(data) {
			   $("#smthe").append(data);

		   },
		   error: function(e) {
		   }
		 });
}

//Survey Page code
function viewModel(id){
	   $.ajax({
		   url: 'getSurveyModelData.do',
		   type: 'GET',
		   data: 'surveyId='+id,
		   dataType: "json",
		   success: function(data) {
			   try{
			   $("#sname").html(data.surveyTitle);
			   $("#sdesc").html(data.surveyDescription);
			   $("#scname").html(data.companyName);
			   $("#stname").html(data.therapeuticName);
			   $("#surl").html(data.surveyUrl);
			   $("#sdt").html(data.createdOn);
			   $("#sst").html(data.scheduledStart);
			   $("#ssf").html(data.scheduledFinish);
			   
			   $("#sursent").html(data.totalSent);
			   $("#surview").html(data.totalCompleted);
			   $("#surpen").html(data.totalPending);
			   
			   
			   }catch(err){

			   }
		   },
		   error: function(e) {
		   }
		 });
	   $(".ui-dialog-titlebar").hide();
	  $("#surveyViewModel").dialog("open");
}

function validateForm(){
	var cny=document.getElementById("smcompany").value;
	var thea=document.getElementById("smthe").value;
	var st=document.getElementById("smstatus").value;
	if(cny == '' || cny == '0'){
		alert("Please select Company");
		document.getElementById("smcompany").focus();
		return false;
	}

	if(thea =='' || thea =='0'){
		alert("Please select Therapeutic Area");
		document.getElementById("smthe").focus();
		return false;
	}
	if(st == ''){
		alert("Please select Status");
		document.getElementById("smstatus").focus();
		return false;
	}

	return true;
}

function getAllDocsByTArea() {
	 var selectedValues = $('#ptareaselectid').val();
	 $("#tareasids").val(selectedValues);
	 $("#getdocsbytareaform").submit();
}

