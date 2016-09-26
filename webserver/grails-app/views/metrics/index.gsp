<html>
<head>
<style type="text/css">
.container-fluid {
	position: relative;
	padding-left: 20px;
	padding-right: 20px;
	zoom: 1;
}

.container-fluid table {
	width: 30%;
	margin-bottom: 18px;
	padding: 0;
	border-collapse: separate;
	*border-collapse: collapse;
	font-size: 13px;
	border: 1px solid #ddd;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
}

table th,table td {
	padding: 10px 10px 9px;
	line-height: 18px;
	text-align: left;
}

th {
	padding-top: 9px;
	font-weight: bold;
	vertical-align: middle;
	border-bottom: 1px solid #ddd;
}

td {
	vertical-align: top;
}

table th+th,table td+td {
	border-left: 1px solid #ddd;
}

table tr+tr td {
	border-top: 1px solid #ddd;
}

}
#menu {
	background-color: gray;
	border: black 1px solid;
	height: 20px;
	width: 100px;
	float: left;
}

#menu li {
	float: left
}

form,button {
	text-decoration: none;
	font: menu;
	color: ButtonText;
	display: inline-block;
	padding: 2px 8px;
}
</style>

</head>
<body>

	<div id="header"></div>

	<div id=optionsMenu>
		<g:form controller="home" action="configuration">
			<button type="submit">Configuration</button>
		</g:form>
		<g:form controller="product" action="list">
			<button type="submit">My Products</button>
		</g:form>
		<g:form controller="order" action="list">
			<button type="submit">My Orders</button>
		</g:form>
		<g:form controller="question" action="list">
			<button type="submit">My Questions</button>
		</g:form>


	</div>
	<br>
	<br>

	<div id="info">
		<g:if
			test="${userInfo && totalItems && active && paused && closed && quantityVisits}">
			<div class='container-fluid'>

				<table>
					Items
					<tr>
						<td>Total Items: <output id="totalItems" name="totalItems">
								${totalItems.paging.total}
							</output><br /></td>
					</tr>
					<tr>
						<td>Active Items: <output id="totalItems" name="totalItems">
								${active.paging.total}
							</output><br /></td>
					</tr>
					<tr>
						<td>Paused Items: <output id="sellerRep" name="sellerRep">
								${paused.paging.total}
							</output><br /></td>
					</tr>
					<tr>
						<td>Closed Items: <output id="site" name="site">
								${closed.paging.total}
							</output><br /></td>
					</tr>
				</table>
				<table>
					Orders
					<tr>
						<td>Registration Date: <output id="registrationDate"
								name="registrationDate">
								${userInfo.registration_date}
							</output><br /></td>
					</tr>
					<tr>
						<td>Seller Reputation: <output id="sellerRep"
								name="sellerRep">
								${userInfo.seller_reputation.transactions.ratings}
							</output><br /></td>
					</tr>
					<tr>
						<td>Completed: <output id="site" name="site">
								${userInfo.seller_reputation.transactions.completed}
							</output><br /></td>
					</tr>
				</table>

				<table>
					Visits
					<tr>
						<td>Total Visits by User: <output id="totalVisits"
								name="totalVisits">
								${quantityVisits}
							</output><br /></td>
					</tr>
										<tr>
						<td>Best Items: <output id="listHotItems"
								name="listHotItems">
								${}
							</output><br /></td>
					</tr>
	
				</table>


			</div>

		</g:if>
	</div>




</body>
</html>