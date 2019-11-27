package cn.edu.sdjzu.xg.bysj.controller.basic.school;

import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

//映射的url分两部分：实体名称(学院)和动作名称(列表),ctl说明这个url是一个controller(servlet)
@WebServlet("/school/list.ctl")
public class ListSchoolController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //获得所有学院
        Collection<School> schools = null;
        try {
            schools = SchoolService.getInstance().findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String schools_str = JSON.toJSONString(schools, SerializerFeature.DisableCircularReferenceDetect);
//        JSON.to
        //向客户端响应数据
        response.getWriter().println(schools_str);
        /**
         *
         *   [
         *     {
         *       "no": "01",
         *       "description": "土木工程"
         *     },
         *     {
         *       "no": "02",
         *       "description": "管理工程"
         *     },
         *     {
         *       "no": "03",
         *       "description": "市政工程"
         *     },
         *     {
         *       "no": "04",
         *       "description": "艺术"
         *     }
         *   ]
         *
         */
    }
}
