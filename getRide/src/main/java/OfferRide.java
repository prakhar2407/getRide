

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class OfferRide
 */
public class OfferRide extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OfferRide() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		String carNo = request.getParameter("carNo");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String date = (String)request.getParameter("date");
		String time = request.getParameter("time");
		String passengers = request.getParameter("passenger");
		
		try {
			String dateTime = date+" "+time;
			Date d1 = sdfo.parse(dateTime);
			
			
			System.out.println("Car No. "+carNo);
			System.out.println("From "+from);
			System.out.println("To "+to);
			System.out.println("Date "+date);
			System.out.println("Time "+time);
			System.out.println("Passengers "+passengers);
			
			//Now Compare the Curretn Date with Entered Date
			//Checks whether the Date and time if past or future
			
			
			Date currentDate = new Date();  
	        System.out.println("Current Date and Time : "+sdfo.format(currentDate));
	        System.out.println("User Date and Time : "+sdfo.format(d1));
	        
	        HttpSession session = request.getSession();
	        // Compare the dates using compareTo() 
	        if (session.getAttribute("email") == null||d1.compareTo(currentDate) < 0 || carNo.equals("") || passengers.equals("") || from.equals("") ||
	        		to.equals("")) { 
	        	
	        	if(session.getAttribute("email") == null) {
					response.sendRedirect("/getRide/loginfirst.html");//With a Message Login First
				}else {
					if(carNo.equals("") || passengers.equals("") || from.equals("") ||
	        		to.equals("")) {
						System.out.println("Some Missing fields are there");
						response.sendRedirect("/getRide/offerridemissingdetails.html");
					}else {
						// When Date d1 < Date date
			            System.out.println("Entered Date is before Current Date or there is some Blank Entry"); 
			            response.sendRedirect("/getRide/offerridedatebeforecurrentdate.html");//Here One Exception Shown in the Page that Date
					}													//Entered is passed away
				}												
	        }else {
	        	
	        	try {
	        		Class.forName("com.mysql.cj.jdbc.Driver");
					
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/getRide","root","Vishal@1972");
					
					Statement stmt = con.createStatement();
					
					String insertQuery = "insert into trip values ('"+carNo+"', '"+from+"', '"+to+"', '"+dateTime+"', '"+passengers+"')";
					int record = stmt.executeUpdate(insertQuery);
					
					
					if(record == 1) {
						System.out.println("\nDone Succesfully\n");
					}
					
					
	        	}catch(Exception e) {
	        		e.printStackTrace();
	        	}
	        	
	        	//One html page which shows Trip Added succesfully
	        	response.sendRedirect("/getRide/offersuccessfully.html");
	        }
	        
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
