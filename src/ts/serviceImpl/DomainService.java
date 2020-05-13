
package ts.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import ts.daoImpl.CustomerInfoDao;
import ts.daoImpl.ExpressSheetDao;
import ts.daoImpl.PackageRouteDao;
import ts.daoImpl.TransHistoryDao;
import ts.daoImpl.TransNodeDao;
import ts.daoImpl.TransPackageContentDao;
import ts.daoImpl.TransPackageDao;
import ts.daoImpl.UserInfoDao;
import ts.model.CustomerInfo;
import ts.model.ExpressSheet;
import ts.model.ListTransHistory;
import ts.model.ListTransPackge;
import ts.model.PackageRoute;
import ts.model.TransHistory;
import ts.model.TransHistoryDetail;
import ts.model.TransNode;
import ts.model.TransPackage;
import ts.model.TransPackageContent;
import ts.model.UserInfo;
import ts.serviceInterface.IDomainService;

public class DomainService implements IDomainService {

	private ExpressSheetDao expressSheetDao;
	private TransPackageDao transPackageDao;
	private TransHistoryDao transHistoryDao;
	private TransPackageContentDao transPackageContentDao;
	private PackageRouteDao packageRouteDao;
	private UserInfoDao userInfoDao;
	private TransNodeDao transNodeDao;
	private CustomerInfoDao customerInfoDao;

	public CustomerInfoDao getCustomerInfoDao() {
		return customerInfoDao;
	}

	public void setCustomerInfoDao(CustomerInfoDao customerInfoDao) {
		this.customerInfoDao = customerInfoDao;
	}

	public TransNodeDao getTransNodeDao() {
		return transNodeDao;
	}

	public void setTransNodeDao(TransNodeDao transNodeDao) {
		this.transNodeDao = transNodeDao;
	}

	public ExpressSheetDao getExpressSheetDao() {
		return expressSheetDao;
	}

	public void setExpressSheetDao(ExpressSheetDao dao) {
		this.expressSheetDao = dao;
	}

	public PackageRouteDao getPackageRouteDao() {
		return packageRouteDao;
	}

	public void setPackageRouteDao(PackageRouteDao packageRouteDao) {
		this.packageRouteDao = packageRouteDao;
	}

	public TransPackageDao getTransPackageDao() {
		return transPackageDao;
	}

	public void setTransPackageDao(TransPackageDao dao) {
		this.transPackageDao = dao;
	}

	public TransHistoryDao getTransHistoryDao() {
		return transHistoryDao;
	}

	public void setTransHistoryDao(TransHistoryDao dao) {
		this.transHistoryDao = dao;
	}

	public TransPackageContentDao getTransPackageContentDao() {
		return transPackageContentDao;
	}

	public void setTransPackageContentDao(TransPackageContentDao dao) {
		this.transPackageContentDao = dao;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao dao) {
		this.userInfoDao = dao;
	}

	public Date getCurrentDate() {
		// ����һ�����������ʱ��,��Ȼ,SQLʱ���JAVAʱ���ʽ��һ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date tm = new Date();
		try {
			tm = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return tm;
	}

	/**
	 * ldq ��ȡ�����˵���Ϣ
	 */
	@Override
	public List<ExpressSheet> getAllExpressList() {
		return expressSheetDao.getAll();
	}

	/**
	 * ldq ɾ����ѡ�˵���id��
	 */
	@Override
	public Response deleteExpressSheet(String id) {
		expressSheetDao.removeById(id);
		return Response.ok("Deleted").header("EntityClass", "D_ExpressSheet").build();
	}

	/**
	 * ldq �����ջ���������ѯ�˵�
	 */
	@Override
	public List<ExpressSheet> getExpressSheetbyrecevername(String recevername) {
		return null;
	}

	@Override
	public List<ExpressSheet> getExpressList(String property, String restrictions, String value) {
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		switch (restrictions.toLowerCase()) {
		case "eq":
			list = expressSheetDao.findBy(property, value, "ID", true);
			break;
		case "like":
			list = expressSheetDao.findLike(property, value + "%", "ID", true);
			break;
		}
		return list;
	}
//	@Override
//	public List<ExpressSheet> getExpressList(String property,
//			String restrictions, String value) {
//		Criterion cr1;
//		Criterion cr2 = Restrictions.eq("Status", 0);
//
//		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
//		switch(restrictions.toLowerCase()){
//		case "eq":
//			cr1 = Restrictions.eq(property, value);
//			break;
//		case "like":
//			cr1 = Restrictions.like(property, value);
//			break;
//		default:
//			cr1 = Restrictions.like(property, value);
//			break;
//		}
//		list = expressSheetDao.findBy("ID", true,cr1,cr2);		
//		return list;
//	}

	@Override
	public List<ExpressSheet> getExpressListInPackage(String packageId) {
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		try {
			list = expressSheetDao.getListInPackage(packageId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public Response getExpressSheet(String id) {
		ExpressSheet es = expressSheetDao.get(id);
		return Response.ok(es).header("EntityClass", "ExpressSheet").build();
	}

	@Override
	public Response newExpressSheet(String id, int uid) {
		ExpressSheet es = null;
		try {
			es = expressSheetDao.get(id);
		} catch (Exception e1) {
		}

		if (es != null) {
//			if(es.getStatus() != 0)
//				return Response.ok(es).header("EntityClass", "L_ExpressSheet").build(); //�Ѿ�����,�Ҳ��ܸ���
//			else
			return Response.ok("����˵���Ϣ�Ѿ�����!\n�޷�����!").header("EntityClass", "E_ExpressSheet").build(); // �Ѿ�����
		}
		try {
			String pkgId = userInfoDao.get(uid).getReceivePackageID();
			ExpressSheet nes = new ExpressSheet();
			nes.setID(id);
			nes.setType(0);
			nes.setAccepter(String.valueOf(uid));
			nes.setAccepteTime(getCurrentDate());
			nes.setStatus(ExpressSheet.STATUS.STATUS_CREATED);
//			TransPackageContent pkg_add = new TransPackageContent();
//			pkg_add.setPkg(transPackageDao.get(pkgId));
//			pkg_add.setExpress(nes);
//			nes.getTransPackageContent().add(pkg_add);
			expressSheetDao.save(nes);
			// �ŵ��ռ�������
			MoveExpressIntoPackage(nes.getID(), pkgId);
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	/**
	 * ldq �༭�˵�
	 */
	@Override
	public Response editExpressSheet(ExpressSheet obj) {
		System.out.println("�����˱����˵�������");
		System.out.println(obj);
		try {
			// ExpressSheet nes = expressSheetDao.get(obj.getID());
			/*
			 * if (obj.getStatus() != ExpressSheet.STATUS.STATUS_CREATED) { return
			 * Response.ok("����˵��Ѹ���!�޷��������!").header("EntityClass",
			 * "E_ExpressSheet").build(); }
			 */
			expressSheetDao.save(obj);
			return Response.ok(obj).header("EntityClass", "R_ExpressSheet").build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	/**
	 * ldq �����˵�
	 */
	@Override
	public Response saveExpressSheet(ExpressSheet obj) {
		System.out.println("�����˱����˵�������");
		System.out.println(obj);

		ExpressSheet es = null;
		try {
			es = expressSheetDao.get(obj.getID());
		} catch (Exception e1) {
		}
		if (es != null) {
			return Response.ok("Error").header("EntityClass", "E_ExpressSheet").build(); // �Ѿ�����
		}

		try {
			// ExpressSheet nes = expressSheetDao.get(obj.getID());
			if (obj.getStatus() != ExpressSheet.STATUS.STATUS_CREATED) {
				return Response.ok("����˵��Ѹ���!�޷��������!").header("EntityClass", "E_ExpressSheet").build();
			}
			expressSheetDao.save(obj);
			return Response.ok(obj).header("EntityClass", "R_ExpressSheet").build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.ok("Error").entity(e.getMessage()).build();
		}
	}

	@Override
	public Response ReceiveExpressSheetId(String id, int uid) {
		try {
			ExpressSheet nes = expressSheetDao.get(id);
			if (nes.getStatus() != ExpressSheet.STATUS.STATUS_CREATED) {
				return Response.ok("����˵�״̬����!�޷��ռ�!").header("EntityClass", "E_ExpressSheet").build();
			}
			nes.setAccepter(String.valueOf(uid));
			nes.setAccepteTime(getCurrentDate());
			nes.setStatus(ExpressSheet.STATUS.STATUS_DAIZHUAYUN); // �����պ��ת��
			expressSheetDao.save(nes);
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@Override
	public Response DispatchExpressSheet(String id, int uid) {
		// TODO Auto-generated method stub
		return null;
	}

	// lyy�޸�
	public Response MoveExpressIntoPackage(String id, String targetPkgId) {

		TransPackage targetPkg = transPackageDao.get(targetPkgId);
		if ((targetPkg.getStatus() > 0) && (targetPkg.getStatus() < 3)) { // ������״̬��㶨��,�򿪵İ������߻������ܲ���==================================================================
			return Response.ok("fasle").header("EntityClass", "MoveExpressIntoPackage").build();
		}

		TransPackageContent pkg_add = new TransPackageContent();
		pkg_add.setPkg(targetPkg);
		pkg_add.setExpress(expressSheetDao.get(id));
		pkg_add.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
		transPackageContentDao.save(pkg_add);
		return Response.ok("true").header("EntityClass", "MoveExpressIntoPackage").build();
	}

	public boolean MoveExpressBetweenPackage(String id, String sourcePkgId, String targetPkgId) {
		// ��Ҫ�����������
		MoveExpressFromPackage(id, sourcePkgId);
		MoveExpressIntoPackage(id, targetPkgId);
		return true;
	}

	@Override
	public Response DeliveryExpressSheetId(String id, int uid) {
		try {
			String pkgId = userInfoDao.get(uid).getDelivePackageID();
			ExpressSheet nes = expressSheetDao.get(id);
			if (nes.getStatus() != ExpressSheet.STATUS.STATUS_TRANSPORT) {
				return Response.ok("����˵�״̬����!�޷�����").header("EntityClass", "E_ExpressSheet").build();
			}

			if (transPackageContentDao.getSn(id, pkgId) == 0) {
				// ��ʱ��һ������ʽ,��·�˰����Ĵ��ݹ���,�Լ��Ļ�������һ��
				MoveExpressBetweenPackage(id, userInfoDao.get(uid).getReceivePackageID(), pkgId);
				return Response.ok("����˵�״̬����!\n�����Ϣû�������ɼ�������!").header("EntityClass", "E_ExpressSheet").build();
			}

			nes.setDeliver(String.valueOf(uid));
			nes.setDeliveTime(getCurrentDate());
			nes.setStatus(ExpressSheet.STATUS.STATUS_DELIVERIED);
			expressSheetDao.save(nes);
			// ���ɼ�������ɾ��
			MoveExpressFromPackage(nes.getID(), pkgId);
			// ���û����ʷ��¼,���Ѹ����ռ��ͽ����ļ�¼
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@Override
	public List<TransPackage> getTransPackageList(String property, String restrictions, String value) {
		List<TransPackage> list = new ArrayList<TransPackage>();
		switch (restrictions.toLowerCase()) {
		case "eq":
			list = transPackageDao.findBy(property, value, "ID", true);
			break;
		case "like":
			list = transPackageDao.findLike(property, value + "%", "ID", true);
			break;
		}
		return list;
	}

	/**
	 * ldq ��ȡ���а�����Ϣ
	 */
	@Override
	public List<TransPackage> getAllTransPackage() {
		return transPackageDao.getAll();
	}

	@Override
	public Response getTransPackage(String id) {
		TransPackage es = null;
		try {
			es = transPackageDao.get(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (es == null)
			return Response.ok("���������ڣ�").header("EntityClass", "N_TransPackage").build();
		return Response.ok(es).header("EntityClass", "TransPackage").build();
	}

	/**
	 * ldq �������
	 */
	@Override
	public Response saveTransPackage(TransPackage obj) {
		TransPackage tg = null;

		try {
			tg = transPackageDao.get(obj.getID());
		} catch (Exception e1) {
			System.out.println(e1);
		}

		if (tg != null) {
			return Response.ok("Error").header("EntityClass", "R_TransPackage").build();
		}

		try {
			transPackageDao.save(obj);
			return Response.ok(obj).header("EntityClass", "R_TransPackage").build();
		} catch (Exception e) {
			return Response.ok("Error").entity(e.getMessage()).build();
		}
	}

	/**
	 * ldq �༭����
	 */
	@Override
	public Response editTransPackage(TransPackage obj) {
		try {
			transPackageDao.save(obj);
			return Response.ok(obj).header("EntityClass", "R_TransPackage").build();
		} catch (Exception e) {
			return Response.ok("Error").entity(e.getMessage()).build();
		}
	}

	// �����ʷ=======================================================================
	public List<TransPackage> findTransPackagebyExpressSheetId(String id) {
		return transPackageDao.findbyExpressSheetId(id);
	}

	// ldq
	public List<TransHistory> getTransHistory(String id) {
		TransPackage transPackage = transPackageDao.get(id);
		System.out.println(transPackage);
		return transHistoryDao.getPkgListOrderByAccTime(transPackage);
	}

	// lyy �޸�
	public Response MoveExpressFromPackage(String id, String sourcePkgId) {
		TransPackage sourcePkg = transPackageDao.get(sourcePkgId);
		if ((sourcePkg.getStatus() > 0) && (sourcePkg.getStatus() < 3)) {
			Response.ok("Deleted1").header("EntityClass", "MoveExpressFromPackage").build();
		}

		TransPackageContent pkg_add = transPackageContentDao.get(id, sourcePkgId);
		pkg_add.setStatus(TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
		transPackageContentDao.save(pkg_add);
		return Response.ok("Deleted").header("EntityClass", "MoveExpressFromPackage").build();
	}

	// lyy ����
	public Response DeleteExpressFromPackage(String id, String PkgId) {
		TransPackage sourcePkg = transPackageDao.get(PkgId);
		if ((sourcePkg.getStatus() != TransPackage.PKG_NEW)) {
			Response.ok("Deleted1").header("EntityClass", "MoveExpressFromPackage").build();
		}
		TransPackageContent pkg_add = transPackageContentDao.get(id, PkgId);
		transPackageContentDao.remove(pkg_add);
		return Response.ok("Deleted").header("EntityClass", "MoveExpressFromPackage").build();
	}

	// lyy �޸��½� ���ã����һ��������ʷaddOneTransHistory
	@Override
	public Response addOneTransHistory(TransHistory transHistory) {
		System.out.println("ִ�����������addOneTransHistory");
		TransPackage transPackage = transHistory.getPkg();
		transHistory.setActTime(getCurrentDate());
		if (transPackage == null) {
			return Response.ok("�Ѱ��������ڣ�").header("EntityClass", "N_TransPackage").build();
		}
		transHistoryDao.save(transHistory);
		return Response.ok(transHistory).header("EntityClass", "TransHistory").build();
	}

	// lyy �½� ���ã���ȡһ��������transHistorylist
	@Override
	public Set<TransHistory> getTransHistoryFromList(String pkgId) {
		System.out.println("ִ�����������getTransHistoryFromList");
		TransPackage transPackage = transPackageDao.get(pkgId);
		Set<TransHistory> transHistoryList = transPackage.getHistory();
		return transHistoryList;
	}

//�������
	// lyy �޸�
	@Override
	public Response newTransPackage(TransPackage transPackage) {

		TransPackage tpk = null;
		try {
			tpk = transPackageDao.get(transPackage.getID());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (tpk != null) {
			return Response.ok("����id�Ѵ���").header("EntityClass", "E_TransPackage").build();
		}
		try {
			transPackageDao.save(transPackage);
			return Response.ok(transPackage).header("EntityClass", "TransPackage").build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	// lyy ����saveTransPackage

	public Response saveOneTransPackage(TransPackage transPackage) {
		try {
			transPackageDao.save(transPackage);
		} catch (Exception e) {
			// TODO: handle exception
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok(transPackage).header("EntityClass", "R_TransPackage").build();
	}

	// lyy �½� ȷ�ϰ������ı����״̬
	@Override
	public Response accPkgAndChangStatus(String pkgId) {

		System.out.println("ִ�����������accPkgAndChangStatus");
		TransPackage transPackage = transPackageDao.get(pkgId);
		if (transPackage == null) {
			return Response.ok("�Ѱ��������ڣ�").header("EntityClass", "N_TransPackage").build();
		}
		transPackage.setStatus(3); // 3����ת��������ȷ�ϡ�
		transPackageDao.save(transPackage);
		return Response.ok(transPackage).header("EntityClass", "TransPackage").build();
	}

	// lyy ���� �ı����״̬Ϊstatus
	public Response changeTransPackageStatus(TransPackage transPackage, int status) {
		System.out.println("ִ�������������changeTransPackageStatus" + transPackage.toString() + status);
		try {
			transPackage.setStatus(status);
			transPackageDao.save(transPackage);
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok(transPackage).header("EntityClass", "TransPackage").build();
	}

	// lyy �½�
	public Response saveTransHistoryList(ListTransHistory transHistories) {
		System.out.println("ִ�������������saveTransHistoryList" + transHistories.toString());
		Date date = getCurrentDate();
		List<TransHistory> transHistories2 = transHistories.getTransHistoryList();
		TransPackage transPackage = null;
		try {
			for (TransHistory transHistory : transHistories2) {
				transHistory.setActTime(date);
				transHistoryDao.save(transHistory);
//					//�ı����״̬
//					transPackage = transHistory.getPkg();
//					transPackage.setStatus(TransPackage.PKG_TRSNSIT);
//					transPackageDao.save(transPackage);
			}
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}

		return Response.ok("��ӳɹ�").header("EntityClass", "successed").build();
	}

	// lyy���� �ı�����б������а���״̬Ϊstatus
	public Response changeTransPackageListStatus(ListTransPackge listTransPackge, int status) {
		System.out.println("ִ�������������changeTransPackageListStatus" + status);
		List<TransPackage> transPackages = listTransPackge.getTransPackageList();
		try {
			if (transPackages.size() != 0) {
				for (TransPackage transPackage : transPackages) {
					// System.out.println(transPackage.toString());
					transPackage.setStatus(status);
					transPackageDao.save(transPackage);
				}
			}
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}

		return Response.ok("�ı�ɹ�").header("EntityClass", "successed").build();

	}

	// lyy ���� �ı�����б���Ŀ����״̬
	public Response changeExpressStatusInTransPackageList(ListTransPackge listTransPackge, int yuan_status,
			int mubiao_status) {
		System.out.println("ִ�������������changeExpressStatusInTransPackageList" + yuan_status);
		List<TransPackage> transPackages = listTransPackge.getTransPackageList();
		try {
			if (transPackages.size() != 0) {
				for (TransPackage transPackage : transPackages) {
					// System.out.println(transPackage.toString());
					// һ�������ҵ��ܶ���
					List<TransPackageContent> list;
					list = transPackageContentDao.getTransPackageContents(transPackage, yuan_status);
					if (list.size() != 0) {
						for (TransPackageContent pc : list) { // ���ڰ������ÿһ������ı���״̬
							// System.out.println("ִ�������������"+pc.toString());
							ExpressSheet es = pc.getExpress();
							es.setStatus(mubiao_status); // �ı���״̬
							expressSheetDao.save(es);
						}
					}

				}
			}
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}

		return Response.ok("�ı�ɹ�").header("EntityClass", "successed").build();

	}

	// lyy ����d
	public Response getRecentOneTranHistory(TransPackage transPackage) {
		System.out.println("ִ�����������getRecentOneTranHistory");
		List<TransHistory> transHistories = null;
		TransHistory transHistory = null;
		try {
			transHistories = transHistoryDao.getPkgListOrderByAccTime(transPackage);
			transHistory = transHistories.get(0);
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok(transHistory).header("EntityClass", "TransHistory").build();
	}

	// lyy ����
	public Response saveOnePackageRoute(PackageRoute packageRoute) {
		System.out.println("ִ�����������saveOnePackageRoute" + packageRoute.toString());
		try {
			packageRoute.setTm(getCurrentDate());
			packageRouteDao.save(packageRoute);
		} catch (Exception e) {
			// TODO: handle exception
			return Response.serverError().entity(e.getMessage()).build();
		}

		return Response.ok(packageRoute).header("EntityClass", "PackageRoute").build();
	}

	// lyy����
	@Override
	public Response saveListPackageRoute(ListTransPackge tListTransPackge, float x, float y) {
		System.out.println("ִ�����������saveListPackageRoute" + tListTransPackge.toString() + "/" + x + "/" + y);
		List<TransPackage> lTransPackages = tListTransPackge.getTransPackageList();
		Date date = getCurrentDate();
		try {
			for (TransPackage transPackage : lTransPackages) {
				PackageRoute packageRoute = new PackageRoute();
				packageRoute.setPkg(transPackage);
				packageRoute.setTm(date);
				packageRoute.setX(x);
				packageRoute.setY(y);
				packageRouteDao.save(packageRoute);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return Response.serverError().entity(e.getMessage()).build();
		}

		return Response.ok("�޸ĳɹ�").header("EntityClass", "ListPackageRoute").build();
	}

	// lyy ����
	public Response getTransPackageContent(String pkgID, String expressID) {
		TransPackageContent transPackageContent = null;
		try {
			transPackageContent = transPackageContentDao.get(expressID, pkgID);
		} catch (Exception e) {
			// TODO: handle exception
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok(transPackageContent).header("EntityClass", "TransPackageContent").build();
	}

	// lyy ����
	public Response changeExpressStatusInPackage(String pkgID, String expressID, int status) {
		TransPackageContent transPackageContent = null;
		try {
			transPackageContent = transPackageContentDao.get(expressID, pkgID);
			transPackageContent.setStatus(status);
			transPackageContentDao.save(transPackageContent);
		} catch (Exception e) {
			// TODO: handle exception
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok(transPackageContent).header("EntityClass", "TransPackageContent").build();
	}

	// lyy ����
	public Response changeExpressStatusInTransPackage(String pkgID, int status) {
		try {
			List<ExpressSheet> lExpressSheets = expressSheetDao.getListInPackage(pkgID);
			for (ExpressSheet es : lExpressSheets) {
				TransPackageContent transPackageContent = transPackageContentDao.get(es.getID(), pkgID);
				transPackageContent.setStatus(status);
				transPackageContentDao.save(transPackageContent);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok("�ı�״̬�ɹ���").header("EntityClass", "ChangeExpressStatus").build();
	}

	// lyy ����
	public List<TransHistoryDetail> getTransHistoryDetailList(String expressID) {
		ExpressSheet es = expressSheetDao.get(expressID);
		List<TransPackage> transPackages = transPackageDao.findbyExpressSheetIdList(expressID);
		List<TransHistoryDetail> transHistoryDetails = new ArrayList<TransHistoryDetail>();
		int count = 0;
		if (es.getStatus() == ExpressSheet.STATUS.STATUS_CREATED) {
			TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
			transHistoryDetail.setExpressSheet(es);
			transHistoryDetail.setSN(count++);
			transHistoryDetails.add(transHistoryDetail);
		} else if (es.getStatus() == ExpressSheet.STATUS.STATUS_DAIZHUAYUN) {
			for (int i = 0; i <= 1; i++) {
				TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
				transHistoryDetail.setExpressSheet(es);
				transHistoryDetail.setSN(count++);
				transHistoryDetails.add(transHistoryDetail);
			}
		} else if (es.getStatus() == ExpressSheet.STATUS.STATUS_TRANSPORT) {
			for (int i = 0; i <= 1; i++) {
				TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
				transHistoryDetail.setExpressSheet(es);
				transHistoryDetail.setSN(count++);
				transHistoryDetails.add(transHistoryDetail);
			}
			for (TransPackage transPackage : transPackages) {
				List<TransHistory> transHistories = transHistoryDao.getPkgListOrderByAscTime(transPackage);
				for (TransHistory transHistory : transHistories) {
					TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
					transHistoryDetail.setExpressSheet(es);
					UserInfo uidfrom = userInfoDao.get(transHistory.getUIDFrom());
					UserInfo uidto = userInfoDao.get(transHistory.getUIDTo());
					TransNode fromNode = transNodeDao.get(uidfrom.getDptID());
					TransNode toNode = transNodeDao.get(uidto.getDptID());
					transHistoryDetail.setFromNode(fromNode);
					transHistoryDetail.setToNode(toNode);
					transHistoryDetail.setTransHistory(transHistory);
					transHistoryDetail.setUIDFrom(uidfrom);
					transHistoryDetail.setUIDTo(uidto);
					transHistoryDetail.setSN(count++);
					transHistoryDetails.add(transHistoryDetail);
				}
			}
		} else if (es.getStatus() == ExpressSheet.STATUS.STATUS_PAISONG) {
			for (int i = 0; i <= 1; i++) {
				TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
				transHistoryDetail.setExpressSheet(es);
				transHistoryDetail.setSN(count++);
				transHistoryDetails.add(transHistoryDetail);
			}
			for (TransPackage transPackage : transPackages) {
				List<TransHistory> transHistories = transHistoryDao.getPkgListOrderByAscTime(transPackage);
				for (TransHistory transHistory : transHistories) {
					TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
					transHistoryDetail.setExpressSheet(es);
					UserInfo uidfrom = userInfoDao.get(transHistory.getUIDFrom());
					UserInfo uidto = userInfoDao.get(transHistory.getUIDTo());
					TransNode fromNode = transNodeDao.get(uidfrom.getDptID());
					TransNode toNode = transNodeDao.get(uidto.getDptID());
					transHistoryDetail.setFromNode(fromNode);
					transHistoryDetail.setToNode(toNode);
					transHistoryDetail.setTransHistory(transHistory);
					transHistoryDetail.setUIDFrom(uidfrom);
					transHistoryDetail.setUIDTo(uidto);
					transHistoryDetail.setSN(count++);
					transHistoryDetails.add(transHistoryDetail);
				}
			}
			TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
			transHistoryDetail.setUIDFrom(userInfoDao.get(Integer.getInteger(es.getDeliver())));
			transHistoryDetail.setSN(count++);
			transHistoryDetails.add(transHistoryDetail);

		} else if (es.getStatus() == ExpressSheet.STATUS.STATUS_DELIVERIED) {
			for (int i = 0; i <= 1; i++) {
				TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
				transHistoryDetail.setExpressSheet(es);
				transHistoryDetail.setSN(count++);
				transHistoryDetails.add(transHistoryDetail);
			}
			for (TransPackage transPackage : transPackages) {
				List<TransHistory> transHistories = transHistoryDao.getPkgListOrderByAscTime(transPackage);
				for (TransHistory transHistory : transHistories) {
					TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
					transHistoryDetail.setExpressSheet(es);
					UserInfo uidfrom = userInfoDao.get(transHistory.getUIDFrom());
					UserInfo uidto = userInfoDao.get(transHistory.getUIDTo());
					TransNode fromNode = transNodeDao.get(uidfrom.getDptID());
					TransNode toNode = transNodeDao.get(uidto.getDptID());
					transHistoryDetail.setFromNode(fromNode);
					transHistoryDetail.setToNode(toNode);
					transHistoryDetail.setTransHistory(transHistory);
					transHistoryDetail.setUIDFrom(uidfrom);
					transHistoryDetail.setUIDTo(uidto);
					transHistoryDetail.setSN(count++);
					transHistoryDetails.add(transHistoryDetail);
				}
			}
			TransHistoryDetail transHistoryDetail = new TransHistoryDetail();
			transHistoryDetail.setUIDFrom(userInfoDao.get(Integer.getInteger(es.getDeliver())));
			transHistoryDetail.setSN(count++);
			transHistoryDetails.add(transHistoryDetail);

			TransHistoryDetail transHistoryDetail1 = new TransHistoryDetail();
			transHistoryDetail1.setUIDFrom(userInfoDao.get(Integer.getInteger(es.getDeliver())));
			transHistoryDetail.setSN(count++);
			transHistoryDetails.add(transHistoryDetail1);
		}

		return transHistoryDetails;

	}

	//tzx
	@Override
	public List<ExpressSheet> getExpressListbytransnode(String id,int status) {
		List<ExpressSheet> expressSheets = expressSheetDao.findBy("status", status, "ID", true);
		System.out.println(id);
		TransNode transNode = transNodeDao.get(id);
		String regionCode = transNode.getRegionCode();
		for (int i = 0; i < expressSheets.size(); i++) {
			if (status == 0) {
				if (!regionCode.equals(expressSheets.get(i).getSender().getRegionCode())) {
					expressSheets.remove(i);
					i--;
				}
			}else if (status == 5) {
				if (!regionCode.equals(expressSheets.get(i).getRecever().getRegionCode())) {
					expressSheets.remove(i);
					i--;
				}
			}
		}
		return expressSheets;
	}

	// ldq��ѯ������route
	@Override
	public List<PackageRoute> getPackageRoute(String packageID) {
		List<PackageRoute> packageRoutes = packageRouteDao.getAll();
		List<PackageRoute> Routes = new ArrayList<>();
		System.out.println(packageID);
		for (int i = 0; i < packageRoutes.size(); i++) {
			System.out.println(packageRoutes.get(i).getPkg().getID());
			if (packageRoutes.get(i).getPkg().getID().equals(packageID)) {
				Routes.add(packageRoutes.get(i));
			}
		}
		return Routes;
	}
	
	//lyy ����
	@Override
	public List<PackageRoute> getPackageRouteListByExpressId(String expressID){
		List<PackageRoute> packageRoutes= new ArrayList<PackageRoute>();
		List<TransPackage> transPackages = transPackageDao.findbyExpressSheetIdList(expressID);
		for(TransPackage transPackage:transPackages) {
			List<PackageRoute> pRoutes = packageRouteDao.getPackageRouteListByPkg(transPackage);
			packageRoutes.addAll(pRoutes);
		}
		
		return packageRoutes;
	}
	

	// ldq ͨ���˿���������ϵ��ʽģ����ѯ�˵�
	@Override
	public List<ExpressSheet> getExpressListbyCustomerinfo(String info) {
		List<ExpressSheet> expressSheets = new ArrayList<ExpressSheet>();
		List<CustomerInfo> customerInfos = new ArrayList<CustomerInfo>();
		System.out.println(info);
		if (info.matches("[0-9]+")) {// infoΪ����
			customerInfos = customerInfoDao.findLike("telCode", info+ "%", "ID", true);
		} else {
			customerInfos = customerInfoDao.findLike("name", info+ "%", "ID", true);
		}
		System.out.println(customerInfos);//���е�����
		for (CustomerInfo customerInfo : customerInfos) {
			expressSheets.addAll(expressSheetDao.findBy("recever", customerInfo, "ID", true));
			expressSheets.addAll(expressSheetDao.findBy("sender", customerInfo, "ID", true));
		}
		return expressSheets;
	}
	// ldq ����ͨ������id��ѯ���еĿ��
	@Override
	public List<ExpressSheet> getExpressListbytranspackageId(String transpackageId) {
		List<TransPackageContent> transPackageContents =  transPackageContentDao.findBy("pkg", transPackageDao.get(transpackageId), "SN", true);
		List<ExpressSheet> expressSheets = new ArrayList<ExpressSheet>();
		for (TransPackageContent transPackageContent : transPackageContents) {
			expressSheets.add(transPackageContent.getExpress());
		}
		return expressSheets;
	}

}
