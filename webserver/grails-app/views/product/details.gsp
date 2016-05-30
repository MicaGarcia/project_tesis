<!DOCTYPE html>
<html>
<head>
<title>Product Details</title>

<style>

button {
        text-decoration: none; 
        font: menu; 
        color: ButtonText;
        display: inline-block; 
        padding: 2px 8px;
    }

</style>
</head>
<body>

<g:form controller= "product" action="list">
    <button type="submit">My Products</button>
</g:form>
	<br><br>
	Product Details
	<br>
	<br> SKU:
	<g:fieldValue bean="${product}" field="sku" />
	<br /> 
	Title:
	<g:fieldValue bean="${product}" field="title" />
	<br />
	Description:
	<g:fieldValue bean="${product}" field="description" />
	<br /> Price:
	<g:fieldValue bean="${product}" field="price" />
	<br /> Currency:
	<g:fieldValue bean="${product}" field="currency" />
		<br> Actual Stock:
	<g:fieldValue bean="${product}" field="stock" />
		<br /> Warranty:
	<g:fieldValue bean="${product}" field="warranty" />

	<br>
	<br>
	<g:form>	
	
		<fieldset class="buttons">
			<g:hiddenField name="id" value="${product?.id}" />
			<g:actionSubmit action="edit" value="Edit" />
			<g:actionSubmit action="deleteProduct" value="Delete"
				onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Product will be deleted')}');" />
		</fieldset>

	</g:form>




</body>
</html>