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

        // Mock redirect (important)
        doNothing().when(response).sendRedirect(anyString());

        servlet.doPost(request, response);

        assertEquals(1, servlet.tasks.size());
        assertTrue(servlet.tasks.contains("Learn DevOps"));

        // Verify redirect happened
        verify(response).sendRedirect("todo");
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

        assertAll(
            () -> assertTrue(output.contains("Todo List")),
            () -> assertTrue(output.contains("Task1")),
            () -> assertTrue(output.contains("<form"))
        );
    }

    @Test
    void testEmptyTaskNotAdded() throws Exception {
        TodoServlet servlet = new TodoServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("task")).thenReturn("");

        // Mock redirect
        doNothing().when(response).sendRedirect(anyString());

        servlet.doPost(request, response);

        assertTrue(servlet.tasks.isEmpty());

        verify(response).sendRedirect("todo");
    }
}