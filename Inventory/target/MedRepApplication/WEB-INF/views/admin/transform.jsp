<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src='<c:url value="/resources/js/transform.js"/>'></script>
<c:if test="${not empty  transformmsg}">
   <div class="notificationmsg">${transformmsg}</div> 
</c:if>
<div class="l-page-header">
	<h2 class="l-page-title">
		<span>TRANSFORM</span> PAGE
	</h2>
	<!--BREADCRUMB-->
	<ul class="breadcrumb t-breadcrumb-page">
		<li><a href="javascript:void(0)">Home</a></li>
		<li class="active">Transform</li>
	</ul>
</div>
<div class="l-box no-border">
	<div class="l-box l-spaced-bottom">

		<div class="l-spaced">
			<div id="tables" class="resp-tabs-skin-1">
				<ul class="resp-tabs-list">
					<li id="nlist">Transform List</li>
					<li id="vscrollTable">Upload Transform</li>
					<li id="plistsource">Source</li>
					<li id="plist">Category</li>
				</ul>
				<div class="resp-tabs-container">
					<!-- Default Table-->
					<div>
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>Transform</span> Table
									</h2>
								</div>
								<div class="l-box-body">
									<table id="dataTableId" cellspacing="0" width="100%"
										class="display">
										<thead>
											<tr>
												<th>Select</th>
												<th>Title</th>
												<th>Source Name</th>
												<th>Category Name</th>												
												<th>Therapeutic Area</th>
												<th>Create Date</th>
												<th>View Details</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="transformObj" items="${transformList}">
												<tr>
													<td>
														<div class="checkbox">
															<label><input type="radio" name="srado"
																value="${transformObj.transformId}"></label>
														</div>
													</td>

													<td>${transformObj.title}</td>
													<td>${transformObj.sourceName}</td>	
													<td>${transformObj.categoryName}</td>													
													<td>${transformObj.therapeuticName}</td>
													<td>${transformObj.createdOn}</td>
													<td><a href="javascript:void(0)"
														onclick="viewTransformModel(${transformObj.transformId})"
														class="btn-link">View</a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<div class="l-box-body l-spaced">									
										<button type="button" id="transformRemovebtn"
											class="btn btn-green">Remove</button>										
									</div>

									<div id="transformViewModel" style="display: none">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" data-dismiss="modal" class="close">
													<span aria-hidden="true">×</span><span class="sr-only">Close</span>
												</button>
											</div>
											<div class="form-group row">
												<div class="col-md-6">
													<h4 id="largeModalLabel" class="modal-title"><span> View </span>
														Transform</h4>
													<div class="modal-body">
														<dl class="dl-horizontal">
															<dt>Title</dt>
															<dd id="tvtitle"></dd>
															<dt>Tag Description</dt>
															<dd id="tvtdesc"></dd>
															<dt>Category Name</dt>
															<dd id="tvcategory"></dd>
															<dt>Source Name</dt>
															<dd id="tvsource"></dd>
															<dt>Therapeutic Area</dt>
															<dd id="tvtarea"></dd>
															<dt>Transform Description</dt>
															<dd id="tvndesc"></dd>	
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
								<span>Upload</span> Transform
							</h2>
						</div>
						<section class="l-box-body l-spaced">
							<form:form id="validateDefault" enctype="multipart/form-data" 
								method="post" cssClass="form-horizontal validate" role="form"
								novalidate="novalidate" action="uploadTransform.do"
								commandName="transformFormObj">
								<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Transform Title
											 <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:input id="userName-v" name="userName-v"
												path="title" cssClass="form-control required" />
										</div>
									</div>
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Tag Description
											 <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:input id="userName-v" name="userName-v"
												path="tagDesc" cssClass="form-control required" />
										</div>
									</div>									
								</div>
								
								<div class="form-group row">									
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Transform Source
											<font color="red">*</font></label>
										<div class="col-sm-8">
											<select id = "sourceId" name="sourceId" class="form-control">
											<option value=0> ----------   Select One  -------- </option>
       										 <c:forEach items="${transformSourcesList}" var="tsourceobj">
            										<option value="${tsourceobj.transformId}"><c:out value="${tsourceobj.transformSourceName}"/></option>
        									</c:forEach>
        									</select>
										</div>
									</div>
									
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Transform Category
											<font color="red">*</font></label>
										<div class="col-sm-8">
											<select id = "categoryId" name="categoryId" class="form-control">
											<option value=0> ----------   Select One  -------- </option>
       										 <c:forEach items="${transformCategoryList}" var="tcategoryobj">
            										<option value="${tcategoryobj.categoryId}"><c:out value="${tcategoryobj.categoryName}"/></option>
        									</c:forEach>
        									</select>
										</div>
									</div>
									
								</div>		
								<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">
												Cover Image </label>
											<div class="col-sm-8">
												<div class="input-group">
													<span class="input-group-btn"><span
														class="btn btn-primary btn-file">Upload File... <form:input
																type="file" path="coverImgFile"></form:input></span></span> <input
														type="text" readonly="" class="form-control">
												</div>
											</div>
									</div>
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Uplaod
												Inner Image </label>
											<div class="col-sm-8">
												<div class="input-group">
													<span class="input-group-btn"><span
														class="btn btn-primary btn-file">Upload File... <form:input
																type="file" path="innerImgFile"></form:input></span></span> <input
														type="text" readonly="" class="form-control">
												</div>
											</div>
									</div>
									</div>
									<div class="form-group row">
									  <div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Uplaod
												Video </label>
											<div class="col-sm-8">
												<div class="input-group">
													<span class="input-group-btn"><span
														class="btn btn-primary btn-file">Upload File... <form:input
																type="file" path="videoFile"></form:input></span></span> <input
														type="text" readonly="" class="form-control">
												</div>
											</div>
									</div>	
									
									 <div class="col-sm-6">
									 <label for="userName-v" class="col-sm-4 control-label">Website URL
												</label>
											<div class="col-sm-8">
												<form:input id="userName-v" name="userName-v"
												path="postUrl" cssClass="form-control required" />
											</div>
									 </div>								
								</div>
								<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">
											Transform Description <font color="red">*</font></label>
												<div class="col-sm-8">
											<form:textarea cssClass="form-control"
												path="transformDesc" required="" rows="5" name="message"
												id="msg" aria-required="true"></form:textarea>
										</div>
										
									</div>		
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Therapeutic<font color="red"></font></label>
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
								
								<div class="row l-box-body l-spaced">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label"></label>
										<div class="col-sm-8">
											<input type="submit" class="btn btn-green" value="Submit" />
										</div>
									</div>
								</div>
								
								</form:form>
					</section>
						
					</div>
				</div>				
				
				<!--  Transform Source  -->
				<div>
						<div class="l-row l-spaced-bottom">
					<div class="l-box">
						<div class="l-box-header">
							<h2 class="l-box-title">
								Create <span>Source </span>
							</h2>
						</div>			
							
							<section class="l-box-body l-spaced">
							<form:form id="validateDefault" enctype="multipart/form-data" onsubmit="return validateForm()"
								method="post" cssClass="form-horizontal validate" role="form"
								novalidate="novalidate" action="uploadTransformSource.do"
								commandName="transformSourceFormObj">
								<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Name
											 <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:input id="userName-v" name="userName-v"
												path="transformSourceName" cssClass="form-control required" />
										</div>
									</div>
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">URL
											 <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:input id="userName-v" name="userName-v"
												path="transformSourceUrl" cssClass="form-control required" />
										</div>
									</div>									
								</div>
									<div class="row l-box-body l-spaced">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label"></label>
										<div class="col-sm-8">
											<input type="submit" class="btn btn-green" value="Submit" />
										</div>
									</div>
								</div>
								</form:form>
							</section>							
						</div>
					</div>	
				</div>
				<!-- Transform Source End -->
				<!--  Transform Category  -->
				<div>
						<div class="l-row l-spaced-bottom">
					<div class="l-box">
						<div class="l-box-header">
							<h2 class="l-box-title">
								Create <span>Category </span>
							</h2>
						</div>			
							
							<section class="l-box-body l-spaced">
							<form:form id="validateDefault" enctype="multipart/form-data" onsubmit="return validateForm()"
								method="post" cssClass="form-horizontal validate" role="form"
								novalidate="novalidate" action="uploadTransformCategory.do"
								commandName="transformCategoryFormObj">
								<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Name
											 <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:input id="userName-v" name="userName-v"
												path="categoryName" cssClass="form-control required" />
										</div>
									</div>
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">URL
											 <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:input id="userName-v" name="userName-v"
												path="categoryUrl" cssClass="form-control required" />
										</div>
									</div>									
								</div>
									<div class="row l-box-body l-spaced">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label"></label>
										<div class="col-sm-8">
											<input type="submit" class="btn btn-green" value="Submit" />
										</div>
									</div>
								</div>
								</form:form>
							</section>							
						</div>
					</div>	
				</div>
				<!-- Transform Category End -->
			
				
			
				<!--  Notification Update  -->
				
				<!-- Notification Update end -->
				
				
			</div>
		</div>
	</div>
</div>

<!-- Notification Remove Hidden form  -->
				<form action="deleteTransform.do" id="transformdelform" method="post">
						<input type="hidden" id="transformId" name="transformId" value="" />
					</form>