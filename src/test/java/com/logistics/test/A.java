package com.logistics.test;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/7/007.
 */
public class A {

    private static Logger logger = LoggerFactory.getLogger(A.class);

    public static void main(String[] args) {

        String labelSql = "select * from T1 where 1=1 and a = :aa  and (b =:bb or c =:cc or d = :dd) and e like :ee";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("aa", 123);
        param.put("cc", 50);
        param.put("ee", 40);
        param.put("bb",40);
        System.out.println(getRunnableSql(labelSql, param));
    }


    public static String getRunnableSql(String labelSql, Map<String, Object> param) {
        StringBuffer sql = new StringBuffer("select * from T1 where 1=1 ");
        String[] sqls = labelSql.split("and");
        boolean isOr = false;
        for (String s : sqls) {
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                int index = s.indexOf(entry.getKey());
                if (index != -1) {
                    if (s.contains("or") && !isOr) {
                        isOr = true;
                        dealOrSql(s, param, sql);
                    }
                    if (!s.contains("or")){
                        sql.append(" and ");
                        s = s.replace(":" + entry.getKey(), entry.getValue().toString());
                        sql.append(s);
                    }
                }
            }
        }
        return sql.toString();
    }

    public static void dealOrSql(String condition, Map<String, Object> param, StringBuffer sql) {
        String[] arrsql = condition.split("or");
        sql.append(" and (");
        for (String s : arrsql) {
            System.out.println(s);
            if (s.contains(")")) {
                s = s.replace(")","");
            }
            if (s.contains("(")) {
                s = s.replace("(","");
            }
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                int index = s.indexOf(entry.getKey());
                if (index != -1) {
                    s = s.replace(":" + entry.getKey(), entry.getValue().toString());
                    sql.append(s);
                    sql.append(" or ");
                }
            }
        }
        sql.delete(sql.lastIndexOf("or"),sql.lastIndexOf("or")+2);
        sql.append(")");
    }

}
