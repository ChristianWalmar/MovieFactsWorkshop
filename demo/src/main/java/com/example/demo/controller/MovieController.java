package com.example.demo.controller;


import com.example.demo.repository.DBManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

@RestController
public class MovieController {
    @GetMapping("/check")
    public String checkConnection(){
        Connection connection = DBManager.getConnection();
        if (connection != null){
            return "connected";
        }else
            return "not connected";
    }
    @GetMapping("/")
    public String welcome(){
        return "Welcome to this movie app!";
    }

    @GetMapping("getFirst")
    public String getFirst() throws SQLException {
        String str = "select title from movies where ID = '1' ";
        PreparedStatement preparedStatement;
        Connection connection = DBManager.getConnection();
        try{
            preparedStatement = connection.prepareStatement(str);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return "Name of first movie in the schema: " + resultSet.getString(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("getRandom")
    public String getRandom(){
        Random random = new Random();
        String query = "SELECT title FROM movies WHERE ID = " + random.nextInt(1583);
        String query2 = "SELECT title FROM movies ORDER BY RAND() LIMIT 1;";
        PreparedStatement preparedStatement;
        Connection connection = DBManager.getConnection();
        try{
            preparedStatement = connection.prepareStatement(query2);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return "Name of first movie in the schema: " + resultSet.getString(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/howManyWonAnAwardThis")
    public String getTenSortByPopularity(){
        String query = "SELECT count(*) FROM imdb.movies where awards = 'yes';";
        PreparedStatement preparedStatement;
        Connection connection = DBManager.getConnection();
        try{
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @GetMapping("/filter")
    public ArrayList<String> getFilter(@RequestParam char x){
        ArrayList<String> resultList = new ArrayList<>();
        String query = "select * from imdb.movies where title like '%x%'";
        PreparedStatement preparedStatement;
        Connection connection = DBManager.getConnection();
        try{
            preparedStatement = connection.prepareStatement((query));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                for (int i = 0; i<10; i++){
                    resultList.add(resultSet.getString(2));
                }
                return resultList;
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }

}
