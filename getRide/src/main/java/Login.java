

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		System.out.println("Email : "+email);
		System.out.println("Password : "+password);
		
		if(email.equals("") || password.equals("") ) {
			response.sendRedirect("/getRide/loginfailed.html");//Here i have to enter some missing field login page
		}else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/getRide","root","Vishal@1972");
				
				Statement stmt = con.createStatement();
				
				String query = "select * from info";
				ResultSet rs = stmt.executeQuery(query);
				
				HashMap<String,String> emailPassword = new HashMap<>();
				
				
				while(rs.next()) {
					emailPassword.put(rs.getString(3), rs.getString(4));
				}
				System.out.println(emailPassword);
				
				if(emailPassword.containsKey(email) && password.equals(emailPassword.get(email))) {
					
					//email And Password is correct 
					HttpSession session = request.getSession(true);
					session.setAttribute("email", email);
					System.out.println("Session Creation Time : "+new Date(session.getCreationTime()));
					response.sendRedirect("/getRide/index2.html");
				}else {
					response.sendRedirect("/getRide/loginfailed.html");//And giving error wrong user name and password
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
