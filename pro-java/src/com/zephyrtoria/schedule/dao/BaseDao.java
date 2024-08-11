package com.zephyrtoria.schedule.dao;

import com.zephyrtoria.schedule.util.JDBCUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public abstract class BaseDao {
    /*
     * 通用的增、删、改的方法
     * @param String sql：sql
     * @param Object... args：给sql中的?设置的值列表，可以是0~n
     */
    protected int executeUpdate(String sql, Object... args) throws SQLException {
        //连接数据库
        Connection connection = JDBCUtil.getConnection();
        //创建PreparedStatement对象，对sql预编译
        PreparedStatement ps = connection.prepareStatement(sql);
        //设置?占位符的值
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                //通用型的设置方式
                ps.setObject(i + 1, args[i]);//?的编号从1开始，不是从0开始，数组的下标是从0开始
            }
        }

        //执行sql
        int len = ps.executeUpdate();
        ps.close();
        //这里检查下是否开启事务，开启不关闭连接，业务方法关闭!
        //connection.getAutoCommit()为false，不要在这里回收connection，由开启事务的地方回收
        //connection.getAutoCommit()为true，正常回收连接
        //没有开启事务的话，直接回收关闭即可!
        if (connection.getAutoCommit()) {
            //回收
            JDBCUtil.release();
        }
        return len;
    }

    /*
     * 通用的查询：多行多列、单行多列、单行单列
     *   多行多列：List<Employee>
     *   单行多列：Employee
     *   单行单列：封装一个结果：Double/Integer...
     *
     * 封装过程：
     *   1. 返回结果：泛型：类型不确定，但是调用者知道，调用时将此次查询的结果类型告知BaseDAO就可以了
     *   2. 返回结果：通用List，可以存储多个结果，也可以存储一个结果
     *   3. 结果的封装：反射，要求调用者告知BaseDAO要封装对象的类对象Class
     * */
    protected <T> ArrayList<T> executeQuery(Class<T> clazz, String sql, Object... args) throws Exception {
        //获取连接
        Connection connection = JDBCUtil.getConnection();
        //创建PreparedStatement对象，对sql预编译
        PreparedStatement ps = connection.prepareStatement(sql);

        //设置?的值
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);//?的编号从1开始，不是从0开始，数组的下标是从0开始
            }
        }

        ArrayList<T> list = new ArrayList<>();

        //因为这是通用的查询，所以是不知道数据库中的列名等信息，所以要获取结果集中的元数据对象
        ResultSet res = ps.executeQuery();

        /*
         * 获取结果集的元数据对象。
         * 元数据对象中有该结果集中列的数量、列名称等信息
         */
        ResultSetMetaData metaData = res.getMetaData();
        int columnCount = metaData.getColumnCount();  //获取结果集中的列数

        //遍历结果集ResultSet，把查询结果中的一条一条记录，变成一个一个T 对象，放到list中。
        while (res.next()) {
            //循环一次代表有一行数据，代表有一个T对象，通过反射创建一个对象
            T t = clazz.newInstance();  //要求这个类型必须有公共的无参构造

            //把这条记录的每一个单元格的值取出来，设置到t对象对应的属性中。
            for (int i = 1; i <= columnCount; i++) {  //循环列
                //for循环一次，代表取某一行的1个单元格的值
                Object value = res.getObject(i);  //ResultSet下标从1开始

                //获取到的列的value值，这个值应该是t对象的某个属性值
                //但是不知道对应哪个属性，所以通过反射获取该属性对应的Field对象
                //metaData.getColumnName(i)  获取第i列的字段名，这里取别名可能没办法对应上
                String columnName = metaData.getColumnLabel(i);  //获取第i列的字段名或字段的别名
                //通过类对象和columnName获取要封装的对象的属性
                Field field = clazz.getDeclaredField(columnName);
                field.setAccessible(true);  //操作private的属性

                field.set(t, value);  //设置对应字段的值
            }

            list.add(t);
        }

        //释放资源
        res.close();
        ps.close();
        //这里检查下是否开启事务，开启不关闭连接，业务方法关闭!
        //没有开启事务的话，直接回收关闭即可!
        if (connection.getAutoCommit()) {
            //回收
            JDBCUtil.release();
        }

        return list;
    }

    /**
     * 通过调用上面的query方法来获取其中的第一个结果，来实现单行单列、单行多列的查询
     */
    protected <T> T executeQueryBean(Class<T> clazz, String sql, Object... args) throws Exception {
        ArrayList<T> list = executeQuery(clazz, sql, args);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
}
