package spring.cloud.web.monitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidConfiguration {
	
	@Value("${druid.allow.ip}")  
    private String allowIp; 
	
	@Value("${druid.deny.ip}")  
    private String denyIp; 
	
	@Value("${druid.login.username}")  
    private String username; 
	
	@Value("${druid.login.password}")  
    private String password; 
	
	@Value("${druid.resetEnable}")  
    private String resetEnable; 

	/**
	 * 
	 * 注册一个：servlet
	 * @return
	 */
	@Bean
	public ServletRegistrationBean DruidStatViewServlet() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
				new StatViewServlet(), "/druid/*");

		// 添加初始化参数：initParams
		servletRegistrationBean.addInitParameter("allow", allowIp); // 白名单：
		servletRegistrationBean.addInitParameter("deny", denyIp); // IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
		servletRegistrationBean.addInitParameter("loginUsername", username); // 登录查看信息的账号密码.
		servletRegistrationBean.addInitParameter("loginPassword", password);
		servletRegistrationBean.addInitParameter("resetEnable", resetEnable); // 是否能够重置数据.
		
		return servletRegistrationBean;
	}

	/**
	 * 
	 * 注册一个：filter
	 * @return
	 */
	@Bean
	public FilterRegistrationBean druidStatFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
				new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*"); // 添加过滤规则.
		filterRegistrationBean.addInitParameter("exclusions",
				"*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"); // 添加不需要忽略的格式信息.

		return filterRegistrationBean;
	}
}
