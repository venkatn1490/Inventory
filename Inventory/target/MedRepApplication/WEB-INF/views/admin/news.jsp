<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src='<c:url value="/resources/js/news.js"/>'></script>
<c:if test="${not empty  newsmsg}">
   <div class="notificationmsg">${newsmsg}</div> 
</c:if>
<div class="l-page-header">
	<h2 class="l-page-title">
		<span>News</span> PAGE
	</h2>
	<!--BREADCRUMB-->
	<ul class="breadcrumb t-breadcrumb-page">
		<li><a href="javascript:void(0)">Home</a></li>
		<li class="active">News</li>
	</ul>
</div>
<div class="l-box no-border">
	<div class="l-box l-spaced-bottom">

		<div class="l-spaced">
			<div id="tables" class="resp-tabs-skin-1">
				<ul class="resp-tabs-list">
					<li id="nlist">News List</li>
					<li id="vscrollTable">Upload News</li>
					<li id="plist">Sources</li>
				</ul>
				<div class="resp-tabs-container">
					<!-- Default Table-->
					<div>
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>News</span> Table
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
												<th>Therapeutic Area</th>
												<th>Create Date</th>
												<th>View Details</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="newsObj" items="${newsList}">
												<tr>
													<td>
														<div class="checkbox">
															<label><input type="radio" name="srado"
																value="${newsObj.newsId}"></label>
														</div>
													</td>

													<td>${newsObj.title}</td>
													<td>${newsObj.sourceName}</td>													
													<td>${newsObj.therapeuticName}</td>
													<td>${newsObj.createdOn}</td>
													<td><a href="javascript:void(0)"
														onclick="viewNewsModel(${newsObj.newsId})"
														class="btn-link">View</a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<div class="l-box-body l-spaced">									
										<button type="button" id="newsRemovebtn"
											class="btn btn-green">Remove</button>										
									</div>

									<div id="newsViewModel" style="display: none">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" data-dismiss="modal" class="close">
													<span aria-hidden="true">×</span><span class="sr-only">Close</span>
												</button>
											</div>
											<div class="form-group row">
												<div class="col-md-6">
													<h4 id="largeModalLabel" class="modal-title"><span> View </span>
														News</h4>
													<div class="modal-body">
														<dl class="dl-horizontal">
															<dt>Title</dt>
															<dd id="nvtitle"></dd>
															<dt>Tag Description</dt>
															<dd id="nvtdesc"></dd>
															<dt>Source Name</dt>
															<dd id="nvsource"></dd>
															<dt>Therapeutic Area</dt>
															<dd id="nvtarea"></dd>
															<dt>News Description</dt>
															<dd id="nvndesc"></dd>	
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
								<span>Upload</span> News
							</h2>
						</div>
						<section class="l-box-body l-spaced">
							<form:form id="validateDefault" enctype="multipart/form-data" 
								method="post" cssClass="form-horizontal validate" role="form"
								novalidate="novalidate" action="uploadNews.do"
								commandName="newsFormObj">
								<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">News Title
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
										<label for="userName-v" class="col-sm-4 control-label">News Source
											<font color="red">*</font></label>
										<div class="col-sm-8">
											<select id = "sourceId" name="sourceId" class="form-control">
											<option value=0> ----------   Select One  -------- </option>
       										 <c:forEach items="${newsSourcesList}" var="tsourceobj">
            										<option value="${tsourceobj.sourceId}"><c:out value="${tsourceobj.sourceName}"/></option>
        									</c:forEach>
        									</select>
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
											News Description <font color="red">*</font></label>
												<div class="col-sm-8">
											<form:textarea cssClass="form-control"
												path="newsDesc" required="" rows="5" name="message"
												id="msg" aria-required="true"></form:textarea>
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
				<!--  News Source  -->
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
								novalidate="novalidate" action="uploadNewsSource.do"
								commandName="newsSourceFormObj">
								<div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Name
											 <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:input id="userName-v" name="userName-v"
												path="sourceName" cssClass="form-control required" />
										</div>
									</div>
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">URL
											 <font color="red">*</font></label>
										<div class="col-sm-8">
											<form:input id="userName-v" name="userName-v"
												path="sourceUrl" cssClass="form-control required" />
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
				<!-- News Sources End -->
			
				<!--  Notification Update  -->
				
				<!-- Notification Update end -->
				
				
			</div>
		</div>
	</div>
</div>

<!-- Notification Remove Hidden form  -->
				<form action="deleteNews.do" id="newsdelform" method="post">
						<input type="hidden" id="newsId" name="newsId" value="" />
					</form>