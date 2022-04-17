package com.ykn.jobscheduler.other;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author hexiangtaoX
 * @date 2022/4/3 10:21
 **/
public class ChunkCsvFileLoader implements CsvFileLoader {

    private static final int PAGE_SIZE = 100;
    private static final String TABLE_NAME = "user_behavior_all";

    private static final String FILE_PATH = "C:/data/UserBehavior.csv";

    public static void main(String[] args) throws IOException {
        System.out.println(loadRows(FILE_PATH).size());
    }

    public static void main2(String[] args) throws Exception {


        Connection conn = getLocalConnection();
        int skipLines = getSkipLines(conn);
        List<String> all = loadRows(skipLines, FILE_PATH);
        int totalPage = all.size() % PAGE_SIZE == 0 ? all.size() / PAGE_SIZE : all.size() / PAGE_SIZE + 1;
        Logger.getAnonymousLogger().info("begin loadCSV totalPage:" + totalPage);
        for (int pageIndex = 1; pageIndex <= totalPage; pageIndex++) {
            executeBatch(conn, all, pageIndex);
        }
    }


    @Data
    static class Item {
        Long uid;
        Long itemId;
        Long categoryId;
        String behaviorType;
        Long timestamps;

        public Item(Long uid, Long itemId, Long categoryId, String behaviorType, Long timestamps) {
            this.uid = uid;
            this.itemId = itemId;
            this.categoryId = categoryId;
            this.behaviorType = behaviorType;
            this.timestamps = timestamps;
        }
    }

    private static List<String> loadRows(int skipLines, String path) throws IOException {
        List<String> lines = readAllLines(skipLines, path);
        if (skipLines <= 0) {
            return lines;
        }
        return lines.subList(skipLines, lines.size());

    }

    private static List<String> loadRows(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));

    }

    private static List<Item> page(List<String> all, int pageIndex, int limit) {


        int begin = (pageIndex - 1) * PAGE_SIZE;
        begin = Math.max(begin, 0);
        int end = Math.min(all.size(), begin + limit);
        List<String> pageLine = all.subList(begin, end);
        return pageLine.stream().map(ChunkCsvFileLoader::toRow).collect(Collectors.toList());
    }


    private static Item toRow(String line) {
        String[] fields = line.split(",");
        return new Item(Long.parseLong(fields[0]), Long.parseLong(fields[1]), Long.parseLong(fields[2]), fields[3], Long.parseLong(fields[4]));
    }

    private static void executeBatch(Connection conn, List<String> all, int pageIndex) throws SQLException {
        NamedParameterStatement ps = new NamedParameterStatement(conn, "INSERT INTO " + TABLE_NAME + "(uid,item_id,category_id,behavior_type,timestamps,create_date) VALUES(:uid,:itemId,:categoryId,:behaviorType,:timestamps,NOW())");
        List<Item> items = page(all, pageIndex, PAGE_SIZE);
        long begin = System.currentTimeMillis();
        Logger.getGlobal().info("begin batchInsert  pageIndex:" + pageIndex);
        for (Item item : items) {
            addBatch(ps, item);
        }
        Logger.getGlobal().info("addBatch end,used " + ((System.currentTimeMillis() - begin) / 1000) + " sec");
        ps.executeBatch();
        ps.getConnection().commit();
        Logger.getGlobal().info("success insert " + items.size() + " rows,  used:" + ((System.currentTimeMillis() - begin) / 1000) + " sec ");

    }

    public static void addBatch(NamedParameterStatement ps, Item item) throws SQLException {
        ps.setLong("uid", item.uid);
        ps.setLong("itemId", item.itemId);
        ps.setLong("categoryId", item.categoryId);
        ps.setString("behaviorType", item.behaviorType);
        ps.setTimestamp("timestamps", new Timestamp(item.timestamps));
        ps.addBatch();
    }


    @Override
    public List<String> load(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }


    private static int getSkipLines(Connection conn) throws SQLException {
        ResultSet rs = conn.prepareStatement("SELECT count(id) from user_behavior_all;").executeQuery();
        rs.next();
        return rs.getInt(1);

    }

    static {
        MemoryMonitor.monitor();
    }

    public static Connection getLocalConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/bigtable?&useUnicode=true&useSSL=false&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&rewriteBatchedStatements=true";
        String user = "root";
        String pass = "eno123!@#";
        return DriverManager.getConnection(url, user, pass);
    }

    public static List<String> readAllLines(int skipLines, String path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            List<String> result = new ArrayList<>();
            int lineIndex = 0;
            for (; ; ) {
                String line = reader.readLine();
                lineIndex++;
                if (line == null) {
                    break;
                }
                if (lineIndex <= skipLines) {
                    continue;
                }
                result.add(line);
            }
            return result;
        }
    }

}
