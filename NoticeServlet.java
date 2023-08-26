package com.learning.hello;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/NoticeServlet")
public class NoticeServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private Connection cnx;
	
	private JakartaServletWebApplication application;
	private TemplateEngine templateEngine;
	
		static {
		
		}
	
	@Override
	  public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			cnx =  DriverManager.getConnection("jdbc:mysql://localhost:3306/persistence","Ameena","Ameena");
			
		}
		catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}
	    
	    application = JakartaServletWebApplication.buildApplication(getServletContext());
	    final WebApplicationTemplateResolver templateResolver = 
	        new WebApplicationTemplateResolver(application);
	    templateResolver.setTemplateMode(TemplateMode.HTML);
	    templateResolver.setPrefix("/WEB-INF/templates/");
	    templateResolver.setSuffix(".html");
	    templateEngine = new TemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver);
	  }
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		String name = req.getParameter("name");
		String notice = req.getParameter("notice");
		String contact = req.getParameter("contact");
		
		Statement stmt = null;
	    PreparedStatement insert = null;
		PreparedStatement update = null;
				
	    
	    String insertNotice = "INSERT INTO NoticeTable(name,notice,contact) VALUES (?,?,?)";
	    String updateName = "UPDATE NoticeTable SET name=?,notice=?,contact=?;";
		
			try {
				insert = cnx.prepareStatement(insertNotice);
				update = cnx.prepareStatement(updateName);
				  insert.setString(1, name);
					insert.setString(2, notice);
					insert.setString(3, contact);
					insert.executeUpdate();
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	  
		var out = resp.getWriter();
		final IWebExchange webExchange = 
		        this.application.buildExchange(req, resp);
		final WebContext ctx = new WebContext(webExchange);
		    
		    ctx.setVariable("submit",name);  
		    templateEngine.process("NoticeForm", ctx, out);
	}
	
	@Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    final IWebExchange webExchange = this.application.buildExchange(req, resp);
	    final WebContext ctx = new WebContext(webExchange);
	    templateEngine.process("NoticeForm", ctx, resp.getWriter());
	  }

}
