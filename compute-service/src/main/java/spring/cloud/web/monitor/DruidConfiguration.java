package spring.cloud.web.monitor;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DruidConfiguration {

	@Bean
	public ServletRegistrationBean DruidStatViewServlet() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
				new StatViewServlet(), "/druid2/*");

		// 添加初始化参数：initParams
		servletRegistrationBean.addInitParameter("allow", "127.0.0.1"); // 白名单：
		servletRegistrationBean.addInitParameter("deny", "192.168.1.73"); // IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
		servletRegistrationBean.addInitParameter("loginUsername", "admin2"); // 登录查看信息的账号密码.
		servletRegistrationBean.addInitParameter("loginPassword", "123456");
		servletRegistrationBean.addInitParameter("resetEnable", "false"); // 是否能够重置数据.
		
		return servletRegistrationBean;
	}

	/**
	 * 
	 * 注册一个：filterRegistrationBean
	 * 
	 * @return
	 */

	@Bean
	public FilterRegistrationBean druidStatFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
				new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*"); // 添加过滤规则.
		filterRegistrationBean.addInitParameter("exclusions",
				"*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*"); // 添加不需要忽略的格式信息.

		return filterRegistrationBean;
	}
}
