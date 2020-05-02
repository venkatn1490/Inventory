<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty  deviceMsg}">
	<div class="notificationmsg">${deviceMsg}</div>
</c:if>
<div class="l-page-header">
	<h2 class="l-page-title">
		<span>Promotional/Landing Banners</span> PAGE
	</h2>
	<!--BREADCRUMB-->
	<ul class="breadcrumb t-breadcrumb-page">
		<li><a href="javascript:void(0)">Home</a></li>
		<li class="active">Promotional/Landing Banners</li>
		
	</ul>
</div>
<div class="l-box no-border">
	<div class="l-box l-spaced-bottom">

		<div class="l-spaced">
			<div id="tables" class="resp-tabs-skin-1">
				<ul class="resp-tabs-list">
					<li id="nlist">All Banners</li>
					<li id="vscrollTable">Create Banner</li>
				</ul>
				<div class="resp-tabs-container">
					<!-- Default Table-->
					<div>
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>Promotional/Landing Banners</span> Table
									</h2>
								</div>
								<div class="l-box-body">
									<form action="../admin/devices.do" method="post"
										id="devicesRemoveUpdate">
										<!--  Original Table for Development -->
										<%-- <table id="dataTableId" cellspacing="0" width="100%" class="display">
										<thead>
											<tr>
												<th>Select</th>
												<th>Name</th>
												<th>Hospital ID</th>
												<th>Contact</th>												
												<th>Email ID</th>
												<th>Location</th>
												<th>View Details</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach var="devicesObj" items="${hospitallist}">
												<tr>
													<td>
														<div class="checkbox">
															<label><input type="radio" name="srado"
																value="${devicesObj.device_id}"></label>
														</div>
													</td>
													
													<td>${devicesObj.hospital_name}</td>
													<td>${devicesObj.createdOn}</td>	
													<td>${devicesObj.companyName}</td>													
													<td>${devicesObj.therapeuticName}</td>
													<td>${devicesObj.createdOn}</td>
													<td><a data-toggle="modal" data-target="#LargeModel" class="btn-link" onClick="viewDeviceModel(${devicesObj.device_id})">View</a>
													</td>
												</tr>
											</c:forEach>
										</tbody>
										</table> --%>


										<!--  Duplicate Table for HTML designs -->

										<table id="dataTableId" cellspacing="0" width="100%" class="">
											<thead>
												<tr>
													<th>Select</th>
													<th>Banner Title</th>
													<th>Status</th>
													<th>Position</th>
													<th>Created Date</th>
													<th>Details</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td>
														<div class="checkbox">
															<label><input type="radio" name="srado"
																value="${devicesObj.device_id}"></label>
														</div>
													</td>
													<td>Electrosurgical Pens</td>
													<td>Visible</td>
													<td>slider 1</td>
													<td>26-09-2018</td>
													<td><a data-toggle="modal" data-target="#LargeModel"
														class="btn-link"
														onClick="viewDeviceModel(${devicesObj.device_id})">View</a>
													</td>
												</tr>
												<tr>
													<td>
														<div class="checkbox">
															<label><input type="radio" name="srado"
																value="${devicesObj.device_id}"></label>
														</div>
													</td>
													<td>Digital Promotion</td>
													<td>Visible</td>
													<td>slider 2</td>
													<td>26-09-2018</td>
													<td><a data-toggle="modal" data-target="#LargeModel"
														class="btn-link"
														onClick="viewDeviceModel(${devicesObj.device_id})">View</a>
													</td>
												</tr>
												<tr>
													<td>
														<div class="checkbox">
															<label><input type="radio" name="srado"
																value="${devicesObj.device_id}"></label>
														</div>
													</td>
													<td>Urine leakage while coughing
													</td>
													<td style="color:red;">Hidden</td>
													<td>slider 3</td>
													<td>26-09-2018</td>
													<td><a data-toggle="modal" data-target="#LargeModel"
														class="btn-link"
														onClick="viewDeviceModel(${devicesObj.device_id})">View</a>
													</td>
												</tr>
											</tbody>
										</table>

										<!--  Ends Duplicate HTML Table here -->

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
													<h4 id="largeModalLabel" class="modal-title">Banner View</h4>
													<button type="button" data-dismiss="modal" class="close">
														<i class="fa fa-times-circle" aria-hidden="true"></i><span
															class="sr-only">Close</span>
													</button>
												</div>
												<div class="form-group row">
													<div class="col-md-5">
														<p style="font-size: 14px; text-align: center; margin-top: 14px;">
															Banner Image Area</p>
															
													</div>
													

													<div class="col-md-7 col-sm-7">
														<div class="modal-body">
															<dl class="dl-horizontal-patient">
																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Title:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="DeviceName">Urine leakage while coughing</dd>
																	</div>
																</div>

																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Status:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="DeviceDesc" style="color:red;">Hidden</dd>
																	</div>
																</div>

																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Created Date:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="companyName">16-09-2018</dd>
																	</div>
																</div>

																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Description:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="therapeuticArea">High quality, disposable electrosurgery pens are 
																			for use with loop and ball electrodes, and 
																			electrosurgical scalpels. Each pen comes with a 
																			10' cord. Packed sterile and disposable. For 
																			complete Relief in Pain and Inflammation</dd>
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
									<span>Create</span> new banner
								</h2>
							</div>
							<section class="l-box-body l-spaced">
								<form id="createMedicalDevice" class="form-horizontal validate"
									role="form" action="../admin/medicalDeviceUpdate.do"
									method="post" enctype="multipart/form-data">

									<div class="form-group row">
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Banner Title<font color="red">*</font>
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control required"
													name="device_name" id="device_name">
											</div>
										</div>
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Banner Position<font
												color="red">*</font></label>
											<div class="col-sm-8">
												<select id="therapeuticId" name="therapeuticId"
													class="form-control">
													<option value=0> ----------   Select One  -------- </option>
													<c:forEach items="${tareaList}" var="tareaobj">
														<option value="${tareaobj.therapeuticId}"><c:out value="${tareaobj.therapeuticName}"/></option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
									
									<div class="form-group row">
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Banner Image<font color="red">*</font></label>
											<div class="col-sm-8">
												<div class="input-group">
													<span class="input-group-btn"><span
														class="btn btn-primary btn-file">Upload File... <input
															type="file" required="required" id="deviceLogo"
															name="deviceLogo" size="50"></span></span> <input type="text"
														class="form-control" readonly name="LogoName">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Banner Status<font
												color="red">*</font></label>
											<div class="col-sm-8">
												<select id="therapeuticId" name="therapeuticId"
													class="form-control">
													<option value=0> ----------   Select One  -------- </option>
													<c:forEach items="${tareaList}" var="tareaobj">
														<option value="${tareaobj.therapeuticId}"><c:out value="${tareaobj.therapeuticName}"/></option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Banner Video</label>
											<div class="col-sm-8">
												<div class="input-group">
													<span class="input-group-btn"><span
														class="btn btn-primary btn-file">Upload File... <input
															type="file" required="required" id="deviceLogo"
															name="deviceLogo" size="50"></span></span> <input type="text"
														class="form-control" readonly name="LogoName">
												</div>
											</div>
										</div>
										<div class="col-sm-6">
					                        <label class="col-sm-4 control-label" for="userName-v">Banner Description<font color="red"></font></label>
					                        <div class="col-sm-8">
					                          <textarea class="form-control required" placeholder="Enter the company description" rows="3" id="device_desc" name="device_desc"></textarea>
					                          </div>
					                        </div>
									</div>
										
										<div class="form-group row">

											<div class="col-sm-6">
											<div class="col-md-4 col-sm-4"></div>
												<div class="col-sm-8">
													<input type="hidden" name="CompanyId" value="" />
													<button type="submit" class="btn btn-green"
														name="buttonName" value="Create"
														onclick="return fnSubmit(this);"
														form="createMedicalDevice">Create</button>
												</div>
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