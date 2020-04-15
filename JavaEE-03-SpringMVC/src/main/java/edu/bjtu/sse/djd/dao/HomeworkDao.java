package edu.bjtu.sse.djd.dao;

import edu.bjtu.sse.djd.DBCP;
import edu.bjtu.sse.djd.entity.Homework;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeworkDao {

    // 查找所有记录
    public List<Homework> selectAll() {
        // 定义 SQL 语句
        String sql = "SELECT * FROM s_homework";
        System.out.println(sql);
        // 一条结果
        Homework h = null;
        // 存储结果集
        List<Homework> resultList = new ArrayList<>();

        try (Connection connection = DBCP.getHikariDataSource().getConnection()){
            try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = pstmt.executeQuery(sql)) {
                    // 输出结果集
                    while (resultSet.next()) {
                        // 初始化一条结果
                        h = new Homework();
                        h.setId(resultSet.getLong("id"));
                        h.setHomeworkTittle(resultSet.getString("title"));
                        h.setHomeworkContent(resultSet.getString("content"));
                        h.setCreateTime(resultSet.getTimestamp("create_time"));
                        h.setUpdateTime(resultSet.getTimestamp("update_time"));
                        resultList.add(h);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    // 添加一条记录
    public void addHomework(Homework h) {
        // 定义 SQL 语句
        // PreparedStatement
        String sql = "INSERT INTO s_homework(title, content) VALUES (?, ?)";

        try (Connection connection = DBCP.getHikariDataSource().getConnection()){
            try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, h.getHomeworkTittle());
                pstmt.setString(2, h.getHomeworkContent());
                System.out.println(sql);
                pstmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除一条记录
    public void deleteHomework(Homework h) {
        // 定义 SQL 语句
        // PreparedStatement
        String sql = "DELETE FROM s_homework WHERE id=?";
        // 执行 SQL
        try (Connection connection = DBCP.getHikariDataSource().getConnection()){
            try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setLong(1, h.getId());
                System.out.println(sql);
                pstmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 更新一条记录
    public void updateHomework(Homework h) {
        // 定义 SQL 语句
        // PreparedStatement
        String sql = "UPDATE s_homework SET title=?, content=?, update_time=? WHERE id=?";

        try (Connection connection = DBCP.getHikariDataSource().getConnection()){
            try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, h.getHomeworkTittle());
                pstmt.setString(2, h.getHomeworkContent());
                pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                pstmt.setLong(4, h.getId());
                System.out.println(sql);
                pstmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
