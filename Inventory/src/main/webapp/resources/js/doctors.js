$(document).ready(function() {

	$("#updateDoctorModel").dialog({
		autoOpen : false,
		modal : true,
		width : 900,
		height : 600,
		show : "fade",
		hide : "fold",
		draggable : true
	});

	$("#createsubmitbuttn").click(function() {
		//validation

		$.ajax({
			url : homeUrl + 'preapi/registration/signupDoctor',
			type : 'POST',
			data : formToJSON(),
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				if (data.status == 'OK') {
					alert("Congratulations and thank you for joining us!");
					$("#createDoctorModel").dialog().dialog('close');
					location.reload();
				} else if (data.status == 'Fail') {
					alert(data.message + ". Please try again .....");
				}
			},
			error : function(e) {
				alert(e.message + " Please try again......");
			}
		});
	});

	$("#updatesubmitbuttn").click(function() {
		//validation
		if(updatepage==1)
		 updatepage=  document.getElementById('locationpage').value;
		
			
		$.ajax({
			url : 'updateDoctor.do',
			type : 'POST',
			data : updateFormToJSON(),
			//dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				if (data == 'success') {
					alert("Doctor Updated Successfully ");
					$("#updateDoctorModel").dialog().dialog('close');
					location.reload();
				} else if (data == 'fail') {
					alert(data.message + ". Please try again .....");
				}
			},
			error : function(e) {
				alert(e.message + " Please try again......");
			}
		});
	});

	

	//close 
	$(".close").click(function() {
		$("#updateDoctorModel").dialog("close");
	});

}); //document ready end

function formToJSON() {
	return JSON.stringify({
		"username" : $("#demail").val(),
		"emailId" : $("#demail").val(),
		"roleName" : "Doctor",
		"roleId" : 2,
		"password" : $("#dpass").val(),
		"firstName" : $("#dfname").val(),
		"lastName" : $("#dlanme").val(),
		"middleName" : $("#dmname").val(),
		"title" : $("#dtitle").val(),
		"mobileNo" : $("#dmob").val(),
		"registrationYear" : $("#dregyr").val(),
		"registrationNumber" : $("#dregno").val(),
		"stateMedCouncil" : $("#dmcounc").val(),
		"therapeuticId": $("#createdoctarea").val(),
		"locations" : getLocationsArray()
		
	});

}

function getLocationsArray() {
	slide = [];
	//alert("page value"+page);
	slide.push({
		"address1" : $("#daddr").val(),
		"address2" : $("#daddr1").val(),
		"city" : $("#dcity").val(),
		"state" : $("#dstate").val(),
		"country" : $("#dcountry").val(),
		"zipcode" : $("#dzip").val(),
		"locationType":$("#dloctyType").val()
		

	});
	for(var i=2;i<=page;i++)
		{
		slide.push({
			"address1" : $("#daddr"+i).val(),
			"address2" : $("#daddr1"+i).val(),
			"city" : $("#dcity"+i).val(),
			"state" : $("#dstate"+i).val(),
			"country" : $("#dcountry"+i).val(),
			"zipcode" : $("#dzip"+i).val(),
			"locationType":$("#dloctyType"+i).val()

		});
		}
	
	return slide;
}

function updateFormToJSON() {
	return JSON.stringify({		
		"emailId" : $("#duemail").val(),
		"firstName" : $("#dufname").val(),
		"lastName" : $("#dulanme").val(),
		"middleName" : $("#dumname").val(),
		"title" : $("#dutitle").val(),
		"mobileNo" : $("#dumob").val(),
		"registrationYear" : $("#duregyr").val(),
		"registrationNumber" : $("#duregno").val(),
		"stateMedCouncil" : $("#dumcounc").val(),
		"username" : $("#docloginid").val(),
		"doctorId" : $("#docid").val(),
		"therapeuticId": $("#updatedoctarea").val(),
		"locations" : getUpdateLocationsArray(),
		
	});

}
function getUpdateLocationsArray() {
	slide = [];
	slide.push({
		"address1" : $("#duaddr").val(),
		"address2" : $("#duaddr1").val(),
		"city" : $("#ducity").val(),
		"state" : $("#dustate").val(),
		"country" : $("#ducountry").val(),
		"zipcode" : $("#duzip").val(),
		"locationType":$("#duloctyType").val()

	});
	for(var i=1;i<updatepage;i++)
		{
		j=i+1;
		slide.push({
			"address1" : $("#duaddr"+j).val(),
			"address2" : $("#duaddr1"+j).val(),
			"city" : $("#ducity"+j).val(),
			"state" : $("#dustate"+j).val(),
			"country" : $("#ducountry"+j).val(),
			"zipcode" : $("#duzip"+j).val(),
			"locationType":$("#duloctyType"+j).val()

		});

		}
	return slide;
}
function clearData(type) {
	if (type == 'createdoctor') {
		$("#demail").val("");
		$("#demail").val("");
		$("#dpass").val("");
		$("#dfname").val("");
		$("#dlanme").val("");
		$("#dmname").val("");
		$("#dmob").val("");
		$("#dregyr").val("");
		$("#dregno").val("");
		$("#dmcounc").val("");
		$("#daddr").val("");
		$("#dcity").val("");
		$("#dstate").val("");
		$("#dcountry").val("");
		$("#dloctyType").val("");
	}
}


function viewModel(id,suffix){
	 createEmptyDataModel(suffix);
	   $.ajax({
		   url: 'getDoctor.do',
		   type: 'GET',
		   data: 'doctorId='+id,
		   dataType: "json",
		   success: function(data) {
			   try{
				   var doctorName;
				   if(data.middleName!=null)
					{
					   doctorName = data.firstName+' '+data.middleName+' '+data.lastName;
					}
				   else
					{
					   doctorName = data.firstName+' '+data.lastName;
					}
			   $("#docProfilePicture"+suffix).html('');
			   $("#docDoctorId"+suffix).attr("value",data.doctorId);
			   $("#docName"+suffix).html(doctorName);
			   $("#docRegistrationNumber"+suffix).html(data.registrationNumber);
			   $("#doctherapeuticName"+suffix).html(data.therapeuticName);
			   $("#docMobileNo"+suffix).html(data.mobileNo);
			   $("#docEmailId"+suffix).html(data.emailId);
			   $("#docAlternateEmailId"+suffix).html(data.alternateEmailId);
			   if(data.profilePicture!=null){
			   		$("#docProfilePicture"+suffix).html("<center><img src='"+data.dPicture+"' alt='Profile picture' style='max-height:90px'></center>");
			   }
		        var locationString="";
		        var loop = 1;
		        $.each(data.locations, function(i, location) {
		        	if(i==0)
		        	{
		        		locationNo = "Address "+(i+1);
		        		locationValue = location.address1+"<br>"+location.address2+"<br>"+location.city+"<br>"+location.state+"<br>"+location.country+"<br>"+location.zipcode;
		        	}
		        	else
		        	{
		        		locationString = locationString+"<dt id='docLocationdyn'>Address "+(i+1)+"</dt><dd id='docLocationdyn'>"+location.address1+"<br>"+location.address2+"<br>"+location.city+"<br>"+location.state+"<br>"+location.country+"<br>"+location.zipcode+"</dd>";
		        	}
		        });
		       $("#docLocationNo"+suffix).html(locationNo);
		       $("#docLocation"+suffix).html(locationValue);
		       $("#dnotif"+suffix).html(data.totalnotificationviewd);
		       $("#dappointm"+suffix).html(data.totalNoappointents);
		       $("#dsur"+suffix).html(data.totalnosurveys);
		       $("#dfeedback"+suffix).html(data.totalnooffeedback);
		       $("#dl-horizontal-id").append(locationString)
			   }catch(err){

			   }
		   },
		   error: function(e) {
		   }
		 });
	   
	   function trackactivity()
	   {
		   
	   }

	 //Create Empty Model
		 function createEmptyDataModel(suffix){
			 $("#docProfilePicture"+suffix).html('');
			   $("#docDoctorId"+suffix).attr("value",'');
			   $("#docName"+suffix).html('');
			   $("#docRegistrationNumber"+suffix).html('');
			   $("#doctherapeuticName"+suffix).html('');
			   $("#docMobileNo"+suffix).html('');
			   $("#docEmailId"+suffix).html('');
			   $("#docAlternateEmailId"+suffix).html('');
			   $("#docLocationNo"+suffix).html('');
		       $("#docLocation"+suffix).html('');
		       $("#dloctyType").html('');
		       $("#dnotif"+suffix).html('');
		       $("#dappointm"+suffix).html('');
		       $("#dsur"+suffix).html('');
		       $("#dfeedback"+suffix).html('');
		       if($("#docLocationdyn").length != 0) {
		    	   $("#docLocationdyn").remove();
		    	 }
		      
		 }
		 function viewModel(id){
			
			   $.ajax({
				   url: 'removeDoctors.do',
				   type: 'GET',
				   data: 'doctorId='+id,
				   dataType: "json",
				   success: function(data) {
					   try{
						   var doctorName;
						   if(data.middleName!=null)
							{
							   doctorName = data.firstName+' '+data.middleName+' '+data.lastName;
							}
						   else
							{
							   doctorName = data.firstName+' '+data.lastName;
							}
					   $("#docProfilePicture"+suffix).html('');
					   $("#docDoctorId"+suffix).attr("value",data.doctorId);
					   $("#docName"+suffix).html(doctorName);
					   $("#docRegistrationNumber"+suffix).html(data.registrationNumber);
					   $("#doctherapeuticName"+suffix).html(data.therapeuticName);
					   $("#docMobileNo"+suffix).html(data.mobileNo);
					   $("#docEmailId"+suffix).html(data.emailId);
					   $("#docAlternateEmailId"+suffix).html(data.alternateEmailId);
					   if(data.profilePicture!=null){
					   		$("#docProfilePicture"+suffix).html("<center><img src='"+data.dPicture+"' alt='Profile picture' style='max-height:90px'></center>");
					   }
				        var locationString="";
				        var loop = 1;
				        $.each(data.locations, function(i, location) {
				        	if(i==0)
				        	{
				        		locationNo = "Address "+(i+1);
				        		locationValue = location.address1+"<br>"+location.address2+"<br>"+location.city+"<br>"+location.state+"<br>"+location.country+"<br>"+location.zipcode;
				        	}
				        	else
				        	{
				        		locationString = locationString+"<dt id='docLocationdyn'>Address "+(i+1)+"</dt><dd id='docLocationdyn'>"+location.address1+"<br>"+location.address2+"<br>"+location.city+"<br>"+location.state+"<br>"+location.country+"<br>"+location.zipcode+"</dd>";
				        	}
				        });
				       $("#docLocationNo"+suffix).html(locationNo);
				       $("#docLocation"+suffix).html(locationValue);
				       $("#dnotif"+suffix).html(data.totalnotificationviewd);
				       $("#dappointm"+suffix).html(data.totalNoappointents);
				       $("#dsur"+suffix).html(data.totalnosurveys);
				       $("#dfeedback"+suffix).html(data.totalnooffeedback);
				       $("#dl-horizontal-id").append(locationString)
					   }catch(err){

					   }
				   },
				   error: function(e) {
				   }
				 });
		 }
}