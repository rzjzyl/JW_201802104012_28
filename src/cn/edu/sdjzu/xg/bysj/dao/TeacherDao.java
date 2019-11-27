package cn.edu.sdjzu.xg.bysj.dao;

import cn.edu.sdjzu.xg.bysj.domain.Degree;
import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import cn.edu.sdjzu.xg.bysj.domain.Teacher;
import util.JdbcHelper;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public final class TeacherDao {
	private static TeacherDao teacherDao=new TeacherDao();
	private TeacherDao(){}
	public static TeacherDao getInstance(){
		return teacherDao;
	}
	public Set<Teacher> findAll() throws SQLException {
		Set<Teacher> teachers = new TreeSet<Teacher>();
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		Statement statement = connection.createStatement();
		//执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
		ResultSet resultSet = statement.executeQuery("SELECT * FROM teacher");
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()){
			ProfTitle profTitle = ProfTitleDao.getInstance().find(resultSet.getInt("proftitle_id"));
			Degree degree = DegreeDao.getInstance().find(resultSet.getInt("degree_id"));
			Department department = DepartmentDao.getInstance().find(resultSet.getInt("department_id"));
			//创建Teacher对象，根据遍历结果中的id,name,profTitle,degree,department值
			Teacher teacher = new Teacher(resultSet.getInt("id"),resultSet.getString("name"),profTitle,degree,department);
			//向teachers集合中添加Teacher对象
			teachers.add(teacher);
		}
		//关闭资源
		JdbcHelper.close(resultSet,statement,connection);
		return teachers;
	}
	public Teacher find(Integer id) throws SQLException{
		Teacher teacher = null;
		Connection connection = JdbcHelper.getConn();
		String selectTeacher_sql = "SELECT * FROM teacher WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(selectTeacher_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		ResultSet resultSet = preparedStatement.executeQuery();
		//由于id不能取重复值，故结果集中最多有一条记录
		//若结果集有一条记录，则以当前记录中的id,description,no,remarks，school值为参数，创建Department对象
		//若结果集中没有记录，则本方法返回null
		if (resultSet.next()){
			ProfTitle profTitle = ProfTitleDao.getInstance().find(resultSet.getInt("proftitle_id"));
			Degree degree = DegreeDao.getInstance().find(resultSet.getInt("degree_id"));
			Department department = DepartmentDao.getInstance().find(resultSet.getInt("department_id"));
			//创建Department对象，根据遍历结果中的id,description,no,remarks，school值
			teacher = new Teacher(resultSet.getInt("id"),resultSet.getString("name"),profTitle,degree,department);
		}
		//关闭资源
		JdbcHelper.close(resultSet,preparedStatement,connection);
		return teacher;
	}

    public boolean update(Teacher teacher) throws SQLException{
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String updateTeacher_sql = "UPDATE teacher SET name=?,proftitle_id=?,degree_id=?,department_id=? WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(updateTeacher_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,teacher.getName());
        preparedStatement.setInt(2,teacher.getTitle().getId());
        preparedStatement.setInt(3,teacher.getDegree().getId());
        preparedStatement.setInt(4,teacher.getDepartment().getId());
        preparedStatement.setInt(5,teacher.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRows>0;
    }
	public boolean add(Teacher teacher) throws SQLException,ClassNotFoundException{
		Connection connection = JdbcHelper.getConn();
		String addTeacher_sql = "INSERT INTO Teacher (name,proftitle_id,degree_id,deprtment_id) VALUES"+" (?,?,?,?)";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(addTeacher_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,teacher.getName());
		preparedStatement.setInt(2,teacher.getTitle().getId());
		preparedStatement.setInt(3,teacher.getDegree().getId());
		preparedStatement.setInt(4,teacher.getDepartment().getId());
		//执行预编译语句，获取添加记录行数并赋值给affectedRowNum
		int affectedRowNum=preparedStatement.executeUpdate();
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRowNum>0;
	}

	public boolean delete(Teacher teacher) throws SQLException {
		Connection connection = JdbcHelper.getConn();
		//创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
		PreparedStatement pstmt = connection.prepareStatement("DELETE FROM Teacher WHERE ID = ?");
		//为预编译的语句参数赋值
		pstmt.setInt(1,teacher.getId());
		//执行预编译对象的executeUpdate()方法，获取删除记录的行数
		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("删除了 "+affectedRowNum+" 条");
		//关闭资源
		JdbcHelper.close(pstmt,connection);
		return affectedRowNum > 0;
	}
}
