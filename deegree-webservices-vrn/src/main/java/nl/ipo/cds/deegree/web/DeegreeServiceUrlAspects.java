/**
 * 
 */
package nl.ipo.cds.deegree.web;

import static org.deegree.commons.xml.CommonNamespaces.XLINK_PREFIX;
import static org.deegree.commons.xml.CommonNamespaces.XLNNS;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLStreamWriter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.deegree.commons.utils.Pair;
import org.deegree.commons.xml.XMLAdapter;
import org.deegree.services.controller.OGCFrontController;
import org.deegree.services.wms.controller.capabilities.WmsCapabilities111SpatialMetadataWriter;
import org.deegree.style.se.unevaluated.Style;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Instrument various Deegree classes to return GetCapabilities including vendor Parameters. Following methods are
 * instrumented and forked from deegree 3.4-pre16.:
 * <ul>
 * <li>{@link OGCFrontController#getHttpGetURL()} method to return get url with vendor parameter.</>
 * <li>{@link OGCFrontController#getHttpPostURL()} method to return post url with vendor parameter.</>
 * </ul>
 * 
 * @author reinoldp
 * 
 */
@Aspect
public class DeegreeServiceUrlAspects {

	private static final Logger logger = LoggerFactory.getLogger(DeegreeServiceUrlAspects.class);

	private List<String> vendorParameters = new ArrayList<>();

	private String host;

	private int port;

	private String protocol;

	@Value("${deegree.webservices.vrn.host:}")
	public void setHost(String host) {
		logger.info("Using host: {}", host);
		this.host = host;
	}

	@Value("${deegree.webservices.vrn.port:-1}")
	public void setPort(int port) {
		logger.info("Using port: {}", port);
		this.port = port;
	}

	@Value("${deegree.webservices.vrn.protocol:https}")
	public void setProtocol(String protocol) {
		logger.info("Using default service protocol: {}", protocol);
		this.protocol = protocol;
	}

	@Value("#{'${deegree.webservices.vrn.vendorparameters:tag}'.split(',')}")
	public void setVendorParameters(List<String> vendorParameters) {
		logger.info("Using vendor parameters: {}", vendorParameters);
		this.vendorParameters = vendorParameters;
	}

	@Around("execution(public static String OGCFrontController.getHttpGetURL())")
	public Object getHttpGetURL(ProceedingJoinPoint invocation) throws Throwable {
		return getServiceUrl();
	}

	@Around("execution(public static String OGCFrontController.getHttpPostURL())")
	public Object getHttpPostURL(ProceedingJoinPoint invocation) throws Throwable {
		return getServiceUrl();
	}

	// Capabilities111XMLAdapter.writeStyle
	/*
	 * public void writeStyle( XMLStreamWriter writer, String name, String title, Pair<Integer, Integer> legendSize,
	 * String layerName, Style style )
	 */
	@Around("execution(* org.deegree.services.wms.controller.capabilities.Capabilities111XMLAdapter.writeStyle(..))")
	public void writeStyle(ProceedingJoinPoint invocation) throws Throwable {
		XMLStreamWriter writer = (XMLStreamWriter) invocation.getArgs()[0];
		String name = (String) invocation.getArgs()[1];
		String title = (String) invocation.getArgs()[2];
		Pair<Integer, Integer> legendSize = (Pair<Integer, Integer>) invocation.getArgs()[3];
		String layerName = (String) invocation.getArgs()[4];
		Style style = (Style) invocation.getArgs()[5];

		writer.writeStartElement("Style");
		XMLAdapter.writeElement(writer, "Name", name);
		XMLAdapter.writeElement(writer, "Title", title);
		if (legendSize.first > 0 && legendSize.second > 0) {
			writer.writeStartElement("LegendURL");
			writer.writeAttribute("width", "" + legendSize.first);
			writer.writeAttribute("height", "" + legendSize.second);
			XMLAdapter.writeElement(writer, "Format", "image/png");
			writer.writeStartElement("OnlineResource");
			writer.writeNamespace(XLINK_PREFIX, XLNNS);
			writer.writeAttribute(XLNNS, "type", "simple");
			if (style.getLegendURL() == null || style.prefersGetLegendGraphicUrl()) {
				String styleName = style.getName() == null ? "" : ("&style=" + style.getName());
				writer.writeAttribute(XLNNS, "href", getServiceUrl()
						+ "request=GetLegendGraphic&version=1.1.1&service=WMS&layer=" + layerName + styleName
						+ "&format=image/png");
			} else {
				writer.writeAttribute(XLNNS, "href", style.getLegendURL().toExternalForm());
			}
			writer.writeEndElement();
			writer.writeEndElement();
		}
		writer.writeEndElement();
	}

	/**
	 * Depending presence of vendor parameter in service url, append '?' or '&'
	 * <p/>
	 * Original declaration: void writeDCP( XMLStreamWriter writer, boolean get, boolean post ) See
	 * {@link WmsCapabilities111SpatialMetadataWriter#writeDCP}
	 * 
	 * @param invocation
	 * @throws Throwable
	 */
	@Around("execution(* org.deegree.services.wms.controller.capabilities.WmsCapabilities111MetadataWriter.writeDCP(..))")
	public void writeDCP(ProceedingJoinPoint invocation) throws Throwable {
		XMLStreamWriter writer = (XMLStreamWriter) invocation.getArgs()[0];
		boolean get = (boolean) invocation.getArgs()[1];
		boolean post = (boolean) invocation.getArgs()[2];
		// code follows:
		writer.writeStartElement("DCPType");
		writer.writeStartElement("HTTP");
		if (get) {
			writer.writeStartElement("Get");
			writer.writeStartElement("OnlineResource");
			writer.writeNamespace(XLINK_PREFIX, XLNNS);
			writer.writeAttribute(XLNNS, "type", "simple");
			writer.writeAttribute(XLNNS, "href", getServiceUrl());
			writer.writeEndElement();
			writer.writeEndElement();
		}
		if (post) {
			writer.writeStartElement("Post");
			writer.writeStartElement("OnlineResource");
			writer.writeNamespace(XLINK_PREFIX, XLNNS);
			writer.writeAttribute(XLNNS, "type", "simple");
			writer.writeAttribute(XLNNS, "href", getServiceUrl());
			writer.writeEndElement();
			writer.writeEndElement();
		}
		writer.writeEndElement();
		writer.writeEndElement();
	}

	private String getServiceUrl() throws MalformedURLException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		String requestUrl = getRequestUrl(request);
		StringBuffer sb = new StringBuffer(requestUrl).append("?");
		for (String vendorParameter : vendorParameters) {
			String value = request.getParameter(vendorParameter);
			if (value != null) {
				sb.append(vendorParameter).append("=").append(value).append("&");
			}
		}
		String url = sb.toString();
		logger.debug("returning modified HttpGet request url including vendor parameters: {}", url);
		return url;
	}

	/**
	 * Create request url. Maybe overrule by baseUrl and x-forwarded headers. See
	 * http://en.wikipedia.org/wiki/List_of_HTTP_header_fields
	 * 
	 * @param request
	 * @return
	 * @throws MalformedURLException
	 */
	private String getRequestUrl(HttpServletRequest request) throws MalformedURLException {
		URL url = new URL(request.getRequestURL().toString());
		String requestProtocol = url.getProtocol();
		String requestHost = url.getHost();
		int requestPort = url.getPort();
		String requestPath = url.getPath();
		// overrule protocol if necessary
		if (protocol != null && protocol.length() >= 1) {
			requestProtocol = protocol;
		} else if (request.getHeader("X-Forwarded-Proto") != null) {
			requestProtocol = request.getHeader("X-Forwarded-Proto");
		}
		// overrule host if necessary
		if (host != null && host.length() >= 1) {
			requestHost = host;
		} else if (request.getHeader("X-Forwarded-Host") != null) {
			requestHost = request.getHeader("X-Forwarded-Host");
		}
		// overrule port if necessary
		if (port >= 1) {
			requestPort = port;
		}
		// construct url
		return new URL(requestProtocol, requestHost, requestPort, requestPath).toString();
	}
}
