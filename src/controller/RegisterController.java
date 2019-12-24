package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.Customer;
import service.CustomerService;
import util.JSONUtil;
import util.mail.MailSender;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/customerRegister.ctl")
public class RegisterController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String register_json = JSONUtil.getJSON(request);
        //创建Json对象存放运行结果
        JSONObject message = new JSONObject();
        JSONObject register = JSONObject.parseObject(register_json);
        try {
            if (register.getString("vcode").equals(MailSender.getVcode())) {
                message.put("message","注册成功");
                register.remove("vcode");
                String customer_json=register.toJSONString();
                CustomerService.getInstance().register(JSON.parseObject(customer_json,Customer.class));
            }else {
                message.put("message", "验证码错误");
            }
        }catch (SQLException e){
            message.put("message","数据库操作异常");
            e.printStackTrace();
        }catch (Exception e){
            message.put("message","网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

}
