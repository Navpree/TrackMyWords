
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


public class phoneApp {


	//receives results from phone
		public ResultSet receivePhone (String keyword){
			ResultSet results=null;
			 try {
				Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD", "COMP303W15_1", "password1");
				Statement stmt = connection.createStatement();
				 
				 String selectDataSQL = "select * from Songs where SongTitle like '%" + keyword  +  "%' ";
				 results = stmt.executeQuery(selectDataSQL);
				
			 } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}   
			 
			 return (results);
		}
		
	

}
