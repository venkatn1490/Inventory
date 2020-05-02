/**
 * 
 */


function getAllDistrict(){
	   var id = document.getElementById("stateSelectedId").value;
	   $("#districtSelectedId").html('');
	   var select = document.getElementById("districtSelectedId");
	 $.ajax({
		   url: 'getAllDistrict.do',
		   type: 'GET',
		   data: 'stateId='+id,    		   
		   success: function(data) { 
		   var el = document.createElement("option");
		    el.textContent = "----------   Select One  --------  ";
		    el.value = "0";
		    select.appendChild(el);
			   for(var i = 0; i < data.length; i++) {
				    var opt = data[i];
				    var el = document.createElement("option");
				    el.textContent = opt;
				    el.value = opt;
				    select.appendChild(el);
				}			  
		   },
		   error: function(e) {
		   }
		 });   	 
}

function getAllSubDistrictData(){
	var id = document.getElementById("districtSelectedId").value;
	var stateId = document.getElementById("stateSelectedId").value;
	$("#subdistrictSelectedId").html('');

	var select = document.getElementById("subdistrictSelectedId");	 
	 $.ajax({
		   url: 'getAllSubDistrict.do',
		   type: 'GET',
		   data: 'districtId='+id+'&stateId='+stateId,    		   
		   success: function(data) { 
			   	var el = document.createElement("option");
			    el.textContent = "----------   Select One  --------  ";
			    el.value = "0";
			    select.appendChild(el); 
			   for(var i = 0; i < data.length; i++) {
				    var opt = data[i];
				    var el = document.createElement("option");
				    el.textContent = opt;
				    el.value = opt;
				    select.appendChild(el);
				}		  
		   },
		   error: function(e) {
		   }
		 });  	 
}

