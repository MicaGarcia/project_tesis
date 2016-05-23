<!DOCTYPE html>
<html>
<head>
<title>Product Details</title>
</head>
<body>

	Product
	<br>
	<br> Title:
	<g:fieldValue bean="${product}" field="title" />
	<br /> Description:
	<g:fieldValue bean="${product}" field="description" />
	<br /> Price:
	<g:fieldValue bean="${product}" field="price" />
	<br /> Currency:
	<g:fieldValue bean="${product}" field="currency" />
	<br />

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