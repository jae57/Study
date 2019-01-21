package dao;

import annotation.Component;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import vo.Project;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

//@Component("projectDao")
public class MySqlProjectDao implements ProjectDao {
    SqlSessionFactory sqlSessionFactory;
    final static Logger logger = Logger.getLogger(MySqlProjectDao.class);

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){ this.sqlSessionFactory = sqlSessionFactory;}

    @Override
    public List<Project> selectList(HashMap<String,Object> paramMap) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.selectList("dao.ProjectDao.selectList",paramMap);
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public int insert(Project project) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int count = sqlSession.insert("dao.ProjectDao.insert",project);
            sqlSession.commit();
            return count;
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public Project selectOne(int no) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.selectOne("dao.ProjectDao.selectOne",no);
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int update(Project project) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            Project original = sqlSession.selectOne("dao.ProjectDao.selectOne",project.getNo());
            Hashtable<String, Object> paramMap = new Hashtable<String,Object>();

            // 로거!!
            logger.debug("Hello!");
            logger.debug(project.getStartDate());
            logger.debug(original.getStartDate());
            logger.debug(project.getStartDate().compareTo(original.getStartDate()));

            // 새로 받아온 값이 기존 값과 다를 경우 map 객체에 넣기
            if(!project.getTitle().equals(original.getTitle())){
                paramMap.put("title",project.getTitle());
            }
            if (!project.getContent().equals(original.getContent())) {
                paramMap.put("content", project.getContent());
            }
            if (project.getStartDate().compareTo(original.getStartDate()) != 0) {
                paramMap.put("startDate", project.getStartDate());
            }
            if (project.getEndDate().compareTo(original.getEndDate()) != 0) {
                paramMap.put("endDate", project.getEndDate());
            }
            if (project.getState() != original.getState()) {
                paramMap.put("state", project.getState());
            }
            if (!project.getTags().equals(original.getTags())) {
                paramMap.put("tags", project.getTags());
            }

            if(paramMap.size() > 0 ){
                paramMap.put("no",project.getNo());
                int count = sqlSession.update("dao.ProjectDao.update",paramMap);
                sqlSession.commit();
                return count;
            }else{
                return 0;
            }
        }finally{
            sqlSession.close();
        }
    }

    @Override
    public int delete(int no) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int count = sqlSession.delete("dao.ProjectDao.delete",no);
            sqlSession.commit();
            return count;
        }finally{
            sqlSession.close();
        }
    }
}
