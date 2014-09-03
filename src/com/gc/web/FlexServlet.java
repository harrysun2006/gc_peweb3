package com.gc.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gc.Constants;
import com.gc.exception.CommonRuntimeException;
import com.gc.util.CommonUtil;
import com.gc.util.FlexUtil;

public class FlexServlet extends HttpServlet {
	
	private final static Log _log = LogFactory.getLog(FlexServlet.class);

	private final static String SERVICE_FIELD = "service";
	private final static String METHOD_FIELD = "method";
	private final static String PARAMS_FIELD = "params";
	private final static String UNDEFINED = "undefined";

	public FlexServlet() {
		super();
	}

	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();
		String path = context.getRealPath(Constants.DEFAULT_UPLOAD_PATH) + File.separatorChar;
		Constants.SETTINGS.put(Constants.PROP_UPLOAD_PATH, path);
		File dir = new File(path);
		if (!dir.exists()) dir.mkdirs();
		Constants.SETTINGS.put(Constants.PROP_TEMPLATE_PATH, context.getRealPath(Constants.DEFAULT_TEMPLATE_PATH) + File.separatorChar);
		String clog = "";
		try {
			byte[] b = CommonUtil.readFile(new File(context.getRealPath("CHANGES.HTML")));
			clog = new String(b, "GBK");
		} catch (IOException e1) {}
		Constants.SETTINGS.put("change.log", clog);
	}

	public void destroy() {
		super.destroy();
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		String s = null;
		String m = null;
		String p = null;
		List<File> files = new ArrayList<File>();
 		try {
 			if (isMultipart) {
 				// 定义文件的上传路径
 				String uploadPath = (String) Constants.SETTINGS.get(Constants.PROP_UPLOAD_PATH);
 				// 限制文件的上传大小
 				int maxPostSize = 4 * 1024 * 1024;
 				
 				// 保存文件到服务器中
 				DiskFileItemFactory factory = new DiskFileItemFactory();
 				// 设置缓冲区大小
 				factory.setSizeThreshold(maxPostSize);
 				factory.setRepository(new File(uploadPath));

 				ServletFileUpload upload = new ServletFileUpload(factory);
 				upload.setHeaderEncoding("utf-8");
 				upload.setSizeMax(maxPostSize);
 				List<FileItem> fileItems = upload.parseRequest(request);
 				Iterator<FileItem> it = fileItems.iterator();
 				FileItem item;
 				File uf;
 				while (it.hasNext()) {
 					item = it.next();
 					if (item.isFormField()) {
 						if (SERVICE_FIELD.equals(item.getFieldName())) s = item.getString();
 						else if (METHOD_FIELD.equals(item.getFieldName())) m = item.getString();
 						else if (PARAMS_FIELD.equals(item.getFieldName())) p = item.getString();
 					} else {
 						uf = new File(uploadPath + "/" + item.getName());
 						item.write(uf);
 						files.add(uf);
 					}
 				}
 			} else {
 				s = request.getParameter(SERVICE_FIELD);
 				m = request.getParameter(METHOD_FIELD);
 				p = request.getParameter(PARAMS_FIELD);
 			}
 			List params = new ArrayList();
 			params.addAll(getFields(p));
 			if (files.size() > 0) params.add(files.toArray(new File[]{}));
 			params.add(response);
 			FlexUtil.invoke(s, m, params);
		} catch (Throwable t) {
			if (s == null) s = UNDEFINED;
			if (m == null) m = UNDEFINED;
			String err = CommonUtil.getString("error.call.method", new Object[]{s, m});
			_log.error(err, t);
			if (files.size() > 0) {// upload, flex using UPLOAD_COMPLETE_DATA event to get error.
				Exception e = new CommonRuntimeException(CommonUtil.getString("error.call.method", new Object[]{s, m}), t);
				e = FlexUtil.translate(e);
				response.setContentType("text/plain");
				response.getOutputStream().print(CommonUtil.base64Encode(FlexUtil.writeObject(e)));
				response.getOutputStream().flush();
			} else {// download, flex using IO_ERROR event to get error, now can not get detailed message.
				response.sendError(500, err);
			}
			throw new CommonRuntimeException(CommonUtil.getString("error.call.method", new Object[]{s, m}), t);
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private List getFields(String p) throws Exception {
		List fields = new ArrayList();
		if (p == null) return fields;
		byte[] b = CommonUtil.base64Decode(p);
		Object obj = FlexUtil.readObject(b);
		if (obj instanceof Object[]) {
			Object[] args = (Object[]) obj;
			for (int i = 0; i < args.length; i++) fields.add(args[i]);
		} else {
			fields.add(obj);
		}
		return fields;
	}
}
