package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.Repair;
import service.RepairService;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

@WebServlet("/repair.ctl")
public class RepairController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject message=new JSONObject();
        //获取当前会话对象
        HttpSession session=request.getSession(false);
        String currentUserClassName=session.getAttribute("currentUser").getClass().getName();
        try {
            //根据request对象，获得代表参数的JSON字串
            String repair_json = JSONUtil.getJSON(request);
            //将JSON字串解析为repair对象
            Repair repairToAdd= JSON.parseObject(repair_json, Repair.class);
            if (currentUserClassName.equals("Customer")) {
                RepairService.getInstance().addByCustomer(repairToAdd);
            }else if (currentUserClassName.equals("AdminUser")){
                RepairService.getInstance().addByAdminUser(repairToAdd);
            }else {
                message.put("message","无访问权限");
            }
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject message=new JSONObject();
        //获取当前会话对象
        HttpSession session=request.getSession(false);
        String currentUserClassName=session.getAttribute("currentUser").getClass().getName();
        try {
            if (currentUserClassName.equals("Customer")) {
                String customerId_str=request.getParameter("customerid");
                int customer_id=Integer.parseInt(customerId_str);
                Set<Repair> repairs=RepairService.getInstance().findByCustomer(customer_id);
                String repairs_json = JSON.toJSONString(repairs, SerializerFeature.DisableCircularReferenceDetect);
                //响应teachers_json到前端
                response.getWriter().println(repairs_json);
            }else if(currentUserClassName.equals("AdminUser")) {
                String roomNo_str=request.getParameter("roomno");
                Set<Repair> repairs=null;
                if (roomNo_str!=null) {
                    repairs = RepairService.getInstance().findByRoomNo(roomNo_str);
                }else {
                    repairs = RepairService.getInstance().findByRepairAdmin();
                }
                String repairs_json = JSON.toJSONString(repairs, SerializerFeature.DisableCircularReferenceDetect);
                //响应teachers_json到前端
                response.getWriter().println(repairs_json);
            }else {
                message.put("message","无访问权限");
            }
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加Teacher对象
        try {
            if (RepairService.getInstance().deleteByAdminUser(id)){
                message.put("message", "删除成功");
            }else {
                message.put("message", "数据库操作异常");
            }
        }catch(Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String repair_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Repair对象
        Repair repairToUpdate = JSON.parseObject(repair_json, Repair.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改Teacher对象对应的记录
        try {
            if(repairToUpdate.getRoom()!=null){
                RepairService.getInstance().update(repairToUpdate);
            }else{
                RepairService.getInstance().commit(repairToUpdate);
            }message.put("message", "修改成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }
}
