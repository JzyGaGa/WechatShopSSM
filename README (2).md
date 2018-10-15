# WechatShopSSM
# 微信商铺 #
## mybatis-config.xml ##
    <!-- 配置全局属性 -->
	<settings>
		<!-- 使用jdbc的getGeneratedKeys获取数据库自增主键值 -->
		<setting name="useGeneratedKeys" value="true" />

		<!-- 使用列别名替换列名 默认:true -->
		<setting name="useColumnLabel" value="true" />

		<!-- 开启驼峰命名转换:Table{create_time} -> Entity{createTime} -->
		<setting name="mapUnderscoreToCamelCase" value="true" />
		<!-- 打印查询语句 -->
		<setting name="logImpl" value="STDOUT_LOGGING" />
	</settings>

### springMvc DispatcherServlet ###
    


	springMvc DispatcherServlet
	Spring IOC和AOP
	MyBatis:ORM

		</dependency>

## 遇到问题待解决 ##

    `	<!-- 文件上传解析器 -->
	<bean id="multipartResolver"
		class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		<property name="defaultEncoding" value="utf-8"></property>
		<property name="maxUploadSize" value="20971520"></property><!-- 最大上传文件大小 -->
		<property name="maxInMemorySize" value="20971520"></property>
	</bean>

`

    <dependency>
			<groupId>commons-fileupload</groupId>
			<artifactId>commons-fileupload</artifactId>
			<version>1.3.2</version>
		</dependency>