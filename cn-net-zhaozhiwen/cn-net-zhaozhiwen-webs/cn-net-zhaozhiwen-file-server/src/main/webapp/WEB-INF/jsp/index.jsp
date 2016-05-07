<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文件上传例子</title>
</head>
<body>
 
<form method="post" enctype="multipart/form-data"  action="http://119.29.177.213:9088/cn-jufuns-file-server/file/upload">
	请选择文件:<input type="file"  name="uploadFile">
     <input type="submit" value="上传" onclick=""/>
</form>
 


</body>
</html>