/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-10
  Time: 오후 4:53
*/

package com.test.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// Catch empty request ("/")
@WebServlet("")
public class RootController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // redirect to Login page
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}