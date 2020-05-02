<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href='<c:url value="/resources/css/styles.css"/>'>
						<div class="col-md-12" align="center">
							<h1 class="animated fadeInDown l-box-title">Register Purchaser</h1>
						</div>
<div class="l-box no-border">
	<div class="l-box l-spaced-bottom">
<div class="l-row l-spaced-bottom">
                <div class="l-box">
                  <section class="l-box-body l-spaced">
                  <form id="createUser" class="form-horizontal validate " role="form" action="../auth/createUser.do" method="post" enctype="multipart/form-data">
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
                        <div class="col-sm-8"><input type="email" class="form-control" required="required"  name="email" id="email" ></div>
                        </div>
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Moblie Number<font color="red">*</font></label>
                        <div class="col-sm-8"><input type="number" class="form-control" required="required" name="mobile" id="mobile" ></div>
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
                        <div class="col-sm-8">	<input type="date" class="form-control required"
													name="dob" id="dob"></div>
                        </div>
                          
                    </div>

                <div class="form-group row">
                 <div class="col-sm-12" align="center">
                   <button type="submit" style="text-align: center; width:200px;" class="btn btn-dark btn-block btn-login" name="buttonName" value="Create"  onclick= "return fnSubmit(this);" form="createUser">Create</button>
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


