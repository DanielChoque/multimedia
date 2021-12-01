package com.sap.multimedia.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

//import org.primefaces.json.JSONArray;
//import org.primefaces.json.JSONObject;

//import com.sakadream.jsf.bean.Employee;
//import com.sakadream.jsf.bean.Multimedia;

public class Funcion {
    private Connection conn;
    //private List<Multimedia> video;
	//private Multimedia e;


    private void connect() throws ClassNotFoundException, SQLException {
        //Class.forName("com.mysql.jdbc.Driver");
    	//Class.forName("com.mysql.jdbc.Driver");
    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        try {
            //String username = "root";
            //String password = "";
            //String url = "jdbc:mysql://127.0.0.1:3306/sof305_offline";
        	String username = "sa";
            String password = "as";
            String url = "jdbc:sqlserver://10.1.26.93:1433;databaseName=IQData";


            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            String username = "root";
            String password = "";
            String url = "jdbc:mysql://10.1.43.236:3306/sof305_offline";

            conn = DriverManager.getConnection(url, username, password);
        }
    }

    private void cleanConnection() throws SQLException {
        conn.close();
    }

   

    public String showPending() throws SQLException, ClassNotFoundException {
        //List<Employee> listEmp = new ArrayList<Employee>();
    	JSONArray Obj = new JSONArray();
        connect();
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Iq_Pending");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            
		    
		        JSONObject list1 = new JSONObject();
		        list1.put("puntoid",resultSet.getString(4));
		        list1.put("area",resultSet.getString(1));
		        list1.put("ticket",resultSet.getString(2));
		        list1.put("punto",resultSet.getString(3));
		        Obj.put(list1);
		    
		    //System.out.println("Obj:"+Obj);	
        }

        cleanConnection();

        return ""+Obj;
    }

    
}
