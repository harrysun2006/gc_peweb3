package com.gc.spring;

import java.sql.Connection;
import java.util.Hashtable;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.mock.web.MockServletContext;

import com.gc.Constants;
import com.gc.util.CommonUtil;
import com.gc.util.JNDIUtil;
import com.gc.util.PropsUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class BeanFactoryGCProcessor implements BeanFactoryPostProcessor {

	private static Log _log = LogFactory.getLog(BeanFactoryGCProcessor.class);
	private static ConfigurableListableBeanFactory beanFactory;
	private final static String XAPOOL_DATASOURCE_CLASSNAME = "org.enhydra.jdbc.pool.StandardXAPoolDataSource";
	private final static String FLEX_MESSAGE_BROKER_BEANNAME = "_messageBroker";

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
			throws BeansException {
		try {
			BeanFactoryGCProcessor.beanFactory = beanFactory;
			hookDataSources1();
			hookServletContext();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * 检查数据连接池是否存在, 如果不存在则使用Apache Naming包建数据连接池.
	 */
	protected void hookDataSources1() {
		String[] dataSources = PropsUtil.getArray(Constants.PROP_SPRING_HIBERNATE_DATA_SOURCES);
		DataSource ds;
		Connection conn = null;
		String dsName, fregex, kregex;
		Hashtable props;
		for (int i = 0; i < dataSources.length; i++) {
			dsName = dataSources[i].trim();
			try {
				ds = (DataSource) JNDIUtil.lookup(dsName);
				conn = ds.getConnection();
			} catch (Exception e) {
				fregex = dsName + "\\..*";
				kregex = dsName + "\\.(.*)";
				props = CommonUtil.getProperties(PropsUtil.getProperties(), fregex, kregex);
				JNDIUtil.addResource(props);
			} finally {
				if (conn != null) try {conn.close();} catch (Exception exp) {}
			}
		}
	}

	/**
	 * 检查数据连接池是否存在, 如果不存在则使用XA Pool包建数据连接池.
	 */
	protected void hookDataSources2() {
		String[] dataSources = PropsUtil.getArray(Constants.PROP_SPRING_HIBERNATE_DATA_SOURCES);
		DataSource ds;
		Connection conn = null;
		String dsName, fregex, kregex;
		Hashtable props;
		
		// StandardXAPoolDataSource xapool = new StandardXAPoolDataSource();
		for (int i = 0; i < dataSources.length; i++) {
			dsName = dataSources[i].trim();
			try {
				ds = (DataSource) JNDIUtil.lookup(dsName);
				conn = ds.getConnection();
			} catch (Exception e) {
				fregex = dsName + "\\..*";
				kregex = dsName + "\\.(.*)";
				props = CommonUtil.getProperties(PropsUtil.getProperties(), fregex, kregex);
				createXAPool(dsName, props);
			} finally {
				if (conn != null) try {conn.close();} catch (Exception exp) {}
			}
		}
	}

	private void createXAPool(String name, Hashtable props) {
		AbstractBeanDefinition bd1, bd2;
		MutablePropertyValues pvs;
		try {
			bd1 = (AbstractBeanDefinition) beanFactory.getBeanDefinition(name + "XA");
			pvs = bd1.getPropertyValues();
			pvs.addPropertyValue(new PropertyValue("driverName", props.get("driverClassName")));
			pvs.addPropertyValue(new PropertyValue("url", props.get("url")));
			bd1.setPropertyValues(pvs);
			bd2 = (AbstractBeanDefinition) beanFactory.getBeanDefinition(name);
			bd2.setBeanClass(Class.forName(XAPOOL_DATASOURCE_CLASSNAME));
			bd2.setDestroyMethodName("shutdown");
			_log.info("bean " + name + " changed class to: " + XAPOOL_DATASOURCE_CLASSNAME);
			pvs = new MutablePropertyValues();
			pvs.addPropertyValue(new PropertyValue("dataSource", bd1));
			pvs.addPropertyValue(new PropertyValue("user", props.get("username")));
			pvs.addPropertyValue(new PropertyValue("password", props.get("password")));
			bd2.setPropertyValues(pvs);
		} catch(Exception e) {
			_log.error("bean " + name + " override failed!", e);
		}
	}

	/**
	 * 检查数据连接池是否存在, 如果不存在则使用C3P0包建数据连接池.
	 */
	protected void hookDataSources3() {
		String[] dataSources = PropsUtil.getArray(Constants.PROP_SPRING_HIBERNATE_DATA_SOURCES);
		DataSource ds;
		Connection conn = null;
		String dsName, fregex, kregex;
		Hashtable props;
		for (int i = 0; i < dataSources.length; i++) {
			dsName = dataSources[i].trim();
			try {
				ds = (ComboPooledDataSource) JNDIUtil.lookup(dsName);
				conn = ds.getConnection();
			} catch (Exception e) {
				fregex = dsName + "\\..*";
				kregex = dsName + "\\.(.*)";
				props = CommonUtil.getProperties(PropsUtil.getProperties(), fregex, kregex);
				ds = createC3P0Pool(props);
			} finally {
				if (conn != null) try {conn.close();} catch (Exception exp) {}
			}
		}
	}

	private DataSource createC3P0Pool(Hashtable props) {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass(PropsUtil.get("driverClassName"));
		} catch (Exception e) {
			
		}
		return ds;
	}

	private void hookServletContext() {
		try {
			AbstractBeanDefinition bd;
			MutablePropertyValues pvs;
			try {
				bd = (AbstractBeanDefinition) beanFactory.getBeanDefinition(FLEX_MESSAGE_BROKER_BEANNAME);
				pvs = bd.getPropertyValues();
				if (!pvs.contains("servletContext")) {
					MockServletContext sc = new MockServletContext();
					pvs.addPropertyValue(new PropertyValue("servletContext", sc));
				}
				bd.setPropertyValues(pvs);
			} catch(Exception e) {
			}
		} catch(Exception e) {
			_log.error("hook beans failed!", e);
		}
	}
}
