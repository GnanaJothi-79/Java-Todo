package com.todo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TodoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    List<String> tasks = new ArrayList<>();

    // Display tasks
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h2>Todo List</h2>");

        out.println("<form method='post'>");
        out.println("Enter Task: <input type='text' name='task'/>");
        out.println("<input type='submit' value='Add'/>");
        out.println("</form>");

        out.println("<h3>Tasks:</h3>");
        for (String task : tasks) {
            out.println("<p>" + task + "</p>");
        }
    }

    // Add task
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String task = request.getParameter("task");

        if (task != null && !task.isEmpty()) {
            tasks.add(task);
        }

        response.sendRedirect("todo");
    }
}