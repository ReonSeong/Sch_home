/*
  Created by IntelliJ IDEA.
  User: ReonQ
  Date: 2026-04-29
  Time: 오후 5:21
*/

package com.test.DAO;

import com.test.Util.DBConnection;
import java.sql.Connection;

public class BaseDAO {
    protected Connection getConnection() throws Exception {
        return DBConnection.getConnection();
    }
}