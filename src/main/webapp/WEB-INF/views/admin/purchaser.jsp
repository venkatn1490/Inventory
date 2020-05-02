<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty  deviceMsg}">
   <div class="notificationmsg">${deviceMsg}</div> 
</c:if>
<div class="l-page-header">
	<h2 class="l-page-title">
		<span>Purchasers</span> PAGE
	</h2>
	<!--BREADCRUMB-->
	<ul class="breadcrumb t-breadcrumb-page">
		<li><a href="javascript:void(0)">Home</a></li>
		<li class="active">Purchasers</li>
	</ul>
</div>
<div class="l-box no-border">
	<div class="l-box l-spaced-bottom">

		<div class="l-spaced">
			<div id="tables" class="resp-tabs-skin-1">
				<ul class="resp-tabs-list">
					<li id="nlist">Purchasers List</li>
					<li id="vscrollTable">Create Purchaser</li>
 					
			</ul>			
		
		
		<div class="resp-tabs-container">
					<!-- Default Table-->
					<div>
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>Purchasers</span> Table
									</h2>
								</div>
								<div class="l-box-body">
								
									<table id="dataTableId" cellspacing="0" width="100%"
										class="display">
										<thead>
											<tr>
												<th>Name</th>
												<th>Mobile Number</th>
												<th>Email Id</th>
												<th>Gender</th>
												<th>Status</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach var="userList" items="${userList}">
												<tr>
													
													<td>${userList.firstName}</td>
													<td>${userList.mobileNo}</td>
													<td>${userList.emailId}</td>
													<td>${userList.gender}</td>	
													<td>${userList.status}</td>												
											<td></td>
											</c:forEach>
										</tbody>
										</table>
										
									
                        </div>
				</div>
				</div>
				</div>
				

<div class="l-row l-spaced-bottom">
                <div class="l-box">
                <div class="l-box-header">
                  <h2 class="l-box-title"><span>Create</span> Supplier</h2></div>
                  <section class="l-box-body l-spaced">
                  <form id="createPurchaser" class="form-horizontal validate" role="form" action="../admin/createUser.do" method="post" enctype="multipart/form-data">
                  <div class="form-group row">
                    <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">First Name<font color="red">*</font></label>
                        <div class="col-sm-8"><input type="text" class="form-control" required="required" name="firstName" id="firstName" ></div>
                    </div>
                      <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Last Name<font color="red">*</font></label>
                        <div class="col-sm-8"><input type="text" class="form-control" required="required"  name="lastName" id="lastName" ></div>
                        </div> 
                   
                    </div>
                    <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Email Id<font color="red">*</font></label>
                        <div class="col-sm-8"><input type="text" class="form-control" required="required"  name="email" id="email" ></div>
                        </div>
                    
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Moblie Number<font color="red">*</font></label>
                        <div class="col-sm-8"><input type="text" class="form-control" required="required" name="mobile" id="mobile" ></div>
                        </div>
                    </div>
                    
                    <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Title<font color="red">*</font></label>
                        <div class="col-sm-8">
                         <select name="title" required="required" >
                        <option value=""></option>
  								<option value="Mr.">Mr.</option>
  								<option value="Ms.">Ms.</option>
  								<option value="Mrs.">Mrs.</option>
						</select></div>
                        </div>
                                       
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Gender<font color="red">*</font></label>
                        <div class="col-sm-8">
                        <select name="gender" required="required" >
                        <option value=""></option>
  								<option value="Male">Male</option>
  								<option value="Female">Female</option>
						</select></div>
                        </div>
                                               
                    </div>                       
                    
                    <div class="form-group row">
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Date Of Birth<font color="red">*</font></label>
                        <div class="col-sm-8">	
                        <input type="date" class="form-control required" name="dob" id="dob">
						<input type="number" class="form-control required" style="display: none" name="role" id="role" value="3"></div>
													</div>
                        </div>
                          
                <div class="form-group row">
                 <div class="col-sm-6">
                  <label class="col-sm-4 control-label" for="basicSelect"></label>
                  <div class="col-sm-8">
                   <button type="submit" class="btn btn-green" name="buttonName" value="Create"  onclick= "return fnSubmit(this);" form="createPurchaser">Create</button>
                  </div>
                </div>
               </div>
              </form>
              </section>
                    </div>
                </div>
                <div>               
                </div>
</div>
</div>
</div>
</div>
</div>
<script type="text/javascript">
function viewModel(id){
	createEmptyDataModel();
	   $("#managerChangeStatus").attr("value",id);

}
function createEmptyDataModel(){
	   $("#managerChangeStatus").attr("value",'');

}
</script>

