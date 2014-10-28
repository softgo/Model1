
package com.bruce.gogo.system.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.bruce.gogo.Constants;
import com.bruce.gogo.action.BaseAction;
import com.bruce.gogo.system.service.ILogin;
import com.bruce.gogo.utils.SessionInfo;
import com.bruce.gogo.utils.TimeTools;

public class LoginAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5968877497648515582L;
	private String error;
	private String username; 
	private ILogin loginService;	
	
	public ILogin getLoginService() {
		return loginService;
	}
	public void setLoginService(ILogin loginService) {
		this.loginService = loginService;
	}
	/**
	 * 显示登录页
	 * @return
	 */
	public String showLogin(){
		return "View";
	}
	/**
	 * 登录
	 * @return
	 */
	public String login(){
		boolean result = loginService.login(request, response);
		error = (String)request.getAttribute("error");
		username = (String)request.getAttribute("username");
		if(result){
			this.setSession(Constants.sessioninfo, request.getAttribute(Constants.sessioninfo));
			return "Login";	
		}
		else{
			return "Logout";
		}
	}
	/**
	 * 注销
	 * @return
	 */
	public String logout(){
		loginService.logout(request);
		return "Logout";
	}
	/**
	 * 欢迎页面
	 * @return
	 */
	public String welcome(){
		SessionInfo sessionInfo = (SessionInfo)this.getSession(Constants.sessioninfo);
		if(sessionInfo == null){
			return "Logout";	
		}
        //取得欢迎页面显示的信息
		String loginName = sessionInfo.getLoginName();
		String loginTime = TimeTools.dateToString(null, "yyyy-mm-dd hh:mm:ss");
		String ip = request.getRemoteAddr();
		request.setAttribute("loginName", loginName);
		request.setAttribute("loginTime", loginTime);
		request.setAttribute("ip", ip);
		return "Welcome";
	}
	/**
	 * 显示验证码
	 * @return
	 */
	public void exec() throws Exception {

		   HttpServletResponse response = ServletActionContext.getResponse();
		   int codeLength = 4;// 验证码长度
		   int mixTimes = 1;// 模糊程度参数
		   Color bgColor = getRandColor(200, 250);// 背景颜色
		   Color bfColor = new Color(0, 0, 0);// 字体颜色
		   boolean ifRandomColor = true;// 单个字符是否颜色随机
		   boolean ifMixColor = false;// 模糊线是否颜色随机

		   // 设置页面不缓存
		   response.setHeader("Pragma", "No-cache");
		   response.setHeader("Cache-Control", "no-cache");
		   response.setDateHeader("Expires", 0);
		   // 在内存中创建图象
		   int width = 13 * codeLength + 6, height = 20;
		   BufferedImage image = new BufferedImage(width, height,
		     BufferedImage.TYPE_INT_RGB);
		   // 获取图形上下文
		   Graphics g = image.getGraphics();
		   // 设定背景色
		   g.setColor(bgColor);
		   g.fillRect(0, 0, width, height);
		   // 设定字体
		   g.setFont(new Font("Arail", Font.BOLD, 18));
		   // 画边框
		   g.setColor(new Color(33, 66, 99));
		   g.drawRect(0, 0, width - 1, height - 1);
		   // 随机产生干扰线，使图象中的认证码不易被其它程序探测到
		   g.setColor(getRandColor(160, 200));
		   for (int i = 0; i < mixTimes * codeLength / 10; i++) {
		    if (ifMixColor) {
		     g.setColor(getRandColor(160, 200));
		    }
		    int x = random.nextInt(width);
		    int y = random.nextInt(height);
		    int xl = random.nextInt(12);
		    int yl = random.nextInt(12);
		    g.drawLine(x, y, x + xl, y + yl);
		   }
		   // 取随机产生的认证码(4位数字)
		   String sRand = "";
		   for (int i = 0; i < codeLength; i++) {
		    String rand = String.valueOf(random.nextInt(10));
		    sRand += rand;
		    // 将认证码显示到图象中
		    if (ifRandomColor)
		     g.setColor(getRandColor(20, 110, 0));
		    else
		     g.setColor(bfColor);
		    // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
		    g.drawString(rand, 13 * i + 6, 16);
		   }
		   // 将认证码存入SESSION
		   // 图象生效
		   ServletActionContext.getRequest().getSession().setAttribute("rand",
		     sRand);
		   g.dispose();
		   // 输出图象到页面
		   ImageIO.write(image, "PNG", response.getOutputStream());

		}

		private static Random random = new Random();

		private Color getRandColor(int fc, int bc) {
		   return getRandColor(fc, bc, fc);
		}

		private Color getRandColor(int fc, int bc, int interval) {
		   if (fc > 255) {
		    fc = 255;
		   }
		   if (bc > 255) {
		    bc = 255;
		   }
		   int r = fc + random.nextInt(bc - interval);
		   int g = fc + random.nextInt(bc - interval);
		   int b = fc + random.nextInt(bc - interval);
		   return new Color(r, g, b);
		}
		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		
}
