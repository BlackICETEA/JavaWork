package cn.gyyx.testLcg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.gyyx.testLcg.BLL.UserBLL;
import cn.gyyx.testLcg.model.User;
import cn.gyyx.testLcg.model.UserException;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private List<User> users=new ArrayList<User>();
	static MemcachedClient client;
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	UserBLL uBLL;
	
	//初始化BLL Memcach单例模式
	public HomeController(){
		uBLL=new UserBLL();
		try {
			if(client==null) 
				client=new XMemcachedClient("192.168.6.195",10001);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//查找
	@RequestMapping(value ="/users", method = RequestMethod.GET)
	public String users(@CookieValue(value = "uuid", defaultValue = "0") String uuid,Model model) {
System.out.println("uuid================="+uuid);
		String name="";
		try {
			name=client.get("\""+uuid+"\"");
System.out.println("memcachen:name = "+name);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		if(null==name||"".equals(name)){
System.out.println("账号不存在");
			return "redirect:/users/login";
		}
		users=uBLL.select();
		model.addAttribute("users",users);
		model.addAttribute("uuid", uuid);
		return "user/users";
	}
	
	//添加
	@RequestMapping(value="/users/add",method=RequestMethod.GET)
	public String add(){ 
		return "user/add";
	}
	//添加
	@RequestMapping(value="/users/add",method=RequestMethod.POST)
	public String add(User user){
		System.out.println(user);
		uBLL.insert(user);
		return "redirect:/users";
	}
	
	
	//删除
	@RequestMapping(value="/users/delete",method=RequestMethod.GET)
	public String delete(int id){
		uBLL.delete(id);
		return "redirect:/users";
	}
	
	
	//修改
	@RequestMapping(value="/users/update",method=RequestMethod.GET)
	public String update(int id,Model model){
		model.addAttribute("id",id);
		return "/user/update";
	}
	//修改
	@RequestMapping(value="/users/update",method=RequestMethod.POST)
	public String update(@CookieValue(value = "uuid", defaultValue = "0") String uuid,User user){
System.out.println(user);
		uBLL.update(user);
		try {
			client.set(uuid,3600,user.name);
System.out.println(uuid);			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "redirect:/users";
	}

	
	//登陆
	@RequestMapping(value="users/login",method=RequestMethod.POST)
	public String login(HttpServletResponse response,String name, String pwd){
		User u;
		
		//验证登陆
		try {
			u=uBLL.select(name);
			System.out.println(u);
			if(! (u.pwd.equals(pwd))){
				throw new UserException("密码不正确");
			}
		} catch (Exception e) {
			throw new UserException("用户信息错误");
		}

		//获得uuid
		String uuid=uBLL.readContentFromGet("http://uuid.gyyx.cn/");
System.out.println("uuid = "+uuid);
		
		//{uuid} 写入Cookie
		Cookie co=new Cookie("uuid",uuid);
		co.setMaxAge(1000);
		response.addCookie(co);
System.out.println("cookie 写入为： "+co.getValue());
		
		//{uuid:username} 写入memcached
		try {
			client.set(uuid,3600,u.name);
			System.out.println("memcacha write complited");
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		//重定向：用户列表
		return "redirect:/users";
	}
	@RequestMapping(value="users/login",method=RequestMethod.GET)
	public String login(){
System.out.println("return :/login");
		return "/login";
	}
	
}
