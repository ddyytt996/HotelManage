package controller;

import com.alibaba.fastjson.JSONObject;
import util.mail.MailSender;

import javax.mail.SendFailedException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("mail.ctl")
public class MailController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String account_str = request.getParameter("account");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            MailSender.sendMail(account_str);
            message.put("message", "已发送邮箱验证码");
        } catch (SendFailedException e){
            message.put("message","邮箱输入错误");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message","网络异常");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
