1. Tomcat 5.5+ Deploy: (http://qixiaopeng.javaeye.com/blog/410380)
1) Host的appBase一定要配置为WEB-INF的父目录, 如果配置为E:\\Projects\\Synventive则servlet会初始化2次, 
        即使定义了autoDeploy=false也会如此.
2) 部署方式有以下2种, 在Host中直接定义在Context
      <Host name="localhost" appBase="E:\\Projects\\Synventive\\www"
            unpackWARs="false" autoDeploy="false" 
            xmlValidation="false" xmlNamespaceAware="false">

        <!-- SingleSignOn valve, share authentication between web applications
             Documentation at: /docs/config/valve.html -->
        <!--
        <Valve className="org.apache.catalina.authenticator.SingleSignOn" />
        -->

        <!-- Access log processes all example.
             Documentation at: /docs/config/valve.html -->
        <!--
        <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"  
               prefix="localhost_access_log." suffix=".txt" pattern="common" resolveHosts="false"/>
        -->
				<!--Context path="" docBase="" reloadable="true" crossContext="true">
				  <Resource name="jdbc/AppPool" auth="Container" type="javax.sql.DataSource"
				            driverClassName="net.sourceforge.jtds.jdbc.Driver"
				            username="warehouse_query" password="synventive"
				            url="jdbc:jtds:sqlserver://localhost:1433/IERP60Std"
				            maxActive="10" maxIdle="30" maxWait="5000"/>
				</Context-->
      </Host>
   或使用conf/servlet.xml + conf/<Engine Name>/<Host Name>/ROOT.xml的方式(ROOT.xml):
<Context path="" docBase="" reloadable="true" crossContext="true">
  <Resource name="jdbc/AppPool" auth="Container" type="javax.sql.DataSource"
            driverClassName="net.sourceforge.jtds.jdbc.Driver"
            username="warehouse_query" password="synventive"
            url="jdbc:jtds:sqlserver://localhost:1433/IERP60Std"
            maxActive="10" maxIdle="30" maxWait="5000"/>
</Context>
3) TOMCAT 5.5+启动的数据源有问题: 
A. 如果不使用Spring bean factory post processor(BeanFactoryAppProcessor), 则程序与Spring Flex有冲突.
B. 不加载Spring Flex, 应用可以启动, 但通过javax.naming.InitialContext无法找到Data Source:
Context envContext  = (Context)initContext.lookup("java:/comp/env");
抛javax.naming.NameNotFoundException异常, 但是通过Spring Bean可以取到Data Source!!!

4) SOLUTION:
A. 通过程序初始化数据源(DataSource Chain): JNDI->Proxool->XA Pool->DBCP->Spring Driver Manager Pool
B. 通过程序绑定数据源(JNDI Chain): SUN JDNI->Apache Naming
