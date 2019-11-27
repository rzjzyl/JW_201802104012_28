package cn.edu.sdjzu.xg.bysj.controller.basic.profTitle;

import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import cn.edu.sdjzu.xg.bysj.service.ProfTitleService;
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

@WebServlet("/profTitle/update.ctl")
public class UpdateProfTitleControl extends HttpServlet {
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String profTitle_json = JSONUtil.getJSON(request);
        //将JSON字串解析为ProfTitle对象
        ProfTitle profTitleToAdd = JSON.parseObject(profTitle_json, ProfTitle.class);
        System.out.println("profTitleToAdd="+profTitleToAdd);
        try {
            //增加加ProfTitle对象
            ProfTitleService.getInstance().update(profTitleToAdd);
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
        ProfTitle profTitle = null;
        try {
            //删除
            profTitle = ProfTitleService.getInstance().find(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        String profTitlesToUpdate_str = JSON.toJSONString(profTitle);
        System.out.println("profTitlesToUpdate_str=" + profTitlesToUpdate_str);
        //响应
        response.getWriter().println(profTitlesToUpdate_str);
        //response.sendRedirect("/html/basic/updateProfTitle.html");
    }
}
