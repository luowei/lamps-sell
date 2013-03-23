package com.vvvv.common.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.vvvv.common.tool.common.PropertiesUtil;

/**
 * @className:CreateTuid.java
 * @classDescription: 用户主键自动生成类
 */
public class CreateTuid {
	public static Map<String, AtomicInteger> tuidCreateMap = new HashMap<String, AtomicInteger>();;


	static {
		// 获取数据库配置文件
		//String path = CreateTuid.class.getResource("/config/database.properties").getPath().replace("%20", " ").substring(1);
		//java.util.Properties properties = PropertiesUtil.getProperties(path);
		InputStream in=CreateTuid.class.getResourceAsStream("/config/database.properties");
		java.util.Properties properties = PropertiesUtil.getProperties(in);

		int maxTuid = 0;
		Connection conn = null;
		
		try {
			/**
			 * 读取数据库连接参数
			 */
			String driverName = (String) properties.get("database.driverClassName");
			String url = (String) properties.get("database.url");
			String root = (String) properties.get("database.username");
			String pwd = (String) properties.get("database.password");

			/**
			 * 初始化数据库连接，获取当前tuid最大值
			 */
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, root, pwd);
			Statement stat = conn.createStatement();
			String sql = "SELECT MAX(tuid) AS tuid FROM tt_user WHERE tuid Like '19%'";
			ResultSet rst = stat.executeQuery(sql);
			while (rst.next()) {
				maxTuid = rst.getInt("tuid");
			}
			int max=0;
			// 如果找不到tuid则从0开始增涨
			if (0 != maxTuid) {
				//如果非零则截取字符，初始化自增长类
				String maxTuid1 = String.valueOf(maxTuid);
				//等进一步完善????????
				maxTuid1 = maxTuid1.substring(maxTuid1.indexOf('9') + 1);
				max = Integer.parseInt(maxTuid1);
			}
			tuidCreateMap.put("1", new AtomicInteger(max+1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//关闭连接
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
