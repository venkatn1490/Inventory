<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty  deviceMsg}">
   <div class="notificationmsg">${deviceMsg}</div> 
</c:if>
<div class="l-page-header">
	<h2 class="l-page-title">
		<span>Orders</span> PAGE
	</h2>
	<!--BREADCRUMB-->
	<ul class="breadcrumb t-breadcrumb-page">
		<li><a href="javascript:void(0)">Home</a></li>
		<li class="active">Orders</li>
	</ul>
</div>
<div class="l-box no-border">
	<div class="l-box l-spaced-bottom">

		<div class="l-spaced">
			<div id="tables" class="resp-tabs-skin-1">
				<ul class="resp-tabs-list">
					<li id="nlist">Orders List</li>
					<li id="nlist">Create New Order</li>
 					
			</ul>			
		
		
		<div class="resp-tabs-container">
					<!-- Default Table-->
					<div>
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>Orders</span> Table
									</h2>
								</div>
								<div class="l-box-body">
								
									<table id="dataTableId" cellspacing="0" width="100%"
										class="display">
										<thead>
											<tr>
												<th>Order Id</th>
												<th>Salesman Name</th>
												<th>Mobile</th>
												<th>Ordered Date</th>
												<th>Items</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach var="orderList" items="${orderList}">
												<tr>
													<td>${orderList.orderId}</td>
													<td>${orderList.salesmanName}</td>
													<td>${orderList.salesmanMobile}</td>	
													<td>${orderList.createdDate}</td>
													<td>
													 <input type="button" class ="order" id="${orderList.orderId}"
														class="btn " value= VIEW >
													
													</td>	
												
											</c:forEach>
										</tbody>
										</table>
										
									
                        </div>
                        
					<form action="../customer/OrderItems.do" id="orderItems" method="get" target="_blank" enctype="multipart/form-data" >
						<input type="hidden" id="orderId" name="orderId" value="" />
					</form>
					
				</div>
				</div>
				</div>
				<div class="l-row l-spaced-bottom">
                <div class="l-box">
                <div class="l-box-header">
                  <h2 class="l-box-title"><span>Create</span> New Order</h2></div>
                  <section class="l-box-body l-spaced">
                  <form id="createCustomerOrder" class="form-horizontal validate" role="form" action="../customer/createCustomerOrder.do" method="post" enctype="multipart/form-data">
                    <div class="form-group row">
                     <div class="col-sm-6">
					 <label class="col-sm-4 control-label" for="userName-v">Select Item<font color="red">*</font></label>
					<select name="itemId" required="required" >
					<c:forEach var="itemList" items="${itemList}">
                        	<option value="${itemList.id}">${itemList.itemName}</option>
						</c:forEach>
						</select>																
						</div>
					<div class="col-sm-6">
					 <label class="col-sm-4 control-label" for="userName-v">Select Salesman<font color="red">*</font></label>
					<select name="salesmanId" required="required" >
					<c:forEach var="salesmanList" items="${salesmanList}">
                        	<option value="${salesmanList.userId}">${salesmanList.firstName} ${salesmanList.lastName}</option>
						</c:forEach>
						</select>																
						</div>
                        </div>
                    <div class="form-group row">
                     
                     <div class="col-sm-6">
                        <label class="col-sm-4 control-label" for="userName-v">Enter Stock<font color="red">*</font></label>
                        <div class="col-sm-8"><input type="number" class="form-control" required="required" name="stock" id="stock" ></div>
                        </div>
                    </div>
                <div class="form-group row">
                 <div class="col-sm-6">
                  <label class="col-sm-4 control-label" for="basicSelect"></label>
                  <div class="col-sm-8">
                   <button type="submit" class="btn btn-green" name="buttonName" value="Create"  onclick= "return fnSubmit(this);" form="createCustomerOrder">Create</button>
                  </div>
                </div>
               </div>
              </form>
              </section>
                    </div>
                </div>
				</div>
                <div>               
                </div>
</div>
</div>
</div>
</div>
<script type="text/javascript">
$(".order").click(function(){
	 var id = $(this).attr("id");
	 
		if(id == '' || id === undefined) {
			alert("Please select a Patient");
		}else {
			$("#orderId").val(id);			
			$("#orderItems").submit();
		}

})

</script>

