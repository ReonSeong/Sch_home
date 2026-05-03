/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-29
  Time: 오후 5:39
*/

//no use after refactor


//package com.test.util;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//public class ViewResolver {
//    // 공통 경로 설정
//    private static final String PREFIX = "/WEB-INF/views/";
//    private static final String SUFFIX = ".jsp";
//
//    /**
//     * 경로를 자동으로 조립해서 포워딩해주는 메서드
//     * @param viewName login
//     *
//     */
//    public static void forward(String viewName, HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String fullPath = PREFIX + viewName + SUFFIX;
//        request.getRequestDispatcher(fullPath).forward(request, response);
//    }
//}