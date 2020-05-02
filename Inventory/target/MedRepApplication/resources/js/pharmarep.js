$(document).ready(function(){
	 $("#createsubmitbuttn").click(function(){
		 //validation
		 
		$.ajax({
	  		   url: homeUrl +'preapi/registration/signupRep',
	  		   type: 'POST',
	  		   data: formToJSON(),
	  		   dataType: "json",
	  		   contentType: "application/json; charset=utf-8",
	  		   success: function(data) {	  			  
	  			   if(data.status == 'OK'){	    alert("Congratulations and thank you for joining us!");
	  			        $("#createPharmaRepModel").dialog().dialog('close');
	  			        location.reload();
	  			   }else if(data.status == 'Fail' || data.status == 'Error'){
	  				    alert(data.message + ". Please try again .....");
	  			   }
	  		   },
	  		   error: function(e) {
	  		 	 alert(e.message + " Please try again......");
	  		   }
	  		 });
	 });
			
}); //document ready end

function formToJSON() {
    return JSON.stringify({
    	"username": $("#premail").val(),
    	"emailId": $("#premail").val(),
    	"roleId": $("#prrole").val(),
        "password": $("#prpass").val(),
        "firstName":  $("#prfname").val(),
        "lastName": $("#prlanme").val(),
        "middleName": $("#prmname").val(),     
        "title":$("#prtitle").val(),
        "mobileNo":  $("#prmob").val(),
        "registrationYear": $("#prregyr").val(),
        "registrationNumber": $("#prregno").val(),
        "coveredArea": $("#prcarea").val(),    
        "coveredZone": $("#prczone").val(),
        "companyId": $("#prcompanyId").val(),
        "therapeuticId": $("#prtherapeuticAreaId").val(),
      //  "managerId": $("#prmid").val(),
        "managerEmail": $("#prmemail").val(),
        "locations" :getLocationsArray()
        }); 

}
function getLocationsArray(){
	slide = [];
	slide.push({ 
        "address1" : $("#praddr").val(),
        "city"  : $("#prcity").val(),
        "state"  : $("#prstate").val(),
        "country"  : $("#prcountry").val(),
        "zipcode":$("#przip").val()
       
        
    });
	return slide;
}

function clearData(type){
	if(type == 'createrep'){
		 $("#premail").val("");$("#prcompanyId").val("");$("#prpass").val("");
		 $("#prfname").val("");$("#prlanme").val("");$("#prmname").val("");
		 $("#prmob").val("");$("#prregyr").val("");$("#prregno").val("");
		 $("#prcarea").val("");$("#praddr").val(""); $("#prcity").val("");
		 $("#prstate").val("");$("#prcountry").val("");$("#prtherapeuticAreaId").val("");
		 $("#prrole").val("");$("#prmemail").val(""); $("#prczone").val("");
		   
	}
}

function getAllTherapeuticData(){
	 $("#prtherapeuticAreaId").html('');
	 var id =document.getElementById("prcompanyId").value;
	 if(id == ''){
		 id = 0;
	 }	
	 
	 $.ajax({
		   url: 'getAllTherapeutics.do',
		   type: 'GET',
		   data: 'companyId='+id,    		   
		   success: function(data) { 
				   $("#prtherapeuticAreaId").append(data);
		   },
		   error: function(e) {
		   }
		 });   	 
}
