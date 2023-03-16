package com.sunghoon.web.controller;

import com.sunghoon.web.DO.Notice;
import com.sunghoon.web.service.NoticeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


@WebServlet("/notice/detail")
public class NoticeDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //초기화
        long id = Long.parseLong(request.getParameter("id"));

        //model 데이터 얻기
        NoticeService noticeService = new NoticeService();
        Notice notice = noticeService.getNotice(id);

        //forward
        request.setAttribute("notice",notice);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/view/notice/detail.jsp");
        requestDispatcher.forward(request,response);

    }
}
