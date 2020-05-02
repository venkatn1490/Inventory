<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <script src='<c:url value="/resources/js/basic/multiselect.js"/>'></script>--%>
<script src='<c:url value="/resources/js/pharmawebinars.js"/>'></script> 

<c:if test="${not empty  webinarssmsg}">
   <div class="webinarssmsg">${webinarssmsg}</div>
</c:if>

<div class="l-page-header">
	<h2 class="l-page-title">
		<span>Pharma Webinars</span> PAGE
	</h2>
	<!--BREADCRUMB-->
	<ul class="breadcrumb t-breadcrumb-page">
		<li><a href="javascript:void(0)">Home</a></li>
		<li class="active">Pharma Webinars</li>
	</ul>
</div>
<div class="l-box no-border">
	<div class="l-box l-spaced-bottom">

		<div class="l-spaced">
			<div id="tables" class="resp-tabs-skin-1">
				<ul class="resp-tabs-list">
					<li id="nlist">Pharma Webinars List</li>
					<li id="plist">Create Pharma Webinars </li>
				</ul>
				<div class="resp-tabs-container">
					<!-- Default Table-->
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>Webinars</span> Table
									</h2>
								</div>
								<div class="l-box-body">

									<table id="dataTableId3" class="display">
										<thead>
											<tr>
												<th>Select</th>
												<th> Webinars Name</th>
												<th>Creating Date</th>
												<th>Company</th>
												<th>Venue Date</th>
												<th>Status</th>
												<th>View Details</th>
										</tr>
										</thead>
										<tbody>
											<c:forEach var="pharmaWebinarObj" items="${pharmaWebinarsList}">
												<tr>
													<td>
														<div class="checkbox">
															<label><input type="radio" name="srado"
																value="${pharmaWebinarObj.webinarId}" /></label>
														</div>
													</td>

													<td>${pharmaWebinarObj.webinarName}</td>
													<td>${pharmaWebinarObj.cDate}</td>
													<td>${pharmaWebinarObj.companyName}</td>
													<td>${pharmaWebinarObj.vdate}</td>
<%-- 													<td>${pharmaWebinarObj.url}</td>
 --%>													<td>${pharmaWebinarObj.status}</td>
 													<td><a data-toggle="modal" id="new"
															data-target="#LargeModelInprogress" class="btn-link"
															onClick="viewModel(${pharmaWebinarObj.webinarId})">View</a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								
								  <div id="LargeModelInprogress" tabindex="-1" role="dialog"
										aria-labelledby="largeModalLabel" aria-hidden="true"
										class="modal fade">
										<div class="modal-dialog modal-lg">
											<div class="modal-content">
												<div class="modal-header">
													<h4 id="largeModalLabel" class="modal-title">View
														Webinars Details</h4>
													<button type="button" data-dismiss="modal" class="close">
														<span aria-hidden="true">X</span><span class="sr-only">Close</span>
													</button>
												</div>

												<div class="form-group row">
														
													<div class="col-md-6">
														<div class="modal-body">
															<dl class="dl-horizontal">	
																																										
																<dt style="margin-top: 9px;">Choose Status</dt>
																<dd>
																	<center>
																		<form action="../admin/updateWebinars.do" method="post"
																			id="formInprogress">
																			<select name="statusDropDown"
																				class="l-spaced form-control">
																				<option value="New">------ select Choose
																					Status -----</option>
																				<option value="Live">Live</option>
																				<option value="Recorded">Recorded</option>
																			</select>
																			 <input name="webniarId" id="webniarIdsChangeStatus"
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
				<!-- Vertical Scroll Table-->
					<div class="l-row l-spaced-bottom">
						<div class="l-box">
							<div class="l-box-header">
								<h2 class="l-box-title">
								<span>Create</span> Webinars
							</h2>
						</div>
						<div class="l-box-body l-spaced">
							<form:form id="validateDefault" enctype="multipart/form-data" onsubmit="return validateForm()"
								method="POST" cssClass="form-horizontal validate" role="form"
								novalidate="novalidate" action="createWebinars.do"
								commandName="pharmaWebinarFormObj">
								<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Webinar
											Name <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:input id="userName-v" name="userName-v"
												path="webinarName" cssClass="form-control required" />
										</div>
									</div>
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Webinar
											Description</label>
										<div class="col-sm-8">
											<form:textarea cssClass="form-control"
												path="webinarDesc" required="" rows="5" name="webinarDesc"
												id="webinarDesc" aria-required="true"></form:textarea>
										</div>
									</div>
								</div>
								<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Company
											Name <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:select id="companySelectedId" path="companyId" cssClass="form-control" onchange="getAllTherapeuticData('create')"
												items="${companyMap}">
											</form:select>
										</div>

									</div>
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Webinar
											Date <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:input id="userName-v" type="date" name="userName-v"
												path="vdate" cssClass="form-control required" />
										</div>
									</div>
								</div>
								<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">
											Status <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:select id="ncstat" class="form-control" path="status">
												<option>----- Select Webinar Life Cycle -----</option>
												<option>Live</option>
												<option>Recorded</option>
											</form:select>
										</div>
									</div>
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Webinar
											URL <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:input id="userName-v" name="userName-v"
												path="vurl" cssClass="form-control required" />
										</div>
									</div>
								</div>
								<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Upload Thumb Image
											 <font color="red">*</font></label>
										<div class="col-sm-8">
												<div class="input-group">
													<span class="input-group-btn"><span
														class="btn btn-primary btn-file">Upload File... <form:input
																type="file" path="thumbImage"></form:input></span></span> <input
														type="text" readonly class="form-control">
												</div>
											</div>

								</div>
								<!-- <div class="row l-box-body l-spaced"> -->
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label"></label>
										<div class="col-sm-8">
											<input type="submit" class="btn btn-green" value="Submit" />
										</div>
									</div>			
                   			 </form:form>
						</div>

					</div>
				</div>

				<!-- Notification Publish Start ...... -->
				

				</div>
				<!-- Notification Publish end -->
				<!--  Notification Update  -->
				
				<!-- Notification Update end -->
			</div>
		</div>
	</div>
</div>

<!-- Notification Remove Hidden form  -->
<script type="text/javascript">
	
	</script>
