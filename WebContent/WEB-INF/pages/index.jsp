   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
  
<h1>Employees List</h1>
<form action="">
	<input type="text" name="searchText" placeholder="Search..">
</form>
<table border="2" width="70%" cellpadding="2">  
<tr><th>Id</th><th>Name</th><th>Salary</th><th>Designation</th><th>Edit</th><th>Delete</th></tr>  
   <c:forEach var="e" items="${list}">   
   <tr>  
   <td>${e.id}</td>  
   <td>${e.name}</td>  
   <td>${e.salary}</td>  
   <td>${e.address}</td>  
   <td><a href="editForm?id=${e.id}">Edit</a></td>  
   <td><a href="delete?id=${e.id}">Delete</a></td>  
   </tr>  
   </c:forEach>  
   </table>  
   <br/>  
   <a href="insertForm">Add New Employee</a>  