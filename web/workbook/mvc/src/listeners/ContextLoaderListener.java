package listeners;

import context.ApplicationContext;
import controls.*;
import dao.MySqlMemberDao;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.InputStream;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
    static ApplicationContext applicationContext;
    public static ApplicationContext getApplicationContext() {return applicationContext;}
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try{
            applicationContext = new ApplicationContext();

            String resource = "dao/mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            applicationContext.addBean("sqlSessionFactory", sqlSessionFactory);

            ServletContext sc = event.getServletContext();
            String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));

            applicationContext.prepareObjectsByProperties(propertiesPath);
            applicationContext.prepareObjectsByAnnotation("");
            applicationContext.injectDependency();
        }catch(Throwable e){
            e.printStackTrace();
        }

    }

    public void contextDestroyed(ServletContextEvent event){

    }
}
