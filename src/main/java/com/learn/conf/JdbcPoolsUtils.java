package com.data.pool;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * Created by gaogf on 2018/3/29.
 */
public class JdbcPoolsUtils implements DataSource {
    private static Vector<Connection> connections = new Vector<>();

    static {
        InputStream inputStream = JdbcPoolsUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            String driver = properties.getProperty("driver");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            //数据库连接池的初始化大小
            Integer poolInitSize = Integer.parseInt(properties.getProperty("jdbcPoolInitSize"));
            for (int i = 0; i < poolInitSize; i++){
                try {
                    Connection connection = DriverManager.getConnection(url, user, password);
                    connections.addElement(connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if(connections.size() > 0){
            Connection connection = connections.firstElement();
            Object close = Proxy.newProxyInstance(JdbcPoolsUtils.class.getClassLoader(), connection.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (!method.getName().equals("close")) {
                        return method.invoke(connection, args);
                    } else {
                        connections.addElement(connection);
                        return null;
                    }
                }
            });
        }else {
            System.out.println("数据库繁忙。。。");
        }
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
