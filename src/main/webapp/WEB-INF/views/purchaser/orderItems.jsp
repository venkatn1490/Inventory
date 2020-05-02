<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty  deviceMsg}">
   <div class="notificationmsg">${deviceMsg}</div> 
</c:if>
<div class="l-page-header">
	<h2 class="l-page-title">
		<span>Order Items</span> PAGE
	</h2>
	<!--BREADCRUMB-->
	<ul class="breadcrumb t-breadcrumb-page">
		<li><a href="javascript:void(0)">Home</a></li>
		<li class="active">Order Items</li>
	</ul>
</div>
<div class="l-box no-border">
	<div class="l-box l-spaced-bottom">

		<div class="l-spaced">
			<div id="tables" class="resp-tabs-skin-1">
				<ul class="resp-tabs-list">
					<li id="nlist">Order Items List</li>
 					
			</ul>			
		
		
		<div class="resp-tabs-container">
					<!-- Default Table-->
					<div>
						<div class="l-row l-spaced-bottom">
							<div class="l-box">
								<div class="l-box-header">
									<h2 class="l-box-title">
										<span>Order Items</span> Table
									</h2>
								</div>
								<div class="l-box-body">
								
									<table id="dataTableId" cellspacing="0" width="100%"
										class="display">
										<thead>
											<tr>
												<th>Order Id</th>
												<th>Item Name</th>
												<th>Stock</th>

											</tr>
										</thead>
										<tbody>
										<c:forEach var="itemList" items="${itemList}">
												<tr>
													<td>${itemList.orderId}</td>
													<td>${itemList.itemName}</td>
													<td>${itemList.stock}</td>	
													
											</c:forEach>
										</tbody>
										</table>
										
									
                        </div>
                    
					
				</div>
				</div>
				</div>
                <div>               
                </div>
</div>
</div>
</div>
</div>
</div>


