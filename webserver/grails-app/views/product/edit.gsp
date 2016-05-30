<!DOCTYPE html>
<html>
<head>
<title>Modify Product</title>
</head>
<body>

<g:form action="updateProduct" enctype="multipart/form-data">
		<table>
		<tr><td><label>SKU:</label></td>
		<td><g:textField name="sku" id="sku" required="true" value="${product?.sku}"/></td></tr>
		
		<tr><td><label>Title:</label></td>
		<td><g:textField name="title" id="title" required="true" value="${product?.title}" /></td></tr>
		
		<tr><td><label>Price:</label></td>
		<td><g:textField name="price" id="price" required="true" value="${product?.price}" /></td></tr>
		
		<tr><td><label>Currency:</label></td>
		<td><select name="currency" id="currency"><option value="ARS">ARS</option></select></td></tr>
	
		<tr><td><label>Stock:</label></td>
		<td><g:textField name="stock" id="stock" required="true" value="${product?.stock}" /></td></tr>
		
		<tr><td><label>Description:</label></td>
		<td><g:textField name="description" id="description" required="true" value="${product?.description}" /></td></tr>
		
		<tr><td><label>Warranty:</label></td>
		<td><g:textField name="warranty" id="warranty" required="true" value="${product?.warranty}" /></td></tr>	
		</table>

		<br />
		<br />
		<g:hiddenField name="id" value="${product?.id}" />
		<input class="save" type="submit" value="Update Product">
		<br />
	</g:form>




</body>
</html>