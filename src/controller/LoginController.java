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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/customerLogin.ctl")
public class LoginController extends HttpServlet {
    //POST, 登录
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String login_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Customer对象
        Customer customer= JSON.parseObject(login_json, Customer.class);
        //创建Json对象存放运行结果
        JSONObject message=new JSONObject();
        try {
            //登录并获取该账号对应的user对象
            Customer loggedCustomer= CustomerService.getInstance().login(customer);
            if (loggedCustomer!=null){
                //创建会话对象
                HttpSession session=request.getSession();
                //设置会话的失效时间
                session.setMaxInactiveInterval(10*60);
                session.setAttribute("currentUser",loggedCustomer);
                //获取客户的customer对象向前端输出
                String customer_json = JSON.toJSONString(loggedCustomer);
                response.getWriter().println(customer_json);
            }else{
                message.put("message","用户名或密码错误");
                response.getWriter().println(message);
            }
        }catch (SQLException e){
            //抓取数据库异常
            message.put("message","数据库操作异常");
            response.getWriter().println(message);
        }catch (Exception e){
            //抓取其他异常
            message.put("message","网络异常");
            response.getWriter().println(message);
            e.printStackTrace();
        }
    }

    //PUT,找回密码
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String register_json = JSONUtil.getJSON(request);

        JSONObject register = JSONObject.parseObject(register_json);
        //创建Json对象存放运行结果
        JSONObject message = new JSONObject();
        try {
            if (register.getString("vcode").equals(MailSender.getVcode())) {
                register.remove("vcode");
                String customer_json = register.toJSONString();
                if (CustomerService.getInstance().findPassword(JSON.parseObject(customer_json, Customer.class))) {
                    message.put("message", "密码重置成功");
                } else {
                    message.put("message", "未找到该账户，找回密码失败");
                }
            } else {
                message.put("message", "验证码错误");
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
    }
}
