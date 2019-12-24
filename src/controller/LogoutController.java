package controller;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout.ctl")
public class LogoutController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取当前会话对象
        HttpSession session=request.getSession();
        //使会话失效
        session.invalidate();
        //向前台输出运行结果
        JSONObject message =new JSONObject();
        message.put("message","退出成功");
        response.getWriter().println(message);
    }
}
