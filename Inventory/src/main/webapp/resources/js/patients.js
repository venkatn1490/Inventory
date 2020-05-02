

function viewPatients(id){
	
	   $.ajax({
		   url: 'patients.do',
		   type: 'GET',
		 data: 'patientId='+id,
		   dataType: "json",
		   success: function(data) {
			   try{
			  
		   $("#patientName").html(data.patientName);
		   $("#patientGender").html(data.sex);
		   $("#patientDateOfBirth").html(data.dateofBirth);
		   $("#patientBloodGroup").html(data.bloodGroup);
		   $("#patientHeight").html(data.height);
		   $("#patientWeight").html(data.weight);
		   $("#patientMarriageStatus").html(data.marriedstatus);
		   $("#patientAadhar").html(data.aadharCardNumber);
		   $("#patientMobileNo").html(data.mobileNo);
		   $("#paientEmailId").html(data.emailId);
		   $("#patientAlternateEmailId").html(data.alternateEmailId);
		   /*if(data.profilePicture!=null){
		   		$("#docProfilePicture"+suffix).html("<center><img src='"+data.dPicture+"' alt='Profile picture' style='max-height:90px'></center>");
		   }*/
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
	       $("#patientLocationNo").html(locationNo);
	       $("#patientLocation").html(locationValue);
	       /*$("#dnotif"+suffix).html(data.totalnotificationviewd);
	       $("#dappointm"+suffix).html(data.totalNoappointents);
	       $("#dsur"+suffix).html(data.totalnosurveys);
	       $("#dfeedback"+suffix).html(data.totalnooffeedback);
	       $("#dl-horizontal-id").append(locationString)*/
		   }catch(err){

		   }
		   },
		   error: function(e) {
		   }
	   		});
}


		 