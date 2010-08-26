<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page</title>
</head>
<body>

<h1>Access Denied</h1>
<p>
	Access to the specified resource has been denied for the following reason: <strong>${errorDetails}</strong>.
</p>
<em>Error Details (for Support Purposes only):</em><br />
<blockquote>
	<pre>${errorTrace}</pre>
</blockquote>

</body>
</html>