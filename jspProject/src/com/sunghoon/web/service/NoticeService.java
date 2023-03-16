package com.sunghoon.web.service;

import com.sunghoon.web.DO.Notice;

import javax.servlet.RequestDispatcher;
import javax.swing.plaf.SliderUI;
import java.sql.*;
import java.util.List;
import java.util.Vector;

public class NoticeService {


    public List<Notice> getNoticeList(){
        return getNoticeList("title","",1);
    }

    public List<Notice> getNoticeList(int page){
        return getNoticeList("title","",page);
    }

    public List<Notice> getNoticeList(String field,String query, int page){
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String sql = "SELECT * FROM (SELECT (ROW_NUMBER() OVER(ORDER BY regdate DESC)) AS ROW, * FROM notice WHERE "+field+" LIKE ? ) N WHERE ROW BETWEEN ? AND ?";
        List<Notice> list = new Vector<>();
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url,"postgres","qkqxld12!@");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+query+"%");
            preparedStatement.setInt(2,5*(page-1)+1);
            preparedStatement.setInt(3,5*(page-1)+5);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String writerId = resultSet.getString("writer_id");
                String content = resultSet.getString("content");
                Date regdate = resultSet.getDate("regdate");
                long hit = resultSet.getLong("hit");
                String files = resultSet.getString("files");
                Notice notice = new Notice(id,title,writerId,content,regdate,hit,files);
                list.add(notice);
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public int getNoticeCount(){
        return getNoticeCount("title","");
    }

    public int getNoticeCount(String field,String query){
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String sql = "SELECT COUNT(*) FROM (SELECT * FROM notice WHERE "+field+" LIKE ? ) N";
        int count = 0;
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url,"postgres","qkqxld12!@");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+query+"%");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public Notice getNotice(long id){
        Notice notice = null;
        String sql = "SELECT * FROM NOTICE WHERE id = ?";
        String url = "jdbc:postgresql://localhost:5432/postgres";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url,"postgres","qkqxld12!@");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String title = resultSet.getString("title");
                String writerId = resultSet.getString("writer_id");
                String content = resultSet.getString("content");
                Date regdate = resultSet.getDate("regdate");
                long hit = resultSet.getLong("hit");
                String files = resultSet.getString("files");
                notice = new Notice(id,title,writerId,content,regdate,hit,files);
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return notice;
    }

    public Notice getNextNotice(int id){
        Notice notice = null;
        String sql = "SELECT id FROM notice WHERE regdate > (SELECT regdate FROM notice WHERE id = ?) ORDER BY regdate LIMIT 1;";
        String url = "jdbc:postgresql://localhost:5432/postgres";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url,"postgres","qkqxld12!@");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String title = resultSet.getString("title");
                String writerId = resultSet.getString("writer_id");
                String content = resultSet.getString("content");
                Date regdate = resultSet.getDate("regdate");
                long hit = resultSet.getLong("hit");
                String files = resultSet.getString("files");
                notice = new Notice(id,title,writerId,content,regdate,hit,files);
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return notice;
    }

    public Notice getPreNotice(int id){
        Notice notice = null;
        String sql = "SELECT id FROM notice WHERE regdate < (SELECT regdate FROM notice WHERE id = ?) ORDER BY regdate DESC LIMIT 1;";
        String url = "jdbc:postgresql://localhost:5432/postgres";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url,"postgres","qkqxld12!@");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String title = resultSet.getString("title");
                String writerId = resultSet.getString("writer_id");
                String content = resultSet.getString("content");
                Date regdate = resultSet.getDate("regdate");
                long hit = resultSet.getLong("hit");
                String files = resultSet.getString("files");
                notice = new Notice(id,title,writerId,content,regdate,hit,files);
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return notice;
    }

}
