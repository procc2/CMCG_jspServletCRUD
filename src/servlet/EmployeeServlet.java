package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.EmployeeDAO;
import dao.UserDAO;
import model.Employee;
import model.User;

public class EmployeeServlet extends HttpServlet {
	private EmployeeDAO dao;

	@Override
	public void init() throws ServletException {
		super.init();
		String jdbcURL = "jdbc:mysql://localhost:3306/demo";
		String jdbcUsername = "usernew";
		String jdbcPassword = "1";

		dao = new EmployeeDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("currentUser");
		String action = req.getServletPath();
		if(u != null) {
		switch (action) {
		case "/insertForm":
			toInsertForm(req, resp);
			break;
		case "/editForm":
			toEditForm(req, resp);
			break;
		case "/insert":
			insertEmployee(req, resp);
			break;
		case "/update":
			updateEmployee(req, resp);
			break;
		case "/delete":
			deleteEmployee(req, resp);
			break;
		case "/":
			getEmployees(req, resp);
			break;
			
		default:
			break;
		}
		}else {
			toLogin(req , resp);
		}
	}
	
	private void getEmployees(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Employee> list = dao.getEmployees(request.getParameter("searchText"));
			request.setAttribute("list", list);
			request.getRequestDispatcher("WEB-INF/pages/index.jsp").forward(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void toInsertForm(HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/pages/insertForm.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void toEditForm(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			Employee e = dao.getEmployeeById(id);
			request.setAttribute("e", e);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/pages/editForm.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void insertEmployee(HttpServletRequest request, HttpServletResponse response) {
		try {
			String name = request.getParameter("name");
			Double salary = Double.parseDouble(request.getParameter("salary"));
			String address = request.getParameter("address");
			Employee e = new Employee(name, salary, address);
			dao.save(e);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/");
			dispatcher.forward(request, response);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ServletException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	private void toLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/pages/login.jsp");
			dispatcher.forward(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
//	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		String userName = request.getParameter("userName");
//		String password = request.getParameter("password");
//		User u = userDao.login(userName, password);
//		if(u!=null) {
//			HttpSession session = request.getSession(true);
//			session.setAttribute("currentUser", u);
//			doGet(request, response);
//		}
//	}
	private void updateEmployee(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			Double salary = Double.parseDouble(request.getParameter("salary"));
			String address = request.getParameter("address");
			Employee e = new Employee(id,name, salary, address);
			dao.update(e);
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/");
//			dispatcher.forward(request, response);
			response.sendRedirect("/jspServletCRUD/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		
		try {
			dao.delete(id);
			response.sendRedirect("/jspServletCRUD/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
