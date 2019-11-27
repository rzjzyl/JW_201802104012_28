
package cn.edu.sdjzu.xg.bysj.controller.basic.degree;

import cn.edu.sdjzu.xg.bysj.domain.Degree;
import cn.edu.sdjzu.xg.bysj.service.DegreeService;
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

@WebServlet("/degree/update.ctl")
public class UpdateDeleteControl extends HttpServlet {
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String degree_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Degree对象
        Degree degreeToAdd = JSON.parseObject(degree_json, Degree.class);
        System.out.println("degreeToAdd="+degreeToAdd);
        try {
            //增加加Degree对象
            DegreeService.getInstance().update(degreeToAdd);
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
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
        Degree degree = null;
        try {
            //删除学位
            degree = DegreeService.getInstance().find(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        String degreesToUpdate_str = JSON.toJSONString(degree);
        System.out.println("schoolsToUpdate_str=" + degreesToUpdate_str);
        //响应
        response.getWriter().println(degreesToUpdate_str);
        //response.sendRedirect("/html/basic/updateDegree.html");
    }
}
