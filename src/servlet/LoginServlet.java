package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.EmployeeDAO;
import dao.UserDAO;
import model.User;

public class LoginServlet extends HttpServlet {
	private UserDAO userDao;
	@Override
	public void init() throws ServletException {
		String jdbcURL = "jdbc:mysql://localhost:3306/demo";
		String jdbcUsername = "usernew";
		String jdbcPassword = "1";

		userDao = new UserDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		login(req, resp);
	}
	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		User u = userDao.login(userName, password);
		if(u!=null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("currentUser", u);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/");
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.sendRedirect("/jspServletCRUD/");
		}
	}
}
