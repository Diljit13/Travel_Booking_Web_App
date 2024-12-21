package com.travel.dao;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ReviewDAO {
	
	private String jdbcURL ="jdbc:mysql://localhost:30006/userdb";
	private String jdbcUserName ="root";
	private String jdbcPassword ="Akash#19122017**";
	
	private static final String INSERT_USER_SQL="INSERT INTO books"+"(id,title,author,isbn,genre,available_copies)VALUES"+"(?,?,?,?,?,?);";
	private static final String SELECT_USER_BY_ID="SELECT*FROM USERS WHERE ID=?;";
	private static final String SELECT_All_USER="SELECT*FROM User;";
	private static final String DELETE_USER_SQL="DELETE*FROM USERS WHERE ID=?;";
	// private static final String UPDATE_USERS_SQL="UPDATE USERS SET id=?,title=?,author=?,isbn=?,genre=?,available_copies=?;";
	
	public ReviewDAO() {
		super();
	}
	public Connection getConnection() {
		Connection connection=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
		}
		catch(SQLException | ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();	
		}
		return connection;
	}

    public void insertUser(Member user){
        PaymentsDAO dao=new PaymentsDAO();

        try(Connection connection=dao.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(INSERT_USER_SQL);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(1, user.getEmail());
            // preparedStatement.setString(1, user.getCountry());
            preparedStatement.setString(1, user.getPassword());

            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Member seledtUser(int id){
        Member user=new Member();
        PaymentsDAO dao=new PaymentsDAO();

        try(Connection connection=dao.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){
                user.setId(id);
                user.setName(resultSet.getString("uname"));
                user.setEmail(resultSet.getString("email"));
                user.setEmail(resultSet.getString("country"));
                user.setEmail(resultSet.getString("password"));

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public List<Member> selectAllUsers(){
        List<Member> users=new ArrayList<Member>();
        PaymentsDAO dao=new PaymentsDAO();
        try(Connection connection=dao.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(SELECT_All_USER);
            ResultSet resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String uname=resultSet.getString("uname");
                String email=resultSet.getString("email");
                String country=resultSet.getString("countryl");
                String password=resultSet.getString("passwd");

                users.add(new Member(id,uname,email,country,password));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public boolean deleteUser(int id){
        boolean status=false;
        PaymentsDAO dao=new PaymentsDAO();

        try(Connection connection=dao.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(DELETE_USER_SQL);
            preparedStatement.setInt(1, id);

            status=preparedStatement.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return status;
    }

}

