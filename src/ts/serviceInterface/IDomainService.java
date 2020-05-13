package ts.serviceInterface;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ts.model.ExpressSheet;
import ts.model.ListTransHistory;
import ts.model.ListTransPackge;
import ts.model.PackageRoute;
import ts.model.TransHistory;
import ts.model.TransHistoryDetail;
import ts.model.TransPackage;

@Path("/Domain")	//ҵ�����
public interface IDomainService {
    //����������ʽӿ�=======================================================================
	
	//ldq��ȡ�����˵���Ϣ
	@GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getAllExpressList") 
	public List<ExpressSheet> getAllExpressList();
	
	//ldqɾ����ѡ�˵���Ϣ
	@GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/deleteExpressSheet/{id}") 
	public Response deleteExpressSheet(@PathParam("id")String id);
	
	//ldqͨ���ջ���������ѯ����
	@GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getExpressSheetbyrecevername/{recevername}") 
	public List<ExpressSheet> getExpressSheetbyrecevername(@PathParam("recevername")String recevername);

	
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getExpressList/{Property}/{Restrictions}/{Value}") 
	public List<ExpressSheet> getExpressList(@PathParam("Property")String property, @PathParam("Restrictions")String restrictions, @PathParam("Value")String value);

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressListInPackage/PackageId/{PackageId}") 
	public List<ExpressSheet> getExpressListInPackage(@PathParam("PackageId")String packageId);

    @GET
    @Produces(MediaType.APPLICATION_JSON )
    @Path("/getExpressSheet/{id}") 
	public Response getExpressSheet(@PathParam("id")String id);

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/newExpressSheet/id/{id}/uid/{uid}") 
	public Response newExpressSheet(@PathParam("id")String id, @PathParam("uid")int uid);
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/saveExpressSheet") 
	public Response saveExpressSheet(ExpressSheet obj);
    
    //ldq�༭�˵�
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/editExpressSheet") 
	public Response editExpressSheet(ExpressSheet obj);
    
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/receiveExpressSheetId/id/{id}/uid/{uid}") 
	public Response ReceiveExpressSheetId(@PathParam("id")String id, @PathParam("uid")int uid);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/dispatchExpressSheetId/id/{id}/uid/{uid}") 
	public Response DispatchExpressSheet(@PathParam("id")String id, @PathParam("uid")int uid);
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/deliveryExpressSheetId/id/{id}/uid/{uid}") 
	public Response DeliveryExpressSheetId(@PathParam("id")String id, @PathParam("uid")int uid);

    //�����������ʽӿ�=======================================================================
    
    //ldq��ȡ���а�����Ϣ
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getAllTransPackage") 
	public List<TransPackage> getAllTransPackage();
    
    @GET	
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getTransPackageList/{Property}/{Restrictions}/{Value}") 
	public List<TransPackage> getTransPackageList(@PathParam("Property")String property, @PathParam("Restrictions")String restrictions, @PathParam("Value")String value);

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTransPackage/{id}") 
	public Response getTransPackage(@PathParam("id")String id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/newTransPackage") 
    public Response newTransPackage(TransPackage transPackage);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/saveTransPackage") 
	public Response saveTransPackage(TransPackage obj);
    
    //ldq �༭������Ϣ
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/editTransPackage") 
	public Response editTransPackage(TransPackage obj);
    
    
  //�����ʷ���ʽӿ�=======================================================================
    //ldq ͨ�����id��ѯ�˿�����ڵİ���
    @GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/findTransPackagebyExpressSheetId/{id}") 
	public List<TransPackage> findTransPackagebyExpressSheetId(@PathParam("id")String id);
    
    //ldq ͨ������id��ѯ������ʷ
      @GET
      @Produces({MediaType.APPLICATION_JSON })
      @Path("/getTransHistory/{id}") 
  	public List<TransHistory> getTransHistory(@PathParam("id")String id);
    
//  lyy �� �޸�
  //�������ӵ�������
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/MoveExpressIntoPackage/{id}/{targetPkgId}") 
  public Response MoveExpressIntoPackage(@PathParam("id")String id,@PathParam("targetPkgId")String targetPkgId);
  
  
  //lyy�޸�
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/MoveExpressFromPackage/{id}/{targetPkgId}") 
  public Response MoveExpressFromPackage(@PathParam("id")String id, @PathParam("targetPkgId")String sourcePkgId);
  
  //lyy����
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/DeleteExpressIntoPackage/{id}/{targetPkgId}") 
  public Response DeleteExpressFromPackage(@PathParam("id")String id, @PathParam("targetPkgId")String sourcePkgId);
  
  
  //lyy����
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/addOneTransHistory")
  public Response addOneTransHistory(TransHistory transHistory);
  
//  //lyy ����
//  @GET
//  @Consumes(MediaType.APPLICATION_JSON)
//  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//  @Path("/getTransHistory/{pkgId}")
//  public Response getTransHistory(@PathParam("pkgId")String pkgId);
  
  //lyy����
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/getTransHistoryFromList/{targetPkgId}")
  public Set<TransHistory> getTransHistoryFromList(@PathParam("targetPkgId")String pkgId);
  
  
  //lyy ���� ���ܰ������ı����״̬
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/accPkgAndChangStatus/{pkgId}")
  public Response accPkgAndChangStatus(@PathParam("pkgId")String pkgId);
  
  //lyy ��������transhistory�б�
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/saveTransHistoryList")
  public Response saveTransHistoryList(ListTransHistory transHistories);

  //lyy����
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/changeTransPackageStatus/{status}")
  public Response changeTransPackageStatus(TransPackage transPackage,@PathParam("status")int status);
  
  //lyy ����
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/changeTransPackageListStatus/{status}")
  public Response changeTransPackageListStatus(ListTransPackge listTransPackge,@PathParam("status")int status);
  
//lyy ����
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/changeExpressStatusInTransPackageList/{yuan_status}/{mubiao_status}")
  public Response changeExpressStatusInTransPackageList(ListTransPackge listTransPackge,@PathParam("yuan_status") int yuan_status,@PathParam("mubiao_status") int mubiao_status);
  //lyy �����õ������һ��history��¼
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/getRecentOneTranHistory")
  public Response getRecentOneTranHistory(TransPackage transPackage);
  
//tzx ��������Ų��Ҹ�����������״̬Ϊstatus�Ŀ��
	@GET
    @Produces({MediaType.APPLICATION_JSON })
    @Path("/getExpressListbytransnode/{transnodeid}/{status}") 
	public List<ExpressSheet> getExpressListbytransnode(@PathParam("transnodeid") String id,@PathParam("status") int status);
  
  //lyy ����saveOneTransPackge
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/saveOneTransPackage")
  public Response saveOneTransPackage(TransPackage transPackage);
  
  //lyy ���� ����packageRoute
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/saveOnePackageRoute")
  public Response saveOnePackageRoute(PackageRoute packageRoute);
  
  //��������listpacakgeroute
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/saveListPackageRoute/{x}/{y}")
  public Response saveListPackageRoute(ListTransPackge tListTransPackge,@PathParam("x") float x,@PathParam("y")float y);
  
  //lyy ���� ��ȡpacakgeContent
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/getTransPackageContent/{pkgID}/{expressID}")
  public Response getTransPackageContent(@PathParam("pkgID")String pkgID,@PathParam("expressID")String expressID);
  
  //lyy �����ı������Ŀ��״̬
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/changeExpressStatusInPackage/{pkgID}/{expressID}/{status}")
  public Response changeExpressStatusInPackage(@PathParam("pkgID")String pkgID,@PathParam("expressID")String expressID,@PathParam("status")int status);
  
  
  //lyy ����
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/changeExpressStatusInTransPackage/{pkgID}/{status}")
  public Response changeExpressStatusInTransPackage(@PathParam("pkgID")String pkgID,@PathParam("status")int status);
  
  //lyy ����
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  @Path("/getTransHistoryDetailList/{expressID}")
  public List<TransHistoryDetail> getTransHistoryDetailList(@PathParam("expressID")String expressID);
  
//ldq ������ѯ������route
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON )
  @Path("/getPackageRoute/{packageID}")
  public List<PackageRoute> getPackageRoute(@PathParam("packageID")String packageID);
  
  //lyy ����
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON )
  @Path("/getPackageRouteListByExpressId/{expressID}")
  public List<PackageRoute> getPackageRouteListByExpressId(@PathParam("expressID")String expressID);
  
//ldq ����ͨ���˿���Ϣģ����ѯ�˵�
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON )
  @Path("/getExpressListbyCustomerinfo/{info}")
  public List<ExpressSheet> getExpressListbyCustomerinfo(@PathParam("info")String info);
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
