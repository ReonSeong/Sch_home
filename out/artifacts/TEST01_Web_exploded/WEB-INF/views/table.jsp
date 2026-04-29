<%--
  Created by IntelliJ IDEA.
  User: tjdrb
  Date: 2026-04-29
  Time: 오후 5:27
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>매장 테이블 배치도</title>
    <style>
        /* 도면 배경 */
        #floor-plan {
            width: 800px;
            height: 600px;
            border: 2px solid #333;
            background-color: #f9f9f9;
            position: relative; /* 자식인 테이블들이 이 안에서 움직임 */
            margin: 20px;
        }
        /* 테이블 아이템 */
        .table-item {
            width: 80px;
            height: 80px;
            background-color: #007bff;
            color: white;
            border-radius: 8px;
            position: absolute; /* 좌표(X, Y)로 위치 지정 */
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: move;
            font-weight: bold;
            box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
        }
    </style>
</head>
<body>
<h2>${sessionScope.user.shopName} - 테이블 관리</h2>

<div id="floor-plan">
    <!-- DB에서 가져온 리스트를 반복문으로 뿌림 -->
    <c:forEach var="table" items="${tableList}">
        <div class="table-item"
             id="table-${table.tableIdx}"
             style="left: ${table.posX}px; top: ${table.posY}px;">
                ${table.tableName}
        </div>
    </c:forEach>
</div>

<button onclick="savePositions()">배치 저장하기</button>

<script>
    // 여기에 나중에 드래그 기능을 넣을 겁니다!
    function savePositions() {
        alert("좌표 저장 로직을 구현할 예정입니다.");
    }
</script>
</body>
</html>