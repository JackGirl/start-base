package cn.ulyer.common.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


/**
 * @author: 万仁杰
 * <description>
 *     代码生成器
 * </description>
 * <update></update>
 * @date: 2020/3/20
 */
public class CodeGenerator {

	final static String DRIVER = "com.mysql.jdbc.Driver";

	final static String URL = "jdbc:mysql://localhost:3306/code-package?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";

	final static String USERNAME = "root";

	final static String PASSWORD = "xiaoxiao1997";

	/**
	 * 需要生成实体 mapper 的表  如果 表很多 可以 在 strategy.exclude 排除表  排除的时候不能使用include
	 */
	final static String [] INCLUDE_TABLE = new String[] {"base_user","base_app","base_menu","base_role","base_resource"
	,"base_action","base_action_resource","base_app_resource","base_resource_service","base_role_menu","base_role_resource",
	"base_role_user"};

	final static DbType DB_TYPE = DbType.MYSQL;

	public static void main(String[] args) {
		generator();
	}

	public static void generator(){
		AutoGenerator generator = new AutoGenerator();
		String projectPath = System.getProperty("user.dir");


		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setActiveRecord(false);
		globalConfig.setBaseColumnList(true);
		globalConfig.setBaseResultMap(true);
		globalConfig.setAuthor("mybatis-plus generator");
		globalConfig.setIdType(IdType.ASSIGN_ID);
		globalConfig.setOutputDir(projectPath+ "/src/main/java");
		globalConfig.setOpen(false);
		globalConfig.setServiceImplName("%sServiceImpl");
		globalConfig.setServiceName("%sService");
		globalConfig.setXmlName("%sMapper");
		globalConfig.setFileOverride(false);
		globalConfig.setDateType(DateType.ONLY_DATE);

		generator.setGlobalConfig(globalConfig);

		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DB_TYPE);
		dataSourceConfig.setUrl(URL);
		dataSourceConfig.setDriverName(DRIVER);
		dataSourceConfig.setUsername(USERNAME);
		dataSourceConfig.setPassword(PASSWORD);


		generator.setDataSource(dataSourceConfig);


		PackageConfig pc = new PackageConfig();
		pc.setParent("generator");
		generator.setPackageInfo(pc);


		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setEntityLombokModel(true);
		strategy.setColumnNaming(NamingStrategy.no_change);
		strategy.setSuperEntityClass(cn.ulyer.common.model.AbstractBaseModel.class);
		strategy.setRestControllerStyle(true);
		strategy.setInclude(INCLUDE_TABLE);
		generator.setStrategy(strategy);

		generator.execute();
	}

}
