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

@WebServlet("/customer.ctl")
public class CustomerController extends HttpServlet {

    //PUT,修改用户名和密码
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String changeInformation_json = JSONUtil.getJSON(request);
        JSONObject changeInformation = JSONObject.parseObject(changeInformation_json);
        //创建Json对象存放运行结果
        JSONObject message = new JSONObject();
        try {
            if (changeInformation.getString("oldpassword") == null) {
                Customer changeName = JSONObject.parseObject(changeInformation_json, Customer.class);
                CustomerService.getInstance().changeName(changeName);
            } else {
                if (changeInformation.getString("vcode").equals(MailSender.getVcode())) {
                    changeInformation.remove("vcode");
                    if (changeInformation.getString("oldpassword")
                            .equals(CustomerService.getInstance().findByAccount(changeInformation.getString("account")).getPassword())) {
                        changeInformation.remove("oldpassword");
                        String changePassword_json = changeInformation.toJSONString();
                        CustomerService.getInstance().changePassword(JSON.parseObject(changePassword_json, Customer.class));
                        message.put("message", "密码修改成功");
                    } else {
                        message.put("message", "原密码输入错误");
                    }
                } else {
                    message.put("message", "验证码错误");
                }
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        response.getWriter().println(message);
    }
}
