package com.gc.hr.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.EmptyCell;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.context.ApplicationContext;

import com.gc.Constants;
import com.gc.common.po.Person;
import com.gc.common.po.Position;
import com.gc.common.po.PsnChange;
import com.gc.common.po.PsnOnline;
import com.gc.common.po.PsnPhoto;
import com.gc.common.po.PsnStatus;
import com.gc.common.po.SecurityLimit;
import com.gc.common.po.SecurityUser;
import com.gc.common.service.BaseServiceUtil;
import com.gc.exception.CommonRuntimeException;
import com.gc.hr.po.ChkLongPlan;
import com.gc.hr.po.HireType;
import com.gc.hr.po.HireTypePK;
import com.gc.hr.po.JobGrade;
import com.gc.hr.po.JobGradePK;
import com.gc.hr.po.JobSpec;
import com.gc.hr.po.JobSpecPK;
import com.gc.hr.po.MarryStatus;
import com.gc.hr.po.MarryStatusPK;
import com.gc.hr.po.NativePlace;
import com.gc.hr.po.NativePlacePK;
import com.gc.hr.po.People;
import com.gc.hr.po.PeoplePK;
import com.gc.hr.po.PolParty;
import com.gc.hr.po.PolPartyPK;
import com.gc.hr.po.RegBranch;
import com.gc.hr.po.RegBranchPK;
import com.gc.hr.po.RegType;
import com.gc.hr.po.RegTypePK;
import com.gc.hr.po.SalaryType;
import com.gc.hr.po.SalaryTypePK;
import com.gc.hr.po.SchDegree;
import com.gc.hr.po.SchDegreePK;
import com.gc.hr.po.SchGraduate;
import com.gc.hr.po.SchGraduatePK;
import com.gc.hr.po.Schooling;
import com.gc.hr.po.SchoolingPK;
import com.gc.hr.po.WorkType;
import com.gc.hr.po.WorkTypePK;
import com.gc.safety.po.AccInPsn;
import com.gc.safety.po.Accident;
import com.gc.util.CommonUtil;
import com.gc.util.FlexUtil;
import com.gc.util.JxlUtil;
import com.gc.util.SpringUtil;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

/**
 * HR Personal ServiceUtil类
 * @author hsun
 *
 */
public class PersonalServiceUtil {

	public static final String BEAN_NAME = "hrPersonalServiceUtil";

	private PersonalService personalService;

	public static PersonalService getPersonalService() {
		ApplicationContext ctx = SpringUtil.getContext();
		PersonalServiceUtil util = (PersonalServiceUtil) ctx.getBean(BEAN_NAME);
		PersonalService service = util.personalService;
		return service;
	}

	public void setPersonalService(PersonalService personalService) {
		this.personalService = personalService;
	}

//==================================== Person ====================================
	public static void uploadPersons(File[] files, HttpServletResponse response) {
		response.setContentType("text/plain");
		List list = new ArrayList();
		Workbook workbook = null;
		try {
			if (files == null || files.length <= 0) {
				response.getWriter().print(CommonUtil.base64Encode(FlexUtil.writeObject("ERROR")));
				response.getWriter().flush();
				return;
			}
			File f = files[0];
			String cellValueString;
			Double cellValueNumber;
			Date cellValueDate;
			workbook = Workbook.getWorkbook(f);
			if (workbook == null) {
				response.getWriter().print(CommonUtil.base64Encode(FlexUtil.writeObject("ERROR")));
				response.getWriter().flush();
				return;
			}
			Sheet[] sheets = workbook.getSheets();
			Sheet sheet;
			if (sheets != null && sheets.length > 0) {
				// 对每个工作表进行循环
				for (int i = 0; i < sheets.length; i++) {
					sheet = sheets[i];
					// 得到当前工作表的行数
					int rowNum = sheet.getRows();
					for (int j = 0; j < rowNum; j++) {
						// 得到当前行的所有单元格
						Cell[] cells = sheet.getRow(j);
						//如果当前行的列数小于第一行，就补空列
						if (cells.length < sheet.getRow(0).length) {
							Cell[] cs = new Cell[sheet.getRow(0).length];
							if (cells != null & cells.length > 0) {
								// 对每个单元格进行循环
								for (int k = 0; k < cells.length; k++) {
									cs[k]=cells[k];
								}
								for (int k = cells.length; k < cs.length; k++) {
									EmptyCell eCell = new EmptyCell(j,k);
									cs[k]=eCell;
								}
								cells=cs;
							}
						}
						
						if (cells != null & cells.length > 0) {
							// 对每个单元格进行循环
							for (int k = 0; k < cells.length; k++) {
								// 读取当前单元格的值
								if (cells[k].getType() == CellType.LABEL) {
									LabelCell labelCell = (LabelCell) cells[k];
									cellValueString = labelCell.getString();
									list.add(cellValueString);
								} else if (cells[k].getType() == CellType.NUMBER) {
									NumberCell numberCell = (NumberCell) cells[k];
									cellValueNumber = numberCell.getValue();
									list.add(cellValueNumber);
								} else if (cells[k].getType() == CellType.DATE) {
									DateCell dateCell = (DateCell) cells[k];
									cellValueDate = dateCell.getDate();
									SimpleDateFormat sdf = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
									String dateString = sdf.format(
											cellValueDate).toString();
									list.add(dateString);
								} else if (cells[k].getType() == CellType.EMPTY) {
									list.add("");
								}
							}
						}
					}
				}
			}
			response.getWriter().print(CommonUtil.base64Encode(FlexUtil.writeObject(list)));
			response.getWriter().flush();
		} catch (Throwable t) {
			try {
				response.getWriter().print(CommonUtil.base64Encode(FlexUtil.writeObject("ERROR")));
				response.getWriter().flush();
			} catch (Throwable t1) {}
			throw new CommonRuntimeException("Error", t);
		} finally {
			if (workbook != null) workbook.close();
		}
	}

	public static int addPersons(Person[] persons) {
		return getPersonalService().addPersons(persons);
	}
	
	public static int addPersons2(Person[] persons) {
		return getPersonalService().addPersons2(persons);
	}

	public static int allotPersonsDepart(Person[] persons) {
		return getPersonalService().allotPersonsDepart(persons);
	}

	public static int allotPersonsLine(Person[] persons) {
		return getPersonalService().allotPersonsLine(persons);
	}

	public static int downPersons(Integer[] ids, Person person, Boolean down) {
		return getPersonalService().downPersons(ids, person, down);
	}

	public static void exportPersonList(SecurityLimit limit, Map qo, HttpServletResponse response) {
		List<Person> persons = getPersons(limit, qo, null);
		try {
			WritableWorkbook wwb = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet ws = wwb.createSheet("Sheet1", 0);
			Person person;
			String[] headers = {"workerId", "name", "pid", "sex", "people", "nativePlace", "regType", "regAddress", "birthday", 
					"marryStatus", "paySocialInsInit", "paySocialInsAdj", "paySocialIns", "annuities", "accumulation", "chkGroup.name", 
					"onDate", "downDate", "downReason", "upgradeDate", 
					"upgradeReason", "type", "regBelong", "party", "grade", "schooling", "allotDate", "allotReason", 
					"depart.name", "office", "line.name", "bus.authNo", "salaryType", "fkPosition.name", "fillTableDate", "commend", 
					"workDate", "retireDate", "serviceLength", "inDate", "outDate", "workLength", "groupNo", "contractNo", 
					"contr1From", "contr1End", "contractReason", "contr2From", "contr2End", "workType", "level", 
					"techLevel", "responsibility", "cert1No", "cert1NoDate", "cert2No", "cert2NoHex", "serviceNo", 
					"serviceNoDate", "frontWorkResume", "frontTrainingResume", "specification", "degree", "graduate", 
					"skill", "lanCom", "national", "state", "city", "address", "zip", "telephone", "email", "officeTel", 
					"officeExt", "officeFax", "comment",};
			for (int i = 0; i < headers.length; i++) {
				ws.addCell(new Label(i, 0, CommonUtil.getString("person." + headers[i])));
			}
			int c, r = 1;
			for (Iterator<Person> it = persons.iterator(); it.hasNext(); ) {
				person = it.next();
				c = 0;
				JxlUtil.writeCell(ws, c++, r, person.getWorkerId());
				JxlUtil.writeCell(ws, c++, r, person.getName());
				JxlUtil.writeCell(ws, c++, r, person.getPid());
				JxlUtil.writeCell(ws, c++, r, person.getSex());
				JxlUtil.writeCell(ws, c++, r, person.getPeople());
				JxlUtil.writeCell(ws, c++, r, person.getNativePlace());
				JxlUtil.writeCell(ws, c++, r, person.getRegType());
				JxlUtil.writeCell(ws, c++, r, person.getRegAddress());
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getBirthday()));
				JxlUtil.writeCell(ws, c++, r, person.getMarryStatus());
				JxlUtil.writeCell(ws, c++, r, person.getPaySocialInsInit());
				JxlUtil.writeCell(ws, c++, r, person.getPaySocialInsAdj());
				JxlUtil.writeCell(ws, c++, r, person.getPaySocialIns$());
				JxlUtil.writeCell(ws, c++, r, person.getAnnuities());
				JxlUtil.writeCell(ws, c++, r, person.getAccumulation());
				JxlUtil.writeCell(ws, c++, r, (person.getChkGroup() == null) ? "" : person.getChkGroup().getName());
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getOnDate()));
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getDownDate()));
				JxlUtil.writeCell(ws, c++, r, person.getDownReason());
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getUpgradeDate()));
				JxlUtil.writeCell(ws, c++, r, person.getUpgradeReason());
				JxlUtil.writeCell(ws, c++, r, person.getType());
				JxlUtil.writeCell(ws, c++, r, person.getRegBelong());
				JxlUtil.writeCell(ws, c++, r, person.getParty());
				JxlUtil.writeCell(ws, c++, r, person.getGrade());
				JxlUtil.writeCell(ws, c++, r, person.getSchooling());
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getAllotDate()));
				JxlUtil.writeCell(ws, c++, r, person.getAllotReason());
				JxlUtil.writeCell(ws, c++, r, (person.getDepart() == null) ? "" : person.getDepart().getName());
				JxlUtil.writeCell(ws, c++, r, person.getOffice());
				JxlUtil.writeCell(ws, c++, r, (person.getLine() == null) ? "" : person.getLine().getName());
				JxlUtil.writeCell(ws, c++, r, (person.getBus() == null) ? "" : person.getBus().getAuthNo());
				JxlUtil.writeCell(ws, c++, r, person.getSalaryType());
				JxlUtil.writeCell(ws, c++, r, (person.getFkPosition() == null) ? "" : person.getFkPosition().getName());
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getFillTableDate()));
				JxlUtil.writeCell(ws, c++, r, person.getCommend());
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getWorkDate()));
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getRetireDate()));
				JxlUtil.writeCell(ws, c++, r, person.getServiceLength());
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getInDate()));
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getOutDate()));
				JxlUtil.writeCell(ws, c++, r, person.getWorkLength());
				JxlUtil.writeCell(ws, c++, r, person.getGroupNo());
				JxlUtil.writeCell(ws, c++, r, person.getContractNo());
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getContr1From()));
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getContr1End()));
				JxlUtil.writeCell(ws, c++, r, person.getContractReason());
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getContr2From()));
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getContr2End()));
				JxlUtil.writeCell(ws, c++, r, person.getWorkType());
				JxlUtil.writeCell(ws, c++, r, (person.getLevel() == null) ? "" : person.getLevel().toString());
				JxlUtil.writeCell(ws, c++, r, person.getTechLevel());
				JxlUtil.writeCell(ws, c++, r, person.getResponsibility());
				JxlUtil.writeCell(ws, c++, r, person.getCert1No());
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getCert1NoDate()));
				JxlUtil.writeCell(ws, c++, r, person.getCert2No());
				JxlUtil.writeCell(ws, c++, r, person.getCert2NoHex());
				JxlUtil.writeCell(ws, c++, r, person.getServiceNo());
				JxlUtil.writeCell(ws, c++, r, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, person.getServiceNoDate()));
				JxlUtil.writeCell(ws, c++, r, person.getFrontWorkResume());
				JxlUtil.writeCell(ws, c++, r, person.getFrontTrainingResume());
				JxlUtil.writeCell(ws, c++, r, person.getSpecification());
				JxlUtil.writeCell(ws, c++, r, person.getDegree());
				JxlUtil.writeCell(ws, c++, r, person.getGraduate());
				JxlUtil.writeCell(ws, c++, r, person.getSkill());
				JxlUtil.writeCell(ws, c++, r, person.getLanCom());
				JxlUtil.writeCell(ws, c++, r, person.getNational());
				JxlUtil.writeCell(ws, c++, r, person.getState());
				JxlUtil.writeCell(ws, c++, r, person.getCity());
				JxlUtil.writeCell(ws, c++, r, person.getAddress());
				JxlUtil.writeCell(ws, c++, r, person.getZip());
				JxlUtil.writeCell(ws, c++, r, person.getTelephone());
				JxlUtil.writeCell(ws, c++, r, person.getEmail());
				JxlUtil.writeCell(ws, c++, r, person.getOfficeTel());
				JxlUtil.writeCell(ws, c++, r, person.getOfficeExt());
				JxlUtil.writeCell(ws, c++, r, person.getOfficeFax());
				JxlUtil.writeCell(ws, c++, r, person.getComment());
				r++;
			}
			wwb.write();
			wwb.close();
			response.setContentType("application/vnd.ms-excel");
			response.flushBuffer();
		} catch (Exception e) {
			throw new CommonRuntimeException(CommonUtil.getString("error.export.person.list"), e);
		}
	}

	public static void exportPersonsCard(Integer[] ids, HttpServletResponse response) {
		List<Person> persons = getPersons(ids);
		try {
			BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font bodyFont = new Font(bfChinese, 12, Font.NORMAL);
			Document document = new Document(PageSize.A4, 36, 36, 36, 36);
			PdfWriter.getInstance(document, response.getOutputStream());
			document.addTitle("PersonCard");
			document.addAuthor("GreateCreate");
			document.addSubject("This pdf is automatically created by system.");
			document.addKeywords("Person card, Greate Create");
			document.addCreator("iText");
			document.open();
			Person person;
			for (Iterator<Person> it = persons.iterator(); it.hasNext(); ) {
				person = it.next();
				document.add(new Paragraph(person.getName(), bodyFont));
				document.add(new Paragraph(person.getWorkerId(), bodyFont));
				document.newPage();
			}
			document.close();
			response.setContentType("application/pdf");
			response.flushBuffer();
		} catch (Exception e) {
			throw new CommonRuntimeException(CommonUtil.getString("error.export.person.cards"), e);
		}
	}

	public static Person getPerson(Integer id) {
		return getPersonalService().getPerson(id);
	}

	/**
	 * 返回人员的卡号使用信息, 对象格式为: {p1:p1, p2:p2, pos:[po1,...,pon]}
	 * p1: =person, 人员
	 * p2: 当前使用卡号cert2No的人员, 可能为null
	 * pos: 卡号使用记录, T_PSN_ONLINE中p1人员downDate大等于date的记录 + T_PSN_ONLINE中cert2No或newCert2No卡号downdate大等于date的记录
	 * @param qo: 参数
	 * 	- date: allotDate, 调度日期
	 *  - person: 表格中选中的人员
	 *  - newCert2No: 新卡号
	 * @return
	 */
	public static Map getPersonByCert2(Map qo) {
		String newCert2No = (String) qo.get("newCert2No");
		Person p1 = (Person) qo.get("person");
		Person p2 = getPersonalService().getPersonByCert2(newCert2No);
		List<PsnOnline> pos = getPersonalService().getCert2Onlines(qo);
		Map r = new HashMap();
		r.put("p1", p1);
		r.put("p2", p2);
		r.put("pos", pos);
		return r;
	}

	public static Person getPersonByWorkerId(Integer branchId, String workerId) {
		return getPersonalService().getPersonByWorkerId(branchId, workerId);
	}

	public static List<Person> getPersons(Integer[] ids) {
		return fillPaySocialInsSals(getPersonalService().getPersons(ids));
	}

	public static List<Person> getPersons(Map qo) {
		return getPersonalService().getPersons(qo);
	}

	public static List<Person> getAllPersons(Integer branchId) {
		return getPersonalService().getAllPersons(branchId);
	}

	public static List<Person> getPersonsByBranchId(Integer branchId) {
		return getPersonalService().getPersonsByBranchId(branchId);
	}

	public static List<Person> getPersons(SecurityLimit limit, Map qo, String[] orderColumns) {
		// System.out.println("java 1: " + new Date().getTime());
		List<Person> r = fillPaySocialInsSals(getPersonalService().getPersons(limit, qo, orderColumns));
		// System.out.println("java 2: " + new Date().getTime());
		return r;
	}

	protected static List<Person> fillPaySocialInsSals(List<Person> r) {
		return fillPaySocialInsSals(r, null);
	}

	protected static List<Person> fillPaySocialInsSals(List<Person> r, Map qo) {
		List<Object[]> l = SalaryServiceUtil.getPaySocialInsSals(qo);
		Map<Integer, Person> m = new Hashtable<Integer, Person>();
		Person p1;
		BigDecimal pid, count;
		for (Person p2: r) m.put(p2.getId(), p2);
		for (Object[] d: l) {
			pid = (BigDecimal) d[0];
			count = (BigDecimal) d[1];
			p1 = m.get(pid.intValue());
			if (p1 != null) p1.setPaySocialInsSal(count.intValue());
		}
		return r;
	}

	public static List<Person> getPersonsCard(Integer[] ids) {
		return getPersonalService().getPersonsCard(ids);
	}

	public static int updatePersonsCert2(Person[] persons) {
		return getPersonalService().updatePersonsCert2(persons);
	}

	public static int updatePersonsInfo(Person[] persons) {
		return getPersonalService().updatePersonsInfo(persons);
	}
	
	public static int updatePersonsInfo2(Person[] persons) {
		return getPersonalService().updatePersonsInfo2(persons);
	}
	
	public static int updatePersonsInfo3(Person[] persons) {
		return getPersonalService().updatePersonsInfo3(persons);
	}

	public static int updatePersonsStatus(Person[] persons) {
		return getPersonalService().updatePersonsStatus(persons);
	}

	public static int updatePersonsContract(Person[] persons){
		return getPersonalService().updatePersonsContract(persons);
	}

//==================================== PsnPhoto ====================================

	public static PsnPhoto getPsnPhoto(Integer personId) {
		return getPersonalService().getPsnPhoto(personId);
	}

	public static void uploadPersonsPhoto(SecurityUser user, File[] files, HttpServletResponse response) {
		response.setContentType("text/plain");
		try {
			/*
				response.setContentType("text/plain");
				byte[] b = FlexUtil.writeObject(new Object[]{new Person(8888, "T001", "Harry Sun"), new Integer(10), Calendar.getInstance(),});
				BASE64Encoder encoder = new BASE64Encoder();
				response.getWriter().print(encoder.encode(b));
				response.getWriter().flush();
			*/
			if (files == null || files.length <= 0) {
				response.getWriter().print(CommonUtil.base64Encode(FlexUtil.writeObject("UNKNOWN_ERROR")));
				response.getWriter().flush();
				return;
			}
			File f = files[0];
			String name = f.getName();
			String workerId = name.substring(0, name.lastIndexOf('.'));
			Person p = getPersonByWorkerId(user.getBranchId(), workerId);
			if (p == null) {
				response.getWriter().print(CommonUtil.base64Encode(FlexUtil.writeObject("NONEXISTENT_ERROR")));
				response.getWriter().flush();
				return;
			}
			if (!checkSecurity(user, p)) {
				response.getWriter().print(CommonUtil.base64Encode(FlexUtil.writeObject("SECURITY_ERROR")));
				response.getWriter().flush();
				return;
			}
			BufferedImage image = ImageIO.read(f);
			if (!checkSize(image)) {
				response.getWriter().print(CommonUtil.base64Encode(FlexUtil.writeObject("SIZE_ERROR")));
				response.getWriter().flush();
				return;
			}
			PsnPhoto pp = p.getPsnPhoto();
			byte[] b = CommonUtil.readFile(f);
			if (pp == null || pp.getBranch() == null) {
				pp = new PsnPhoto();
				pp.setBranch(p.getBranch());
				pp.setId(p.getId());
				pp.setPhoto(b);
				pp.setPhotoDate(Calendar.getInstance());
				pp.setUploader(user.getPerson());
				BaseServiceUtil.addObject(pp);
			} else {
				pp.setPhoto(b);
				pp.setPhotoDate(Calendar.getInstance());
				pp.setUploader(user.getPerson());
				BaseServiceUtil.addObject(pp);
			}
			response.getWriter().print(CommonUtil.base64Encode(FlexUtil.writeObject("SUCCESS")));
			response.getWriter().flush();
		} catch (Throwable t) {
			try {
				response.getWriter().print(CommonUtil.base64Encode(FlexUtil.writeObject("UNKNOWN_ERROR")));
				response.getWriter().flush();
			} catch (Throwable t1) {}
			throw new CommonRuntimeException("Error", t);
		}
	}

	protected static boolean checkSecurity(SecurityUser user, Person p) {
		SecurityLimit limit = user.getLimit();
		boolean b = (user.getBranchId().equals(user.getPerson().getBranchId()) && user.getBranchId().equals(p.getBranchId()));
		int l = limit.getHrLimit();
		if (l == 1 || l == 3 || l == 4) return b;
		else if (l == 7 || l == 8) return b && (user.getPerson().getDepartId().equals(p.getDepartId()));
		else return false;
	}

	/**
	 * 1寸 					2.5*3.5cm 295*413
	 * 身份证大头照 	3.3*2.2cm 260*390
	 * 2寸 					3.5*5.3cm 413*626
	 * 小2寸(护照)		3.3*4.8cm 390*567
	*/
	protected static boolean checkSize(BufferedImage image) {
		if (image == null) return false;
		int w = image.getWidth();
		int h = image.getHeight();
		return (w >= 240 && w <= 480 && h >= 360 && h <= 720);
	}

	public static List<Person> findPersons(Map qo) {
		return getPersonalService().findPersons(qo);
	}

	/**
	 * 员工卡照片下载，打成压缩包.zip文件下载
	 * @param pid  照片名中是否包含身份证号
	 * @param workerId 照片名中是否包含员工号
	 * @param personName 照片名中是否包含员工姓名
	 */
	public void downloadPersonsPhoto(Boolean pid,Boolean workerId,Boolean personName, Person[] downPersonsList, HttpServletResponse response){
		if (downPersonsList == null || downPersonsList.length <= 0)
			return;
		ZipOutputStream zos = null;
		ZipEntry zipEntry;
		try {
			zos = new ZipOutputStream(response.getOutputStream());
			zos.setEncoding("GBK");
			StringBuffer photoName = null;
			for (int i = 0; i < downPersonsList.length; i++) {
				Person pson = (Person) downPersonsList[i];
				photoName = new StringBuffer();
				if (personName)
					photoName.append(pson.getName() + " ");
				if (pid)
					photoName.append(pson.getPid() + " ");
				if (workerId)
					photoName.append(pson.getWorkerId() + " ");
				zipEntry = new ZipEntry(photoName.toString().trim() + ".jpg");
				// 获取照片byte[]
				PsnPhoto pphoto = getPsnPhoto(pson.getId());
				if (pphoto != null) {
					zipEntry.setSize(pphoto.getPhoto().length);
					zos.putNextEntry(zipEntry);
					zos.write(pphoto.getPhoto());
					zos.flush();
				}
			}
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment;filename=");
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != zos)
					zos.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

//==================================== PsnOnline ====================================

	@SuppressWarnings("unchecked")
	public static List<PsnOnline> getPsnOnlines(Integer branchId, Calendar accDate, Integer departId) {
		List<PsnOnline> list = new ArrayList<PsnOnline>();
		List<PsnOnline> psnOnlineList =  getPersonalService().getPsnOnlines(branchId, accDate, departId);
		List<Person> personList = getPersonalService().getDrivers(branchId, accDate);
		for (Iterator iterator = psnOnlineList.iterator(); iterator.hasNext();) {
			PsnOnline psnOnline = (PsnOnline) iterator.next();
			for (Iterator iterator1 = personList.iterator(); iterator1.hasNext();) {
				Person person = (Person) iterator1.next();
				if (psnOnline.getPersonId().intValue() == person.getId().intValue()) {
					psnOnline.setPerson(person);
					list.add(psnOnline);
				}
			}
		}
		return list;
	}

	public static List<PsnOnline> getPsnOnlinesByDepart(Integer branchId, Integer departId) {
		return getPersonalService().getPsnOnlinesByDepart(branchId, departId);
	}

	public static List<PsnOnline> getDriverOnlines(Integer branchId, Integer departId) {
		return getPersonalService().getDriverOnlines(branchId, departId);
	}
	
	public static List<PsnOnline> getDriverOnlines2(Integer branchId, Integer departId) {
		return getPersonalService().getDriverOnlines2(branchId, departId);
	}

	public static List<PsnOnline> getPsnOnlineList(Person person) {
		Map qo = new Hashtable();
		qo.put("person", person);
		return getPsnOnlineList(qo);
	}

	public static List<PsnOnline> getPsnOnlineList(Map qo) {
		qo.put(Constants.PARAM_FETCH, "depart, line, bus, alloter");
		return getPersonalService().getPsnOnlineList(qo);
	}

//==================================== PsnStatus ====================================

	public static List<Person> getDrivers(Integer branchId, Calendar date) {
		return getPersonalService().getDrivers(branchId, date);
	}

	public static List<PsnStatus> getPsnStatusList(Person person) {
		Map qo = new Hashtable();
		qo.put("person", person);
		return getPsnStatusList(qo);
	}

	public static List<PsnStatus> getPsnStatusList(Map qo) {
		qo.put(Constants.PARAM_FETCH, "fkPosition, upgrader");
		return getPersonalService().getPsnStatusList(qo);
	}

//==================================== Position =====================================

	public static Position getPosition(Integer branchId, String no) {
		return getPersonalService().getPosition(branchId, no);
	}

	public static List<Position> getPositions(Integer branchId) {
		return getPersonalService().getPositions(branchId);
	}

//==================================== T_PSN_XXX ====================================

	/**
	 * People
	 * @param branchId
	 * @param name
	 * @return
	 */
	public static People getPeople(Integer branchId, String name) {
		return getPeople(new PeoplePK(branchId, name));
	}

	public static People getPeople(PeoplePK id) {
		return getPersonalService().getPeople(id);
	}

	public static List<People> getPeoples() {
		return getPersonalService().getPeoples(null);
	}

	public static List<People> getPeoples(Integer branchId) {
		return getPersonalService().getPeoples(branchId);
	}

	public static List<People> getPeoples1(Integer branchId) {
		return getPersonalService().getPeoples1(branchId);
	}

	public static List<People> getPeoples2(Integer branchId) {
		return getPersonalService().getPeoples2(branchId);
	}

	/**
	 * PolParty
	 * @param branchId
	 * @param name
	 * @return
	 */
	public static PolParty getPolParty(Integer branchId, String name) {
		return getPolParty(new PolPartyPK(branchId, name));
	}

	public static PolParty getPolParty(PolPartyPK id) {
		return getPersonalService().getPolParty(id);
	}

	public static List<PolParty> getPolParties(Integer branchId) {
		return getPersonalService().getPolParties(branchId);
	}

	/**
	 * HireType
	 * @param branchId
	 * @param name
	 * @return
	 */
	public static HireType getHireType(Integer branchId, String name) {
		return getHireType(new HireTypePK(branchId, name));
	}

	public static HireType getHireType(HireTypePK id) {
		return getPersonalService().getHireType(id);
	}

	public static List<HireType> getHireTypes(Integer branchId) {
		return getPersonalService().getHireTypes(branchId);
	}

	/**
	 * JobGrade
	 * @param branchId
	 * @param name
	 * @return
	 */
	public static JobGrade getJobGrade(Integer branchId, String name) {
		return getJobGrade(new JobGradePK(branchId, name));
	}

	public static JobGrade getJobGrade(JobGradePK id) {
		return getPersonalService().getJobGrade(id);
	}

	public static List<JobGrade> getJobGrades(Integer branchId) {
		return getPersonalService().getJobGrades(branchId);
	}

	/**
	 * JobSpec
	 * @param branchId
	 * @param name
	 * @return
	 */
	public static JobSpec getJobSpec(Integer branchId, String name) {
		return getJobSpec(new JobSpecPK(branchId, name));
	}

	public static JobSpec getJobSpec(JobSpecPK id) {
		return getPersonalService().getJobSpec(id);
	}

	public static List<JobSpec> getJobSpecs(Integer branchId) {
		return getPersonalService().getJobSpecs(branchId);
	}

	/**
	 * MarryStatus
	 * @param branchId
	 * @param name
	 * @return
	 */
	public static MarryStatus getMarryStatus(Integer branchId, String name) {
		return getMarryStatus(new MarryStatusPK(branchId, name));
	}

	public static MarryStatus getMarryStatus(MarryStatusPK id) {
		return getPersonalService().getMarryStatus(id);
	}

	public static List<MarryStatus> getMarryStatusList(Integer branchId) {
		return getPersonalService().getMarryStatusList(branchId);
	}

	/**
	 * NativePlace
	 * @param branchId
	 * @param name
	 * @return
	 */
	public static NativePlace getNativePlace(Integer branchId, String name) {
		return getNativePlace(new NativePlacePK(branchId, name));
	}

	public static NativePlace getNativePlace(NativePlacePK id) {
		return getPersonalService().getNativePlace(id);
	}

	public static List<NativePlace> getNativePlaces(Integer branchId) {
		return getPersonalService().getNativePlaces(branchId);
	}

	/**
	 * RegBranch
	 * @param branchId
	 * @param name
	 * @return
	 */
	public static RegBranch getRegBranch(Integer branchId, String name) {
		return getRegBranch(new RegBranchPK(branchId, name));
	}

	public static RegBranch getRegBranch(RegBranchPK id) {
		return getPersonalService().getRegBranch(id);
	}

	public static List<RegBranch> getRegBranches(Integer branchId) {
		return getPersonalService().getRegBranches(branchId);
	}

	/**
	 * SchDegree
	 * @param branchId
	 * @param name
	 * @return
	 */
	public static SchDegree getSchDegree(Integer branchId, String name) {
		return getSchDegree(new SchDegreePK(branchId, name));
	}

	public static SchDegree getSchDegree(SchDegreePK id) {
		return getPersonalService().getSchDegree(id);
	}

	public static List<SchDegree> getSchDegrees(Integer branchId) {
		return getPersonalService().getSchDegrees(branchId);
	}

	/**
	 * SchGraduate
	 * @param branchId
	 * @param name
	 * @return
	 */
	public static SchGraduate getSchGraduate(Integer branchId, String name) {
		return getSchGraduate(new SchGraduatePK(branchId, name));
	}

	public static SchGraduate getSchGraduate(SchGraduatePK id) {
		return getPersonalService().getSchGraduate(id);
	}

	public static List<SchGraduate> getSchGraduates(Integer branchId) {
		return getPersonalService().getSchGraduates(branchId);
	}

	/**
	 * Schooling
	 * @param branchId
	 * @param name
	 * @return
	 */
	public static Schooling getSchooling(Integer branchId, String name) {
		return getSchooling(new SchoolingPK(branchId, name));
	}

	public static Schooling getSchooling(SchoolingPK id) {
		return getPersonalService().getSchooling(id);
	}

	public static List<Schooling> getSchoolings(Integer branchId) {
		return getPersonalService().getSchoolings(branchId);
	}

	/**
	 * WorkType
	 * @param branchId
	 * @param name
	 * @return
	 */
	public static WorkType getWorkType(Integer branchId, String name) {
		return getWorkType(new WorkTypePK(branchId, name));
	}

	public static WorkType getWorkType(WorkTypePK id) {
		return getPersonalService().getWorkType(id);
	}

	public static List<WorkType> getWorkTypes(Integer branchId) {
		return getPersonalService().getWorkTypes(branchId);
	}

	/**
	 * RegType
	 * @param branchId
	 * @param name
	 * @return
	 */
	public static RegType getRegType(Integer branchId, String name) {
		return getRegType(new RegTypePK(branchId, name));
	}

	public static RegType getRegType(RegTypePK id) {
		return getPersonalService().getRegType(id);
	}

	public static List<RegType> getRegTypes(Integer branchId) {
		return getPersonalService().getRegTypes(branchId);
	}

	/**
	 * SalaryType
	 * @param branchId
	 * @param name
	 * @return
	 */
	public static SalaryType getSalaryType(Integer branchId, String name) {
		return getSalaryType(new SalaryTypePK(branchId, name));
	}

	public static SalaryType getSalaryType(SalaryTypePK id) {
		return getPersonalService().getSalaryType(id);
	}

	public static List<SalaryType> getSalaryTypes(Integer branchId) {
		return getPersonalService().getSalaryTypes(branchId);
	}

//==================================== Report ====================================

	/**
	 * 从业人员人数变动情况表, 对象格式为: {bdate:d1, edate:d2, period:p, user:u, aa:aa, bb:bb, cc:cc, bcount:c1, ecount:c2, increase:[...], decrease:[...]}
	 * bdate: 起始日期
	 * edate: 截止日期
	 * period: 报告期
	 * user: 查询用户
	 * aa: 填报单位
	 * bb: 人员类别
	 * cc: 岗位类别
	 * bcount: 期初人数
	 * ecount: 期末人数
	 * increase: 本期增加, 元素为PsnChange对象
	 * decrease: 本期减少, 元素为PsnChange对象
	 * @param qo: 参数, a, b, c, depart, regBelong, position, bdate, edate
	 * 注意: 数据库连接在service/dao方法之后会commit/rollback, 所以调用SP_PSN_CHANGE生成TMP_PSN_CHANGE数据以及后续调用必须放在service/dao层执行!!!
	 * @return
	 */
	public static Map reportP01(Map qo) {
		return getPersonalService().reportP01(qo);
	}

	public static void exportP01(Map qo, HttpServletResponse response) {
		try {
			String path = (String) Constants.SETTINGS.get(Constants.PROP_TEMPLATE_PATH);
			WritableWorkbook wwb = JxlUtil.copy(response.getOutputStream(), path + "hr_report_p01.xls");
			WritableSheet ws = wwb.getSheet(0);
			Map r = reportP01(qo);
			SecurityUser user = (SecurityUser) r.get("user");
			JxlUtil.writeCell(ws, 1, 1, CommonUtil.getString("report.p01.name", new Object[]{user.getBranchName()}));
			JxlUtil.writeCell(ws, 3, 3, r.get("period"));
			JxlUtil.writeCell(ws, 3, 4, r.get("aa"));
			JxlUtil.writeCell(ws, 3, 5, r.get("bb"));
			JxlUtil.writeCell(ws, 3, 6, r.get("cc"));
			JxlUtil.writeCell(ws, 13, 3, r.get("bcount"));
			JxlUtil.writeCell(ws, 13, 4, r.get("ecount"));
			PsnChange pc;
			Person p;
			List<PsnChange> ilist = (List<PsnChange>) r.get("increase");
			List<PsnChange> dlist = (List<PsnChange>) r.get("decrease");
			int row1 = 9, row2 = 9;
			for (Iterator<PsnChange> it1 = ilist.iterator(); it1.hasNext(); ) {
				pc = it1.next();
				p = pc.getPerson();
				row1++;
				if (row1 > 25) JxlUtil.insertRow(ws, row1);
				JxlUtil.writeCell(ws, 1, row1, new Integer(row1-9));
				JxlUtil.writeCell(ws, 2, row1, p.getWorkerId());
				JxlUtil.writeCell(ws, 3, row1, p.getName());
				JxlUtil.writeCell(ws, 4, row1, p.getSex());
				JxlUtil.writeCell(ws, 5, row1, pc.getNewDepartName());
				JxlUtil.writeCell(ws, 6, row1, pc.getNewType());
				JxlUtil.writeCell(ws, 7, row1, pc.getNewPositionName());
				JxlUtil.writeCell(ws, 8, row1, pc.getSource());
				JxlUtil.writeCell(ws, 9, row1, pc.getReason());
			}
			for (Iterator<PsnChange> it2 = dlist.iterator(); it2.hasNext(); ) {
				pc = it2.next();
				p = pc.getPerson();
				row2++;
				if (row2 > 26 || row2 > row1) JxlUtil.insertRow(ws, row2);
				JxlUtil.writeCell(ws, 10, row2, new Integer(row2-9));
				JxlUtil.writeCell(ws, 11, row2, p.getWorkerId());
				JxlUtil.writeCell(ws, 12, row2, p.getName());
				JxlUtil.writeCell(ws, 13, row2, p.getSex());
				JxlUtil.writeCell(ws, 14, row2, pc.getOldDepartName());
				JxlUtil.writeCell(ws, 15, row2, pc.getOldType());
				JxlUtil.writeCell(ws, 16, row2, pc.getOldPositionName());
				JxlUtil.writeCell(ws, 17, row2, pc.getDest());
				JxlUtil.writeCell(ws, 18, row2, pc.getReason());
			}
			wwb.write();
			wwb.close();
			response.setContentType("application/vnd.ms-excel");
			response.flushBuffer();
		} catch (Exception e) {
			throw new CommonRuntimeException(CommonUtil.getString("error.export.p01.report"), e);
		}
	}

	/**
	 * 从业人员人数变动明细表, 对象格式为: {bdate:d1, edate:d2, period:p, user:u, change:{value:.., label:..}, data:[...], total:{...}}
	 * bdate: 起始日期
	 * edate: 截止日期
	 * period: 报告期
	 * user: 查询用户
	 * change: 变动类型
	 * data: 变动数据, 元素为PsnChange对象
	 * total: 小计
	 * @param qo: 参数, change, bdate, edate
	 * 注意: 数据库连接在service/dao方法之后会commit/rollback, 所以调用SP_PSN_CHANGE生成TMP_PSN_CHANGE数据以及后续调用必须放在service/dao层执行!!!
	 * @return
	 */
	public static Map reportP02(Map qo) {
		return getPersonalService().reportP02(qo);
	}

	public static void exportP02(Map qo, HttpServletResponse response) {
		try {
			String path = (String) Constants.SETTINGS.get(Constants.PROP_TEMPLATE_PATH);
			WritableWorkbook wwb = JxlUtil.copy(response.getOutputStream(), path + "hr_report_p02.xls");
			WritableSheet ws = wwb.getSheet(0);
			Map r = reportP02(qo);
			SecurityUser user = (SecurityUser) r.get("user");
			Map change = (Map) r.get("change");
			Map total = (Map) r.get("total");
			JxlUtil.writeCell(ws, 1, 1, CommonUtil.getString("report.p02.name", new Object[]{user.getBranchName()}));
			JxlUtil.writeCell(ws, 3, 3, r.get("period"));
			JxlUtil.writeCell(ws, 14, 3, change.get("label"));
			PsnChange pc;
			Person p;
			List<PsnChange> ilist = (List<PsnChange>) r.get("data");
			int row = 5, s = 0, id1 = 0, id2;
			for (Iterator<PsnChange> it1 = ilist.iterator(); it1.hasNext(); ) {
				pc = it1.next();
				p = pc.getPerson();
				id2 = p.getId();
				row++;
				if (row > 25) JxlUtil.insertRow(ws, row);
				if (id1 != id2) {
					s++;
					JxlUtil.writeCell(ws, 1, row, new Integer(s));
					JxlUtil.writeCell(ws, 2, row, p.getWorkerId());
					JxlUtil.writeCell(ws, 3, row, p.getName());
					JxlUtil.writeCell(ws, 4, row, p.getSex());
					JxlUtil.writeCell(ws, 5, row, p.getPid());
				}
				JxlUtil.writeCell(ws, 6, row, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, pc.getDate()));
				JxlUtil.writeCell(ws, 7, row, pc.getReason());
				JxlUtil.writeCell(ws, 8, row, pc.getOn());
				JxlUtil.writeCell(ws, 9, row, pc.getDown());
				JxlUtil.writeCell(ws, 10, row, pc.getNewDepartName());
				JxlUtil.writeCell(ws, 11, row, pc.getNewOffice());
				JxlUtil.writeCell(ws, 12, row, pc.getNewLineName());
				JxlUtil.writeCell(ws, 13, row, pc.getNewBusAuthNo());
				JxlUtil.writeCell(ws, 14, row, pc.getNewCert2No());
				JxlUtil.writeCell(ws, 15, row, pc.getNewRegType());
				JxlUtil.writeCell(ws, 16, row, pc.getNewType());
				JxlUtil.writeCell(ws, 17, row, pc.getNewSalaryType());
				JxlUtil.writeCell(ws, 18, row, pc.getNewPositionName());
				JxlUtil.writeCell(ws, 19, row, pc.getNewWorkType());
				JxlUtil.writeCell(ws, 20, row, pc.getNewRegBelong());
				JxlUtil.writeCell(ws, 21, row, pc.getNewParty());
				JxlUtil.writeCell(ws, 22, row, pc.getNewGrade());
				JxlUtil.writeCell(ws, 23, row, pc.getNewSchooling());
				JxlUtil.writeCell(ws, 24, row, pc.getNewContractNo());
				JxlUtil.writeCell(ws, 25, row, CommonUtil.formatCalendar(Constants.DEFAULT_DATE_FORMAT, pc.getNewContr1End()));
				id1 = id2;
			}
			for (int i = 0; i < 18; i++) JxlUtil.writeCell(ws, i+8, Math.max(row, 25)+2, total.get("c"+i));

			wwb.write();
			wwb.close();
			response.setContentType("application/vnd.ms-excel");
			response.flushBuffer();
		} catch (Exception e) {
			throw new CommonRuntimeException(CommonUtil.getString("error.export.p02.report"), e);
		}
	}
	
	/**
	 * 工龄表查询
	 */
	public List<Person> getWorkLengths(Map qo) {
		return fillPaySocialInsSals(getPersonalService().getWorkLengths(qo), qo);
	}

//==================================== TEST ====================================

	public static void testTx(PolParty party, People people) {
		getPersonalService().testTx(party, people);
	}

	public static Object testAmf(Object obj) {
		System.out.println(obj);
		return obj;
	}

	/**
	 * BlazeDS在匹配多态函数时有问题, flex.messaging.util.MethodMatcher.getMethod方法会依次进行函数匹配
	 * 在methods[]数组中:
	 * 1. 如果testPoly(Map)在最前面, 则前端调用testPoly(People)时, 后台抛错:java.lang.ClassCastException
	 * flex.messaging.io.amf.translator.decoder.MapDecoder.decodeObject(91):
	 * --return decodeMap((Map)shell, (Map)encodedObject);
	 * --shell转换前应该判断一下类型
	 * 2. 其它类型没有此问题.
	 * 解决方案: 
	 * 1. testPoly(Map)改为queryPoly(Map)
	 * 2. 研究BlazeDS是否可以注入自定义实现的MapDecoder(似乎不行)
	 * 3. 修改MapDecoder, 重新打包flex-messaging-core.jar(OK)
	 * 参见: flex.messaging.io.amf.translator.decoder.DecoderFactory
	 * @param obj
	 * @return
	 */
	public static List<Person> testPoly(Map obj) {
		System.out.println(obj);
		List<Person> list = new ArrayList<Person>();
		list.add(new Person(6666, "MAP", "MAP"));
		return list;
	}

	public static List<Person> testPoly(People p) {
		System.out.println(p);
		List<Person> list = new ArrayList<Person>();
		list.add(new Person(7777, "PEOPLE", "PEOPLE"));
		return list;
	}

	public static List<Person> testPoly(Schooling s) {
		System.out.println(s);
		List<Person> list = new ArrayList<Person>();
		list.add(new Person(8888, "SCHOOLING", "SCHOOLING"));
		return list;
	}

	public static Person testList(Person p) {
		List<ChkLongPlan> list = p.getChkLongPlans();
		ChkLongPlan clp;
		for (Iterator<ChkLongPlan> it = list.iterator(); it.hasNext(); ) {
			clp = it.next();
			System.out.println(clp);
			clp.setCheckDescription("test");
		}
		clp = new ChkLongPlan(2, "A09-000099");
		clp.setCheckDescription("new");
		p.addChkLongPlan(clp);
		return p;
	}

	public static Accident testSet(Accident acc) {
		Set<AccInPsn> set = acc.getAccInPsns();
		AccInPsn aip;
		for (Iterator<AccInPsn> it = set.iterator(); it.hasNext(); ) {
			aip = it.next();
			System.out.println(aip);
			aip.setName("test");
		}
		aip = new AccInPsn();
		aip.setName("new");
		acc.getAccInPsns().add(aip);
		return acc;
	}

	public static Map testMap(Map obj) {
		Object key, value;
		for (Iterator it = obj.keySet().iterator(); it.hasNext(); ) {
			key = it.next();
			value = obj.get(key);
			System.out.println(key + ": " + value);
		}
		obj.put(new Person(8888, "8888", "flex"), new ChkLongPlan(2, "A09-000099"));
		return obj;
	}
	
	//===============================contract历史合同状态记录表================================================================================	
	/**
	 * map :changeDate 合同变更日期   changeNo 合同变更文号(历史合同号)  isLimitCnt 合同类别（固定：有传值 非固定：不传值） endDate 合同终止日期
	 */
	public static List<PsnStatus> getContractList(Map qo){
		// qo.put(Constants.PARAM_FETCH, "fkPosition, upgrader");
		return getPersonalService().getContractList(qo);
	}

	public Map getLateDatePsnStatusTotle(Map qo){
		return getPersonalService().getLateDatePsnStatusTotle(qo);
	}
	
	/**
	 * 合同到期人员清单
	 * @param qo
	 * @return
	 */
	public static List getContractReportList(Map qo){
		return getPersonalService().getContractReportList(qo);
	}
	
	public static void exportContractList(Map qo,HttpServletResponse response) {
		try {
			String path = (String) Constants.SETTINGS.get(Constants.PROP_TEMPLATE_PATH);
			WritableWorkbook wwb = JxlUtil.copy(response.getOutputStream(), path + "hr_contract_report.xls");
			
			wwb.write();
			wwb.close();
			response.setContentType("application/vnd.ms-excel");
			response.flushBuffer();
		} catch (Exception e) {
			throw new CommonRuntimeException(CommonUtil.getString("error.export.p02.report"), e);
		}
	}

}
