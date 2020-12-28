

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FindRide
 */
public class FindRide extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindRide() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		
		try {
			
			String from = request.getParameter("from");
			String to = request.getParameter("to");
			String date = (String)request.getParameter("date");
			String time = request.getParameter("time");
			String passengers = request.getParameter("passenger");
			SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			
			
//			 if ( passengers.equals("") || from.equals("") ||
//		        		to.equals("") || time.equals("") || date.equals("")) {
//				 //Some field is blank
//				 response.sendRedirect("/getRide/findride.html");
//			 }
			
			String dateTime = date+" "+time;
			Date d1 = sdfo.parse(dateTime);
			
			System.out.println("From "+from);
			System.out.println("To "+to);
			System.out.println("Date "+date);
			System.out.println("Time "+time);
			System.out.println("Passengers "+passengers);
			Date currentDate = new Date();  
	        System.out.println("Current Date and Time : "+sdfo.format(currentDate));
	        System.out.println("User Date and Time : "+sdfo.format(d1));
	        
	        HttpSession session = request.getSession();
	        
	        if (session.getAttribute("email") == null||d1.compareTo(currentDate) < 0 ||passengers.equals("") || from.equals("") ||
	        		to.equals("") || time.equals("") || date.equals("")) { 
	        	
	        	
	        	
				if(session.getAttribute("email") == null) {
					response.sendRedirect("/getRide/loginfirst.html");//With a Message Login First
				}else {
	            // When Date d1 < Date date
	            System.out.println("Entered Date is before Current Date or there is some Blank Entry"); 
	            response.sendRedirect("/getRide/findride.html");//Here One Exception Shown in the Page that Date
				}												//Entered is passed away
	        }else {
	        	RequestDispatcher rd = request.getRequestDispatcher("viewride.jsp");
	        	request.setAttribute("from", from);
	        	request.setAttribute("to", to);
	        	request.setAttribute("dateTime", dateTime);
	        	request.setAttribute("passengers", passengers);
	        	rd.forward(request, response);
	        }
	        
	        //One Page required Html View Ride Available
	        
	        
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
