package com.medrep.app.util;

import java.sql.Types;

import org.hibernate.dialect.MySQLDialect;

public class JsonMySQLDialect extends MySQLDialect {

public JsonMySQLDialect() {
    this.registerColumnType(Types.VARCHAR, "json");
}

}