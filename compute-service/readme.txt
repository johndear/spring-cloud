1、启动项目，本地运行，LocalApplication中的main方法，注释掉pom.xml中
				<exclusion>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-tomcat</artifactId>
				</exclusion>

2、远程部署，去掉注释，使用mvn package打包，部署丢在tomcat/webapps下