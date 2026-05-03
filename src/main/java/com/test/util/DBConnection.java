/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-29
  Time: 오후 5:12
*/


// No use After Refactoring


//package com.test.util;
//
//import java.sql.Connection;
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.sql.DataSource;
//
//public class DBConnection {
//
//    // 1. static을 붙여야 DBConnection.getConnection()으로 바로 호출 가능합니다.
//    public static Connection getConnection() throws Exception {
//        // 2. 서버(Tomcat)의 context.xml에 설정된 리소스를 찾는 과정
//        Context initContext = new InitialContext();
//        Context envContext = (Context) initContext.lookup("java:/comp/env");
//
//        // "jdbc/myoracle" 이 이름이 context.xml의 name과 일치해야 합니다.
//        DataSource ds = (DataSource) envContext.lookup("jdbc/myoracle");
//
//        return ds.getConnection();
//    }
//}