
package cn.edu.sdjzu.xg.bysj.controller.basic.profTitle;

import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import cn.edu.sdjzu.xg.bysj.service.ProfTitleService;
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

@WebServlet("/profTitle/list.ctl")
public class ListProfTitleController extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Collection<ProfTitle> profTitles = null;
        try {
            profTitles = ProfTitleService.getInstance().findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String profTitles_str = JSON.toJSONString(profTitles, SerializerFeature.DisableCircularReferenceDetect);
        response.getWriter().println(profTitles_str);
    }
}

