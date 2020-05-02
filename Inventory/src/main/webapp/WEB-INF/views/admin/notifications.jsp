<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src='<c:url value="/resources/js/basic/multiselect.js"/>'></script>
<script src='<c:url value="/resources/js/notifications.js"/>'></script>

<c:if test="${not empty  notificationmsg}">
   <div class="notificationmsg">${notificationmsg}</div>
</c:if>

<div class="l-page-header">
	<h2 class="l-page-title">
		<span>Notifications</span> PAGE
	</h2>
	<!--BREADCRUMB-->
	<ul class="breadcrumb t-breadcrumb-page">
		<li><a href="javascript:void(0)">Home</a></li>
		<li class="active">Notifications</li>
	</ul>
</div>
<div class="l-box no-border">
	<div class="l-box l-spaced-bottom">

		<div class="l-spaced">
			<div id="tables_accordion" class="resp-tabs-skin-1">
				<ul class="resp-tabs-list">
					<li id="nlist">Notification List</li>
					<li id="vscrollTable">Create Notification</li>
					<li id="plist">Publish Notification</li>
				</ul>
				<div class="resp-tabs-container">
					<!-- Default Table-->
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>Notification</span> Table
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
												<th>Status</th>
												<th>View Details</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="notificationObj" items="${notificationList}">
												<tr>
													<td>
														<div class="checkbox">
															<label><input type="radio" name="srado"
																value="${notificationObj.notificationId}" /></label>
														</div>
													</td>

													<td>${notificationObj.notificationName}</td>
													<td>${notificationObj.createdOn}</td>
													<td>${notificationObj.companyName}</td>
													<td>${notificationObj.therapeuticName}</td>
													<td>${notificationObj.status}</td>
													<td><a href="javascript:void(0)"
														onclick="viewNotificationModel(${notificationObj.notificationId})"
														class="btn-link">View</a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<div class="l-box-body l-spaced">
										<button type="button" id="notificationUpdatebtn"
											class="btn btn-green">Update</button>
										<button type="button" id="notificationRemovebtn"
											class="btn btn-green">Remove</button>
										<button type="button" id="notificationdownloadbtn" title="Click here to download Report"
											class="btn btn-green">Download</button>
									</div>

									<div id="notificationViewModel" style="display: none">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" data-dismiss="modal" class="close">
													<span aria-hidden="true">×</span><span class="sr-only">Close</span>
												</button>
											</div>
											<div class="form-group row">
												<div class="col-md-6">
													<h4 id="largeModalLabel" class="modal-title"><span> View </span>
														Notification</h4>
													<div class="modal-body">
														<dl class="dl-horizontal">
															<dt>Notification Name</dt>
															<dd id="nvtitle"></dd>
															<dt>Notification Description</dt>
															<dd id="nvdesc"></dd>
															<dt>Company</dt>
															<dd id="nvcompany"></dd>
															<dt>Therapeutic Area</dt>
															<dd id="nvtarea"></dd>
															<dt>Created Date</dt>
															<dd id="nvdt"></dd>
															<!-- <div id="detailsViewId"></div> -->
														</dl>

													</div>
												</div>
												<div class="col-md-6">
													<h4 id="largeModalLabel" class="modal-title">Notification
														Details</h4>
													<div class="col-md-12">
														<div id="noticontainer" onmouseover="stopimg();"
															onmouseout="resumeimg();">
															<img src="#" width="100%" height="300px" id="slider" />
															<div id="imgdescription"></div>
															<a href="#" id="previmg" onclick="previmg();"><i
																class="icon fa fa-chevron-left"></i></a> <a href="#"
																id="neximg" onclick="newImg();"><i
																class="icon fa fa-chevron-right"></i></a>
														</div>
														<script type="text/javascript">
														 /* var slide = [
													                  {src:'<c:url value="/resources/img/notification-img1.jpg"/>',
													                    des:"this is new image for onclick position."
													                  },
													                  {src:'<c:url value="/resources/img/notification-img2.jpg"/>',des:"image two"},
													                  {src:'<c:url value="/resources/img/notification-img3.jpg"/>',des:"image three"},
													                ]; */
                var slide = [
                ];
                var img = document.getElementById("slider");
                var index = 0;

                var imgdescription = document.getElementById("imgdescription");


                function newImg(){
                	 if (index >=slide.length){
                         index = 0;
                         }else{
                        	 index ++;
                         }
                img.setAttribute("src", slide[index].src);
                imgdescription.innerHTML = slide[index].des;


                }

                function previmg(){


                if(index < 0){
                  index = slide.length-1;
                }else{
                	 index --;
                }
                  img.setAttribute("src",slide[index].src);
                  imgdescription.innerHTML = slide[index].des;
                }

              </script>
													</div>
													<div class="modal-body pull-left">
														<dl class="dl-horizontal">
															<dt>Notifications Sent</dt>
															<dd id="nvsent"></dd>
															<dt>Notifications Viewd</dt>
															<dd id="nvview"></dd>
															<dt>Notifications Pending</dt>
															<dd id="nvpending"></dd>
															<dt>Converted to Appointments</dt>
															<dd id="nvappoints"></dd>
															<!-- <dt>Notification Remainders</dt>
															<dd id="nvremainders"></dd>
															<dt>Notification favorites</dt>
															<dd id="nvfavorites"></dd> -->
														</dl>
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
								<span>Create</span> Notifications
							</h2>
						</div>
						<div class="l-box-body l-spaced">
							<form:form id="validateDefault" enctype="multipart/form-data" onsubmit="return validateForm()"
								method="POST" cssClass="form-horizontal validate" role="form"
								novalidate="novalidate" action="createNotification.do"
								commandName="notificationFormObj">
								<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Notification
											Name <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:input id="userName-v" name="userName-v"
												path="notificationName" cssClass="form-control required" />
										</div>
									</div>
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Notification
											Description</label>
										<div class="col-sm-8">
											<form:textarea cssClass="form-control"
												path="notificationDesc" required="" rows="5" name="message"
												id="msg" aria-required="true"></form:textarea>
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
										<label for="userName-v" class="col-sm-4 control-label">Therapeutic
											Area <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:select path="therapeuticId" cssClass="form-control" id="therapeuticAreaId"
												items="${therapeuticMap}">
											</form:select>
										</div>
									</div>
								</div>
								<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">
											Status <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:select id="ncstat" class="form-control" path="status">
												<option>----- Select Notification Life Cycle -----</option>
												<option>New</option>
												<option>Publish</option>
												<option>Retired</option>
											</form:select>
										</div>
									</div>
								</div>
								<div class="form-group row">
									<h2 class="l-box-title">page 1</h2>
								</div>

								<div id="room_fileds">
									<div class="form-group row">
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Notification
												Detail Name <font color="red">*</font></label>
											<div class="col-sm-8">
												<form:input id="userName-v-sub" name="userName-v"
													path="notificationDetails[0].detailTitle"
													cssClass="form-control required" />
											</div>
										</div>
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Notification
												Detail Description</label>
											<div class="col-sm-8">

												<form:textarea cssClass="form-control"
													path="notificationDetails[0].detailDesc" required=""
													rows="5" name="message" id="msg" aria-required="true"></form:textarea>
											</div>
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Notification
												Detail Type</label>
											<div class="col-sm-8">
												<select class="form-control" name="_type">
													<option>----- Select Notification Type -----</option>
													<option>PDF</option>
													<option>Video</option>
													<option>Image</option>
												</select>
											</div>
										</div>
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Uplaod
												File </label>
											<div class="col-sm-8">
												<div class="input-group">
													<span class="input-group-btn"><span
														class="btn btn-primary btn-file">Upload File... <form:input
																type="file" path="fileList"></form:input></span></span> <input
														type="text" readonly class="form-control">
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label"></label>
										<div class="col-sm-8">
											<input type="button" id="more_fields" onclick="add_fields();"
												value=" + Add Another Page" class="addctntxt" />
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

									<script type="text/javascript">
                      var page = 1;
                      var arrPage = 1;
                      function add_fields() {
                      page++;
                      var objTo = document.getElementById('room_fileds');
                      var divtest = document.createElement("div");
                      if(arrPage == 1) { divtest.innerHTML = '<div class="form-group row"><h2 class="l-box-title">Page ' + page +':</h2></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Name</label><div class="col-sm-8"><form:input id="userName-v" name="userName-v" path="notificationDetails[1].detailTitle" cssClass="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Description</label><div class="col-sm-8"><form:textarea cssClass="form-control" path="notificationDetails[1].detailDesc" required="" rows="5" name="message" id="msg" aria-required="true"></form:textarea></div></div></div><div class="form-group row"><div class="col-sm-6"><label class="col-sm-4 control-label">Notification Type</label><div class="col-sm-8"><select class="form-control"><option>----- Select Notification Type -----</option><option>PDF</option><option>Video</option><option>Image</option></select></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Uplaod File</label><div class="col-sm-8"><div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...<form:input type="file" path="fileList"></form:input></span></span><input type="text" readonly="" class="form-control"></div></div></div></div>'; }
                      else if(arrPage == 2) { divtest.innerHTML = '<div class="form-group row"><h2 class="l-box-title">Page ' + page +':</h2></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Name</label><div class="col-sm-8"><form:input id="userName-v" name="userName-v" path="notificationDetails[2].detailTitle" cssClass="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Description</label><div class="col-sm-8"><form:textarea cssClass="form-control" path="notificationDetails[2].detailDesc" required="" rows="5" name="message" id="msg" aria-required="true"></form:textarea></div></div></div><div class="form-group row"><div class="col-sm-6"><label class="col-sm-4 control-label">Notification Type</label><div class="col-sm-8"><select class="form-control"><option>----- Select Notification Type -----</option><option>PDF</option><option>Video</option><option>Image</option></select></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Uplaod File</label><div class="col-sm-8"><div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...<form:input type="file" path="fileList"></form:input></span></span><input type="text" readonly="" class="form-control"></div></div></div></div>'; }
                      else if(arrPage == 3) { divtest.innerHTML = '<div class="form-group row"><h2 class="l-box-title">Page ' + page +':</h2></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Name</label><div class="col-sm-8"><form:input id="userName-v" name="userName-v" path="notificationDetails[3].detailTitle" cssClass="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Description</label><div class="col-sm-8"><form:textarea cssClass="form-control" path="notificationDetails[3].detailDesc" required="" rows="5" name="message" id="msg" aria-required="true"></form:textarea></div></div></div><div class="form-group row"><div class="col-sm-6"><label class="col-sm-4 control-label">Notification Type</label><div class="col-sm-8"><select class="form-control"><option>----- Select Notification Type -----</option><option>PDF</option><option>Video</option><option>Image</option></select></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Uplaod File</label><div class="col-sm-8"><div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...<form:input type="file" path="fileList"></form:input></span></span><input type="text" readonly="" class="form-control"></div></div></div></div>'; }
                      else if(arrPage == 4) { divtest.innerHTML = '<div class="form-group row"><h2 class="l-box-title">Page ' + page +':</h2></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Name</label><div class="col-sm-8"><form:input id="userName-v" name="userName-v" path="notificationDetails[4].detailTitle" cssClass="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Description</label><div class="col-sm-8"><form:textarea cssClass="form-control" path="notificationDetails[4].detailDesc" required="" rows="5" name="message" id="msg" aria-required="true"></form:textarea></div></div></div><div class="form-group row"><div class="col-sm-6"><label class="col-sm-4 control-label">Notification Type</label><div class="col-sm-8"><select class="form-control"><option>----- Select Notification Type -----</option><option>PDF</option><option>Video</option><option>Image</option></select></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Uplaod File</label><div class="col-sm-8"><div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...<form:input type="file" path="fileList"></form:input></span></span><input type="text" readonly="" class="form-control"></div></div></div></div>'; }
                      else if(arrPage == 5) { divtest.innerHTML = '<div class="form-group row"><h2 class="l-box-title">Page ' + page +':</h2></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Name</label><div class="col-sm-8"><form:input id="userName-v" name="userName-v" path="notificationDetails[5].detailTitle" cssClass="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Description</label><div class="col-sm-8"><form:textarea cssClass="form-control" path="notificationDetails[5].detailDesc" required="" rows="5" name="message" id="msg" aria-required="true"></form:textarea></div></div></div><div class="form-group row"><div class="col-sm-6"><label class="col-sm-4 control-label">Notification Type</label><div class="col-sm-8"><select class="form-control"><option>----- Select Notification Type -----</option><option>PDF</option><option>Video</option><option>Image</option></select></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Uplaod File</label><div class="col-sm-8"><div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...<form:input type="file" path="fileList"></form:input></span></span><input type="text" readonly="" class="form-control"></div></div></div></div>'; }
                      else if(arrPage == 6) { divtest.innerHTML = '<div class="form-group row"><h2 class="l-box-title">Page ' + page +':</h2></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Name</label><div class="col-sm-8"><form:input id="userName-v" name="userName-v" path="notificationDetails[6].detailTitle" cssClass="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Description</label><div class="col-sm-8"><form:textarea cssClass="form-control" path="notificationDetails[6].detailDesc" required="" rows="5" name="message" id="msg" aria-required="true"></form:textarea></div></div></div><div class="form-group row"><div class="col-sm-6"><label class="col-sm-4 control-label">Notification Type</label><div class="col-sm-8"><select class="form-control"><option>----- Select Notification Type -----</option><option>PDF</option><option>Video</option><option>Image</option></select></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Uplaod File</label><div class="col-sm-8"><div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...<form:input type="file" path="fileList"></form:input></span></span><input type="text" readonly="" class="form-control"></div></div></div></div>'; }
                      else if(arrPage == 7) { divtest.innerHTML = '<div class="form-group row"><h2 class="l-box-title">Page ' + page +':</h2></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Name</label><div class="col-sm-8"><form:input id="userName-v" name="userName-v" path="notificationDetails[7].detailTitle" cssClass="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Description</label><div class="col-sm-8"><form:textarea cssClass="form-control" path="notificationDetails[7].detailDesc" required="" rows="5" name="message" id="msg" aria-required="true"></form:textarea></div></div></div><div class="form-group row"><div class="col-sm-6"><label class="col-sm-4 control-label">Notification Type</label><div class="col-sm-8"><select class="form-control"><option>----- Select Notification Type -----</option><option>PDF</option><option>Video</option><option>Image</option></select></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Uplaod File</label><div class="col-sm-8"><div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...<form:input type="file" path="fileList"></form:input></span></span><input type="text" readonly="" class="form-control"></div></div></div></div>'; }
                      else if(arrPage == 8) { divtest.innerHTML = '<div class="form-group row"><h2 class="l-box-title">Page ' + page +':</h2></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Name</label><div class="col-sm-8"><form:input id="userName-v" name="userName-v" path="notificationDetails[8].detailTitle" cssClass="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Description</label><div class="col-sm-8"><form:textarea cssClass="form-control" path="notificationDetails[8].detailDesc" required="" rows="5" name="message" id="msg" aria-required="true"></form:textarea></div></div></div><div class="form-group row"><div class="col-sm-6"><label class="col-sm-4 control-label">Notification Type</label><div class="col-sm-8"><select class="form-control"><option>----- Select Notification Type -----</option><option>PDF</option><option>Video</option><option>Image</option></select></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Uplaod File</label><div class="col-sm-8"><div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...<form:input type="file" path="fileList"></form:input></span></span><input type="text" readonly="" class="form-control"></div></div></div></div>'; }
                      else { divtest.innerHTML = '<div class="form-group row"><h2 class="l-box-title">Page ' + page +':</h2></div><div class="form-group row"><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Name</label><div class="col-sm-8"><form:input id="userName-v" name="userName-v" path="notificationDetails[9].detailTitle" cssClass="form-control required" /></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Notification Detail Description</label><div class="col-sm-8"><form:textarea cssClass="form-control" path="notificationDetails[9].detailDesc" required="" rows="5" name="message" id="msg" aria-required="true"></form:textarea></div></div></div><div class="form-group row"><div class="col-sm-6"><label class="col-sm-4 control-label">Notification Type</label><div class="col-sm-8"><select class="form-control"><option>----- Select Notification Type -----</option><option>PDF</option><option>Video</option><option>Image</option></select></div></div><div class="col-sm-6"><label for="userName-v" class="col-sm-4 control-label">Uplaod File</label><div class="col-sm-8"><div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...<form:input type="file" path="fileList"></form:input></span></span><input type="text" readonly="" class="form-control"></div></div></div></div>'; }
                      objTo.appendChild(divtest);
                      ++arrPage;
                      }
                    </script>

                    </form:form>
						</div>

					</div>
				</div>

				<!-- Notification Publish Start ...... -->
				<div class="l-row l-spaced-bottom">
					<div class="l-box">
						<div class="l-box-header">
							<h2 class="l-box-title">
								<span>Publish </span> Notification
							</h2>
						</div>

							<section class="l-box-body l-spaced">
								<form:form id="validateDefault" method="POST" cssClass="form-horizontal validate" role="form"
												novalidate="novalidate" action="publishNotification.do" commandName="notificationFormObj">
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
												<!--  <form:select multiple="multiple" id="pdocsselectid" path="publishDocsIds"  cssClass="width: 300px;"
												>
											</form:select>-->
											
											<form:select multiple="multiple" id="pdocsselectid" path="publishDocsIds" itemLabel="displayName" cssClass="width: 300px;"
												itemValue="doctorId" items="${docsList}">
											</form:select>
											</div>
										</div>
									</div>

									<div class="form-group row">
									<div class="col-sm-6">
												 <label for="userName-v" class="col-sm-4 control-label">Notifications
												 </label>
										<div class="col-sm-8">
										 <form:select id="pnotificationselectid" path="publishNotificationId" cssClass="form-control" itemLabel="notificationName"
												itemValue="notificationId" items="${notificationList}">
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
									</form:form>
							</section>
						</div>
					</div>

				</div>
				<!-- Notification Publish end -->
				<!--  Notification Update  -->
				<div class="l-row l-spaced-bottom" id="notificationUpdateModel">
					<div class="l-box">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" data-dismiss="modal" class="close">
									<span aria-hidden="true">×</span><span class="sr-only">Close</span>
								</button>
							</div>

							<section class="l-box-body l-spaced">
								<form:form id="validateDefault" enctype="multipart/form-data" onsubmit="return validateUpdateForm()"
									method="post" cssClass="form-horizontal validate" role="form"
									novalidate="novalidate" action="updateNotification.do"
									commandName="notificationFormObj">
									<form:input type="hidden" id="nuid" path="notificationId"
										value="" />
										<h4 id="largeModalLabel" class="modal-title"><span>Update</span>
														Notification</h4>
									<div class="form-group row">
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Notification
												Name <font color="red">*</font></label>
											<div class="col-sm-8">
												<form:input id="nuname" name="userName-v"
													path="notificationName" cssClass="form-control required" />
											</div>
										</div>
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Notification
												Description</label>
											<div class="col-sm-8">
												<form:textarea cssClass="form-control"
													path="notificationDesc" required="" rows="5" name="message"
													id="nudesc" aria-required="true"></form:textarea>
											</div>
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Company
												Name <font color="red">*</font></label>
											<div class="col-sm-8">
												<form:select path="companyId" cssClass="form-control" onchange="getAllTherapeuticData('update')"
													id="nucompany" items="${companyMap}">
												</form:select>
											</div>

										</div>
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">Therapeutic
												Area <font color="red">*</font></label>
											<div class="col-sm-8">
												<form:select path="therapeuticId" cssClass="form-control"
													id="nutharea" items="${therapeuticMap}">
												</form:select>
											</div>
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label for="userName-v" class="col-sm-4 control-label">
												Status <font color="red">*</font></label>
											<div class="col-sm-8">
												<form:select class="form-control" path="status" id="nustauts">
													<option>----- Select Notification Life Cycle -----</option>
													<option>New</option>
													<option>Publish</option>
													<option>Retired</option>
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
							</section>
							</form:form>
						</div>
					</div>

				</div>
				<!-- Notification Update end -->
			</div>
		</div>
	</div>
</div>

<!-- Notification Remove Hidden form  -->
					<form action="removeNotification.do" id="notificationdelform" method="post">
						<input type="hidden" id="notificationDelId" name="notificationDelId" value="" />
					</form>
					<!-- Notification Remove Hidden form END  -->

					<form action="getDocsByTAreas.do" id="getdocsbytareaform" method="post">
						<input type="hidden" id="tareasids" name="tareaIds" value="" />
					</form>
					
					<form action="Report" id="notificationstat" method="post">
						<input type="hidden" id="notificationStatId" name="notificationStatId" value="" />
					</form>

							<script>

							$(document).ready(function(){

								$("#tables_accordion").easyResponsiveTabs({
							        type: "default", //Types: default, vertical, accordion
							        width: 'auto', //auto or any width like 600px
							        fit: true,   // 100% fit in a container
							        closed: 'accordion', // Start closed if in accordion view
							        activate: function(event) {
							            // Callback function if tab is switched
							        }
							    });
								
								
								var publsh = "${publish}";
								if (publsh == "true") {
									console.log(1111)
								var stab = 2 // selected tab
								lis = $("ul.resp-tabs-list > li");
								lis.removeClass("resp-tab-active");
								$("ul.resp-tabs-list li[aria-controls='tab_item-"+stab+"']").addClass("resp-tab-active");
								divs = $("div.resp-tabs-container > div");
								divs.removeClass("resp-tab-content-active");
								divs.removeAttr("style");

								$("#tables_accordion >.resp-tabs-container div[aria-labelledby='tab_item-"+stab+"']").addClass("resp-tab-content-active").attr("style","display: block;");

								}
							});
							function getAllDocsByTArea() {
								 var selectedValues = $('#ptareaselectid').val();	
								 $("#tareasids").val(selectedValues);
								 $('#pdocsselectid option').remove();
								 
									 $.ajax({
										   url: 'getDocsByTAreass.do',
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
</script>
