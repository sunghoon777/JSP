package com.file.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/download")
public class downloadController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<FileDTO> files = new FileDAO().getList();
        req.setAttribute("files",files);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/view/file/download.jsp");
        requestDispatcher.forward(req,resp);
    }

}
