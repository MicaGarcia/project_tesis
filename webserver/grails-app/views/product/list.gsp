<!DOCTYPE html>
<html>
<head>
<title>Products</title>

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

	<div id=optionsMenu>
		<g:form controller="home" action="configuration">
			<button type="submit">Configuration</button>
		</g:form>
		<g:form controller="question" action="list">
			<button type="submit">My Questions</button>
		</g:form>
		<g:form controller="order" action="list">
			<button type="submit">My Orders</button>
		</g:form>

	</div>
	<br>
	<g:form action="create">
		<button type="submit">New Product</button>
	</g:form>
	<br><br>
	<div>
		<table border="1" cellspacing="5" cellpadding="5">
			<thead>
				<tr>
					<g:sortableColumn property="sku" title="SKU" />
					<g:sortableColumn property="title" title="Title" />
					<g:sortableColumn property="price" title="Price" />
					<g:sortableColumn property="stock" title="Stock" />
					<th>Listing Type</th>
					<th>ML Item ID</th>
					<th>Meli Category</th>
					<th>Shipping</th>
					<th>Status</th>
					<th>Action</th>

				</tr>
			</thead>
			<tbody>
				<g:each in="${listProducts}" status="i" var="product">
					<tr>

						<td><g:link action="details" id="${product.id}">
								${product.sku}
							</g:link></td>
						<td>
							${fieldValue(bean: product, field: "title")}
						</td>
						<td>
							${fieldValue(bean: product, field: "price")}
						</td>
						<td>
							${fieldValue(bean: product, field: "stock")}
						</td>
						<g:form controller="publisher" action="publish">
							<td><g:select id="listing" name="listing"
									from="${listingType}" value="" /></td>
							<td><a href="${product.items.permalink.find{true}}"> ${product.items.itemId.find{true}}
							</a></td>
							<td>
								${product.items.categoryId.find{true}}
							</td>
							<td>
								${product.items.shipping.find{true}}
							</td>
							<td>
								${product.items.status.find{true}}
							</td>

							<g:hiddenField name="productId" value="${product.id}" />
							<g:hiddenField name="itemId"
								value="${product.items.id.find{true}}" />
							<g:if
								test="${product.items.status.find{true} == 'active' || product.items.status.find{true} == 'paused'}">
								<td><g:submitButton name="save" value="Modificar" /></td>
							</g:if>

							<g:if test="${!product.items.itemId.find{true}}">
								<td><g:submitButton name="save" value="Publicar" /></td>
							</g:if>

						</g:form>


					</tr>
				</g:each>
			</tbody>
		</table>

	</div>

</body>
</html>