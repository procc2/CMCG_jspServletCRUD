<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    

        <h1>Edit Employee</h1>  
       <form action="update">    
        <table >    
        <tr>  
        <td></td>    
         <td></td>  
         </tr>   
         <tr>    
          <td><input type="hidden" name="id" value="<c:out value='${e.id}' />"/></td>  
         </tr> 
         <tr>    
          <td>Name : </td>   
          <td><input type="text" name="name" value="<c:out value='${e.name}' />"/></td>  
         </tr>    
         <tr>    
          <td>Salary :</td>    
          <td><input type="text" name="salary" value="<c:out value='${e.salary}' />" /></td>  
         </tr>   
         <tr>    
          <td>Address :</td>    
          <td><input type="text" name="address" value="<c:out value='${e.address}' />" /></td>  
         </tr>   
           
         <tr>    
          <td> </td>    
          <td><input type="submit" value="Update" /></td>    
         </tr>    
        </table>    
       </form>    
