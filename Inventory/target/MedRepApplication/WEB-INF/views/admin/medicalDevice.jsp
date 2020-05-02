<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty  deviceMsg}">
   <div class="notificationmsg">${deviceMsg}</div> 
</c:if>
<div class="l-page-header">
	<h2 class="l-page-title">
		<span>Medical Devices</span> PAGE
	</h2>
	<!--BREADCRUMB-->
	<ul class="breadcrumb t-breadcrumb-page">
		<li><a href="javascript:void(0)">Home</a></li>
		<li class="active">Medical Devices</li>
	</ul>
</div>
<div class="l-box no-border">
	<div class="l-box l-spaced-bottom">

		<div class="l-spaced">
			<div id="tables" class="resp-tabs-skin-1">
				<ul class="resp-tabs-list">
					<li id="nlist">Medical Devices List</li>
					<li id="vscrollTable">Create Medical Device</li>
					<li id="plistsource">Order History</li>
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
								 <form action="../admin/devices.do" method="post" id="devicesRemoveUpdate">
									<table id="dataTableId" cellspacing="0" width="100%"
										class="display">
										<thead>
											<tr>
												<th>Select</th>
												<th>Name</th>
												<th>Creating Date</th>
												<th>Company</th>												
												<th>Therapeutic Area</th>
												<th>status</th>
												<th>View Details</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach var="devicesObj" items="${deviceformlist}">
												<tr>
													<td>
														<div class="checkbox">
															<label><input type="radio" name="srado"
																value="${devicesObj.device_id}"></label>
														</div>
													</td>

													<td>${devicesObj.device_name}</td>
													<td>${devicesObj.createdOn}</td>	
													<td>${devicesObj.companyName}</td>													
													<td>${devicesObj.therapeuticName}</td>
													<td>${devicesObj.createdOn}</td>
													<td><a data-toggle="modal" data-target="#LargeModel" class="btn-link" onClick="viewDeviceModel(${devicesObj.device_id})">View</a>
													<%-- <a href="javascript:void(0)"
														onclick="viewDeviceModel(${devicesObj.device_id})"
														class="btn-link">View</a> --%></td>
												</tr>
											</c:forEach>
										</tbody>
										</table>
										</form>
										<div class="l-box-body l-spaced">
                            <button type="submit" class="btn btn-green" name="buttonName" value="UpdateToTab" form="devicesRemoveUpdate">Update</button>
                            <button type="submit" class="btn btn-green" name="buttonName" value="Remove" form="devicesRemoveUpdate">Remove</button>
                        </div>
										
      <div id="LargeModel" tabindex="-1" role="dialog" aria-labelledby="largeModalLabel" aria-hidden="true" class="modal fade">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
             <h4 id="largeModalLabel" class="modal-title">View Medical Device</h4>
            <button type="button" data-dismiss="modal" class="close"><span aria-hidden="true">X</span><span class="sr-only">Close</span></button>
          </div>
          <div class="form-group row">
          <div class="col-md-6">
          <div class="modal-body">
            <dl class="dl-horizontal">
              <dt>Medical Device Name</dt>
              <dd id="DeviceName"><%-- ${pharmaCompany.companyName} --%></dd>
              <dt>Medical Device Description</dt>
              <dd id="DeviceDesc"><%-- ${pharmaCompany.companyDesc} --%></dd>
              <dt>Company Name</dt>
              <dd id="companyName"><%-- ${pharmaCompany.contactName} --%></dd>
              <dt>Therapeutic Areas</dt>
              <dd id="therapeuticArea">
              <%-- <c:forEach var="therapeuticArea" items="${pharmaCompany.therapeuticAreas}">
              	${therapeuticArea.therapeuticName}</br>
              </c:forEach></br> --%></dd>
              <dt>Features</dt>
              <dd id="DeviceFeatures"><%-- ${pharmaCompany.location.city} --%></dd>
              <dt>Price</dt>
              <dd id="DevicePrice"><%-- ${pharmaCompany.location.state} --%></dd>
              <dt>Per</dt>
              <dd id="DeviceUnit"><%-- ${pharmaCompany.location.country} --%></dd>
              
            </dl>
          </div>
          </div>
          <div class="col-md-6">
            <div class="modal-body">
	           <a id="DeviceUrl" href="" target="_blank"><span><img id="devicePicture" src = "" alt="" style="max-width:60px;height:auto;"/></span></a>
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
				
		
<!-- </div>
</div> -->

<div class="l-row l-spaced-bottom">
                <div class="l-box">
                <div class="l-box-header">
                  <h2 class="l-box-title"><span>Create</span> Medical Device</h2></div>
                  <section class="l-box-body l-spaced">
                  <form id="createMedicalDevice" class="form-horizontal validate" role="form" action="../admin/medicalDeviceUpdate.do" method="post" enctype="multipart/form-data">
                  <div class="form-group row">
                    <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Medical Device Name<font color="red">*</font></label>
                        <div class="col-sm-8"><input type="text" class="form-control required" name="device_name" id="device_name" ></div>
                    </div>
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Medical Device Description<font color="red">*</font></label>
                        <div class="col-sm-8">
                          <textarea class="form-control required" placeholder="Enter the company description" rows="3" id="device_desc" name="device_desc"></textarea>
                          </div>
                        </div>
                   
                    </div>
                    <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Medical Device Image</label>
                        <div class="col-sm-8">
                          <div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...
                            <input type="file" required="required"  id="deviceLogo" name="deviceLogo" size="50"></span></span>
                            <input type="text" class="form-control" readonly name="LogoName">
                          </div>
                        </div>
                    </div>
                        <!-- <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Company Url</label>
                        <div class="col-sm-8">
                          <textarea class="form-control" placeholder="Copy paste the company URL" rows="3" id="CompanyUrl" name="CompanyUrl"></textarea>
                          </div>
                        </div> -->
                    </div>
                    
                    <div>
                     
                    </div>
                    <div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Company
											Name <font color="red">*</font></label>
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
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Therapeutic Area<font color="red"></font></label>
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
                        <label class="col-sm-4 control-label" for="userName-v">Comapny Logo</label>
                        <div class="col-sm-8">
                          <div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...
                            <input type="file" required="required"  id="companyLogo" name="companyLogo" size="50"></span></span>
                            <input type="text" class="form-control" readonly name="LogoName">
                          </div>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Features</label>
                        <div class="col-sm-8">
                          
                         <textarea class="form-control"  rows="3" id="features" name="features"></textarea>
                        </div>
                    </div>
                    </div>
                    
                     <div class="l-box-header">
                  <h2 class="l-box-title"><span>Price</span> Information</h2></div>
                    
  <div class="form-group row">
                    <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Medical Device Price<font color="red">*</font></label>
                        <div class="col-sm-8"><input type="text" class="form-control required" name="device_price" id="device_price" ></div>
                    </div>
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Per<font color="red">*</font></label>
                        <div class="col-sm-8">
                          <input type="text" class="form-control required" placeholder="Unit" id="device_unit" name="device_unit"></textarea>
                          </div>
                        </div>
                   
                    </div>
                    <div class="form-group row">
                
                  <div class="col-sm-6">
                  <label class="col-sm-4 control-label" for="basicSelect"></label>
                  <div class="col-sm-8">
                    <input type="hidden" name="CompanyId" value=""/>
                    <button type="submit" class="btn btn-green" name="buttonName" value="Create"  onclick= "return fnSubmit(this);" form="createMedicalDevice">Create</button>
                  </div>
                </div>
                </div>
                <div class="form-group row">
                   <div class="col-sm-6">
                  <div class="col-sm-8">

                  </div>
                  </div>
                </div>
              </form>
              </section>
                    </div>
                </div>
                
                 <div>
                <div class="l-row l-spaced-bottom">
                  <div class="l-box">
                    <div class="l-box-header">
                      <h2 class="l-box-title"><span>Update</span> Medical Device</h2>
                    </div>
                      <c:set var="deviceId_" value=""/>
	                  <c:set var="deviceName_" value=""/>
	                  <c:set var="deviceDesc_" value=""/>
	                  <c:set var="companyUrl_" value=""/>
	                  <c:set var="deviceUrl_" value=""/>
	                  <c:set var="device_price_" value=""/>
	                  <c:set var="device_unit_" value=""/>
	                  <c:set var="companyId_" value=""/>
	                  <c:set var="therapeuticId_" value=""/>
	                  <c:set var="features_" value=""/>
	                 
	                  <c:set var="diabled_attribute" value="disabled"/>
	                  <c:set var="selected_list_theo" value=""/>
	                  <c:if test="${not empty deviceFormObj}">
	 	               <c:set var="deviceId_" value="${deviceFormObj.device_id}"/>
	                  <c:set var="deviceName_" value="${devicesObj.device_name}"/>
	                  <c:set var="deviceDesc_" value="${devicesObj.device_desc}"/>
	                  <c:set var="companyUrl_" value="${devicesObj.companyUrl}"/>
	                  <c:set var="deviceUrl_" value="${devicesObj.deviceUrl}"/>
	                  <c:set var="device_price_" value="${devicesObj.device_price}"/>
	                  <c:set var="device_unit_" value="${devicesObj.device_unit}"/>
	                  <c:set var="companyId_" value="${devicesObj.companyId}"/>
	                  <c:set var="therapeuticId_" value="${devicesObj.therapeuticId}"/>
	                  <c:set var="features_" value="${devicesObj.features}"/>
		                  <c:set var="diabled_attribute" value=""/>
		                  <c:set var="selected_list_theo" value='selected="selected"'/>
	                  </c:if>
                    <section class="l-box-body l-spaced">
                    <form id="updateMedicalDevice" class="form-horizontal validate" role="form" action="../admin/medicalDeviceUpdate.do" method="post" enctype="multipart/form-data">
                   <div class="form-group row">
                    <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Medical Device Name<font color="red">*</font></label>
                        <div class="col-sm-8"><input type="text" class="form-control required" name="device_name" id="device_name" value="${deviceName_}" ${diabled_attribute} ></div>
                    </div>
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Medical Device Description<font color="red">*</font></label>
                        <div class="col-sm-8">
                          <textarea class="form-control required" placeholder="Enter the company description" rows="3" id="device_desc" name="device_desc" ${diabled_attribute}>${deviceDesc_}</textarea>
                          </div>
                        </div>
                   
                    </div>
                    <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Medical Device Image</label>
                        <div class="col-sm-8">
                          <div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...
                            <input type="file" required="required"  id="deviceLogo" name="deviceLogo" size="50"></span></span>
                            <input type="text" class="form-control" readonly name="LogoName">
                          </div>
                        </div>
                    </div>
                        <!-- <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Company Url</label>
                        <div class="col-sm-8">
                          <textarea class="form-control" placeholder="Copy paste the company URL" rows="3" id="CompanyUrl" name="CompanyUrl"></textarea>
                          </div>
                        </div> -->
                    </div>
                    
                    <div>
                     
                    </div>
                    <div class="form-group row">
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Company
											Name <font color="red">*</font></label>
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
									<div class="col-sm-6">
										<label for="userName-v" class="col-sm-4 control-label">Therapeutic Area<font color="red"></font></label>
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
                        <label class="col-sm-4 control-label" for="userName-v">Comapny Logo</label>
                        <div class="col-sm-8">
                          <div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...
                            <input type="file" required="required"  id="companyLogo" name="companyLogo" size="50"></span></span>
                            <input type="text" class="form-control" readonly name="LogoName">
                          </div>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Features</label>
                        <div class="col-sm-8">
                          
                         <textarea class="form-control"  rows="3" id="features" name="features" value="${features_}" ${diabled_attribute}></textarea>
                        </div>
                    </div>
                    </div>
                    
                     <div class="l-box-header">
                  <h2 class="l-box-title"><span>Price</span> Information</h2></div>
                    
  <div class="form-group row">
                    <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Medical Device Price<font color="red">*</font></label>
                        <div class="col-sm-8"><input type="text" class="form-control required" name="device_price" id="device_price" value="${device_price_}" ${diabled_attribute}/></div>
                    </div>
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Per<font color="red">*</font></label>
                        <div class="col-sm-8">
                          <input type="text" class="form-control required" placeholder="Unit" id="device_unit" name="device_unit" value="${device_unit_}" ${diabled_attribute}/>
                          </div>
                        </div>
                   
                    </div>
                  
                  
 <div class="form-group row">
                <div class="col-sm-6">
                  <label class="col-sm-4 control-label" for="basicSelect"></label>
                  <div class="col-sm-8">
                    <input type="hidden" name="device_id" value="${deviceId_}"/>
                    <button type="submit" class="btn btn-green" name="buttonName" value="Update" onclick="return fnSubmit(this);" form="updateMedicalDevice" ${diabled_attribute}>Update</button>
                  </div>
                </div>
                </div>
              </form>
                    </section>
                  </div>
                </div>
                </div>

</div>

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
