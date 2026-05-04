package com.todo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

public class TodoServletTest {

    @Test
    void testDoPostAddsTask() throws Exception {
        TodoServlet servlet = new TodoServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("task")).thenReturn("Learn DevOps");

        servlet.doPost(request, response);

        assertEquals(1, servlet.tasks.size());
        assertEquals("Learn DevOps", servlet.tasks.get(0));
    }

    @Test
    void testDoGetDisplaysTasks() throws Exception {
        TodoServlet servlet = new TodoServlet();
        servlet.tasks.add("Task1");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);

        pw.flush();

        String output = sw.toString();

        assertTrue(output.contains("Todo List"));
        assertTrue(output.contains("Task1"));
    }
}