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
import java.util.List;
import java.util.Vector;

@WebServlet("/notice/list")
public class NoticeListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //초기화
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/view/notice/list.jsp");
        String tempPage = request.getParameter("page");
        String tempField = request.getParameter("field");
        String tempQuery = request.getParameter("query");
        int page = 1;
        String field = "title";
        String query = "";

        //검증
        if(tempPage != null && !tempPage.isEmpty()){
            try {
                page = Integer.parseInt(tempPage);
                if(page == 0)
                    page = 1;
            }catch (NumberFormatException e){
                page = 1;
            }
        }
        if(tempField != null && !tempField.isEmpty()){
            field = tempField;
        }
        if(tempQuery != null && !tempQuery.isEmpty()){
            query = tempQuery;
        }

        //model 데이터 얻기
        NoticeService service = new NoticeService();
        List<Notice> noticeList = service.getNoticeList(field,query,page);
        int count = service.getNoticeCount(field,query);
        if(count == 0){
            page = 0;
        }
        //forward
        request.setAttribute("noticeList",noticeList);
        request.setAttribute("page",page);
        request.setAttribute("count",count);
        requestDispatcher.forward(request,response);
    }

}
