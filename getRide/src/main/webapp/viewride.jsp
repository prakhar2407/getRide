<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<%@ page import = "java.io.*,java.util.*,java.sql.Connection,java.sql.DriverManager,java.sql.ResultSet,java.sql.Statement,java.text.SimpleDateFormat" %>
<%
   		try{
   			/* import java.text.SimpleDateFormat;import java.util.Date; */
   			String from =(String) request.getAttribute("from");
   			String to =(String) request.getAttribute("to");
   			String dateTime =(String) request.getAttribute("dateTime");
   			String passengers =(String) request.getAttribute("passengers");
   			System.out.println(from+" "+to+" "+dateTime+" "+passengers);
   			
   			session.setAttribute("passengers", Integer.parseInt(passengers));
   			session.setAttribute("from",from );
   			session.setAttribute("to", to);
   			//Date Entered by User 
   			SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
   			Date enteredDate = sdfo.parse(dateTime);
   			
   
   			
   			
   			Class.forName("com.mysql.cj.jdbc.Driver");
            
    		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/getRide","root","Vishal@1972");
         
    		Statement stmt = con.createStatement();
    		
    		String query = "select * from trip where fromWhere ='"+from+"' AND toWhere ='"+to+"'";
			ResultSet rs = stmt.executeQuery(query);
			
			System.out.println("Entered Date : "+sdfo.format(enteredDate));
			
   			
			
			ArrayList<String> carNoList= new ArrayList<>();
			ArrayList<String> dateTimeList= new ArrayList<>();
			ArrayList<Integer> seatsList= new ArrayList<>();
			while(rs.next()){
				Integer seatsAvailable = Integer.parseInt(rs.getString(5));
				Date dateAvailable = sdfo.parse(rs.getString(4));
				System.out.println("Available Date : "+sdfo.format(dateAvailable));
				if((seatsAvailable >= Integer.parseInt(passengers)) && (enteredDate.compareTo(dateAvailable) < 0)){
					carNoList.add(rs.getString(1));
					dateTimeList.add(rs.getString(4));
					seatsList.add(seatsAvailable);
				}
				
			}
			if(carNoList.size() == 0){
				response.sendRedirect("/getRide/norideavailable.html");
			}
%>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <title>View Ride</title>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Baloo+Bhai+2:wght@500&display=swap" rel="stylesheet">

</head>
<body>
    <nav id="navbar">
        <div class="nav-list">
            <div id="logo">
                <img src="/getRide/img/Logo.png" alt="">
            </div>
            <h1> Reise Car</h1>
            <li> <a href="/getRide/index2.html">Home </a></li>
            <li> <a href="#service">Service </a></li>
            <li> <a href="#client-section">clients </a></li>
            <li> <a href="#about">About us </a></li>
            <li> <a href="#contact">Contact us </a></li>

        </div>
        <div class="rightnav">


            <li class="item"><a href="/getRide/Logout">Log Out</a> </li>
            <li class="item"><a href="">Update Profile</a> </li>
        </div>
    </nav>


    <div class="form-Container">

        <h1> VIEW AVAILABLE RIDE </h1>
  		<table border = '1' width='100%'>
		  <tr>
		    <th>Car No.</th>
		    <th>From</th>
		    <th>To</th>
		    <th>Date and Time</th>
		    <th>Seats Available</th>
		  </tr>
		  <tr>
		  <% 
		  for(int i = 0;i < carNoList.size();i++){
			  %>
            <tr>
                <td><%=carNoList.get(i) %></td>
                <td><%=from %></td>
                <td><%=to %></td>
                <td><%=dateTimeList.get(i) %></td>
                <td><%=seatsList.get(i) %></td>
            </tr>
            <%
            }
		  System.out.println(carNoList+" "+dateTimeList+" "+seatsList);
            %>
		  </tr>
	</table>

    </div>

 <div class="form-Container">

    <h1> Do You Want to Book!!!</h1>
    <form method="post" action="/getRide/book.jsp" >
    
    
    
      <div class="nav-container-2_2">
      <div class="Start" style="font-family: 'Montserrat', sans-serif">
        <select name="carNo" id="carNo">
          <option value="">Select Car No. </option>
		<%for(int i = 0;i < carNoList.size();i++) { %>
          <option value="<%=carNoList.get(i) %>"><%=carNoList.get(i) %></option>
		<%} %>
        </select>
      </div>
      
      
      
        <div class="submit-btn">
          <input type="submit" value=" Submit Now ">  
        </form>    
        
<%
	}catch(Exception e){
		e.printStackTrace();
	} %>
        </div>

    </div>


    </form>

  </div>


    <!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>

    <!-- Option 2: Separate Popper and Bootstrap JS -->
    <!--
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js" integrity="sha384-q2kxQ16AaE6UbzuKqyBE9/u/KzioAlnx2maXQHiDX9d4/zp8Ok3f+M7DPm+Ib6IU" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js" integrity="sha384-pQQkAEnwaBkjpqZ8RU1fF1AKtTcHJwFl3pblpTlHXybJjHpMYo79HY3hIi4NKxyj" crossorigin="anonymous"></script>
    -->
</body>





</html>