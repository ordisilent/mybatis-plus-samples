package com.baomidou.mybatisplus.samples.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * <p>
 * mysql 代码生成器演示例子
 * </p>
 *
 * @author jobob
 * @since 2018-09-12
 */
public class MysqlGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setFileOverride(false);// 是否覆盖同名文件，默认是false
        gc.setOutputDir(projectPath + "/mybatis-plus-sample-generator/src/main/java");
        gc.setAuthor("jiatao01@qiyi.com");
        gc.setOpen(false);
        gc.setServiceName("I%sRepository");
        gc.setServiceImplName("%sRepository");
       /* gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList*/
        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceName("MP%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        //jdbc:mysql://bj.paytest.w.qiyi.db:8767/pay_lottery?useUnicode=true&autoReconnect=true&zeroDateTimeBehavior=convertToNull
        dsc.setUrl("jdbc:mysql://bj.paytest.w.qiyi.db:8767/jr_quick_pass?useUnicode=true&autoReconnect=true&&zeroDateTimeBehavior=convertToNull");
//         dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("qiyipay");
        dsc.setPassword("9s4_e7nb_biu_wry");
        /*dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });*/
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setModuleName("quick.pass");//asset or activity
        pc.setParent("com.iqiyi.jr");
        pc.setService("dao.repository");
        pc.setServiceImpl("dao.repository.impl");
        pc.setEntity("dao.model");
        pc.setMapper("dao.mapper");

        //自定义
        mpg.setPackageInfo(pc);

        // 自定义配置
        // mapperxml配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("ConfigPackage",pc.getParent()+".config");
                map.put("myServicePackage",pc.getParent()+".service");

                map.put("superMyServiceClassPackage","com.iqiyi.jr."+pc.getModuleName()+".service.BaseService");
                map.put("superMyServiceClassName","BaseService");
                // Request
                map.put("myRequestPackage","com.iqiyi.jr."+pc.getModuleName()+".controller.request");
                map.put("superMyRequestClassPackage","com.iqiyi.jr."+pc.getModuleName()+".controller.request.BaseRequest");
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/mybatis-plus-sample-generator/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*======================================= 自定义 =========================================*/
        // config 文件夹
        focList.add(new FileOutConfig("/mytemplates/config.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/mybatis-plus-sample-generator/src/main/java/com/iqiyi/jr/"+pc.getModuleName()+"/config"
                        + "/AppConfig" + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig("/mytemplates/myservice.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/mybatis-plus-sample-generator/src/main/java/com/iqiyi/jr/"+pc.getModuleName()+"/service"
                        + "/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
            }
        });
        // 自己定义的BaseService(类)
        focList.add(new FileOutConfig("/mytemplates/BaseService.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/mybatis-plus-sample-generator/src/main/java/com/iqiyi/jr/"+pc.getModuleName()+"/service/"
                        + "BaseService" + StringPool.DOT_JAVA;
            }
        });
        // Request
        focList.add(new FileOutConfig("/mytemplates/request.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/mybatis-plus-sample-generator/src/main/java/com/iqiyi/jr/"+pc.getModuleName()+"/controller/request"
                        + "/" + tableInfo.getEntityName() + "Request" + StringPool.DOT_JAVA;
            }
        });
        // BaseRequest
        focList.add(new FileOutConfig("/mytemplates/BaseRequest.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/mybatis-plus-sample-generator/src/main/java/com/iqiyi/jr/"+pc.getModuleName()+"/controller/request/"
                        + "BaseRequest" + StringPool.DOT_JAVA;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.baomidou.mybatisplus.samples.generator.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setSuperControllerClass("com.iqiyi.jr.loan.controller.BaseController");
//        strategy.setInclude(scanner("输入表名"));
//        strategy.setInclude("activity_gift_info","activity_group","activity_info","activity_join_record","activity_partner","activity_send_plan","activity_send_record");
//        strategy.setInclude("quick_pass_bind_card");
        /*strategy.setInclude("installment_order"
                ,"installment_loan_apply");
                ,"installment_card"
                ,"installment_card_auth"
                ,"installment_credit_apply"
                ,"installment_credit_auth"
                ,"installment_living_body"
                ,"installment_loan"
                ,"installment_loan_apply"
                ,"installment_ocr"
                ,"installment_order"
                ,"installment_plan"
                ,"installment_plan_sync"
                ,"installment_pre_filter"
                ,"installment_repay_order"
                ,"installment_repay_req"
                ,"installment_repay_req_ext"
                ,"installment_user"
        );*/

//        strategy.setExclude("asset_account");
        // 是否生成前缀Card or installmentCard
        strategy.setTablePrefix("quick_pass");
//        strategy.setSuperEntityColumns("id"); //配置上说明不需要知道主键
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setRestControllerStyle(true);
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
