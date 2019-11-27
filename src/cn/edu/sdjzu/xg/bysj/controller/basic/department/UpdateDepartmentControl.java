package cn.edu.sdjzu.xg.bysj.controller.basic.department;

import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
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

@WebServlet("/department/update.ctl")
public class UpdateDepartmentControl extends HttpServlet {
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String department_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Department对象
        Department departmentToAdd = JSON.parseObject(department_json, Department.class);
        System.out.println("departmentToAdd="+departmentToAdd);
        try {
            //增加加Department对象
            DepartmentService.getInstance().update(departmentToAdd);
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
        Department department = null;
        try {
            //删除专业
            department = DepartmentService.getInstance().find(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        String departmentsToUpdate_str = JSON.toJSONString(department);
        System.out.println("departmentsToUpdate_str=" + departmentsToUpdate_str);
        //响应
        response.getWriter().println(departmentsToUpdate_str);
        //response.sendRedirect("/html/basic/updateDepartment.html");
    }
}
