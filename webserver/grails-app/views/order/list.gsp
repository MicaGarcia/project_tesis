<!DOCTYPE html>
<html>
<head>

<title>Orders</title>
</head>
<style>
table {
	border-collapse: collapse;
	width: 80%;
}
table,thead,td {
	border: 1px solid black;
}
thead {
	height: 20px;
}
td {
	height: 10px;
}
form, button {
        text-decoration: none; 
        font: menu; 
        color: ButtonText;
        display: inline-block; 
        padding: 2px 8px;
    }
</style>

<body>
<div id= optionsMenu>
<form action="/home/configuration">
    <button type="submit">Configuration</button>
</form>
<form action="/product/mapping">
    <button type="submit">My Products</button>
</form>
<form action="/questions/list">
    <button type="submit">My Questions</button>
</form>

</div>
	<br>
	<br>
	<div>${error}</div>
	<div>
	<table id="orderTable">
		<thead>
				<tr>
					
					<g:sortableColumn property="orderId" title="Order" />
					<g:sortableColumn property="item.itemId" title="Item ID" />
					<g:sortableColumn property="item.product.titulo" title="Item Title" />
					<g:sortableColumn property="quantity" title="Quantity" />
					<g:sortableColumn property="totalAmount" title="Total Amount" />
					<g:sortableColumn property="buyer" title="Buyer" />
					<g:sortableColumn property="payment" title="Payment" />
					<g:sortableColumn property="shipping" title="Shipping" />
					<g:sortableColumn property="status" title="Status" />
					<td>Action</td>
								
					
				</tr>
		</thead>
		<tbody>
			<g:each in="${listOrders}" status="i" var="order">
			<tr>
			<g:form controller="feedback" action="giveFeedback">
			<g:hiddenField name="orderId" value="${order.id}" />
					<td>${fieldValue(bean: order, field: "orderId")}</td>
					<td>${fieldValue(bean: order, field: "itemId")}</td>
					<td>${fieldValue(bean: order, field: "itemTitle")}</td>
					<td>${fieldValue(bean: order, field: "quantity")}</td>
					<td>${fieldValue(bean: order, field: "totalAmount")}</td>
					<td>${fieldValue(bean: order, field: "buyer")}</td>
					<td>${fieldValue(bean: order, field: "payment")}</td>
					<td>${fieldValue(bean: order, field: "shipping")}</td>
					<td>${fieldValue(bean: order, field: "status")}</td>
					<td>
					<g:if test="${fieldValue(bean: order, field: "sellerFeed") != 'null'}">
						Feedback complete
						</g:if>
					<g:if test="${fieldValue(bean: order, field: "sellerFeed") == 'null'}">
						<select id="feedback" name="feedback">
  							<option value="positive">Positive</option>
  							<option value="neutral">Neutral</option>
  							<option value="negative">Negative</option>
						</select>
						
							<select id="reason" name="reason">
  							<option value="SELLER_OUT_OF_STOCK">SELLER_OUT_OF_STOCK</option>
  							<option value="SELLER_DIDNT_TRY_TO_CONTACT_BUYER">SELLER_DIDNT_TRY_TO_CONTACT_BUYER</option>
  							<option value="BUYER_NOT_ENOUGH_MONEY">BUYER_NOT_ENOUGH_MONEY</option>
  							<option value="BUYER_REGRETS">BUYER_REGRETS</option>
						</select>
						
						
						<g:textArea name="feedText" id="feedText" value="Message..." rows="3"/>
<%--						<g:textArea name="reason" id="reason" value="If you give a negative rating, write a reason..." rows="3"/>--%>
						<g:submitButton name="save" value="Feedback" />
						</g:if>												
					</td>
					
			
			</g:form>
			<tr>
			</g:each>
		
		</tbody>
	
	</table>
	
	
	</div>

</body>


<script type="text/javascript">
function categorychanged(enable) 
{
    if (enable) 
    {
        document.test.category_parent.style.display="block";
    }
    else 
    {
        document.test.category_parent.style.display="none";
    }
}
</script>
</html>

