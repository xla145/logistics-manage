package com.logistics.test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据库表转换成javaBean对象小工具(已用了很长时间),
 * 1 bean属性按原始数据库字段经过去掉下划线,并大写处理首字母等等.
 * 2 生成的bean带了数据库的字段说明.
 * 3 各位自己可以修改此工具用到项目中去.
 */
public class MyEntityUtils {

	private static Set<String> tablenameSet = new HashSet<String>();
	private String tablename = "";
	private String[] colnames;
	private String[] colTypes;
	private int[] colSizes; // 列名大小
	private int[] colScale; // 列名小数精度
	private boolean importUtil = false;
	private boolean importSql = false;
	private boolean importMath = false;

	/**
	 * @param
	 */
	public void tableToEntity(Connection conn) {
		//数据连Connection获取,自己想办法就行.
//		Connection conn = getConn();
		try {
			for (String table:tablenameSet) {
				tablename = table;
                System.out.printf(""+tablename);
				String strsql = "SELECT * FROM " + tablename;//+" WHERE ROWNUM=1" 读一行记录;
				PreparedStatement pstmt = conn.prepareStatement(strsql);
				pstmt.executeQuery();
				ResultSetMetaData rsmd = pstmt.getMetaData();
				int size = rsmd.getColumnCount(); // 共有多少列
				colnames = new String[size];
				colTypes = new String[size];
				colSizes = new int[size];
				colScale = new int[size];
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					rsmd.getCatalogName(i + 1);
					colnames[i] = rsmd.getColumnName(i + 1).toLowerCase();
					colTypes[i] = rsmd.getColumnTypeName(i + 1).toLowerCase();
					colScale[i] = rsmd.getScale(i + 1);
					if ("datetime".equals(colTypes[i]) || "timestamp".equals(colTypes[i])) {
						importUtil = true;
					}
					if ("image".equals(colTypes[i]) || "text".equals(colTypes[i])) {
						importSql = true;
					}
					if(colScale[i]>0){
						importMath = true;
					}
					colSizes[i] = rsmd.getPrecision(i + 1);
				}
				String content = parse(colnames, colTypes, colSizes);
				try {
					File file = new File("G:\\xla\\logistics-manage\\src\\main\\java\\com\\logistics\\service\\vo\\"+initcap(tablename) + ".java");
					FileOutputStream fos = new FileOutputStream(file);
					PrintWriter fw = new PrintWriter(fos);
					fw.println(content);
					fw.flush();
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 解析处理(生成实体类主体代码)
	 */
	private String parse(String[] colNames, String[] colTypes, int[] colSizes) {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\npackage com.logistics.service.vo;\r\n");
		sb.append("\r\nimport cn.assist.easydao.annotation.Id;\r\n");
		sb.append("\r\nimport cn.assist.easydao.annotation.Temporary;\r\n");
		sb.append("\r\nimport cn.assist.easydao.pojo.BasePojo;\r\n");
		if (importUtil) {
			sb.append("import java.util.Date;\r\n");
		}
		if (importSql) {
			sb.append("import java.sql.*;\r\n\r\n");
		}
		if(importMath){
			sb.append("import java.math.*;\r\n\r\n");
		}
		//表注释
		processColnames(sb);
		sb.append("public class " + initcap(tablename) + " extends BasePojo {\r\n");
		processAllAttrs(sb);
		processAllMethod(sb);
		sb.append("}\r\n");
		return sb.toString();

	}
//	/**
//	 * 处理列名,把空格下划线'_'去掉,同时把下划线后的首字母大写
//	 * 要是整个列在3个字符及以内,则去掉'_'后,不把"_"后首字母大写.
//	 * 同时把数据库列名,列类型写到注释中以便查看,
//	 * @param sb
//	 */
	private void processColnames(StringBuffer sb) {
		sb.append("\r\n/** " + tablename + "\r\n");
		String colsiz="";
		String colsca="";
		for (int i = 0; i < colnames.length; i++) {
			colsiz = colSizes[i]<=0? "" : (colScale[i]<=0? "("+colSizes[i]+")" : "("+colSizes[i]+","+colScale[i]+")");
			sb.append("\t" + colnames[i].toUpperCase() +"	"+colTypes[i].toUpperCase()+ colsiz+"\r\n");
			char[] ch = colnames[i].toCharArray();
			char c ='a';
			if(ch.length>3){
				for(int j=0;j <ch.length; j++){
					c = ch[j];
					if(c == '_'){
						if (ch[j+1]>= 'a' && ch[j+1] <= 'z') {
							ch[j+1]=(char) (ch[j+1]-32);
						}
					}
				}
			}
			String str = new String(ch);
			colnames[i] = str.replaceAll("_", "");
		}
		sb.append("*/\r\n");
	}
	/**
	 * 生成所有的方法
	 * 
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tpublic void set" + initcap(colnames[i]) + "("
					+ oracleSqlType2JavaType(colTypes[i],colScale[i],colSizes[i]) + " " + colnames[i]
					+ "){\r\n");
			sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");

			sb.append("\tpublic " + oracleSqlType2JavaType(colTypes[i],colScale[i],colSizes[i]) + " get"
					+ initcap(colnames[i]) + "(){\r\n");
			sb.append("\t\treturn " + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
		}
	}

	/**
	 * 解析输出属性
	 * 
	 * @return
	 */
	private void processAllAttrs(StringBuffer sb) {
		sb.append("\t@Temporary\r\n");
		sb.append("\tprivate static final long serialVersionUID = 1L;\r\n");
		sb.append("\t@Id\r\n");
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tprivate " + oracleSqlType2JavaType(colTypes[i],colScale[i],colSizes[i]) + " "
					+ colnames[i] + ";\r\n");
		}
		sb.append("\r\n");
	}


	/**
	 * 把输入字符串的首字母改成大写
	 * @param str
	 * @return
	 */
	public static String initcap(String str) {
		char[] ch = str.toCharArray();
		char[] chs = new char[ch.length];
		for (int i=0; i < ch.length; i++) {	
			if (ch[0] >= 'a' && ch[0] <= 'z') {
				ch[0] = (char) (ch[0] - 32);
			}
			if (ch[i] == '_') {
				if (ch[i+1] >= 'a' && ch[i+1] <= 'z') {
					ch[i+1] = (char) (ch[i+1] - 32);
				}
			}
			chs[i] = ch[i];
		}
		return new String(chs).replace("_", "");
	}
	
	/**
	 * Oracle
	 * @param sqlType
	 * @param scale
	 * @return
	 */
	private String oracleSqlType2JavaType(String sqlType, int scale,int size) {
		if (sqlType.equals("int")) {
			return "Integer";
		} else if (sqlType.equals("long")) {
			return "Long";
		} else if (sqlType.equals("float")
				|| sqlType.equals("float precision")
				|| sqlType.equals("double")
				|| sqlType.equals("double precision")
				) {
			return "BigDecimal";
		}else if (sqlType.equals("number")
				||sqlType.equals("decimal")
				|| sqlType.equals("numeric")
				|| sqlType.equals("real")) {
			return scale==0? (size<10? "Integer" : "Long") : "BigDecimal";
		}else if (sqlType.equals("varchar")
				|| sqlType.equals("varchar2")
				|| sqlType.equals("char")
				|| sqlType.equals("nvarchar")
				|| sqlType.equals("nchar")) {
			return "String";
		} else if (sqlType.equals("datetime")
				|| sqlType.equals("date")
				|| sqlType.equals("timestamp")) {
			return "Date";
		} else if(sqlType.equals("tinyint")) {
			return "boolean";
		}
		return null;
	}
	public static Connection getConn(){
		Connection conn=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/logistics?useUnicode=true&characterEncoding=UTF8","root","123456");
			PreparedStatement pstmt = conn.prepareStatement("show tables");
			pstmt.executeQuery();
			ResultSet rs = pstmt.getResultSet();
			while (rs.next()) {
				tablenameSet.add(rs.getString(1));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		File file = new File("G:\\xla\\logistics-manage\\logistics-manage\\src\\main\\java\\com\\yuelinghui\\service\\vo\\"+455 + ".java");
//		System.out.println(file.exists());
		MyEntityUtils t = new MyEntityUtils();
		Connection conn = getConn();
		tablenameSet.clear();
		tablenameSet.add("audit_order");
		t.tableToEntity(conn);

	}

}
