<html>
<head>
<title>New Product</title>

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

textField {
       width: 100%;
       clear: both;
}
</style>
</head>
<body>

	<div id=optionsMenu>
		<g:form controller="home" action="configuration">
			<button type="submit">Home</button>
		</g:form>

		<g:form action="list">
			<button type="submit">All Products</button>
		</g:form>

	</div>
	New Product
	<br />
	<br />
	
	
	<g:form action="save" enctype="multipart/form-data">
		<table>
		<tr><td><label>SKU:</label></td>
		<td><g:textField name="sku" id="sku" required="true"/></td></tr>
		
		<tr><td><label>Title:</label></td>
		<td><g:textField name="title" id="title" required="true" /></td></tr>
		
		<tr><td><label>Price:</label></td>
		<td><g:textField name="price" id="price" required="true"/></td></tr>
		
		<tr><td><label>Currency:</label></td>
		<td><select name="currency" id="currency"><option value="ARS">ARS</option></select></td></tr>
	
		<tr><td><label>Stock:</label></td>
		<td><g:textField name="stock" id="stock" required="true"/></td></tr>
		
		<tr><td><label>Description:</label></td>
		<td><g:textField name="description" id="description" required="true"/></td></tr>
		
		<tr><td><label>Warranty:</label></td>
		<td><g:textField name="warranty" id="warranty" required="true"/></td></tr>	
		</table>
		<br />
		<br />
		<input class="save" type="submit" value="Save Product">
		<br />
		<%--		Imagen:--%>
		<%--		<br/>--%>
		<%--		<input type="file" name="imagenFile" id="imagenFile"/>--%>


	</g:form>
</body>
</html>