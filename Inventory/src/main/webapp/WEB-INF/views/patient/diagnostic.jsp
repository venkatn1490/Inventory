<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/MedRepApplication/resources/js/findnearby.js"></script>

<c:if test="${not empty  deviceMsg}">
	<div class="notificationmsg">${deviceMsg}</div>
</c:if>
<div class="l-page-header">
	<h2 class="l-page-title">
		<span>Find Nearby</span> PAGE
	</h2>
	<!--BREADCRUMB-->
	<ul class="breadcrumb t-breadcrumb-page">
		<li><a href="javascript:void(0)">Home</a></li>
		<li class="active">Find Nearby</li>
		<ul class="sub-menu-button">
			<li class="left-orange-border" style="background-color:transparent;">
			<a href="../patient/hospital.do"	style="color: #d17b0b; font-size: 16px; letter-spacing: 1px;">
					Hospitals </a></li>
			<li class="orange-border"><a href="../patient/ambulances.do"
				style="color: #d17b0b; font-size: 16px; letter-spacing: 1px;">
					Ambulances </a></li>
					<li class="right-orange-border" style="background-color:#d17b0b;"><a href="#"
				style="color: #fff; font-size: 16px; letter-spacing: 1px;">
				Diagnostics	 </a></li>
			
		</ul>
	</ul>
</div>
<div class="l-box no-border">
	<div class="l-box l-spaced-bottom">

		<div class="l-spaced">
			<div id="tables" class="resp-tabs-skin-1">
				<ul class="resp-tabs-list">
					<li id="nlist">Diagnostics List</li>
					<li id="vscrollTable">Create Diagnostic</li>
				</ul>
				<div class="resp-tabs-container">
					<!-- Default Table-->
					<div>
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>Diagnostics</span> Table
									</h2>
								</div>
								<div class="l-box-body">
									<form action="../admin/devices.do" method="post"
										id="devicesRemoveUpdate">
										 <table id="dataTableId" cellspacing="0" width="100%" class="display">
										<thead>
											<tr>
												<th>Select</th>
												<th>Name</th>
												<th>Diagnostic ID</th>
												<th>Contact</th>												
												<th>Location</th>
												<th>View Details</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach var="devicesObj" items="${diagnosticlist}">
												<tr>
													<td>
														<div class="checkbox">
															<label><input type="radio" name="srado"
																value="${devicesObj.diagnostics_id}"></label>
														</div>
													</td>
													
													<td>${devicesObj.diagnostics_name}</td>
													<td>DS${devicesObj.diagnostics_id}</td>	
													<td>${devicesObj.mobileNo}</td>	
													<td>${devicesObj.address1} ${devicesObj.address2} ${devicesObj.locatity} ${devicesObj.city} ${devicesObj.state}</td>													
													<td><a data-toggle="modal" data-target="#LargeModel" class="btn-link" onClick="viewDeviceModel(${devicesObj.diagnostics_id})">View</a>
													</td>
												</tr>
											</c:forEach>
										</tbody>
										</table> 

									</form>
									<div class="l-box-body l-spaced">
										<button type="button" id="notificationUpdatebtn"
											class="btn btn-green">Update</button>
										<button type="button" id="notificationRemovebtn"
											class="btn btn-green">Remove</button>
									</div>

									<div id="LargeModel" tabindex="-1" role="dialog"
										aria-labelledby="largeModalLabel" aria-hidden="true"
										class="modal fade">
										<div class="modal-dialog modal-lg">
											<div class="modal-content">
												<div class="modal-header">
													<h4 id="largeModalLabel" class="modal-title">View
														Diagnostic</h4>
													<button type="button" data-dismiss="modal" class="close">
														<i class="fa fa-times-circle" aria-hidden="true"></i><span
															class="sr-only">Close</span>
													</button>
												</div>
												<div class="form-group row">
													<div class="col-md-6">
														<p
															style="font-size: 14px; text-align: center; margin-top: 14px;">
															Diagnostics Images slider Area</p>
													</div>

													<div class="col-md-6 col-sm-6">
														<div class="modal-body">
															<dl class="dl-horizontal-patient">

																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Name:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="DeviceName">Sri Krishna Diagnostics</dd>
																	</div>
																</div>

																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Hospital ID:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="DeviceDesc">HS1001</dd>
																	</div>
																</div>

																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Contact:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="companyName">9874532164</dd>
																	</div>
																</div>

																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Email Id:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="therapeuticArea">srikrishna@gmail.com</dd>
																	</div>
																</div>

																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Location:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="DeviceFeatures">Banjara Hills, Hyderabad
																			- 500086</dd>
																	</div>
																</div>
																
																<div class="col-md-12 col-sm-12">
																<div class="col-md-8 col-sm-8" align="center">
																	<p style="padding:4px 12px; background-color:#189ee3; color:#fff; border-radius:4px;">Home Collection Available</p>
																</div>
																</div>
	
															</dl>
														</div>
													</div>
												</div>

											</div>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>


					<!-- </div>
</div> -->

					<div class="l-row l-spaced-bottom">
						<div class="l-box">
							<div class="l-box-header">
								<h2 class="l-box-title">
									<span>Create</span> Hospital
								</h2>
							</div>
							<section class="l-box-body l-spaced">
								<form id="createDiagnosticsDevice" class="form-horizontal validate"
									role="form" action="../patient/createDiagnostic.do"
									method="post" enctype="multipart/form-data">

									<div class="form-group row">
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Diagnostic
												Name<font color="red">*</font>
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control required"
													name="diagnosticName" id="diagnosticName">
											</div>
										</div>
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">State<font
												color="red">*</font></label>
											<div class="col-sm-8">
												 <select id="stateSelectedId"  name="stateSelectedId"
													class="form-control"  onchange="getAllDistrict()">
													<option value=0> ----------   Select One  -------- </option>
													<c:forEach items="${statesall}" var="statelist">
														<option value="${statelist}"><c:out value="${statelist}"/></option>
													</c:forEach>
												</select> 
											</div>
										</div>
									</div>
									
									<div class="form-group row">
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Contact Number<font color="red"></font>
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control required"
													name="ContactNumber" id="ContactNumber">
											</div>
										</div>
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">City<font
												color="red">*</font></label>
											<div class="col-sm-8">
												 <select id="districtSelectedId" name="districtSelectedId"
													class="form-control" onchange="getAllSubDistrictData()">
													<option value=0> ----------   Select One  -------- </option>
												</select> 
											</div>
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Email ID<font color="red"></font>
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control required"
													name="emailId" id="emailId">
											</div>
										</div>
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Locality/Area<font
												color="red">*</font></label>
											<div class="col-sm-8">
												<select id="subdistrictSelectedId" name="subdistrictSelectedId"
													class="form-control">
													<option value=0> ----------   Select One  -------- </option>
												</select> 
											</div>
										</div>
									</div>
									
									<div class="form-group row">
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Diagnostic Thumb Image</label>
											<div class="col-sm-8">
												<div class="input-group">
													<span class="input-group-btn"><span
														class="btn btn-primary btn-file">Upload File... <input
															type="file" required="required" id="diagnosticLogo"
															name="diagnosticLogo" size="50"></span></span> <input type="text"
														class="form-control" readonly name="diagnosticLogo">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Address Line1<font color="red"></font>
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control required"
													name="AddressLine1" id="AddressLine1">
											</div>
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Diagnostic Images</label>
											<div class="col-sm-8">
												<div class="input-group">
													<span class="input-group-btn"><span
														class="btn btn-primary btn-file">Upload File... <input
															type="file" required="required" id="diagnosticImage"
															name="diagnosticImage" size="50"></span></span> <input type="text"
														class="form-control" readonly name="diagnosticImage">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Address Line2<font color="red"></font>
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control required"
													name="AddressLine2" id="AddressLine2">
											</div>
										</div>
									</div>
									<div class="form-group row">

											<div class="col-sm-6">
												<div class="col-sm-8">
														<label class="checkmarklab">Home Collection Available
														  <input type="checkbox" checked="checked" id="homecollection" name="homecollection">
														  <span class="checkmark"></span>
														</label>													
												</div>
											</div>
											<div class="col-sm-6">
												<label class="col-sm-4 control-label" for="userName-v">zipcode<font color="red"></font>
												</label>
												<div class="col-sm-8">
													<input type="text" class="form-control required"
														name="zipcode" id="zipcode">
												</div>
											</div>
										</div>
										
										<div class="form-group row">

											<div class="col-sm-6">
												<label class="col-sm-4 control-label" for="basicSelect"></label>
												<div class="col-sm-8">
													<input type="hidden" name="CompanyId" value="" />
													<button type="submit" class="btn btn-green"
														name="buttonName" value="Create"
														onclick="return fnSubmit(this);"
														form="createDiagnosticsDevice">Create</button>
												</div>
											</div>
										
											
										</div>
										<div class="form-group row">
											<div class="col-sm-6">
												<div class="col-sm-8"></div>
											</div>
										</div>
								</form>
							</section>
						</div>
					</div>

					<div></div>

				</div>
				<script src="js/index.js"></script>
				<script type="text/javascript">
function viewDeviceModel(id){
	   $.ajax({
		   url: 'getDevices.do',
		   type: 'GET',
		   data: 'deviceId='+id,
		   dataType: "json",
		   success: function(data) {
			   try{
			   $("#DeviceName").html(data.device_name);
			   $("#DeviceDesc").html(data.device_desc);
			   $("#companyName").html(data.companyName);
			   $("#DeviceFeatures").html(data.features);
			   $("#DevicePrice").html(data.device_price);
			   $("#DeviceUnit").html(data.device_unit);
			   $("#therapeuticArea").html(data.therapeuticArea);
			   $("#DeviceUrl").attr("href",data.deviceUrl);
			 /*   $("#devicePicture").attr("src",data.displayPicture.dPicture);
			   $("#devicePicture").attr("alt",data.device_name+" logo");
			   var therapeuticAreaString=""; */
		      /*  $.each(data.therapeuticAreas, function(i, therapeuticArea) {
		    	   therapeuticAreaString+=therapeuticArea.therapeuticName+"\n";
		        }); */
		      //  $("#companyTherapeuticAreas").html(therapeuticAreaString);
			   } catch(err){

			   }
		   },
		   error: function(e) {
		   }
		 });
}

function fnSubmit(ele)
{
	var elem=ele.value;
	var x="";
	if(elem=="Create")
		{
	 x=document.forms.namedItem("createMedicalDevice");
		}else if (elem=="Update")
			{
			 x=document.forms.namedItem("updateMedicalDevice");	
			}
	
	var txt="";
	//alert(document.forms.namedItem("createPharmaCompany").companyName.value);
	/* for(var i=0;i<x.length;i++)
		{
		if(x.elements[i].value=="" && (x.elements[i].id!="CompanyUrl") && ( x.elements[i].id!="CompanyLogo") && ( x.elements[i].id!=""))
			{
			
			alert("Please fill all the Mandatory fields");
			return false;
			}
		} */
}
	
</script>