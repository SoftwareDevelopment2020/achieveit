package com.softwaredevelopment.achieveit.config.datasource;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = "com.softwaredevelopment.achieveit.mapper", sqlSessionTemplateRef = "SqlSessionTemplate")
public class DataSourceConfig {

    private static final String ENVIRONMENT_ID = "achieveit";

    private Map<String, SqlSessionFactory> sqlSessionFactoryMap = new HashMap<>();

    protected SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryMap.get(ENVIRONMENT_ID);
        if (sqlSessionFactory == null) {
            String resource = "config/mybatis-config.xml";
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(inputStream, ENVIRONMENT_ID);
            sqlSessionFactoryMap.put(ENVIRONMENT_ID, sqlSessionFactory);
        }
        return sqlSessionFactory;
    }

    @Bean(name = "DataSource")
    public DataSource setDataSource() {
        return getSqlSessionFactory().getConfiguration().getEnvironment().getDataSource();
    }

    @Bean(name = "TransactionManager")
    public DataSourceTransactionManager setTransactionManager(@Qualifier("DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("DataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        bean.setConfiguration((MybatisConfiguration) getSqlSessionFactory().getConfiguration());
        return bean.getObject();
    }

    @Bean(name = "SqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
