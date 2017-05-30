import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Set;

/**
 * http://camel.apache.org/pojo-producing.html
 */
public class JmxRoute {

	private static Logger log = LoggerFactory.getLogger(JmxRoute.class.getName());


	private static String getRouteState(String psRoute, MBeanServerConnection connection) {
		return getState("routes", psRoute, connection);
	}

	private static void startRoute(String psRoute, MBeanServerConnection connection) throws Exception {
		ObjectName objectName = getCamelObject("routes", psRoute, connection);
		Object[] params = {};
		String[] sig = {};
		connection.invoke(objectName, "start", params, sig);
	}


	private static void stopRoute(String psRoute, MBeanServerConnection connection) throws Exception {
		ObjectName objectName = getCamelObject("routes", psRoute, connection);
		Object[] params = {};
		String[] sig = {};
		connection.invoke(objectName, "stop", params, sig);
	}

	private static String getState(String psType, String psName, MBeanServerConnection connection) {
		String lsRes = null;
		ObjectName loContextMBean = getCamelObject(psType, psName, connection);
		if (loContextMBean != null) {
			try {
				lsRes = (String) connection.getAttribute(loContextMBean, "State");
			} catch (AttributeNotFoundException | InstanceNotFoundException | MBeanException | ReflectionException
					| IOException e) {
				log.error("Can't get state for " + psType + " " + psName, e);
			}
		}
		return lsRes;
	}


	private static ObjectName getCamelObject(String psType, String psName, MBeanServerConnection connection) {
		ObjectName loRes = null;
		ObjectName loQuery;
		try {
			loQuery = new ObjectName("org.apache.camel:type=" + psType + ",name=\"" + psName + "\",*");
			Set<ObjectName> loSet = connection.queryNames(loQuery, null);
			if (loSet.size() > 0) {
				loRes = loSet.iterator().next();
			} else {
				log.error("No {} found for name {}", psType, psName);
			}
		} catch (MalformedObjectNameException | IOException e) {
			log.error("Can't get MBean type=" + psType + ", name= " + psName, e);
		}
		return loRes;
	}


	/**
	 * Main method
	 *
	 * @param args arguments
	 */
	public static void main(String[] args) throws Exception {

		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

		CamelContext context = new DefaultCamelContext();


		context.addRoutes(new RouteBuilder() {
			public void configure() {
				from("direct:in").routeId("testRouteId").setProperty("myProperty", constant("totoLiToto")).log(LoggingLevel.INFO, "${exchangeProperty.myProperty}");
			}
		});


		context.start();

		String routeState = getRouteState("testRouteId", mBeanServer);
		stopRoute("testRouteId", mBeanServer);
		routeState = getRouteState("testRouteId", mBeanServer);
		startRoute("testRouteId", mBeanServer);
		routeState = getRouteState("testRouteId", mBeanServer);


		ProducerTemplate template = context.createProducerTemplate();


		template.sendBody("direct:in", "test message");
	}
}
