package com.file.web;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/downloadAction")
public class downloadActionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("file");
        String directory = "C:/Users/user/Desktop/upload";
        File file = new File(directory+"/"+fileName);
        String mimeType = getServletContext().getMimeType(file.toString());
        System.out.println(file.toString());
        System.out.println(mimeType);
        if(mimeType == null){
            //이진 데이터의 파일을 전달할때 octet-stream 사용
            resp.setContentType("application/octet-stream");
        }
        String downloadName = null;
        //MSIE(인터넷 익스플로어) 일 때에는 EUC-KR 로 보냄
        if(req.getHeader("user-agent").indexOf("MSIE") == -1){
            downloadName = new String(fileName.getBytes("UTF-8"),"8859_1");
        }else{
            downloadName = new String(fileName.getBytes("EUC-KR"),"8859_1");
        }
        resp.setHeader("Content-Disposition","attachment;filename=\""+downloadName+"\";");
        FileInputStream fileInputStream = new FileInputStream(file);
        ServletOutputStream servletOutputStream = resp.getOutputStream();
        byte b[] = new byte[1024];
        int data = 0;
        while ((data = fileInputStream.read(b,0,1024)) != -1){
            servletOutputStream.write(b,0,data);
        }
        new FileDAO().hit(fileName);
        servletOutputStream.flush();
        servletOutputStream.close();
        fileInputStream.close();
    }

}
