

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SignUp
 */
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static boolean isValidEmail(String email) 
	{ 
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +  //part before @
				"(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; 
 
		Pattern pat = Pattern.compile(emailRegex); 
		if (email == null) 
			return false; 
		return pat.matcher(email).matches(); 
	} 
    public static boolean isValidPhoneNo(String s) 
	{ 
	      
	    // The given argument to compile() method  
	    // is regular expression. With the help of  
	    // regular expression we can validate mobile 
	    // number.  
	    // 1) Begins with 0 or 91 
	    // 2) Then contains 7 or 8 or 9. 
	    // 3) Then contains 9 digits 
	    Pattern p = Pattern.compile("(91||0)?[6-9][0-9]{9}"); 
	  
	    // Pattern class contains matcher() method 
	    // to find matching between given number  
	    // and regular expression 
	    Matcher m = p.matcher(s); 
	    return (m.find() && m.group().equals(s)); 
	}
    
    public boolean isAlreadyEmail(String email) {
    	ArrayList<String> emailList = new ArrayList<>();
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
         
    		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/getRide","root","Vishal@1972");
         
    		Statement stmt = con.createStatement();
    		
    		String query = "select * from info";
			ResultSet rs = stmt.executeQuery(query);
			
			
			
			while(rs.next()) {
				emailList.add(rs.getString(3));
			}
			
			
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	System.out.println("The Email List : "+emailList);
    	if(emailList.contains(email)) {
			return true;
		}
    	return false;
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
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("sname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phoneNo = request.getParameter("phone");
		String gender = request.getParameter("myGender");
		
		
		System.out.println("First Name : "+firstName);
		System.out.println("Last Name : "+lastName);
		System.out.println("Email : "+email);
		System.out.println("Password: "+password);
		System.out.println("Phone No. : "+phoneNo);
		System.out.println("Gender : "+gender);
		
		System.out.println(firstName.equals(""));
		System.out.println(lastName.equals(""));
		System.out.println(isValidEmail(email));
		System.out.println(password.equals(""));
		System.out.println(isValidPhoneNo(phoneNo));
		System.out.println(gender == null);
		System.out.println(isAlreadyEmail(email));
		
		
//		if User Don't Enter any info 
		if(firstName.equals("") || lastName.equals("") ||
				!isValidEmail(email) || password.equals("") ||
				!isValidPhoneNo(phoneNo) || gender == null || isAlreadyEmail(email)) {
			System.out.println("Some Fields are Empty");
			response.sendRedirect("/getRide/signupfailed.html");//Here we have to Enter new Page So that it shows Error in Another Page
		}else {
			
			System.out.println("First Name : "+firstName);
			System.out.println("Last Name : "+lastName);
			System.out.println("Email : "+email);
			System.out.println("Password: "+password);
			System.out.println("Phone No. : "+phoneNo);
			System.out.println("Gender : "+gender);
			
			
			
			//Now connect to Database And inserting values into them
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/getRide","root","Vishal@1972");
				String insertQuery = "insert into info values ('"+firstName+"', '"+lastName+"', '"+email+"', '"+password+"', '"+phoneNo+"','"+gender+"')";
				Statement stmt = con.createStatement();
				int record = stmt.executeUpdate(insertQuery);
				if(record == 1) {
					HttpSession session = request.getSession();
					session.setAttribute("fname", firstName);
					session.setAttribute("sname", lastName);
					session.setAttribute("email", email);
					session.setAttribute("password", password);
					session.setAttribute("phone", phoneNo);
					session.setAttribute("myGender", gender);
					session.invalidate();
					System.out.println("Registration Doned Succesfully");
					response.sendRedirect("/getRide/index1.html");
				}
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
