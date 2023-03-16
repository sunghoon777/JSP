package com.file.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FileDAO {

    private Connection connection;

    public FileDAO(){
        try {
            String url = "jdbc:mysql://localhost:3306/FILE";
            String id = "root";
            String password = "qkqxld12!@";
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,id,password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int upload(String fileName, String fileRealName){
        String query = "INSERT INTO FILE VALUES(?,?,0)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,fileName);
            ps.setString(2,fileRealName);
            return ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public int hit(String fileReaName){
        String query = "UPDATE FILE SET downloadCount = downloadCount + 1 WHERE fileRealName = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,fileReaName);
            return ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList<FileDTO> getList(){
        String query = "SELECT * FROM FILE";
        ArrayList<FileDTO> list = new ArrayList<FileDTO>();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(new FileDTO(rs.getString("fileName"),rs.getString("fileRealName"),rs.getInt("downloadCount")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
