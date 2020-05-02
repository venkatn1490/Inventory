<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src='<c:url value="/resources/js/pharmarep.js"/>'></script>
        <div class="l-page-header">
          <h2 class="l-page-title"><span>Pharma Representatives</span> Page</h2>
          <!--BREADCRUMB-->
          <ul class="breadcrumb t-breadcrumb-page">
            <li><a href="javascript:void(0)">Home</a></li>
            <li class="active">Representatives</li>
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
                      <h2 class="l-box-title"><span>New Representatives</span> Table</h2>
                    </div>
                    <div class="l-box-body">
                    <form action="../admin/removeReps.do" method="post" id="newRepRemove">
                      <table id="dataTableId" cellspacing="0" width="auto" class="display">
                        <thead>
                          <tr>
                            <th>Select</th>
                            <th>Name</th>
                            <th>Company Name</th>
                            <th>Mobile No.</th>
                            <th>E-mail</th>
                            <th>Therapeutic Area</th>
                            <th>Covered Area</th>
                            <th>Covered Zone</th>
                            <th>Details</th>
                          </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="newRep" items="${newRepList}">
	                        <tr>
	                        	<td>
	                              <div class="checkbox">
	                                <label><input type="checkbox" name="removeReps" value="${newRep.repId}"></label>
	                              </div>
	                            </td>
	                            <c:if test="${not empty  newRep.title && not empty  newRep.firstName && not empty  newRep.middleName && not empty  newRep.lastName}">
	                            	<c:set var="repName" value="${newRep.title} ${newRep.firstName} ${newRep.middleName} ${newRep.lastName}"/>
	                            </c:if>
	                            <c:if test="${not empty  newRep.title && not empty  newRep.firstName && empty  newRep.middleName && not empty  newRep.lastName}">
	                            	<c:set var="repName" value="${newRep.title} ${newRep.firstName} ${newRep.lastName}"/>
	                            </c:if>
	                            <td>${newRep.displayName}</td>
	                            <td>${newRep.companyName}</td>
	                            <td>${newRep.mobileNo}</td>
	                            <td>${newRep.emailId}</td>
	                            <td>${newRep.therapeuticName}</td>
	                            <td>${newRep.coveredArea}</td>
	                            <td>${newRep.coveredZone}</td>
		                        <td><a data-toggle="modal" data-target="#LargeModelNew" class="btn-link" onClick="viewModel(${newRep.repId},'New')">View</a></td>
	                        </tr>
                        </c:forEach>
                          </tbody>
                      </table>
                      </form>
                      	<div class="l-box-body l-spaced">
                      	   <a data-toggle="modal" id="new"
											data-target="#createPharmaRepModel"
											onclick="clearData('createrep');" class="btn btn-green">Create</a>
                            <button type="submit" class="btn btn-green" value="Remove" form="newRepRemove">Remove</button>
                        </div>
      <div id="LargeModelNew" tabindex="-1" role="dialog" aria-labelledby="largeModalLabel" aria-hidden="true" class="modal fade">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
 		<div class="modal-header">
            <h4 id="largeModalLabel" class="modal-title">View Pharma Representative Details</h4>
            <button type="button" data-dismiss="modal" class="close"><span aria-hidden="true">X</span><span class="sr-only">Close</span></button>
          </div>
          <div class="form-group row">
          <div class="col-md-6">
          <div class="modal-body">
            <dl class="dl-horizontal">
              <dt>Name</dt>
              <dd id="repNameNew"><%-- ${repName} --%></dd>
              <dt>Representative ID</dt>
              <dd id="repRegistrationNumberNew"><%-- ${inprogressRep.repId} --%></dd>
              <dt>Therapeutic Area</dt>
              <dd id="reptherapeuticNameNew"><%-- ${inprogressRep.therapeuticName} --%></dd>
              <dt>Mobile No.</dt>
              <dd id="repMobileNoNew"><%-- ${inprogressRep.mobileNo} --%></dd>
              <dt>Email</dt>
              <dd id="repEmailIdNew"><%-- ${inprogressRep.emailId} --%></dd>
              <dt>Alternate Email</dt>
              <dd id="repAlternateEmailIdNew"><%-- ${inprogressRep.alternateEmailId}< --%>/dd>
              <dt id="repLocationNoNew"></dt>
              <dd id="repLocationNew"></dd>
            </dl>
          </div>
          </div>
          <div class="col-md-6">
          <div class="modal-body">
            <dl class="dl-horizontal">
              <dt></dt>
              <dd id="repProfilePictureNew"><%-- <center><img src="data:image/${inprogressRep.profilePicture.mimeType};base64,${inprogressRep.profilePicture.data}" alt="Profile picture" style="max-height:90px"></center> --%></dd>
				<dt>&nbsp;</dt>
				<dd>&nbsp;</dd>
              <dt>&nbsp;</dt>
              <dd></dd>
              <%-- <dd><center><button class="btn btn-success l-spaced" type="button">Track Activity Score</button></center></dd> --%>
              <dt style="margin-top:9px;">Choose Status</dt>
              <dd><center>
              <form action="../admin/pharma-reps.do" method="post" id="formNew">
              	<select name="statusDropDown" class="l-spaced form-control">
	                <option value="New">------ select Choose Status -----</option>
	                <option value="Active">Active</option>
	                <option value="Disabled">Disabled</option>
	                <option value="Inprogress">Inprogress</option>
                </select>
                <input name="repId" type="hidden" id="repPharmaRepIdNew" value=""/>
               </form></center>
              </dd>
             <div class="l-spaced l-box-body l-spaced">
            <dt>&nbsp;</dt>
            <dd><center><button type="submit" class="btn btn-green" value="Submit" form="formNew">Update</button></center></dd>
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
                      <h2 class="l-box-title"><span>Active Representatives</span> Table</h2>
                    </div>
                    <div class="l-box-body">
                    <form action="../admin/removeReps.do" method="post" id="activeRepRemove">
                      <table id="dataTableId1" cellspacing="0" width="100%" class="display">
                        <thead>
                          <tr>
                            <th>Select</th>
                            <th>Name</th>
                            <th>Company Name</th>
                            <th>Mobile No.</th>
                            <th>E-mail</th>
                            <th>Therapeutic Area</th>
                            <th>Covered Area</th>
                            <th>Covered Zone</th>
                            <th>Details</th>
                          </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="activeRep" items="${activeRepList}">
	                        <tr>
	                        	<td>
	                              <div class="checkbox">
	                                <label><input type="checkbox" name="removeReps" value="${activeRep.repId}"></label>
	                              </div>
	                            </td>
	                            <c:if test="${not empty  activeRep.title && not empty  activeRep.firstName && not empty  activeRep.middleName && not empty  activeRep.lastName}">
	                            	<c:set var="repName" value="${activeRep.title} ${activeRep.firstName} ${activeRep.middleName} ${activeRep.lastName}"/>
	                            </c:if>
	                            <c:if test="${not empty  activeRep.title && not empty  activeRep.firstName && empty  activeRep.middleName && not empty  activeRep.lastName}">
	                            	<c:set var="repName" value="${activeRep.title} ${activeRep.firstName} ${activeRep.lastName}"/>
	                            </c:if>
	                            <td>${activeRep.displayName}</td>
	                            <td>${activeRep.companyName}</td>
	                            <td>${activeRep.mobileNo}</td>
	                            <td>${activeRep.emailId}</td>
	                            <td>${activeRep.therapeuticName}</td>
	                            <td>${activeRep.coveredArea}</td>
	                            <td>${activeRep.coveredZone}</td>
	                            <td><a data-toggle="modal" data-target="#LargeModelActive" class="btn-link" onClick="viewModel(${activeRep.repId},'Active')">View</a></td>
	                        </tr>
                        </c:forEach>
                          </tbody>
                      </table>
                      	</form>
                      	<div class="l-box-body l-spaced">
                            <button type="submit" class="btn btn-green" value="Remove" form="activeRepRemove">Remove</button>
                        </div>
<div id="LargeModelActive" tabindex="-1" role="dialog" aria-labelledby="largeModalLabel" aria-hidden="true" class="modal fade">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
 		<div class="modal-header">
            <h4 id="largeModalLabel" class="modal-title">View Pharma Representative Details</h4>
            <button type="button" data-dismiss="modal" class="close"><span aria-hidden="true">X</span><span class="sr-only">Close</span></button>
          </div>
          <div class="form-group row">
          <div class="col-md-6">
          <div class="modal-body">
            <dl class="dl-horizontal">
              <dt>Name</dt>
              <dd id="repNameActive"><%-- ${repName} --%></dd>
              <dt>Representative ID</dt>
              <dd id="repRegistrationNumberActive"><%-- ${inprogressRep.repId} --%></dd>
              <dt>Therapeutic Area</dt>
              <dd id="reptherapeuticNameActive"><%-- ${inprogressRep.therapeuticName} --%></dd>
              <dt>Mobile No.</dt>
              <dd id="repMobileNoActive"><%-- ${inprogressRep.mobileNo} --%></dd>
              <dt>Email</dt>
              <dd id="repEmailIdActive"><%-- ${inprogressRep.emailId} --%></dd>
              <dt>Alternate Email</dt>
              <dd id="repAlternateEmailIdActive"><%-- ${inprogressRep.alternateEmailId}< --%>/dd>
              <dt id="repLocationNoActive"></dt>
              <dd id="repLocationActive"></dd>
            </dl>
          </div>
          </div>
          <div class="col-md-6">
          <div class="modal-body">
            <dl class="dl-horizontal">
              <dt></dt>
              <dd id="repProfilePictureActive"><%-- <center><img src="data:image/${inprogressRep.profilePicture.mimeType};base64,${inprogressRep.profilePicture.data}" alt="Profile picture" style="max-height:90px"></center> --%></dd>
				<dt>&nbsp;</dt>
				<dd>&nbsp;</dd>
              <dt>&nbsp;</dt>
              <dd><center><button class="btn btn-success l-spaced" type="button">Track Activity Score</button></center></dd>
              <dt style="margin-top:9px;">Choose Status</dt>
              <dd><center>
              <form action="../admin/pharma-reps.do" method="post" id="formActive">
              	<select name="statusDropDown" class="l-spaced form-control">
	                <option value="New">------ select Choose Status -----</option>
	                <option value="Active">Active</option>
	                <option value="Disabled">Disabled</option>
	                <option value="Inprogress">Inprogress</option>
                </select>
                <input name="repId" type="hidden" id="repPharmaRepIdActive" value=""/>
               </form></center>
              </dd>
             <div class="l-spaced l-box-body l-spaced">
            <dt>&nbsp;</dt>
            <dd><center><button type="submit" class="btn btn-green" value="Submit" form="formActive">Update</button></center></dd>
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
                      <h2 class="l-box-title"><span>Disabled Representatives</span> Table</h2>
                    </div>
                    <div class="l-box-body">
                    <form action="../admin/removeReps.do" method="post" id="disabledRepRemove">
                      <table id="dataTableId2" cellspacing="0" width="100%" class="display">
                        <thead>
                          <tr>
                            <th>Select</th>
                            <th>Name</th>
                            <th>Company Name</th>
                            <th>Mobile No.</th>
                            <th>E-mail</th>
                            <th>Therapeutic Area</th>
                            <th>Covered Area</th>
                            <th>Covered Zone</th>
                            <th>Details</th>
                          </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="disabledRep" items="${disabledRepList}">
	                        <tr>
	                        	<td>
	                              <div class="checkbox">
	                                <label><input type="checkbox" name="removeReps" value="${disabledRep.repId}"></label>
	                              </div>
	                            </td>
	                            <c:if test="${not empty  disabledRep.title && not empty  disabledRep.firstName && not empty  disabledRep.middleName && not empty  disabledRep.lastName}">
	                            	<c:set var="repName" value="${disabledRep.title} ${disabledRep.firstName} ${disabledRep.middleName} ${disabledRep.lastName}"/>
	                            </c:if>
	                            <c:if test="${not empty  disabledRep.title && not empty  disabledRep.firstName && empty  disabledRep.middleName && not empty  disabledRep.lastName}">
	                            	<c:set var="repName" value="${disabledRep.title} ${disabledRep.firstName} ${disabledRep.lastName}"/>
	                            </c:if>
	                            <td>${disabledRep.displayName}</td>
	                            <td>${disabledRep.companyName}</td>
	                            <td>${disabledRep.mobileNo}</td>
	                            <td>${disabledRep.emailId}</td>
	                            <td>${disabledRep.therapeuticName}</td>
	                            <td>${disabledRep.coveredArea}</td>
	                            <td>${disabledRep.coveredZone}</td>
	                            <td><a data-toggle="modal" data-target="#LargeModelDisabled" class="btn-link" onClick="viewModel(${disabledRep.repId},'Disabled')">View</a></td>
	                        </tr>
                        </c:forEach>
                          </tbody>
                      </table>
                      	</form>
                      	<div class="l-box-body l-spaced">
                            <button type="submit" class="btn btn-green" value="Remove" form="disabledRepRemove">Remove</button>
                        </div>
<div id="LargeModelDisabled" tabindex="-1" role="dialog" aria-labelledby="largeModalLabel" aria-hidden="true" class="modal fade">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
 		<div class="modal-header">
            <h4 id="largeModalLabel" class="modal-title">View Pharma Representative Details</h4>
            <button type="button" data-dismiss="modal" class="close"><span aria-hidden="true">X</span><span class="sr-only">Close</span></button>
          </div>
          <div class="form-group row">
          <div class="col-md-6">
          <div class="modal-body">
            <dl class="dl-horizontal">
              <dt>Name</dt>
              <dd id="repNameDisabled"><%-- ${repName} --%></dd>
              <dt>Representative ID</dt>
              <dd id="repRegistrationNumberDisabled"><%-- ${inprogressRep.repId} --%></dd>
              <dt>Therapeutic Area</dt>
              <dd id="reptherapeuticNameDisabled"><%-- ${inprogressRep.therapeuticName} --%></dd>
              <dt>Mobile No.</dt>
              <dd id="repMobileNoDisabled"><%-- ${inprogressRep.mobileNo} --%></dd>
              <dt>Email</dt>
              <dd id="repEmailIdDisabled"><%-- ${inprogressRep.emailId} --%></dd>
              <dt>Alternate Email</dt>
              <dd id="repAlternateEmailIdDisabled"><%-- ${inprogressRep.alternateEmailId}< --%>/dd>
              <dt id="repLocationNoDisabled"></dt>
              <dd id="repLocationDisabled"></dd>
            </dl>
          </div>
          </div>
          <div class="col-md-6">
          <div class="modal-body">
            <dl class="dl-horizontal">
              <dt></dt>
              <dd id="repProfilePictureDisabled"><%-- <center><img src="data:image/${inprogressRep.profilePicture.mimeType};base64,${inprogressRep.profilePicture.data}" alt="Profile picture" style="max-height:90px"></center> --%></dd>
				<dt>&nbsp;</dt>
				<dd>&nbsp;</dd>
              <dt>&nbsp;</dt>
              <dd><center><button class="btn btn-success l-spaced" type="button">Track Activity Score</button></center></dd>
              <dt style="margin-top:9px;">Choose Status</dt>
              <dd><center>
              <form action="../admin/pharma-reps.do" method="post" id="formDisabled">
              	<select name="statusDropDown" class="l-spaced form-control">
	                <option value="New">------ select Choose Status -----</option>
	                <option value="Active">Active</option>
	                <option value="Disabled">Disabled</option>
	                <option value="Inprogress">Inprogress</option>
                </select>
                <input name="repId" type="hidden" id="repPharmaRepIdDisabled" value=""/>
               </form></center>
              </dd>
             <div class="l-spaced l-box-body l-spaced">
            <dt>&nbsp;</dt>
            <dd><center><button type="submit" class="btn btn-green" value="Submit" form="formDisabled">Update</button></center></dd>
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
                      <h2 class="l-box-title"><span>Not Verified Representatives</span> Table</h2>
                    </div>
                    <div class="l-box-body">
                    <form action="../admin/removeReps.do" method="post" id="inprogressRepRemove">
                      <table id="dataTableId3" cellspacing="0" width="100%" class="display">
                        <thead>
                          <tr>
                            <th>Select</th>
                            <th>Name</th>
                            <th>Company Name</th>
                            <th>Mobile No.</th>
                            <th>E-mail</th>
                            <th>Therapeutic Area</th>
                            <th>Covered Area</th>
                            <th>Covered Zone</th>
                            <th>Details</th>
                          </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="inprogressRep" items="${inProgressRepList}">
	                        <tr>
	                        	<td>
	                              <div class="checkbox">
	                                <label><input type="checkbox" name="removeReps" value="${inprogressRep.repId}"></label>
	                              </div>
	                            </td>
	                            <c:if test="${not empty  inprogressRep.title && not empty  inprogressRep.firstName && not empty  inprogressRep.middleName && not empty  inprogressRep.lastName}">
	                            	<c:set var="repName" value="${inprogressRep.title} ${inprogressRep.firstName} ${inprogressRep.middleName} ${inprogressRep.lastName}"/>
	                            </c:if>
	                            <c:if test="${not empty  inprogressRep.title && not empty  inprogressRep.firstName && empty  inprogressRep.middleName && not empty  inprogressRep.lastName}">
	                            	<c:set var="repName" value="${inprogressRep.title} ${inprogressRep.firstName} ${inprogressRep.lastName}"/>
	                            </c:if>
	                             <td>${inprogressRep.displayName}</td>
	                            <td>${inprogressRep.companyName}</td>
	                            <td>${inprogressRep.mobileNo}</td>
	                            <td>${inprogressRep.emailId}</td>
	                            <td>${inprogressRep.therapeuticName}</td>
	                            <td>${inprogressRep.coveredArea}</td>
	                            <td>${inprogressRep.coveredZone}</td>
	                            <td><a data-toggle="modal" data-target="#LargeModelInprogress" class="btn-link" onClick="viewModel(${inprogressRep.repId},'Inprogress')">View</a></td>
	                        </tr>
                        </c:forEach>
                          </tbody>
                      </table>
                      	</form>
                      	<div class="l-box-body l-spaced">
                            <button type="submit" class="btn btn-green" value="Remove" form="inprogressRepRemove">Remove</button>
                        </div>
      <div id="LargeModelInprogress" tabindex="-1" role="dialog" aria-labelledby="largeModalLabel" aria-hidden="true" class="modal fade">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
 		<div class="modal-header">
            <h4 id="largeModalLabel" class="modal-title">View Pharma Representative Details</h4>
            <button type="button" data-dismiss="modal" class="close"><span aria-hidden="true">X</span><span class="sr-only">Close</span></button>
          </div>
          <div class="form-group row">
          <div class="col-md-6">
          <div class="modal-body">
            <dl class="dl-horizontal">
              <dt>Name</dt>
              <dd id="repNameInprogress"><%-- ${repName} --%></dd>
              <dt>Representative ID</dt>
              <dd id="repRegistrationNumberInprogress"><%-- ${inprogressRep.repId} --%></dd>
              <dt>Therapeutic Area</dt>
              <dd id="reptherapeuticNameInprogress"><%-- ${inprogressRep.therapeuticName} --%></dd>
              <dt>Mobile No.</dt>
              <dd id="repMobileNoInprogress"><%-- ${inprogressRep.mobileNo} --%></dd>
              <dt>Email</dt>
              <dd id="repEmailIdInprogress"><%-- ${inprogressRep.emailId} --%></dd>
              <dt>Alternate Email</dt>
              <dd id="repAlternateEmailIdInprogress"><%-- ${inprogressRep.alternateEmailId}< --%>/dd>
              <dt id="repLocationNoInprogress"></dt>
              <dd id="repLocationInprogress"></dd>
            </dl>
          </div>
          </div>
          <div class="col-md-6">
          <div class="modal-body">
            <dl class="dl-horizontal">
              <dt></dt>
              <dd id="repProfilePictureInprogress"><%-- <center><img src="data:image/${inprogressRep.profilePicture.mimeType};base64,${inprogressRep.profilePicture.data}" alt="Profile picture" style="max-height:90px"></center> --%></dd>
				<dt>&nbsp;</dt>
				<dd>&nbsp;</dd>
              <dt>&nbsp;</dt>
              <dd><center><button class="btn btn-success l-spaced" type="button">Track Activity Score</button></center></dd>
              <dt style="margin-top:9px;">Choose Status</dt>
              <dd><center>
              <form action="../admin/pharma-reps.do" method="post" id="formInprogress">
              	<select name="statusDropDown" class="l-spaced form-control">
	                <option value="New">------ select Choose Status -----</option>
	                <option value="Active">Active</option>
	                <option value="Disabled">Disabled</option>
	                <option value="Inprogress">Inprogress</option>
                </select>
                <input name="repId" type="hidden" id="repPharmaRepIdInprogress" value=""/>
               </form></center>
              </dd>
             <div class="l-spaced l-box-body l-spaced">
            <dt>&nbsp;</dt>
            <dd><center><button type="submit" class="btn btn-green" value="Submit" form="formInprogress">Update</button></center></dd>
           </div>
            </dl>
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
                  </div>
                </div>
              </div>
             </div>
            </div>

            	<!-- Create Pharma Rep -->

			<div id="createPharmaRepModel" tabindex="-1" role="dialog"
				aria-labelledby="largeModalLabel" aria-hidden="true"
				class="modal fade">
				<div class="modal-dialog modal-lg" style="height: 1100px">
					<div class="modal-content">
						<div class="modal-header">
							<h4 id="largeModalLabel" class="modal-title">Create Pharma Representative</h4>
							<button type="button" data-dismiss="modal" class="close">
								<span aria-hidden="true">X</span><span class="sr-only">Close</span>
							</button>
						</div>

						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Title
								</label>
								<div class="col-sm-8">
									<select id="prtitle">
										<option value="Mr">Mr.</option>
										<option value="Ms">Ms.</option>
										<option value="Miss">Miss.</option>
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
									<input id="premail" name="premail" class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Password
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="prpass" name="prpass" type="password"
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
									<input id="prfname" name="userName-v"
										class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Last
									Name<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="prlanme" name="prlname" class="form-control required" />
								</div>
							</div>
						</div>

						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Middle
									Name </label>
								<div class="col-sm-8">
									<input id="prmname" name="prmname" class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Mobile
									No. <font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="prmob" name="prmob" class="form-control required" />
								</div>
							</div>
						</div>

						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Role<font color="red">*</font>
								
								</label>
								<div class="col-sm-8">
									<select class="form-control" id="prrole">
											 <option value="3">MedRep</option>
	                						 <option value="4">Rep Manager</option>
									</select>
								</div>
							</div>

							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Manager Email
									 <font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="prmemail" name="prmemail"
										class="form-control required" />
								</div>
							</div>

						</div>

						<div class="form-group row">
						    <div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Company
									Name <font color="red">*</font></label>
								<div class="col-sm-8">
									<select id="prcompanyId" class="form-control" onchange="getAllTherapeuticData()">
											<c:forEach var="entry" items="${companyMap}">
  												<option value="${entry.key}">${entry.value}</option>
											</c:forEach>
									 </select>
								</div>
							</div>

							<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Therapeutic
											Area <font color="red">*</font></label>
										<div class="col-sm-8">
											<select class="form-control" id="prtherapeuticAreaId">
											</select>
										</div>
							</div>

						</div>

						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Covered Area
									 <font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="prcarea" name="prcarea"
										class="form-control required" />
								</div>
							</div>

							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Covered Zone
									 <font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="prczone" name="prczone"
										class="form-control required" />
								</div>
							</div>

						</div>


						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Address
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="praddr" name="praddr" class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">City
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="prcity" name="prcity" class="form-control required" />
								</div>
							</div>
						</div>

						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">State
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="prstate" name="prstate" class="form-control required" />
								</div>
							</div>
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Country
									<font color="red">*</font>
								</label>
								<div class="col-sm-8">
									<input id="prcountry" name="prcountry"
										class="form-control required" />
								</div>
							</div>
						</div>

						<div class="form-group row">
							<div class="col-sm-6">
								<label for="userName-v" class="col-sm-4 control-label">Zip
									Code <font color="red">*</font></label>
								<div class="col-sm-8">
									<input id="przip" name="przip" class="form-control required" />
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

			<!-- Create Pharam Rep end here -->

          </div>
        </div>
          </div>
<script type="text/javascript">
function viewModel(id,suffix){
	  createEmptyDataModel(suffix);
	   $.ajax({
		   url: 'getPharmaRep.do',
		   type: 'GET',
		   data: 'repId='+id,
		   dataType: "json",
		   success: function(data) {
			   try{
				   var pharmaRepName;
				   if(data.middleName!=null)
					{
					   pharmaRepName = data.title+' '+data.firstName+' '+data.middleName+' '+data.lastName;
					}
				   else
					{
					   pharmaRepName = data.title+' '+data.firstName+' '+data.lastName;
					}
			   $("#repPharmaRepId"+suffix).attr("value",data.repId);
			   $("#repName"+suffix).html(data.displayName);
			   $("#repRegistrationNumber"+suffix).html(data.repId);
			   $("#reptherapeuticName"+suffix).html(data.therapeuticName);
			   $("#repMobileNo"+suffix).html(data.mobileNo);
			   $("#repEmailId"+suffix).html(data.emailId);
			   $("#repAlternateEmailId"+suffix).html(data.alternateEmailId);
			   if(data.profilePicture!=null){
				   $("#repProfilePicture"+suffix).html("<center><img src='"+data.profilePicture.dPicture+"' alt='Profile picture' style='max-height:90px'></center>");
			   }
		        var locationString;
		        var loop = 1;
		        $.each(data.locations, function(i, location) {
		        	if(i==0)
		        	{
		        		locationNo = "Address "+(i+1);
		        		locationValue = location.address1+"<br>"+location.address2+"<br>"+location.city+"<br>"+location.state+"<br>"+location.country+"<br>"+location.zipcode;
		        	}
		        	else
		        	{
		        		locationString = "<dt>Address "+(i+1)+"</dt><dd>"+location.address1+"<br>"+location.address2+"<br>"+location.city+"<br>"+location.state+"<br>"+location.country+"<br>"+location.zipcode+"</dd>";
		        	}
		        });
		       $("#repLocationNo"+suffix).html(locationNo);
		       $("#repLocation"+suffix).html(locationValue);
			   }catch(err){
			   }
		   },
		   error: function(e) {
		   }
		 });

	 //Create Empty Model
	 function createEmptyDataModel(suffix){
		 $("#repPharmaRepId"+suffix).attr("value",'');
		   $("#repName"+suffix).html('');
		   $("#repRegistrationNumber"+suffix).html('');
		   $("#reptherapeuticName"+suffix).html('');
		   $("#repMobileNo"+suffix).html('');
		   $("#repEmailId"+suffix).html('');
		   $("#repAlternateEmailId"+suffix).html('');
		   $("#docProfilePicture"+suffix).html('');
		   $("#repLocationNo"+suffix).html('');
	       $("#repLocation"+suffix).html('');
	 }

}
</script>
