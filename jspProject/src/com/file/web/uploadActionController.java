package com.file.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


@WebServlet("/uploadAction")
public class uploadActionController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String directory = "C:/Users/user/Desktop/upload/";
        int maxSize = 1024*1024*100;
        String encoding = "UTF-8";
        MultipartRequest multipartRequest = new MultipartRequest(req,directory,maxSize,encoding,new DefaultFileRenamePolicy());
        Enumeration fileNames = multipartRequest.getFileNames();
        while (fileNames.hasMoreElements()){
            String parameter = (String)fileNames.nextElement();
            System.out.println(parameter);
            String fileName = multipartRequest.getOriginalFileName(parameter);
            String fileRealName = multipartRequest.getFilesystemName(parameter);
            System.out.println(fileName);
            if(fileName == null){
                continue;
            }
            if(!fileName.endsWith(".doc") && !fileName.endsWith(".hwp") && !fileName.endsWith(".pdf") && !fileName.endsWith(".xls") && !fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg")){
                File file = new File(directory+fileRealName);
                file.delete();
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().println("업로드 할 수 없는 확장자 입니다.");
            }
            else{
                new FileDAO().upload(fileName,fileRealName);
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().println("파일명 : "+fileName+"<br>"+"실제 파일명 : "+fileRealName+"<br>");
            }
        }
    }
}
