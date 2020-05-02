<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src='<c:url value="/resources/js/doctors.js"/>'></script>
<!--  JS Script included here  -->
<div class="l-page-header">
	<h2 class="l-page-title">
		<span>Doctors</span> Page
	</h2>
	<!--BREADCRUMB-->
	<ul class="breadcrumb t-breadcrumb-page">
		<li><a href="javascript:void(0)">Home</a></li>
		<li class="active">Doctors</li>
	</ul>
</div>
<div class="l-box no-border">
	<div class="l-box l-spaced-bottom">

		<div class="l-spaced">
			<div id="tables" class="resp-tabs-skin-1">
				<ul class="resp-tabs-list">
					<li>New</li>
					<li>Active</li>
					<li>Disabled</li>
					<li>Inprogress</li>
				</ul>
				<div class="resp-tabs-container">
					<!-- New -->
					<div>
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>New Doctors</span> Table
									</h2>
								</div>
								<div class="l-box-body">
									<form action="../admin/removeDoctors.do" method="post"
										id="newDoctorRemove">
										<table id="dataTableId" cellspacing="0" width="auto"
											class="display">
											<thead>
												<tr>
													<th>Select</th>
													<th>Name</th>
													<th>MCI Registration ID</th>
													<th>Mobile No.</th>
													<th>E-mail</th>
													<th>Therapeutic Area</th>
													<!-- <th>Region</th>
                            <th>City</th> -->
													<th>Details</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="newDoctor" items="${newDoctorList}">
													<tr>
														<td>
															<div class="checkbox">
																<label><input type="checkbox"
																	name="removeDoctors" value="${newDoctor.doctorId}"></label>
															</div>
														</td>
														<%--<c:if
															test="${not empty  newDoctor.title && not empty  newDoctor.firstName && not empty  newDoctor.lastName && not empty  newDoctor.middleName }">
															<c:set var="doctorName"
																value="${newDoctor.title} ${newDoctor.firstName} ${newDoctor.middleName} ${newDoctor.lastName}" />
														</c:if>
														<c:if
															test="${ not empty  newDoctor.firstName && not empty  newDoctor.lastName}">
															<c:set var="doctorName"
																value="${newDoctor.title} ${newDoctor.firstName} ${newDoctor.lastName}" />
														</c:if> --%>
														<td>${newDoctor.displayName}</td>
														<td>${newDoctor.registrationNumber}</td>
														<td>${newDoctor.mobileNo}</td>
														<td>${newDoctor.emailId}</td>
														<td>${newDoctor.therapeuticName}</td>
														<%-- <td>
		                            <c:forEach var="location" items="${newDoctor.locations}">
		                            	${location.address2}</td>
		                            	<td>${location.city}
		                            </c:forEach>
	                            </td> --%>
														<td><a data-toggle="modal" id="new"
															data-target="#LargeModelNew" class="btn-link"
															onClick="viewModel(${newDoctor.doctorId},'New')">View</a></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</form>
									<div class="l-box-body l-spaced">
										<a data-toggle="modal" id="new"
											data-target="#createDoctorModel"
											onclick="clearData('createdoctor');" class="btn btn-green">Create</a>
										<a data-toggle="modal" id="updatedocbuttn"
											onclick="clearData('updatedoctor');" class="btn btn-green">Update</a>
										<button type="submit" class="btn btn-green" value="Remove"
											form="newDoctorRemove">Remove</button>
									</div>

									<div id="LargeModelNew" tabindex="-1" role="dialog"
										aria-labelledby="largeModalLabel" aria-hidden="true"
										class="modal fade">
										<div class="modal-dialog modal-lg">
											<div class="modal-content">
												<div class="modal-header">
													<h4 id="largeModalLabel" class="modal-title">View
														Doctor Details</h4>
													<button type="button" data-dismiss="modal" class="close">
														<span aria-hidden="true">X</span><span class="sr-only">Close</span>
													</button>
												</div>

												<div class="form-group row">
													<div class="col-md-6">
														<div class="modal-body">
															<dl class="dl-horizontal" id="dl-horizontal-id">
																<dt>Name</dt>
																<dd id="docNameNew">
																	<%-- ${doctorName} --%>
																</dd>
																<dt>MCI Registration ID</dt>
																<dd id="docRegistrationNumberNew">
																	<%-- ${newDoctor.registrationNumber} --%>
																</dd>
																<dt>Therapeutic Area</dt>
																<dd id="doctherapeuticNameNew">
																	<%-- ${newDoctor.therapeuticName} --%>
																</dd>
																<dt>Mobile No.</dt>
																<dd id="docMobileNoNew">
																	<%-- ${newDoctor.mobileNo} --%>
																</dd>
																<dt>Email</dt>
																<dd id="docEmailIdNew">
																	<%-- ${newDoctor.emailId} --%>
																</dd>
																<dt>Alternate Email</dt>
																<dd id="docAlternateEmailIdNew">
																	<%-- ${newDoctor.alternateEmailId} --%>
																</dd>
																<dt id="docLocationNoNew"></dt>
																<dd id="docLocationNew"></dd>
															</dl>
														</div>
													</div>
													<div class="col-md-6">
														<div class="modal-body">
															<dl class="dl-horizontal">
																<dt></dt>
																<dd id="docProfilePictureNew">
																	<%-- <center><img src="data:image/${newDoctor.profilePicture.mimeType};base64,${newDoctor.profilePicture.data}" alt="Profile picture" style="max-height:90px"></center> --%>
																</dd>
																<dt>&nbsp;</dt>
																<dd>&nbsp;</dd>
																<dt>&nbsp;</dt>
																<%-- <dd>
																	<center>
																	<center>
																		
																		<button class="btn btn-success l-spaced" id="trackactivity" onclick="trackdownload('New')" type="button">Track
																			Activity Score</button>
																	</center>
																</dd> --%>
																<dt style="margin-top: 9px;">Choose Status</dt>
																<dd>
																	<center>
																		<form action="../admin/doctors.do" method="post"
																			id="formNew">
																			<select name="statusDropDown"
																				class="l-spaced form-control">
																				<option value="New">------ select Choose
																					Status -----</option>
																				<option value="Active">Active</option>
																				<option value="Disabled">Disabled</option>
																				<option value="Inprogress">Inprogress</option>
																			</select> <input name="doctorId" id="docDoctorIdNew"
																				type="hidden" value="" />
																		</form>
																	</center>
																</dd>
																<div class="l-spaced l-box-body l-spaced">
																	<dt>&nbsp;</dt>
																	<dd>
																		<center>
																			<button type="submit" class="btn btn-green"
																				value="Submit" form="formNew">Update</button>
																		</center>
																	</dd>
																	<div class="l-spaced l-box-body l-spaced">
																	<dt>Notification</dt>
																	<dd id="dnotifNew"></dd>
																	<dt>Survey</dt>
																	<dd id="dsurNew"></dd>
																	<dt>Appointments</dt>
																	<dd id="dappointmNew"></dd>
																	<dt>Feedback</dt>
																	<dd id="dfeedbackNew"></dd>
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
					<!-- Active -->
					<div>
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>Active Doctors</span> Table
									</h2>
								</div>
								<div class="l-box-body">
									<form action="../admin/removeDoctors.do" method="post"
										id="activeDoctorRemove">
										<table id="dataTableId1" cellspacing="0" width="100%"
											class="display">
											<thead>
												<tr>
													<th>Select</th>
													<th>Name</th>
													<th>MCI Registration ID</th>
													<th>Mobile No.</th>
													<th>E-mail</th>
													<th>Therapeutic Area</th>
													<!-- <th>Region</th>
                            <th>City</th> -->
													<th>Details</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="activeDoctor" items="${activeDoctorList}">
													<tr>
														<td>
															<div class="checkbox">
																<label><input type="checkbox"
																	name="removeDoctors" value="${activeDoctor.doctorId}"></label>
															</div>
														</td>
														<c:if
															test="${not empty  activeDoctor.title && not empty  activeDoctor.firstName && not empty  activeDoctor.middleName && not empty  activeDoctor.lastName}">
															<c:set var="doctorName"
																value=" ${activeDoctor.firstName} ${activeDoctor.middleName} ${activeDoctor.lastName}" />
														</c:if>
														<c:if
															test="${not empty  activeDoctor.title && not empty  activeDoctor.firstName && empty  activeDoctor.middleName && not empty  activeDoctor.lastName}">
															<c:set var="doctorName"
																value=" ${activeDoctor.firstName} ${activeDoctor.lastName}" />
														</c:if>
														<td>${doctorName}</td>
														<td>${activeDoctor.registrationNumber}</td>
														<td>${activeDoctor.mobileNo}</td>
														<td>${activeDoctor.emailId}</td>
														<td>${activeDoctor.therapeuticName}</td>
														<%-- <td>
		                            <c:forEach var="location" items="${activeDoctor.locations}">
		                            	${location.address2}</td>
		                            	<td>${location.city}
		                            </c:forEach>
	                            </td> --%>
														<td><a data-toggle="modal"
															data-target="#LargeModelActive" class="btn-link"
															onClick="viewModel(${activeDoctor.doctorId},'Active')">View</a></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</form>
									<div class="l-box-body l-spaced">
										<button type="submit" class="btn btn-green" value="Remove"
											form="activeDoctorRemove">Remove</button>
									</div>
									<div id="LargeModelActive" tabindex="-1" role="dialog"
										aria-labelledby="largeModalLabel" aria-hidden="true"
										class="modal fade">
										<div class="modal-dialog modal-lg">
											<div class="modal-content">
												<div class="modal-header">
													<h4 id="largeModalLabel" class="modal-title">View
														Doctor Details</h4>
													<button type="button" data-dismiss="modal" class="close">
														<span aria-hidden="true">X</span><span class="sr-only">Close</span>
													</button>
												</div>

												<div class="form-group row">
													<div class="col-md-6">
														<div class="modal-body">
															<dl class="dl-horizontal">
																<dt>Name</dt>
																<dd id="docNameActive">
																	<%-- ${doctorName} --%>
																</dd>
																<dt>MCI Registration ID</dt>
																<dd id="docRegistrationNumberActive">
																	<%-- ${newDoctor.registrationNumber} --%>
																</dd>
																<dt>Therapeutic Area</dt>
																<dd id="doctherapeuticNameActive">
																	<%-- ${newDoctor.therapeuticName} --%>
																</dd>
																<dt>Mobile No.</dt>
																<dd id="docMobileNoActive">
																	<%-- ${newDoctor.mobileNo} --%>
																</dd>
																<dt>Email</dt>
																<dd id="docEmailIdActive">
																	<%-- ${newDoctor.emailId} --%>
																</dd>
																<dt>Alternate Email</dt>
																<dd id="docAlternateEmailIdActive">
																	<%-- ${newDoctor.alternateEmailId} --%>
																</dd>
																<dt id="docLocationNoActive"></dt>
																<dd id="docLocationActive"></dd>
															</dl>
														</div>
													</div>
													<div class="col-md-6">
														<div class="modal-body">
															<dl class="dl-horizontal">
																<dt></dt>
																<dd id="docProfilePictureActive">
																	<%-- <center><img src="data:image/${newDoctor.profilePicture.mimeType};base64,${newDoctor.profilePicture.data}" alt="Profile picture" style="max-height:90px"></center> --%>
																</dd>
																<dt>&nbsp;</dt>
																<dd>&nbsp;</dd>
																<%-- <dt>&nbsp;</dt>
																<dd>
																	<center>
																		<button class="btn btn-success l-spaced" type="button">Track
																			Activity Score</button>
																	</center>
																</dd> --%>
																<dt style="margin-top: 9px;">Choose Status</dt>
																<dd>
																	<center>
																		<form action="../admin/doctors.do" method="post"
																			id="formActive">
																			<select name="statusDropDown"
																				class="l-spaced form-control">
																				<option value="New">------ select Choose
																					Status -----</option>
																				<option value="Active">Active</option>
																				<option value="Disabled">Disabled</option>
																				<option value="Inprogress">Inprogress</option>
																			</select> <input name="doctorId" id="docDoctorIdActive"
																				type="hidden" value="" />
																		</form>
																	</center>
																</dd>
																<div class="l-spaced l-box-body l-spaced">
																	<dt>&nbsp;</dt>
																	<dd>
																		<center>
																			<button type="submit" class="btn btn-green"
																				value="Submit" form="formActive">Update</button>
																		</center>
																	</dd>
																</div>
																<div class="l-spaced l-box-body l-spaced">
																	<dt>Notification</dt>
																	<dd id="dnotifActive"></dd>
																	<dt>Survey</dt>
																	<dd id="dsurActive"></dd>
																	<dt>Appointments</dt>
																	<dd id="dappointmActive"></dd>
																	<dt>Feedback</dt>
																	<dd id="dfeedbackActive"></dd>
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
					<!-- disabled -->
					<div>
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>Disabled Doctors</span> Table
									</h2>
								</div>
								<div class="l-box-body">
									<form action="../admin/removeDoctors.do" method="post"
										id="disabledDoctorRemove">
										<table id="dataTableId2" cellspacing="0" width="100%"
											class="display">
											<thead>
												<tr>
													<th>Select</th>
													<th>Name</th>
													<th>MCI Registration ID</th>
													<th>Mobile No.</th>
													<th>E-mail</th>
													<th>Therapeutic Area</th>
													<!-- <th>Region</th>
                            <th>City</th> -->
													<th>Details</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="disabledDoctor"
													items="${disabledDoctorList}">
													<tr>
														<td>
															<div class="checkbox">
																<label><input type="checkbox"
																	name="removeDoctors" value="${disabledDoctor.doctorId}"></label>
															</div>
														</td>
													<%--	<c:if
															test="${not empty  disabledDoctor.title && not empty  disabledDoctor.firstName && not empty  disabledDoctor.middleName && not empty  disabledDoctor.lastName}">
															<c:set var="doctorName"
																value="${disabledDoctor.title} ${disabledDoctor.firstName} ${disabledDoctor.middleName} ${disabledDoctor.lastName}" />
														</c:if>
														<c:if
															test="${not empty  disabledDoctor.title && not empty  disabledDoctor.firstName && empty  disabledDoctor.middleName && not empty  disabledDoctor.lastName}">
															<c:set var="doctorName"
																value="${disabledDoctor.title} ${disabledDoctor.firstName} ${disabledDoctor.lastName}" />
														</c:if> --%>
														<td>${newDoctor.displayName}</td>
														<td>${disabledDoctor.registrationNumber}</td>
														<td>${disabledDoctor.mobileNo}</td>
														<td>${disabledDoctor.emailId}</td>
														<td>${disabledDoctor.therapeuticName}</td>
														<%-- <td>
		                            <c:forEach var="location" items="${disabledDoctor.locations}">
		                            	${location.address2}</td>
		                            	<td>${location.city}
		                            </c:forEach>
	                            </td> --%>
														<td><a data-toggle="modal"
															data-target="#LargeModelDisabled" class="btn-link"
															onClick="viewModel(${disabledDoctor.doctorId},'Disabled')">View</a></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</form>
									<div class="l-box-body l-spaced">
										<button type="submit" class="btn btn-green" value="Remove"
											form="disabledDoctorRemove">Remove</button>
									</div>
									<div id="LargeModelDisabled" tabindex="-1" role="dialog"
										aria-labelledby="largeModalLabel" aria-hidden="true"
										class="modal fade">
										<div class="modal-dialog modal-lg">
											<div class="modal-content">
												<div class="modal-header">
													<h4 id="largeModalLabel" class="modal-title">View
														Doctor Details</h4>
													<button type="button" data-dismiss="modal" class="close">
														<span aria-hidden="true">X</span><span class="sr-only">Close</span>
													</button>
												</div>

												<div class="form-group row">
													<div class="col-md-6">
														<div class="modal-body">
															<dl class="dl-horizontal">
																<dt>Name</dt>
																<dd id="docNameDisabled">
																	<%-- ${doctorName} --%>
																</dd>
																<dt>MCI Registration ID</dt>
																<dd id="docRegistrationNumberDisabled">
																	<%-- ${newDoctor.registrationNumber} --%>
																</dd>
																<dt>Therapeutic Area</dt>
																<dd id="doctherapeuticNameDisabled">
																	<%-- ${newDoctor.therapeuticName} --%>
																</dd>
																<dt>Mobile No.</dt>
																<dd id="docMobileNoDisabled">
																	<%-- ${newDoctor.mobileNo} --%>
																</dd>
																<dt>Email</dt>
																<dd id="docEmailIdDisabled">
																	<%-- ${newDoctor.emailId} --%>
																</dd>
																<dt>Alternate Email</dt>
																<dd id="docAlternateEmailIdDisabled">
																	<%-- ${newDoctor.alternateEmailId} --%>
																</dd>
																<dt id="docLocationNoDisabled"></dt>
																<dd id="docLocationDisabled"></dd>
															</dl>
														</div>
													</div>
													<div class="col-md-6">
														<div class="modal-body">
															<dl class="dl-horizontal">
																<dt></dt>
																<dd id="docProfilePictureDisabled">
																	<%-- <center><img src="data:image/${newDoctor.profilePicture.mimeType};base64,${newDoctor.profilePicture.data}" alt="Profile picture" style="max-height:90px"></center> --%>
																</dd>
																<dt>&nbsp;</dt>
																<dd>&nbsp;</dd>
																<%-- <dt>&nbsp;</dt>
																<dd>
																	<center>
																		<button class="btn btn-success l-spaced" type="button">Track
																			Activity Score</button>
																	</center>
																</dd> --%>
																<dt style="margin-top: 9px;">Choose Status</dt>
																<dd>
																	<center>
																		<form action="../admin/doctors.do" method="post"
																			id="formDisabled">
																			<select name="statusDropDown"
																				class="l-spaced form-control">
																				<option value="New">------ select Choose
																					Status -----</option>
																				<option value="Active">Active</option>
																				<option value="Disabled">Disabled</option>
																				<option value="Inprogress">Inprogress</option>
																			</select> <input name="doctorId" id="docDoctorIdDisabled"
																				type="hidden" value="" />
																		</form>
																	</center>
																</dd>
																<div class="l-spaced l-box-body l-spaced">
																	<dt>&nbsp;</dt>
																	<dd>
																		<center>
																			<button type="submit" class="btn btn-green"
																				value="Submit" form="formDisabled">Update</button>
																		</center>
																	</dd>
																</div>
																<div class="l-spaced l-box-body l-spaced">
																	<dt>Notification</dt>
																	<dd id="dnotifDisabled"></dd>
																	<dt>Survey</dt>
																	<dd id="dsurDisabled"></dd>
																	<dt>Appointments</dt>
																	<dd id="dappointmDisabled"></dd>
																	<dt>Feedback</dt>
																	<dd id="dfeedbackDisabled"></dd>
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
					<!-- Inprogress -->
					<div>
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>Not Verified Doctors</span> Table
									</h2>
								</div>
								<div class="l-box-body">
									<form action="../admin/removeDoctors.do" method="post"
										id="inprogressDoctorRemove">
										<table id="dataTableId3" cellspacing="0" width="100%"
											class="display">
											<thead>
												<tr>
													<th>Select</th>
													<th>Name</th>
													<th> MCI Registration ID</th>
													<th>Mobile No.</th>
													<th>E-mail</th>
													<th>Therapeutic Area</th>
													<!-- <th>Region</th>
                            <th>City</th> -->
													<th>Details</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="inprogressDoctor"
													items="${inProgressDoctorList}">
													<tr>
														<td>
															<div class="checkbox">
																<label><input type="checkbox"
																	name="removeDoctors"
																	value="${inprogressDoctor.doctorId}"></label>
															</div>
														</td>
														<c:if
															test="${not empty  inprogressDoctor.title && not empty  inprogressDoctor.firstName && not empty  inprogressDoctor.middleName && not empty  inprogressDoctor.lastName}">
															<c:set var="doctorName"
																value="${inprogressDoctor.title} ${inprogressDoctor.firstName} ${inprogressDoctor.middleName} ${inprogressDoctor.lastName}" />
														</c:if>
														<c:if
															test="${not empty  inprogressDoctor.title && not empty  inprogressDoctor.firstName && empty  inprogressDoctor.middleName && not empty  inprogressDoctor.lastName}">
															<c:set var="doctorName"
																value="${inprogressDoctor.title} ${inprogressDoctor.firstName} ${inprogressDoctor.lastName}" />
														</c:if>
														<td>${doctorName}</td>
														<td>${inprogressDoctor.registrationNumber}</td>
														<td>${inprogressDoctor.mobileNo}</td>
														<td>${inprogressDoctor.emailId}</td>
														<td>${inprogressDoctor.therapeuticName}</td>
														<%-- <td>
		                            <c:forEach var="location" items="${inprogressDoctor.locations}">
		                            	${location.address2}</td>
		                            	<td>${location.city}
		                            </c:forEach>
	                            </td> --%>
														<td><a data-toggle="modal"
															data-target="#LargeModelInprogress" class="btn-link"
															onClick="viewModel(${inprogressDoctor.doctorId},'Inprogress')">View</a></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</form>
									<div class="l-box-body l-spaced">
										<button type="submit" class="btn btn-green" value="Remove"
											form="inprogressDoctorRemove">Remove</button>
									</div>
									<div id="LargeModelInprogress" tabindex="-1" role="dialog"
										aria-labelledby="largeModalLabel" aria-hidden="true"
										class="modal fade">
										<div class="modal-dialog modal-lg">
											<div class="modal-content">
												<div class="modal-header">
													<h4 id="largeModalLabel" class="modal-title">View
														Doctor Details</h4>
													<button type="button" data-dismiss="modal" class="close">
														<span aria-hidden="true">X</span><span class="sr-only">Close</span>
													</button>
												</div>

												<div class="form-group row">
													<div class="col-md-6">
														<div class="modal-body">
															<dl class="dl-horizontal">
																<dt>Name</dt>
																<dd id="docNameInprogress">
																	<%-- ${doctorName} --%>
																</dd>
																<dt>MCI Registration ID</dt>
																<dd id="docRegistrationNumberInprogress">
																	<%-- ${newDoctor.registrationNumber} --%>
																</dd>
																<dt>Therapeutic Area</dt>
																<dd id="doctherapeuticNameInprogress">
																	<%-- ${newDoctor.therapeuticName} --%>
																</dd>
																<dt>Mobile No.</dt>
																<dd id="docMobileNoInprogress">
																	<%-- ${newDoctor.mobileNo} --%>
																</dd>
																<dt>Email</dt>
																<dd id="docEmailIdInprogress">
																	<%-- ${newDoctor.emailId} --%>
																</dd>
																<dt>Alternate Email</dt>
																<dd id="docAlternateEmailIdInprogress">
																	<%-- ${newDoctor.alternateEmailId} --%>
																</dd>
																<dt id="docLocationNoInprogress"></dt>
																<dd id="docLocationInprogress"></dd>
															</dl>
														</div>
													</div>
													<div class="col-md-6">
														<div class="modal-body">
															<dl class="dl-horizontal">
																<dt></dt>
																<dd id="docProfilePictureInprogress">
																	<%-- <center><img src="data:image/${newDoctor.profilePicture.mimeType};base64,${newDoctor.profilePicture.data}" alt="Profile picture" style="max-height:90px"></center> --%>
																</dd>
																<dt>&nbsp;</dt>
																<dd>&nbsp;</dd>
																<dt>&nbsp;</dt>
																<dd>
																	<center>
																		<button class="btn btn-success l-spaced" type="button">Track
																			Activity Score</button>
																	</center>
																</dd>
																<dt style="margin-top: 9px;">Choose Status</dt>
																<dd>
																	<center>
																		<form action="../admin/doctors.do" method="post"
																			id="formInprogress">
																			<select name="statusDropDown"
																				class="l-spaced form-control">
																				<option value="New">------ select Choose
																					Status -----</option>
																				<option value="Active">Active</option>
																				<option value="Disabled">Disabled</option>
																				<option value="Inprogress">Inprogress</option>
																			</select> <input name="doctorId" id="docDoctorIdInprogress"
																				type="hidden" value="" />
																		</form>
																	</center>
																</dd>
																<div class="l-spaced l-box-body l-spaced">
																	<dt>&nbsp;</dt>
																	<dd>
																		<center>
																			<button type="submit" class="btn btn-green"
																				value="Submit" form="formInprogress">Update</button>
																		</center>
																	</dd>
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
				</div>
			</div>


			<!-- Create Doctor -->

			<div id="createDoctorModel" tabindex="-1" role="dialog"
				aria-labelledby="largeModalLabel" aria-hidden="true"
				class="modal fade">
				<div class="modal-dialog modal-lg" style="height: 1100px">
					<div class="modal-content">
						<div class="modal-header">
							<h4 id="largeModalLabel" class="modal-title">Create Doctor</h4>
							<button type="button" data-dismiss="modal" class="close">
								<span aria-hidden="true">X</span><span class="sr-only">Close</span>
							</button>
						</div>

						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Title
								</label>
								<div class="col-sm-8">
									<select id="dtitle">
									<option value="Dr">Dr.</option>
										<!-- <option value="Mr">Mr.</option>
										<option value="Ms">Ms.</option>
										<option value="Miss">Miss.</option> -->
									</select>
								</div>
							</div>

						</div>
						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">EMail
									ID <font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="demail" name="demail" class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Password
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="dpass" name="dpass" type="password"
										class="form-control required" />
								</div>
							</div>
						</div>

						<div class="form-group row">

							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">First
									Name <font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="dfname" name="userName-v"
										class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Last
									Name<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="dlanme" name="dlname" class="form-control required" />
								</div>
							</div>
						</div>

						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Middle
									Name </label>
								<div class="col-sm-8">
									<input id="dmname" name="dmname" class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Mobile
									No. <font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="dmob" name="dmob" class="form-control required" />
								</div>
							</div>
						</div>
						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Registration
									Year <font color="red">*</font>
								</label>
								<div class="col-sm-8">
								<select id="dregyr" name="dregyr" class="form-control required">
								<script type="text/javascript">
									  var myDate = new Date();
									  var year = myDate.getFullYear();
									  for(var i = year; i > 1945; i--){
										  document.write('<option value="'+i+'">'+i+'</option>');
									  }
  									</script>
								</select>
									
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label"> MCI Registration
									ID <font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="dregno" name="dregno" class="form-control required" />
								</div>
							</div>
						</div>

						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">State
									Med Concuncil <font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<select id="dmcounc" name="dmcounc" class="form-control required" >
								<option value="Andhra Pradesh">Andhra Pradesh</option>
								<option value="Arunachal Pradesh">Arunachal Pradesh</option>
								<option value="Assam">Assam</option>
								<option value="Bihar">Bihar</option>
								<option value="Chattisghar">Chattisghar</option>
								<option value="Chandigarh">Chandigarh</option>
								<option value="Dadra and Nagar Haveli">Dadra and Nagar Haveli</option>
								<option value="Daman and Diu">Daman and Diu</option>
								<option value="Delhi">Delhi</option>
								<option value="Goa">Goa</option>
								<option value="Gujarat">Gujarat</option>
								<option value="Haryana">Haryana</option>
								<option value="Himachal Pradesh">Himachal Pradesh</option>
								<option value="Jammu and Kashmir">Jammu and Kashmir</option>
								<option value="Jharkhand">Jharkhand</option>
								<option value="Karnataka">Karnataka</option>
								<option value="Kerala">Kerala</option>
								<option value="Madhya Pradesh">Madhya Pradesh</option>
								<option value="Manipur">Manipur</option>
								<option value="Meghalaya">Megalaya</option>
								<option value="Mizoram">Mizoram</option>
								<option value="Nagaland">Nagaland</option>
								<option value="Orissa">Orissa</option>
								<option value="Punjab">Punjab</option>
								<option value="Pondicherry">Pondicherry</option>
								<option value="Rajasthan">Rajasthan</option>
								<option value="Sikkim">Sikkim</option>
								<option value="Tamilnadu">Tamilnadu</option>
								<option value="Tripura">Tripura</option>
								<option value="Uttar Pradesh">Uttar Pradesh</option>
								<option value="Uttarakhand">Uttarakhand</option>
								<option value="West Bengal">West Bengal</option>
								
								</select>
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Therapeutic
									Area<font color="red">*</font> </label>
								<div class="col-sm-8">
									 <select id = "createdoctarea"  class="form-control required">
       										 <c:forEach items="${tareaList}" var="tareaobj">
            										<option value="${tareaobj.therapeuticId}"><c:out value="${tareaobj.therapeuticName}"/></option>
        									</c:forEach>
    								</select>
								</div>
							</div>

						</div>
							
						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Address
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="daddr" name="daddr" class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Address1
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="daddr1" name="daddr1" class="form-control required" />
								</div>
							</div>
						</div>

						<div class="form-group row">
						<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">City
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="dcity" name="dcity" class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">State
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="dstate" name="dstate" class="form-control required" />
								</div>
							</div>

						</div>

						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Country
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="dcountry" name="dcountry"
										class="form-control required" />
								</div>
							</div>

							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Zip
									Code </label>
								<div class="col-sm-8">
									<input id="dzip" name="dzip" class="form-control required" />
								</div>
							</div>

						</div>
						<div class="form-group row" >
								<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Location Type
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
								
									<select id="dloctyType" 
										class="form-control required" >
										 <c:forEach items="${locationtypelist}" var="loctypeobj">
										 <c:set var="idAsString">${loctypeobj.locationtypeId}</c:set>
            										<option value="${idAsString}"><c:out value="${loctypeobj.locationType}"/></option>
        									</c:forEach>
										</select>
								</div>
							</div>
							</div>
						<div id="room_fileds"></div>
						<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label"></label>
										<div class="col-sm-8">
											<input type="button" id="more_fields" onclick="add_fields();"
												value=" + Add Another Address" class="addctntxt" />
										</div>
									</div>
								</div>
								
								
						<div class="row l-box-body l-spaced">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label"></label>
								<div class="col-sm-8">
									<input type="submit" class="btn btn-green" value="Submit"
										style="position: absolute; right: -60px; top: -9px"
										id="createsubmitbuttn" />
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
	<script type="text/javascript">
                      var page = 1;
                      var arrPage = 1;
                      function add_fields() {
                      page++;
                      var objTo = document.getElementById('room_fileds');
                      var divtest = document.createElement("div");
                      if(arrPage == 1) { divtest.innerHTML = '<div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Address<font color="red">*</font></label><div class="col-sm-8"><input id="daddr'+page +'" name="daddr'+page +' "class="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Address1<font color="red">*</font></label><div class="col-sm-8"><input id="daddr1'+page +'" name="daddr1'+page +'" class="form-control required" /></div></div></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">City<font color="red">*</font></label><div class="col-sm-8"><input id="dcity'+page +'" name="dcity'+page +'" class="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">State<font color="red">*</font></label><div class="col-sm-8"><input id="dstate'+page +'" name="dstate'+page +'" class="form-control required" /></div></div></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Country <font color="red">*</font></label><div class="col-sm-8"><input id="dcountry'+page +'" name="dcountry'+page +'" class="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Zip Code </label><div class="col-sm-8"><input id="dzip'+page +'" name="dzip'+page +'"  class="form-control required" /></div></div></div>	<div class="form-group row" ><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Location Type<font color="red">*</font></label><div class="col-sm-8"><select id="dloctyType'+page+'" name="dloctyType'+page+'" class="form-control required"/ ><c:forEach items="${locationtypelist}" var="loctypeobj"> <c:set var="idAsString">${loctypeobj.locationtypeId}</c:set><option value="${idAsString}"><c:out value="${loctypeobj.locationType}"/></option></c:forEach></select></div></div></div>'; }
                      else { divtest.innerHTML = '<div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Address<font color="red">*</font></label><div class="col-sm-8"><input id="daddr'+page +'" name="daddr'+page +'" class="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Address1<font color="red">*</font></label><div class="col-sm-8"><input id="daddr1'+page +'" name="daddr1'+page +'" class="form-control required" /></div></div></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">City<font color="red">*</font></label><div class="col-sm-8"><input id="dcity'+page +'" name="dcity'+page +'" class="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">State<font color="red">*</font></label><div class="col-sm-8"><input id="dstate'+page +'" name="dstate'+page +'" class="form-control required" /></div></div></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Country <font color="red">*</font></label><div class="col-sm-8"><input id="dcountry'+page +'" name="dcountry'+page +'" class="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Zip Code </label><div class="col-sm-8"><input id="dzip'+page +'" name="dzip'+page +'" class="form-control required" /></div></div></div> 	<div class="form-group row" ><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Location Type<font color="red">*</font></label><div class="col-sm-8"><select id="dloctyType'+page+'" name="dloctyType'+page+'" class="form-control required" ><c:forEach items="${locationtypelist}" var="loctypeobj"> <c:set var="idAsString">${loctypeobj.locationtypeId}</c:set><option value="${idAsString}"><c:out value="${loctypeobj.locationType}"/></option></c:forEach></select></div></div></div>'; }
                      objTo.appendChild(divtest);
                      ++arrPage;
                      }
                    </script>
			<!-- Create Doctor end here -->

			<!-- Update Doctor -->
			<div class="l-row l-spaced-bottom" id="updateDoctorModel">
				<div class="modal-dialog modal-lg" style="height: 1100px">
					<div class="modal-content" id="model-dynamic-add">
						<div class="modal-header">
							<h4 id="largeModalLabel" class="modal-title">Update Doctor</h4>
							<button type="button" data-dismiss="modal" class="close">
								<span aria-hidden="true">X</span><span class="sr-only">Close</span>
							</button>
						</div>
						<input type ="hidden" id="docid" value ="" />
						<input type ="hidden" id="docloginid" value ="" />
						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Title
								</label>
								<div class="col-sm-8">
									<select id="dutitle">
									<option value="Dr">Dr.</option>
										<!-- <option value="Mr">Mr.</option>
										<option value="Ms">Ms.</option>
										<option value="Miss">Miss.</option> -->
									</select>
								</div>
							</div>

						</div>
						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">EMail
									ID <font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="duemail" name="duemail" class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Password
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="dupass" name="dupass" type="password"
										class="form-control required" />
								</div>
							</div>
						</div>

						<div class="form-group row">

							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">First
									Name <font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="dufname" name="userName-v"
										class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Last
									Name<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="dulanme" name="dulname" class="form-control required" />
								</div>
							</div>
						</div>

						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Middle
									Name </label>
								<div class="col-sm-8">
									<input id="dumname" name="dumname" class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Mobile
									No. <font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="dumob" name="dumob" class="form-control required" />
								</div>
							</div>
						</div>
						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Registration
									Year <font color="red">*</font>
								</label>
								<div class="col-sm-8">
								<select id="duregyr" name="duregyr" class="form-control required">
								<script type="text/javascript">
									  var myDate = new Date();
									  var year = myDate.getFullYear();
									  for(var i = year; i >1945 ; i--){
										  document.write('<option value="'+i+'">'+i+'</option>');
									  }
  									</script>
								</select>
									
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">MCI Registration ID
									 <font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="duregno" name="duregno" class="form-control required" />
								</div>
							</div>
						</div>

						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">State
									Med Concuncil <font color="red">*</font>
								</label>
								<div class="col-sm-8">
								<select id="dumcounc" name="dumcounc" class="form-control required" >
								<option value="Andhra Pradesh">Andhra Pradesh</option>
								<option value="Arunachal Pradesh">Arunachal Pradesh</option>
								<option value="Assam">Assam</option>
								<option value="Bihar">Bihar</option>
								<option value="Chattisghar">Chattisghar</option>
								<option value="Chandigarh">Chandigarh</option>
								<option value="Dadra and Nagar Haveli">Dadra and Nagar Haveli</option>
								<option value="Daman and Diu">Daman and Diu</option>
								<option value="Delhi">Delhi</option>
								<option value="Goa">Goa</option>
								<option value="Gujarat">Gujarat</option>
								<option value="Haryana">Haryana</option>
								<option value="Himachal Pradesh">Himachal Pradesh</option>
								<option value="Jammu and Kashmir">Jammu and Kashmir</option>
								<option value="Jharkhand">Jharkhand</option>
								<option value="Karnataka">Karnataka</option>
								<option value="Kerala">Kerala</option>
								<option value="Madhya Pradesh">Madhya Pradesh</option>
								<option value="Manipur">Manipur</option>
								<option value="Meghalaya">Megalaya</option>
								<option value="Mizoram">Mizoram</option>
								<option value="Nagaland">Nagaland</option>
								<option value="Orissa">Orissa</option>
								<option value="Punjab">Punjab</option>
								<option value="Pondicherry">Pondicherry</option>
								<option value="Rajasthan">Rajasthan</option>
								<option value="Sikkim">Sikkim</option>
								<option value="Tamilnadu">Tamilnadu</option>
								<option value="Tripura">Tripura</option>
								<option value="Uttar Pradesh">Uttar Pradesh</option>
								<option value="Uttarakhand">Uttarakhand</option>
								<option value="West Bengal">West Bengal</option>
								
								</select>
									
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Therapeutic
									Area </label>
								<div class="col-sm-8">
									 <select id = "updatedoctarea"  class="form-control">
       										 <c:forEach items="${tareaList}" var="tareaobj">
            										<option value="${tareaobj.therapeuticId}"><c:out value="${tareaobj.therapeuticName}"/></option>
        									</c:forEach>
    								</select>
								</div>
							</div>

						</div>


						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Address
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="duaddr" name="duaddr" class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Address1
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="duaddr1" name="duaddr1" class="form-control required" />
								</div>
							</div>
						</div>

						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">City
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="ducity" name="ducity" class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">State
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="dustate" name="dustate" class="form-control required" />
								</div>
							</div>
						</div>

						<div class="form-group row" >
								<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Country
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="ducountry" name="ducountry"
										class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Zip
									Code </label>
								<div class="col-sm-8">
									<input id="duzip" name="duzip" class="form-control required" />
								</div>
							</div>

						</div>
						<div class="form-group row" >
								<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Location Type
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									
										<select id="duloctyType"  class="form-control required" >
										 <c:forEach items="${locationtypelist}" var="loctypeobj">
										<c:set var="idAsString">${loctypeobj.locationtypeId}</c:set>
											<option value="${idAsString}"><c:out value="${loctypeobj.locationType}"/>
										 </option></c:forEach></select>
								</div>
							</div>
							</div >
							<div id="dynamic-add-add"></div>
							<div id="room_fileds_updated"></div>
						<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label"></label>
										<div class="col-sm-8">
											<input type="button" id="more_fields" onclick="add_update_fields();"
												value=" + Add Another Address" class="addctntxt" />
												<input type="hidden" id="locationpage"/>
										</div>
									</div>
								</div>
							
							<div>
						<div class="row l-box-body l-spaced">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label"></label>
								<div class="col-sm-8">
									<input type="submit" class="btn btn-green" value="Update"
										style="position: absolute; right: -60px; top: -9px"
										id="updatesubmitbuttn" />
										
								</div>
							</div>
						</div>

					</div>
					
				</div>
			</div>
<script type="text/javascript">
					var updatepage =1;
                      var arrPage = 1;
                      function add_update_fields() {
                    	  updatepage=  document.getElementById('locationpage').value;
                    	  updatepage++;
                      var objTo = document.getElementById('room_fileds_updated');
                      var divtest = document.createElement("div");
                      if(arrPage == 1) { divtest.innerHTML = '<div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Address<font color="red">*</font></label><div class="col-sm-8"><input id="duaddr'+updatepage +'" name="duaddr'+updatepage +' "class="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Address1<font color="red">*</font></label><div class="col-sm-8"><input id="duaddr1'+updatepage +'" name="duaddr1'+updatepage +'" class="form-control required" /></div></div></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">City<font color="red">*</font></label><div class="col-sm-8"><input id="ducity'+updatepage +'" name="ducity'+updatepage +'" class="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">State<font color="red">*</font></label><div class="col-sm-8"><input id="dustate'+updatepage +'" name="dustate'+updatepage +'" class="form-control required" /></div></div></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Country <font color="red">*</font></label><div class="col-sm-8"><input id="ducountry'+updatepage +'" name="ducountry'+updatepage +'" class="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Zip Code </label><div class="col-sm-8"><input id="duzip'+updatepage +'" name="duzip'+updatepage +'"  class="form-control required" /></div></div></div>	<div class="form-group row" ><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Location Type<font color="red">*</font></label><div class="col-sm-8"><select id="duloctyType'+updatepage+'" name="duloctyType'+updatepage+'" class="form-control required"/ ><c:forEach items="${locationtypelist}" var="loctypeobj"> <c:set var="idAsString">${loctypeobj.locationtypeId}</c:set><option value="${idAsString}"><c:out value="${loctypeobj.locationType}"/></option></c:forEach></select></div></div></div>'; }
                      else { divtest.innerHTML = '<div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Address<font color="red">*</font></label><div class="col-sm-8"><input id="duaddr'+updatepage +'" name="duaddr'+updatepage +'" class="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Address1<font color="red">*</font></label><div class="col-sm-8"><input id="duaddr1'+updatepage +'" name="duaddr1'+updatepage +'" class="form-control required" /></div></div></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">City<font color="red">*</font></label><div class="col-sm-8"><input id="ducity'+updatepage +'" name="ducity'+updatepage +'" class="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">State<font color="red">*</font></label><div class="col-sm-8"><input id="dustate'+updatepage +'" name="dustate'+updatepage +'" class="form-control required" /></div></div></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Country <font color="red">*</font></label><div class="col-sm-8"><input id="ducountry'+updatepage +'" name="ducountry'+updatepage +'" class="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Zip Code </label><div class="col-sm-8"><input id="duzip'+updatepage +'" name="duzip'+updatepage +'" class="form-control required" /></div></div></div> 	<div class="form-group row" ><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Location Type<font color="red">*</font></label><div class="col-sm-8"><select id="duloctyType'+updatepage+'" name="duloctyType'+updatepage+'" class="form-control required" ><c:forEach items="${locationtypelist}" var="loctypeobj"> <c:set var="idAsString">${loctypeobj.locationtypeId}</c:set><option value="${idAsString}"><c:out value="${loctypeobj.locationType}"/></option></c:forEach></select></div></div></div>'; }
                      objTo.appendChild(divtest);
                      ++arrPage;
                      }
                      
                    </script>
			<!-- Update Doctor end here -->

		</div>
	</div>
</div>



<script type="text/javascript">


//Doctor Update
$("#updatedocbuttn").click(function() {
	var id = 0;
	$("input:checkbox[name=removeDoctors]:checked").each(function() {
		id = $(this).val();
	});
	//alert();
	$.ajax({
		url : 'getDoctor.do',
		type : 'GET',
		data : 'doctorId=' + id,
		dataType : "json",
		success : function(data) {
			try {
				$("#docid").val(data.doctorId);
				$("#docloginid").val(data.username);
				$("#dutitle").val(data.title);
				$("#duemail").val(data.emailId);
				$("#dupass").val(data.password);
				$("#dufname").val(data.firstName);
				$("#dulanme").val(data.lastName);
				$("#dumname").val(data.middleName);
				$("#dumob").val(data.mobileNo);
				$("#duregyr").val(data.registrationYear);
				$("#duregno").val(data.registrationNumber);
				$("#dumcounc").val(data.stateMedCouncil);
				$("#duaddr").val(data.locations[0].address1);
				$("#duaddr1").val(data.locations[0].address2);
				$("#ducity").val(data.locations[0].city);
				$("#dustate").val(data.locations[0].state);
				$("#ducountry").val(data.locations[0].country);
				$("#duzip").val(data.locations[0].zipcode);
				$("#updatedoctarea").val(data.therapeuticId);
				$("#duloctyType").val(data.locations[0].locationType);
				$("#dynamic-add-add").val("");
				$("#locationpage").val(data.locations.length);
					
				var localstring="";
				for(var i=1;i<data.locations.length;i++)
					{
					var j=i+1;
					//localstring="</div>";
					localstring=localstring+'<div class="form-group row" id="divdel"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Address<font color="red">*</font></label><div class="col-sm-8"><input id="duaddr'+j+'" name="duaddr'+j+'" value="'+data.locations[i].address1+'" class="form-control required" /></div></div>';
					localstring=localstring+'<div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Address1<font color="red">*</font></label><div class="col-sm-8"><input id="duaddr1'+j+'" name="duaddr1'+j+'" value="'+data.locations[i].address2+'" class="form-control required" /></div></div></div>';
					localstring=localstring+'<div class="form-group row" id="divdel"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">City<font color="red">*</font></label><div class="col-sm-8"><input id="ducity'+j+'" name="ducity'+j+'" value="'+data.locations[i].city+'" class="form-control required" /></div></div>';
					localstring=localstring+'<div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">State<font color="red">*</font></label><div class="col-sm-8"><input id="dustate'+j+'" name="dustate'+j+'"  value="'+data.locations[i].state+'" class="form-control required" /></div></div></div>'
					localstring=localstring+'<div class="form-group row" id="divdel"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Country<font color="red">*</font></label><div class="col-sm-8"><input id="ducountry'+j+'" name="ducountry'+j+'"  value="'+data.locations[i].country+'" class="form-control required" /></div></div>'
					localstring=localstring+'<div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Zip Code </label><div class="col-sm-8"><input id="duzip'+j+'" name="duzip'+j+'" value="'+data.locations[i].zipcode+'" class="form-control required" /></div></div></div>';
					localstring=localstring+'<div class="form-group row" id="divdel"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Location Type<font color="red">*</font></label><div class="col-sm-8"><select id="duloctyType'+j+'" name="duloctyType'+j+'" value="'+data.locations[i].locationType+'"  class="form-control required" ><c:forEach items="${locationtypelist}" var="loctypeobj"><c:set var="idAsString">${loctypeobj.locationtypeId}</c:set><option value="${idAsString}"><c:out value="${loctypeobj.locationType}"/></option></c:forEach></select></div></div></div>'; 
					}
				$("#dynamic-add-add").html(localstring)

			} catch (err) {
				console.log(err);
			}
		},
		error : function(e) {
			console.log(e);
		}
	});

	$(".ui-dialog-titlebar").hide();
	$("#updateDoctorModel").dialog("open");
});
function trackdownload(suffix)
{
	$.ajax({
		url : 'getReport.do',
		type : 'GET',
		data : 'doctorId=' + id,
		dataType : "json",
		success : function(data) {
			alert("Downloaded Succssfully");
		},
		error: function(e) {
			console.log(e);
		}
	});
}
</script>
