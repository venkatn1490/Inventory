<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/MedRepApplication/resources/js/pharmaCampaign.js"></script>

<c:if test="${not empty  pharmacampaignmsg}">
	<div class="pharmacampaignmsg">${deviceMsg}</div>
</c:if>
<div class="l-page-header">
	<h2 class="l-page-title">
		<span>Pharma Campaignsy</span> PAGE
	</h2>
	<!--BREADCRUMB-->
	<ul class="breadcrumb t-breadcrumb-page">
		<li><a href="javascript:void(0)">Home</a></li>
		<li class="active">Pharma Campaigns</li>
		
	</ul>
</div>
<div class="l-box no-border">
	<div class="l-box l-spaced-bottom">

		<div class="l-spaced">
			<div id="tables" class="resp-tabs-skin-1">
				<ul class="resp-tabs-list">
					<li id="nlist">All Campaigns</li>
					<li id="vscrollTable">Create Campaign</li>
					<li id="">Publish Campaign</li>
				</ul>
				<div class="resp-tabs-container">
					<!-- Default Table-->
					<div>
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>Pharma Campaigns</span> Table
									</h2>
								</div>
								<div class="l-box-body">
									<form action="../admin/pharmacampagin.do" method="post"
										id="devicesRemoveUpdate">
										<!--  Original Table for Development -->
										<table id="dataTableId" cellspacing="0" width="100%" class="display">
										<thead>
											<tr>
												<th>Select</th>
												<th>Campaign Title</th>
												<th> Company Name</th>
												<th>Therapeutic Name</th>												
												<th>End Date</th>
												<th>status</th>
												<th>docor/patient Staus</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach var="devicesObj" items="${pharmaCampaignlists}">
												<tr>
													<td>
														<div class="checkbox">
															<label><input type="radio" name="srado"
																value="${devicesObj.campaignId}"></label>
														</div>
													</td>
													
													<td>${devicesObj.campaignTitle}</td>
													<td>${devicesObj.companyName}</td>	
													<td>${devicesObj.therapeuticName}</td>													
													<td>${devicesObj.campaignEndDate}</td>
													<td>${devicesObj.status}</td>
													<td>${devicesObj.doctorStatus} / ${devicesObj.patientStatus}</td>
													<td><a data-toggle="modal" data-target="#LargeModel" class="btn-link" onClick="viewDeviceModel(${devicesObj.campaignId})">View</a>
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
													<h4 id="largeModalLabel" class="modal-title">Campaign View</h4>
													<button type="button" data-dismiss="modal" class="close">
														<i class="fa fa-times-circle" aria-hidden="true"></i><span
															class="sr-only">Close</span>
													</button>
												</div>
												<div class="form-group row">
													<div class="col-md-5">
														<p style="font-size: 14px; text-align: center; margin-top: 14px;">
															Campaign Image Area</p>
															
														<p align="center" style="padding:4px 12px; background-color:#189ee3; color:#fff; border-radius:4px; margin:0 auto;width:210px;">Enabled Request Sample</p>
															
													</div>
													

													<div class="col-md-7 col-sm-7">
														<div class="modal-body">
															<dl class="dl-horizontal-patient">
																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Campaign Name:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="DeviceName">Sri Krishna Diagnostics</dd>
																	</div>
																</div>

																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Company:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="DeviceDesc">BPL</dd>
																	</div>
																</div>

																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Therapeutic Area:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="companyName">Dental & Oral Health</dd>
																	</div>
																</div>

																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Campaign Description:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="therapeuticArea">High quality, disposable electrosurgery pens are 
																			for use with loop and ball electrodes, and 
																			electrosurgical scalpels. Each pen comes with a 
																			10' cord. Packed sterile and disposable. For 
																			complete Relief in Pain and Inflammation</dd>
																	</div>
																</div>

																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Campaign End Date:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="DeviceFeatures">29-08-2017</dd>
																	</div>
																</div>
																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Company Email:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="DeviceFeatures">bplmedixa@gmail.com</dd>
																	</div>
																</div>
																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Contact Person:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="DeviceFeatures">Bhupal</dd>
																	</div>
																</div>
																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Contact Email:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="DeviceFeatures">bhupal@gmail.com</dd>
																	</div>
																</div>
																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Price:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="DeviceFeatures">Not Mentioned</dd>
																	</div>
																</div>
																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Target City:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="DeviceFeatures">Hyderabad</dd>
																	</div>
																</div>
																<div class="col-md-12 col-sm-12">
																	<div class="col-md-4 col-sm-4">
																		<dt>Target Area:</dt>
																	</div>
																	<div class="col-md-8 col-sm-8">
																		<dd id="DeviceFeatures">Banjara Hills</dd>
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
									<span>Create</span> Campaign
								</h2>
							</div>
							<section class="l-box-body l-spaced">
								<form id="createPharmaCampaign" class="form-horizontal validate"
									role="form" action="../admin/createPharmaCampaign.do"
									method="post" enctype="multipart/form-data">

									<div class="form-group row">
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Campaign Title<font color="red">*</font>
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control required"
													name="campaignTitle" id="campaignTitle">
											</div>
										</div>
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Select Company<font
												color="red">*</font></label>
											<div class="col-sm-8">
												<select id = "companyId" name="companyId" class="form-control">
											 <option value=0> ----------   Select One  -------- </option>
       										 <c:forEach items="${companyMap}" var="companyobj">
       										  <c:set var="detailValue" value="${companyobj.key}"/>
    										<c:set var="companyName" value="${companyobj.value}"/>
            										<option value="${detailValue}"><c:out value="${companyName}"/></option>
        									</c:forEach>
    								</select> 
											</div>
										</div>
									</div>
									
									<div class="form-group row">
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Start Date<font color="red"></font>
											</label>
											<div class="col-sm-8">
												<input type="date" class="form-control required"
													name="startDate" id="startDate">
											</div>
										</div>
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Select Therapeutic Area<font
												color="red">*</font></label>
											<div class="col-sm-8">
												 <select id = "therapeuticId" name="therapeuticId" class="form-control">
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
											<label class="col-sm-4 control-label" for="userName-v">End Date<font color="red"></font>
											</label>
											<div class="col-sm-8">
												<input type="date" class="form-control required"
													name="endDate" id="endDate">
											</div>
										</div>
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Select State<font
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
											<label class="col-sm-4 control-label" for="userName-v">Outer Image</label>
											<div class="col-sm-8">
												<div class="input-group">
													<span class="input-group-btn"><span
														class="btn btn-primary btn-file">Upload File... <input
															type="file" required="required" id="campgainLogo"
															name="campgainLogo" size="50"></span></span> <input type="text"
														class="form-control" readonly name="campgainLogo">
												</div>
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
											<label class="col-sm-4 control-label" for="userName-v">Inner Image</label>
											<div class="col-sm-8">
												<div class="input-group">
													<span class="input-group-btn"><span
														class="btn btn-primary btn-file">Upload File... <input
															type="file" required="required" id="deviceLogo"
															name="campgainImage" size="50"></span></span> <input type="text"
														class="form-control" readonly name="LogoName">
												</div>
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
											<label class="col-sm-4 control-label" for="userName-v">Contact Number<font color="red"></font>
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control required"
													name="contactNumber" id="contactNumber">
											</div>
										</div>
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Address Line<font color="red"></font>
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control required"
													name="adddressLine" id="adddressLine">
											</div>
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Company Email<font color="red"></font>
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control required"
													name="companyemailId" id="companyemailId">
											</div>
										</div>
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Contact Name<font color="red"></font>
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control required"
													name="contactName" id="contactName">
											</div>
										</div>
									</div>
									<div class="form-group row">
											<div class="col-sm-6">
					                        <label class="col-sm-4 control-label" for="userName-v">Campaign Description<font color="red"></font></label>
					                        <div class="col-sm-8">
					                          <textarea class="form-control required" placeholder="Enter the company description" rows="3" id="campgainDescription" name="campgainDescription"></textarea>
					                          </div>
					                        </div>
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Contact Email<font color="red"></font>
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control required"
													name="contactEmail" id="contactEmail">
											</div>
										</div>
									</div>
									<div class="form-group row">

											<div class="col-sm-6">
												<div class="col-sm-8">
														<label class="checkmarklab">Enable Request Sample
														  <input type="checkbox" checked="1" id="requestSample" name="requestSample">
														  <span class="checkmark"></span>
														</label>													
												</div>
											</div>
											<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Price<font color="red"></font>
											</label>
											<div class="col-sm-8">
												<input type="text" class="form-control required"
													name="price" id="price">
											</div>
										</div>
										</div>
										
										<div class="form-group row">

											<div class="col-sm-6">
												<div class="col-sm-8">
													<button type="submit" class="btn btn-green"
														name="buttonName" value="Create"
														onclick="return fnSubmit(this);"
														form="createPharmaCampaign">Create</button>
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

			<div class="l-row l-spaced-bottom">
						<div class="l-box">
							<div class="l-box-header">
								<h2 class="l-box-title">
									<span>Publish</span> Campaign
								</h2>
							</div>
							<section class="l-box-body l-spaced">
								<form id="publishPharmaCampaign" class="form-horizontal validate"
									role="form" action="../admin/publishCampaign.do"
									method="post" enctype="multipart/form-data">
									<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Therapeutics
												 </label>
										<div class="col-sm-8">
										  <form:select multiple="multiple" id="ptareaselectid" path="publishTareaIds" itemLabel="therapeuticName" cssClass="width: 300px;"
												itemValue="therapeuticId" items="${tareaList}">
											</form:select>
											<input onclick="getAllDocsByTArea()"  style="margin-left:293px;margin-top:-50px;width: 97px;" class="btn btn-green" value="Get Doctors" />
										 </div>
										</div>
									</div>

									<div class="form-group row" id ="npdocsdiv" style="display:block;">
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Doctors
												 <font color="red">*</font></label>
											<div class="col-sm-8">
												<!--  <form:select multiple="multiple" id="pdocsselectid" path="publishDocsIds"  cssClass="width: 300px;"
												>
											</form:select>-->
											
											<form:select multiple="multiple" id="pdocsselectid"  path="publishDocsIds" itemLabel="displayName" cssClass="width: 300px;"
												itemValue="doctorId" items="${docsList}">
											</form:select>
											</div>
										</div>
									</div>
									<div class="form-group row">
									<!-- <div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Select  <font
												color="red">*</font></label>
											<div class="col-sm-8">
   														<input id="audienceType" type="radio" name="audienceType" value="patient" checked> patient<br>
 														 <input id="audienceType" type="radio" name="audienceType" value="doctor"> doctor<br>
												
											</div>
										</div> -->
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Select State<font
												color="red">*</font></label>
											<div class="col-sm-8">
												<select id="pstateselectid"  name="pstateselectid" multiple="multiple"
													  cssClass="width: 300px;">
													<c:forEach items="${statesall}" var="statelist">
														<option value="${statelist}"><c:out value="${statelist}"/></option>
													</c:forEach>
												</select>  
										</div>
										<div class="col-sm-4">
											<input onclick="getAllDistrictByStates()"  style="margin-left:293px;margin-top:-50px;width: 97px;" class="btn btn-green" value="Get District" />
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">City<font
												color="red">*</font></label>
											<div class="col-sm-8">
												 
												<select id="pdistrictSelectedId"  name="pdistrictSelectedId" multiple="multiple"
													cssClass="width: 300px;">
													<%-- <c:forEach items="${statesall}" var="statelist">
														<option value="${statelist}"><c:out value="${statelist}"/></option>
													</c:forEach> --%>
												</select> 
											</div>
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Select Campaign<font
												color="red">*</font></label>
											<div class="col-sm-8">
												<select id="campaignId" name="campaignId"
													class="form-control">
													<option value=0> ----------   Select One  -------- </option>
													<c:forEach items="${pharmaCampaignlists}" var="tareaobj">
														<option value="${tareaobj.campaignId}"><c:out value="${tareaobj.campaignTitle}"/></option>
													</c:forEach>
												</select>
											</div>
										</div>
									<!--  <div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Target Locatity<font
												color="red">*</font></label>
											<div class="col-sm-8">
												<select id="subdistrictSelectedId1" name="subdistrictSelectedId1"
													class="form-control">
													<option value=0> ----------   Select One  -------- </option>
												</select> 
											</div>
										</div> -->
									</div>
										
										<div class="form-group row">

											<div class="col-sm-6">
												<div class="col-sm-8">
													<button type="submit" class="btn btn-green"
														name="buttonName" value="Create"
														onclick="return fnSubmit(this);"
														form="publishPharmaCampaign">Publish</button>
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
	
}


	
</script>