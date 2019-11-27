package cn.edu.sdjzu.xg.bysj.controller.basic.teacher;

import cn.edu.sdjzu.xg.bysj.domain.Teacher;
import cn.edu.sdjzu.xg.bysj.service.TeacherService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/teacher/update.ctl")
public class UpdateTeacherControl extends HttpServlet {
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String teacher_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Teacher对象
        Teacher teacherToAdd = JSON.parseObject(teacher_json, Teacher.class);
        System.out.println("teacherToAdd="+teacherToAdd);
        try {
            //增加加Teacher对象
            TeacherService.getInstance().update(teacherToAdd);
        }catch (SQLException e){
            e.printStackTrace();
        }
        //创建JSON对象
        JSONObject resp = new JSONObject();
        //加入数据信息
        resp.put("MSG", "OK");
        //响应
        response.getWriter().println(resp);

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("update doGet");
        //读取参数id
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        System.out.println("id="+id);
        Teacher teacher = null;
        try {
            //删除学院
            teacher = TeacherService.getInstance().find(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        String teachersToUpdate_str = JSON.toJSONString(teacher);
        System.out.println("teachersToUpdate_str=" + teachersToUpdate_str);
        //响应
        response.getWriter().println(teachersToUpdate_str);
        //response.sendRedirect("/html/basic/updateTeacher.html");
    }
}
