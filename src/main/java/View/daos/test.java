package View.daos;

import Implementations.SQLite.SQLiteLoader;
import Model.User.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class test extends SQLiteLoader {

    public static void main(String[] args) throws SQLException {
        //new test().getUserFromDB();
        String message= "Hello world!";
        String newMessage = message.substring(6,12) + message.substring(12,6);

    }

    public static void badMethod(){
        throw new Error();
    }
    static Exception print(int i){
        if (i>0){
            return new Exception();
        } else{
            throw new RuntimeException();
        }
    }

    public void getUserFromDB() throws SQLException {
        Connection connection = this.connect();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sqlQuery());
        List<User> userList = new ArrayList<>();
        while (rs.next()){
            userList.add(new User(rs.getString("receiverName"),rs.getString("receiverRFC")));
        }
        String CP= "35017";
        for (User user : userList){
            System.out.println("INSERT INTO COMPANY VALUES ('"+user.getCompanyRFC()+"', '"+user.getCompanyName()+ "', '"+ CP() + "');");
        }
    }

    public String CP(){
        Random random = new Random();
        int randomNumber = random.nextInt(6);
        if (randomNumber==0){
            return "35017";
        }
        if (randomNumber==1){
            return "35018";
        }
        if (randomNumber==2){
            return "35508";
        }
        if (randomNumber==3){
            return "38700";
        }
        if (randomNumber==4){
            return "38800";
        }
        if (randomNumber==5){
            return "35600";
        }
        return randomNumber+"";

    }

    private String sqlQuery() {
        return "SELECT * FROM empresas";
    }
}
