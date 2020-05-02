<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <div class="l-page-header">
          <h2 class="l-page-title"><span>Pharma company</span> Page</h2>
          <!--BREADCRUMB-->
          <ul class="breadcrumb t-breadcrumb-page">
            <li><a href="javascript:void(0)">Home</a></li>
            <li class="active">Pharma company</li>
          </ul>
        </div>
          <div class="l-box no-border">
            <div class="l-box l-spaced-bottom">

            <div class="l-spaced">
          <div id="tables" class="resp-tabs-skin-1">
            <ul class="resp-tabs-list">
              <li>List Of Companies</li>
              <li id="vscrollTable">Create Companies</li>
               <li id="employee">Update Company</li>
            </ul>
            <div class="resp-tabs-container">
              <!-- Default Table-->
              <div>
              <div class="l-row l-spaced-bottom">
                  <div class="l-box">
                    <div class="l-box-header">
                      <h2 class="l-box-title"><span>Pharma Company</span> Table</h2>
                    </div>
                    <div class="l-box-body">
                    <form action="../admin/pharma-company.do" method="post" id="pharmaRemoveUpdate">
                      <table id="dataTableId" cellspacing="0" width="100%" class="display">
                        <thead>
                          <tr>
                            <th>Select</th>
                            <th>Company Name</th>
                            <th>Logo</th>
                            <th>Contact Name</th>
                            <th>Contact Number</th>
                            <th>City</th>
                            <th>State</th>
                            <th>View Details</th>
                          </tr>
                        </thead>
                        <tbody>
                         <c:forEach var="pharmaCompany" items="${pharmaCompaniesList}">
                          		<tr>
		                            <td>
		                              <div class="checkbox">
		                                <label><input type="radio" name="removeUpdate" value="${pharmaCompany.companyId}" form="pharmaRemoveUpdate"></label>
		                              </div>
		                            </td>
		                            <td>${pharmaCompany.companyName}</td>
		                            <td><img src = "${pharmaCompany.displayPicture.dPicture}" alt="${pharmaCompany.companyName} Logo" style="max-width: 60px;"/></td>
		                            <td>${pharmaCompany.contactName}</td>
		                            <td>${pharmaCompany.contactNo}</td>
		                            <td>${pharmaCompany.location.city}</td>
		                            <td>${pharmaCompany.location.state}</td>
		                            <td><a data-toggle="modal" data-target="#LargeModel" class="btn-link" onClick="viewModel(${pharmaCompany.companyId})">View</a></td>
		                        </tr>
                          </c:forEach>
                        </tbody>
                      </table>
                      </form>
                      	<div class="l-box-body l-spaced">
                            <button type="submit" class="btn btn-green" name="buttonName" value="UpdateToTab" form="pharmaRemoveUpdate">Update</button>
                            <button type="submit" class="btn btn-green" name="buttonName" value="Remove" form="pharmaRemoveUpdate">Remove</button>
                        </div>

<c:forEach var="pharmaCompany" items="${pharmaCompaniesList}">
      <div id="LargeModel" tabindex="-1" role="dialog" aria-labelledby="largeModalLabel" aria-hidden="true" class="modal fade">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
             <h4 id="largeModalLabel" class="modal-title">View Company</h4>
            <button type="button" data-dismiss="modal" class="close"><span aria-hidden="true">X</span><span class="sr-only">Close</span></button>
          </div>
          <div class="form-group row">
          <div class="col-md-6">
          <div class="modal-body">
            <dl class="dl-horizontal">
              <dt>Company Name</dt>
              <dd id="companyName"><%-- ${pharmaCompany.companyName} --%></dd>
              <dt>Company Description</dt>
              <dd id="companyDesc"><%-- ${pharmaCompany.companyDesc} --%></dd>
              <dt>Contact Name</dt>
              <dd id="companyContactName"><%-- ${pharmaCompany.contactName} --%></dd>
              <dt>Contact Number</dt>
              <dd id="companyContactNo"><%-- ${pharmaCompany.contactNo} --%></dd>
              <dt>Therapeutic Areas</dt>
              <dd id="companyTherapeuticAreas">
              <%-- <c:forEach var="therapeuticArea" items="${pharmaCompany.therapeuticAreas}">
              	${therapeuticArea.therapeuticName}</br>
              </c:forEach></br> --%></dd>
              <dt>City</dt>
              <dd id="companyCity"><%-- ${pharmaCompany.location.city} --%></dd>
              <dt>State</dt>
              <dd id="companyState"><%-- ${pharmaCompany.location.state} --%></dd>
              <dt>Country</dt>
              <dd id="companyCountry"><%-- ${pharmaCompany.location.country} --%></dd>
              <dt>Zip code</dt>
              <dd id="companyZipcode"><%-- ${pharmaCompany.location.zipcode} --%></dd>
            </dl>
          </div>
          </div>
          <div class="col-md-6">
            <div class="modal-body">
	           <a id="companyUrl" href="" target="_blank"><span><img id="companyProfilePicture" src = "" alt="" style="max-width:60px;height:auto;"/></span></a>
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
    </c:forEach>

                    </div>
                  </div>
                </div>
              </div>
              <!-- Vertical Scroll Table-->
                <div class="l-row l-spaced-bottom">
                <div class="l-box">
                <div class="l-box-header">
                  <h2 class="l-box-title"><span>Create</span> Company</h2></div>
                  <section class="l-box-body l-spaced">
                  <form id="createPharmaCompany" class="form-horizontal validate" role="form" action="../admin/pharma-companyUpdate.do" method="post" enctype="multipart/form-data">
                  <div class="form-group row">
                    <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Company Name<font color="red">*</font></label>
                        <div class="col-sm-8"><input type="text" class="form-control required" name="CompanyName" id="companyName" ></div>
                    </div>
                    <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Upload Logo</label>
                        <div class="col-sm-8">
                          <div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...
                            <input type="file" required="required"  id="CompanyLogo" name="CompanyLogo" size="50"></span></span>
                            <input type="text" class="form-control" readonly name="LogoName">
                          </div>
                        </div>
                    </div>
                    </div>
                    <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">About Company<font color="red">*</font></label>
                        <div class="col-sm-8">
                          <textarea class="form-control required" placeholder="Enter the company description" rows="3" id="CompanyDesc" name="CompanyDesc"></textarea>
                          </div>
                        </div>
                         <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Company Url</label>
                        <div class="col-sm-8">
                          <textarea class="form-control" placeholder="Copy paste the company URL" rows="3" id="CompanyUrl" name="CompanyUrl"></textarea>
                          </div>
                        </div>
                    </div>
                    <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Address 1<font color="red">*</font></label>
                        <div class="col-sm-8">
                          <textarea class="form-control required" placeholder="Enter address 1" rows="3" id="CompanyAddress1" name="CompanyAddress1"></textarea>
                          </div>
                        </div>
                        <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Address 2<font color="red">*</font></label>
                        <div class="col-sm-8">
                          <textarea class="form-control required" placeholder="Enter address 2" rows="3" id="CompanyAddress2" name="CompanyAddress2"></textarea>
                          </div>
                        </div>
                    </div>
                     <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">City<font color="red">*</font></label>
                        <div class="col-sm-8">
                         <input type="text" class="form-control required" name="CompanyCity" id="CompanyCity">
                          </div>
                        </div>
                        <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">State<font color="red">*</font></label>
                        <div class="col-sm-8">
                         <input type="text" class="form-control required" name="CompanyState" id="CompanyState">
                          </div>
                        </div>
                    </div>
                    <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Country<font color="red">*</font></label>
                        <div class="col-sm-8">
                         <input type="text" class="form-control required" name="CompanyCountry" id="CompanyCountry">
                          </div>
                        </div>
                        <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Zipcode<font color="red">*</font></label>
                        <div class="col-sm-8">
                         <input type="text" class="form-control required" name="CompanyZipcode" id="CompanyZipcode">
                          </div>
                        </div>
                    </div>
                    <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Point Of Contact Name<font color="red">*</font></label>
                        <div class="col-sm-8">
                         <input type="text" class="form-control required" name="CompanyContactName" id="CompanyContactName">
                          </div>
                        </div>
                        <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Point Of Contact Phone No.<font color="red">*</font></label>
                        <div class="col-sm-8">
                         <input type="text" class="form-control required" name="CompanyContactNo" id="CompanyContactNo">
                          </div>
                        </div>
                    </div>

                    <div class="form-group row">
                    <div class="col-sm-6">
                  <label class="col-sm-4 control-label" for="basicMultiSelect">Therapeutic Area<font color="red">*</font></label>
                  <div class="col-sm-8">
                  <select name="CompanyTherapeuticAreas" id="CompanyTherapeuticAreas" size="5" multiple="multiple" tabindex="1" style="width:18.6em;">
                  <c:forEach var="therapeuticAreas" items="${therapeuticAreaList}">
			        <option value="${therapeuticAreas.therapeuticId}">${therapeuticAreas.therapeuticName}</option>
			      </c:forEach>
			      </select>
                  </div>
                  </div>
                  <div class="col-sm-6">
                  <label class="col-sm-4 control-label" for="basicSelect"></label>
                  <div class="col-sm-8">
                    <input type="hidden" name="CompanyId" value=""/>
                    <button type="submit" class="btn btn-green" name="buttonName" value="Create"  onclick= "return fnSubmit(this);" form="createPharmaCompany">Create</button>
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

                <!--employee table --->
                <div>
                <div class="l-row l-spaced-bottom">
                  <div class="l-box">
                    <div class="l-box-header">
                      <h2 class="l-box-title"><span>Update</span> Company details</h2>
                    </div>
                      <c:set var="companyId_" value=""/>
	                  <c:set var="companyName_" value=""/>
	                  <c:set var="companyDesc_" value=""/>
	                  <c:set var="companyUrl_" value=""/>
	                  <c:set var="companyAddress1_" value=""/>
	                  <c:set var="companyAddress2_" value=""/>
	                  <c:set var="companyCity_" value=""/>
	                  <c:set var="companyState_" value=""/>
	                  <c:set var="companyCountry_" value=""/>
	                  <c:set var="companyZipcode_" value=""/>
	                  <c:set var="companyContactName_" value=""/>
	                  <c:set var="companyContactNo_" value=""/>
	                  <c:set var="diabled_attribute" value="disabled"/>
	                  <c:set var="selected_list_theo" value=""/>
	                  <c:if test="${not empty pharmaCompany}">
		                  <c:set var="companyId_" value="${pharmaCompany.companyId}"/>
		                  <c:set var="companyName_" value="${pharmaCompany.companyName}"/>
		                  <c:set var="companyDesc_" value="${pharmaCompany.companyDesc}"/>
		                  <c:set var="companyUrl_" value="${pharmaCompany.companyUrl}"/>
		                  <c:set var="companyAddress1_" value="${pharmaCompany.location.address1}"/>
		                  <c:set var="companyAddress2_" value="${pharmaCompany.location.address2}"/>
		                  <c:set var="companyCity_" value="${pharmaCompany.location.city}"/>
		                  <c:set var="companyState_" value="${pharmaCompany.location.state}"/>
		                  <c:set var="companyCountry_" value="${pharmaCompany.location.country}"/>
		                  <c:set var="companyZipcode_" value="${pharmaCompany.location.zipcode}"/>
		                  <c:set var="companyContactName_" value="${pharmaCompany.contactName}"/>
		                  <c:set var="companyTherapeuticAreas_" value="${pharmaCompany.therapeuticAreas}"/>
		                  <c:set var="companyContactNo_" value="${pharmaCompany.contactNo}"/>
		                  <c:set var="diabled_attribute" value=""/>
		                  <c:set var="selected_list_theo" value='selected="selected"'/>
	                  </c:if>
                    <section class="l-box-body l-spaced">
                    <form id="updatePharmaCompany" class="form-horizontal validate" role="form" action="../admin/pharma-companyUpdate.do" method="post" enctype="multipart/form-data">
                  <div class="form-group row">
                    <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Company Name<font color="red">*</font></label>
                        <div class="col-sm-8"><input type="text" class="form-control required" name="CompanyName" id="CompanyName" value="${companyName_}" ${diabled_attribute}></div>
                    </div>
                    <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Upload Logo</label>
                        <div class="col-sm-8">
                          <div class="input-group"><span class="input-group-btn"><span class="btn btn-primary btn-file">Upload File...
                            <input type="file" required="required" name="CompanyLogo" id="CompanyLogo" size="50"></span></span>
                            <input type="text" class="form-control" readonly name="LogoName">
                          </div>
                        </div>
                    </div>
                    </div>
                    <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">About Company<font color="red">*</font></label>
                        <div class="col-sm-8">
                          <textarea class="form-control required" placeholder="Enter the company description" rows="3" id="CompanyDesc" name="CompanyDesc" ${diabled_attribute}>${companyDesc_}</textarea>
                          </div>
                        </div>
                        <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Company Url</label>
                        <div class="col-sm-8">
                          <textarea class="form-control" placeholder="Copy paste the company URL" rows="3" id="CompanyUrl" name="CompanyUrl" ${diabled_attribute}>${companyUrl_}</textarea>
                          </div>
                        </div>
                    </div>
                    <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Address 1<font color="red">*</font></label>
                        <div class="col-sm-8">
                          <textarea class="form-control required" placeholder="Enter address 1" rows="3" id="CompanyAddress1" name="CompanyAddress1" ${diabled_attribute}>${companyAddress1_}</textarea>
                          </div>
                        </div>
                        <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Address 2<font color="red">*</font></label>
                        <div class="col-sm-8">
                          <textarea class="form-control required" placeholder="Enter address 2" rows="3" id="CompanyAddress2" name="CompanyAddress2" ${diabled_attribute}>${companyAddress2_}</textarea>
                          </div>
                        </div>
                    </div>
                     <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">City<font color="red">*</font></label>
                        <div class="col-sm-8">
                         <input type="text" class="form-control required" name="CompanyCity" id="CompanyCity" value="${companyCity_}" ${diabled_attribute}>
                          </div>
                        </div>
                        <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">State<font color="red">*</font></label>
                        <div class="col-sm-8">
                         <input type="text" class="form-control required" name="CompanyState" id="CompanyState" value="${companyState_}" ${diabled_attribute}>
                          </div>
                        </div>
                    </div>
                    <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Country<font color="red">*</font></label>
                        <div class="col-sm-8">
                         <input type="text" class="form-control required" name="CompanyCountry" id="CompanyCountry" value="${companyCountry_}" ${diabled_attribute}>
                          </div>
                        </div>
                        <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Zipcode</label>
                        <div class="col-sm-8">
                         <input type="text" class="form-control required" name="CompanyZipcode" id="CompanyZipcode" value="${companyZipcode_}" ${diabled_attribute}>
                          </div>
                        </div>
                    </div>
                    <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Point Of Contact Name<font color="red">*</font></label>
                        <div class="col-sm-8">
                         <input type="text" class="form-control required" name="CompanyContactName" id="CompanyContactName" value="${companyContactName_}" ${diabled_attribute}>
                          </div>
                        </div>
                        <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Point Of Contact Phone No.<font color="red">*</font></label>
                        <div class="col-sm-8">
                         <input type="text" class="form-control required" name="CompanyContactNo" id="CompanyContactNo" value="${companyContactNo_}" ${diabled_attribute}>
                          </div>
                        </div>
                    </div>

                    <div class="form-group row">
                    <div class="col-sm-6">
                  <label class="col-sm-4 control-label" for="basicMultiSelect">Therapeutic Area<font color="red">*</font></label>
                  <div class="col-sm-8">
                  <select name="CompanyTherapeuticAreas" id="CompanyTherapeuticAreas" size="5" multiple="multiple" tabindex="1" style="width:18.6em;" ${diabled_attribute}>
                  <c:forEach var="therapeuticAreas" items="${therapeuticAreaList}">
                   <c:set var="flag" value="false"/>
                    <c:forEach var="therapeuticAreasToBeSelected" items="${companyTherapeuticAreas_}">
                  	   <c:if test="${therapeuticAreasToBeSelected.therapeuticId == therapeuticAreas.therapeuticId}">
			        	<option value="${therapeuticAreas.therapeuticId}" ${selected_list_theo}>${therapeuticAreas.therapeuticName}</option>
			        	<c:set var="flag" value="true"/>
			           </c:if>
			        </c:forEach>

			         <c:if test='${ flag == "false"}'>
			        	<option value="${therapeuticAreas.therapeuticId}">${therapeuticAreas.therapeuticName}</option>
			           </c:if>
			      </c:forEach>
			      </select>
                  </div>
                  </div>
                  <div class="col-sm-6">
                  <label class="col-sm-4 control-label" for="basicSelect"></label>
                  <div class="col-sm-8">
                    <input type="hidden" name="CompanyId" value="${companyId_}"/>
                    <button type="submit" class="btn btn-green" name="buttonName" value="Update" onclick="return fnSubmit(this);"form="updatePharmaCompany" ${diabled_attribute}>Update</button>
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
                </div>
            </div>
          </div>
        </div>
          </div>
          </div>
 <script type="text/javascript">
function viewModel(id){
	   $.ajax({
		   url: 'getCompany.do',
		   type: 'GET',
		   data: 'companyId='+id,
		   dataType: "json",
		   success: function(data) {
			   try{
			   $("#companyName").html(data.companyName);
			   $("#companyDesc").html(data.companyDesc);
			   $("#companyContactName").html(data.contactName);
			   $("#companyContactNo").html(data.contactNo);
			   $("#companyCity").html(data.location.city);
			   $("#companyState").html(data.location.state);
			   $("#companyCountry").html(data.location.country);
			   $("#companyZipcode").html(data.location.zipcode);
			   $("#companyUrl").attr("href",data.companyUrl)
			   $("#companyProfilePicture").attr("src",data.displayPicture.dPicture);
			   $("#companyProfilePicture").attr("alt",data.companyName+" logo");
			   var therapeuticAreaString="";
		       $.each(data.therapeuticAreas, function(i, therapeuticArea) {
		    	   therapeuticAreaString+=therapeuticArea.therapeuticName+"\n";
		        });
		       $("#companyTherapeuticAreas").html(therapeuticAreaString);
			   }catch(err){

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
	 x=document.forms.namedItem("createPharmaCompany");
		}else if (elem=="Update")
			{
			 x=document.forms.namedItem("updatePharmaCompany");	
			}
	
	var txt="";
	//alert(document.forms.namedItem("createPharmaCompany").companyName.value);
	for(var i=0;i<x.length;i++)
		{
		if(x.elements[i].value=="" && (x.elements[i].id!="CompanyUrl") && ( x.elements[i].id!="CompanyLogo") && ( x.elements[i].id!=""))
			{
			
			alert("Please fill all the Mandatory fields");
			return false;
			}
		}
	
	
	
	}
</script>