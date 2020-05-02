<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src='<c:url value="/resources/js/surveys.js"/>'></script>

<c:if test="${not empty  notificationmsg}">
   <div class="notificationmsg">${notificationmsg}</div>
</c:if>
<div class="l-page-header">
	<h2 class="l-page-title">
		<span>Surveys</span> PAGE
	</h2>
	<!--BREADCRUMB-->
	<ul class="breadcrumb t-breadcrumb-page">
		<li><a href="javascript:void(0)">Home</a></li>
		<li class="active">Surveys</li>
	</ul>
</div>

		<div class="l-spaced">
			<div id="tables_accordion" class="resp-tabs-skin-1">
				<ul class="resp-tabs-list">
					<li>Survey List</li>
					<li>Publish Survey</li>
				</ul>
				<div class="resp-tabs-container">
					<!-- Default Table-->
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>Survey</span> List
									</h2>
								</div>
								<div class="l-box-body">
									<table id="dataTableId" class="display">
										<thead>
											<tr>
												<th>Select</th>
												<th>Name</th>
												<th>Creating Date</th>
												<th>Company</th>
												<th>Therapeutic Area</th>
												<th>Survey Life Cycle Stage</th>
												<th>View Details</th>
											</tr>
										</thead>
										<tbody>

											<c:forEach var="surveyObj" items="${surveyList}">
												<tr>
													<td>
														<div class="checkbox">
															<label><input type="radio" name="srado"
																value="${surveyObj.surveyId}"></label>
														</div>
													</td>

													<td>${surveyObj.surveyTitle}</td>
													<td>${surveyObj.createdOn}</td>
													<td>${surveyObj.companyName}</td>
													<td>${surveyObj.therapeuticName}</td>
													<td>${surveyObj.status}</td>
													<td><a href="javascript:void(0)"
														onclick="viewModel(${surveyObj.surveyId})"
														class="btn-link">View</a></td>
												</tr>
											</c:forEach>

										</tbody>
									</table>
									<div class="l-box-body l-spaced">
										<button type="button" id="surveyUpdatebtn"
											class="btn btn-green">Update</button>
										<button type="button" id="surveyRemovebtn"
											class="btn btn-green">Remove</button>
									</div>

									<div id="surveyViewModel" style="display: none">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" data-dismiss="modal" class="close">
													<span aria-hidden="true">×</span><span class="sr-only">Close</span>
												</button>
											</div>
											<div class="form-group row">
												<div class="col-md-6">
													<h4 id="largeModalLabel" class="modal-title">View
														Survey</h4>
													<div class="modal-body">
														<dl class="dl-horizontal">
															<dt>Survey Name</dt>
															<dd id="sname"></dd>
															<dt>Survey Description</dt>
															<dd id="sdesc"></dd>
															<dt>Preview Survey</dt>
															<dd id="surl"></dd>
															<dt>Company Name</dt>
															<dd id="scname"></dd>
															<dt>Therapeutic Area</dt>
															<dd id="stname"></dd>
															<dt>Created Date</dt>
															<dd id="sdt"></dd>
															<dt>Schedule Start</dt>
															<dd id="sst"></dd>
															<dt>Schedule Finish</dt>
															<dd id="ssf"></dd>
														</dl>

													</div>
												</div>
												<div class="col-md-6">
													<h4 id="largeModalLabel" class="modal-title">Survey
														Details</h4>
													<div class="col-md-12">
														<img src="${pageContext.request.contextPath }/resources/img/notification-img1.jpg"
															class="img-thumbnail">
													</div>
													<div class="modal-body">
														<dl class="dl-horizontal">
															<dt>No. of Surveys Sent</dt>
															<dd id="sursent"></dd>
															<dt>No. of Surveys Viewed</dt>
															<dd id="surview"></dd>
															<dt>No. of Surveys Pending</dt>
															<dd id="surpen"></dd>
															
														</dl>
													</div>
												</div>
											</div>
										</div>
										<!-- <div class="modal-footer">
            <button type="button" data-dismiss="modal" class="btn btn-danger">Close</button>
            <button type="button" class="btn btn-success">Save changes</button>
          </div> -->


									</div>

								</div>
							</div>
						</div>


						<!-- Publish Survey -->
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>Publish </span> Survey
									</h2>
								</div>
								<div class="l-box-body l-spaced">

									<form:form method="post" class="form-horizontal validate" action="publishSurvey.do" commandName="surveyFormObj">
									<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Therapeutics
												 </label>
										<div class="col-sm-8">
										  <form:select multiple="multiple" id="ptareaselectid" path="publishTareaIds" itemLabel="therapeuticName" cssClass="width: 300px;"
												itemValue="therapeuticId" items="${therapeuticsAreaList}">
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
												 <form:select multiple="multiple" id="pdocsselectid" path="publishDocsIds" itemLabel="displayName" cssClass="width: 300px;"
												itemValue="doctorId" items="${docsList}">
											</form:select>
											</div>
										</div>
									</div>


								<div class="form-group row"">
									<div class="col-sm-6">
												 <label for="userName-v" class="col-sm-4 control-label">Survey
												 </label>
										<div class="col-sm-8">

 					<form:select path="publishSurveyId" cssClass="form-control" items="${surveyList}" itemValue="surveyId" itemLabel="surveyTitle">
											</form:select>
										 </div>
										</div>
									</div>

									<div class="form-group row">

									</div>
									<div class="form-group row">

									</div>
									<div class="row l-box-body l-spaced">
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label"></label>
											<div class="col-sm-8">
												<input type="submit" class="btn btn-green" value="Publish" />
											</div>
										</div>
									</div>



								<div>
							</div>
							</form:form>
						</div>

					<!-- </div>-->
					<!-- Vertical Scroll Table-->

					<!-- Survey Remove Hidden form  -->
					<form:form action="removeSurvey.do" id="surveydelform" method="post" commandName="surveyFormObj">
						<input type="hidden" id="surveyDelId" name="surveyDelId" value="" />
					</form:form>

					<form action="getDocsByTAreas.do" id="getdocsbytareaform" method="post">
						<input type="hidden" id="tareasids" name="tareaIds" value="" />
						<input type="hidden" name="referrer" value="surveys" />
					</form>

					<!-- Survey Remove Hidden form END  -->
				</div>
			</div>
		</div>
	</div>
</div>

					<!-- Survey update -->
					<div class="l-row l-spaced-bottom" id="surveyUpdateModel">
						<div class="l-box">
							<div class="modal-header">
								<button type="button" data-dismiss="modal" class="close">
									<span aria-hidden="true">×</span><span class="sr-only">Close</span>
								</button>
							</div>
							<section class="l-box-body l-spaced">
							<h4 id="largeModalLabel" class="modal-title"><span>Update</span>
														Survey</h4>
								<form:form id="validateDefault" onsubmit="return validateForm()"
									cssClass="form-horizontal validate" role="form"
									novalidate="novalidate" action="updateSurvey.do"
									commandName="surveyFormObj">
									<form:input type="hidden" id="smid" path="surveyId" value="" />
									<div class="form-group row">
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Survey
												Name</label>
											<div class="col-sm-8">
												<form:input id="smname" name="userName-v" path="surveyTitle" readonly="true"
													cssClass="form-control required" />
												</td>
											</div>
										</div>
										<div class="col-sm-6">
											<label class="col-sm-4 control-label" for="userName-v">Preview Survey
												</label>
											<div class="col-sm-8">
											<a href="" id="smurl"></a>
											</div>
										</div>

									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Company
												Name<font color="red">*</font></label>
											<div class="col-sm-8">
											   <form:select id="smcompany" path="companyId" cssClass="form-control" onchange="getAllSurveyTherapeuticData()"
												items="${companyMap}">
											</form:select>
											</div>
										</div>
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Therapeutic
												Area <font color="red">*</font></label>
											<div class="col-sm-8">
												<form:select path="therapeuticId" cssClass="form-control" id="smthe"
												items="${therapeuticMap}">
											</form:select>
											</div>
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Survey
												Description</label>
											<div class="col-sm-8">
												<form:textarea cssClass="form-control"
													path="surveyDescription" required="" rows="5"
													name="message" id="smdesc" aria-required="true"></form:textarea>
											</div>
										</div>

										<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">
											Status <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:select class="form-control" path="status" id="smstatus">
												<option>New</option>
												<option>Active</option>
												<option>Publish</option>
												<option>Complete</option>
											</form:select>
										</div>
									</div>

									</div>
									<div class="row l-box-body l-spaced">
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label"></label>
											<div class="col-sm-8">
												<input type="submit" class="btn btn-green" value="Update" />
											</div>
										</div>
									</div>
								</form:form>
							</section>
						</div>
					</div>
					<!-- Survey update end here -->

						<script>

							$(document).ready(function(){

								var publsh = "${publish}";
								var _defaultTab=0;
								if(publsh=="true")
									_defaultTab=1;
								$("#tables_accordion").easyResponsiveTabs({
							        type: "default", //Types: default, vertical, accordion
							        width: 'auto', //auto or any width like 600px
							        fit: true,   // 100% fit in a container
							        closed: 'accordion', // Start closed if in accordion view
							        activate: function(event) {
							            // Callback function if tab is switched
							        },
							        defaultTab:_defaultTab
							    });

								if (publsh == "true")
									$('h2[aria-controls="tab_item-1"]').click();

							});

</script>

