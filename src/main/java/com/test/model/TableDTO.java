package com.test.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
/*
 *  File Name : TableDTO.java
 *  Writer : ReonQ
 *  Updated Date     Version     User        Change log
 *  2026-04-19           0.1     ReonQ       First make
 *  2026-05-01           0.2     ReonQ       Cange to @data , Add att
 *
 *  Now Version : 0.2
 *
 *  Description:
 *  TableDTO.java
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDTO {
    private int tableIdx;      // table index(auto increment)
    private String shopCode;   //
    private String tableName;  //
    private int posX;          // Screen X coordinate 화면상의 X 좌표
    private int posY;          // Screen X coordinate 화면상의 Y 좌표
    private String status;     // table status(If Y == available, N == not available(broken,fixing,etc..), R = reserved
    private String att1;       // IF status == R then reserved time
    private String att2;       // IF status == R then how many customer
    private String att3;       //

}