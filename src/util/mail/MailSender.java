package util.mail;

public class MailSender {
    private static String vcode=null;
    public static void sendMail(String mailBox) throws Exception {
        MailOperation operation = new MailOperation();
        String user = "m13625427730@163.com";
        String password = "xjl25941";
        String host = "smtp.163.com";
        String from = "m13625427730@163.com";
        String to = mailBox;// 收件人
        String subject = "Hello";
        //邮箱内容
        StringBuffer sb = new StringBuffer();
        vcode = " ";
        for (int i = 1; i <= 6; i++) {
            vcode += (int) (Math.random() * 10);
        }
        sb.append("<!DOCTYPE>" + "<div bgcolor='#f1fcfa'   style='border:1px solid #d9f4ee; font-size:14px; line-height:22px; color:#005aa0;padding-left:1px;padding-top:5px;   padding-bottom:5px;'><span style='font-weight:bold;'>温馨提示：</span>"
                + "<div style='width:950px;font-family:arial;'>欢迎使用解某某的软件，您的验证码为：<br/><h2 style='color:green'>" + vcode + "</h2><br/>本邮件由系统自动发出，请勿回复。<br/>感谢您的使用。<br/>济南解某某电子商务有限公司</div>"
                + "</div>");
        String res = operation.sendMail(user, password, host, from, to,
                subject, sb.toString());
        System.out.println(res);
    }
    public static String getVcode(){
        return vcode;
    }
}
