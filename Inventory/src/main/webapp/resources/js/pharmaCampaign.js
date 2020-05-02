/**
 * 
 */
$(document).ready(function(){	

	 $('#ptareaselectid').multipleSelect();
	 $('#pdocsselectid').multipleSelect();
	 $('#pstateselectid').multipleSelect();
	 $('#pdistrictSelectedId').multipleSelect();

	 

});
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


function getAllDistrict1(){
	   var id = document.getElementById("stateSelectedId1").value;
	   $("#districtSelectedId1").html('');
	   var select = document.getElementById("districtSelectedId1");
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

function getAllSubDistrictData1(){
	var id = document.getElementById("districtSelectedId1").value;
	var stateId = document.getElementById("stateSelectedId1").value;
	$("#subdistrictSelectedId1").html('');

	var select = document.getElementById("subdistrictSelectedId1");	 
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

function getAllDocsByTArea() {
	 var selectedValues = $('#ptareaselectid').val();	
	 $("#tareasids").val(selectedValues);
	 $('#pdocsselectid option').remove();
	 
		 $.ajax({
			   url: 'getDocsByTAass.do',
			   type: 'GET',
			   data: 'tareaIds='+selectedValues,    		   
			   success: function(data) { 
				 $('#pdocsselectid').append(data);
				 $('#pdocsselectid').multipleSelect();
				 $('.ms-parent').css('width','276px');
			
			   },
			   error: function(e) {
			   }
			 });   	 
	// $("#getdocsbytareaform").submit();	 
}
function getAllDistrictByStates(){
	   var selectedValues = $('#pstateselectid').val();	 
	   	 $("#tareasids").val(selectedValues);
		 $('#pdistrictSelectedId option').remove();
	   $.ajax({
		   url: 'getMulDistrictByStates.do',
		   type: 'GET',
		   data: 'stateId='+selectedValues,    		   
		   success: function(data) { 
			   $('#pdistrictSelectedId').append(data);
			   $('#pdistrictSelectedId').multipleSelect();
			   $('.ms-parent').css('width','276px');  
		   },
		   error: function(e) {
		   }
		 });   	 
}