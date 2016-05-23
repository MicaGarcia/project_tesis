<html>
<head>
<meta/>
<title>Login</title>
</head>

<body>

<g:form action="userLogin" method="post">

<g:if test="${flash.message}">
        <div>${flash.message}</div>
      </g:if>

<center>
	<table cellspacing="4" cellpadding="7" border="1"><tbody><tr><td width="45%" align="right">
	
<label for="username">username:</label></td><td> <input type="text" name="username" id="username" size="25" maxlength="40"></td></tr><tr><td align="right">
<label for="password">password:</label></td><td>  <input type="password" name="password" id="password" size="25" maxlength="32"></td></tr>

<tr align="center"><td colspan="2"><input class="save" type="submit" value="Ingresar"></td></tr></tbody>
	</table>
</center>

</g:form>

</body>
</html>