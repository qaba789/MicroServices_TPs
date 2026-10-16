package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/hotels")
public class HotelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String villeParam = request.getParameter("ville");

        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/HotelDB");

            try (Connection conn = ds.getConnection();
                 Statement stmt = conn.createStatement()) {

                stmt.execute("CREATE TABLE IF NOT EXISTS HOTEL (ID INT PRIMARY KEY, NOM VARCHAR(255), VILLE VARCHAR(255), PRIX DECIMAL)");

                ResultSet rsCheck = stmt.executeQuery("SELECT COUNT(*) FROM HOTEL");
                if (rsCheck.next() && rsCheck.getInt(1) == 0) {
                    stmt.execute("INSERT INTO HOTEL VALUES (1, 'Hotel Atlas', 'Khouribga', 500.0)");
                    stmt.execute("INSERT INTO HOTEL VALUES (2, 'Hotel Mirage', 'Casablanca', 850.0)");
                    stmt.execute("INSERT INTO HOTEL VALUES (3, 'Hotel Oasis', 'Khouribga', 420.0)");
                }
            }
            out.println("<html><body>");
            out.println("<h2>Available Hotels Directory</h2>");

            boolean hasFilter = (villeParam != null && !villeParam.trim().isEmpty());
            String sql = hasFilter ? "SELECT * FROM HOTEL WHERE VILLE = ?" : "SELECT * FROM HOTEL";

            try (Connection conn = ds.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                if (hasFilter) {
                    pstmt.setString(1, villeParam);
                    out.println("<p>Filtered by City: <strong>" + villeParam + "</strong></p>");
                }

                try (ResultSet rs = pstmt.executeQuery()) {
                    out.println("<table border='1' cellpadding='8' cellspacing='0'>");
                    out.println("<tr style='background-color: #f2f2f2;'><th>ID</th><th>Hotel Name</th><th>City</th><th>Price (MAD)</th></tr>");

                    boolean found = false;
                    while (rs.next()) {
                        found = true;
                        out.println("<tr>");
                        out.println("<td>" + rs.getInt("ID") + "</td>");
                        out.println("<td>" + rs.getString("NOM") + "</td>");
                        out.println("<td>" + rs.getString("VILLE") + "</td>");
                        out.println("<td>" + rs.getBigDecimal("PRIX") + "</td>");
                        out.println("</tr>");
                    }

                    if (!found) {
                        out.println("<tr><td colspan='4'>No hotels found matching your criteria.</td></tr>");
                    }
                    out.println("</table>");
                }
            }
            out.println("</body></html>");

        } catch (NamingException | SQLException e) {
            throw new ServletException("Database or JNDI error", e);
        }
    }

}