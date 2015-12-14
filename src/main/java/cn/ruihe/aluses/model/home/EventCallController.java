package cn.ruihe.aluses.model.home;

import cn.ruihe.aluses.common.MessageException;
import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.common.message.ApiResult;
import cn.ruihe.aluses.entity.BrcUserInfo;
import cn.ruihe.aluses.entity.CliPartake;
import cn.ruihe.aluses.entity.SysEventCallArticle;
import cn.ruihe.aluses.entity.SysEventCallTheme;
import cn.ruihe.aluses.entity.SysUser;
import cn.ruihe.aluses.model.home.interceptor.AllowRole;
import cn.ruihe.aluses.model.home.service.EventCallService;
import cn.ruihe.aluses.model.home.service.ProjectService;
import cn.ruihe.aluses.model.home.vo.SimpleProjectOutput;
import cn.ruihe.utils.BrcUserInfoUtils;
import cn.ruihe.utils.ImagesUP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


/**
 * @Author dhc
 * @Date 2015-07-21 13-45
 */
@Controller
@RequestMapping("ec")
public class EventCallController {
    @Autowired
    EventCallService service;

    @Autowired
    ProjectService projectService;
    
    @Autowired
    HttpServletRequest request;
    
    @Autowired
    ServletResponse request1;
    
    @Autowired
    HttpServletResponse response;

    @AllowRole(1)
    @RequestMapping(value = "themes", method = RequestMethod.GET)
    public String showThemes(Model model) {
        List<SysEventCallTheme> themes = service.getThemes();
        model.addAttribute("themes", themes);
        return "admin/ec_themes";
    }

    @AllowRole(1)
    @RequestMapping(value = "themes/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<SysEventCallTheme> showThemes(@PathVariable int id) {
        SysEventCallTheme theme = service.getTheme(id);
        return ApiResult.SUCCESS(theme);
    }

    @AllowRole(1)
    @RequestMapping(value = "themes", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult<SysEventCallTheme> updateThemes(SysEventCallTheme input) {
        SysEventCallTheme theme;
        if(input.getId() == null) {
            input.setParentid(0);
            theme = service.addTheme(input);
        } else {
            theme = service.updateTheme(input);
        }
        return ApiResult.SUCCESS(theme);
    }

    @AllowRole(1)
    @RequestMapping(value = "themes/{id}/status", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult changeThemeStatus(@PathVariable int id, @RequestParam int status) {
        int result = service.changeThemeStatus(id, status);
        return ApiResult.SUCCESS(result);
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getEventCalls(HttpSession session, Model model, @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "key", required = false) String key, @RequestParam(value = "project", required = false) Integer project) {
        if(page == null) {
            page = 1;
        }
        SysUser user = (SysUser) session.getAttribute("user");
        Integer projectid;
        if(user.getRole() == 1 && project != null && project != 0) {
            projectid = project;
        } else {
            projectid = user.getProject();
        }
        Map<String, String[]> pms = new HashMap<>();
        if(project != null) {
            pms.put("project", new String[]{project.toString()});
        }
        if(!StringUtils.isEmpty(key)) {
            pms.put("key", new String[]{key});
        }
        Pager<SysEventCallArticle> pager = service.getArticles(projectid, page, 12, key);
        List<SysEventCallTheme> themes = service.getThemes();
        List<SimpleProjectOutput> projects = projectService.getSimpleProjects();
        List<SysEventCallArticle> sysEventCallArticle=pager.getDatas();
        for(SysEventCallArticle ec:sysEventCallArticle)
        {
        	for(SimpleProjectOutput pj:projects)
        	{
        		if(ec.getProject()==pj.getId())
        		{
        			ec.setResult(pj.getName());
        			continue;
        		}
        	}
        }
        pager.setDatas(sysEventCallArticle);
        model.addAttribute("ats", pager.getDatas());
        model.addAttribute("pager", pager.pagesLink(pms));
        model.addAttribute("themes", themes);
        model.addAttribute("projects", projects);
        model.addAttribute("project", projectid == null ? 0 : projectid);
        model.addAttribute("key", key);
        if(!StringUtils.isEmpty(key)&&(pager==null||pager.getSize()==0))
        {
        	throw new MessageException("没有相关信息", 400);
        }
        return "admin/ec";
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "article", method = RequestMethod.GET)
    public String getEventCall(Model model, @RequestParam(required = false) Integer id) {
        List<SysEventCallTheme> themes = service.getThemes();
        model.addAttribute("themes", themes);
        if(id == null) {
            List<SimpleProjectOutput> projects = projectService.getSimpleProjects();
            model.addAttribute("projects", projects);
            return "admin/ec_article_add";
        } else {
            SysEventCallArticle article = service.getArticle(id);
            SimpleProjectOutput pj = projectService.getSimpleProject(article.getProject());
            model.addAttribute("a", article);
            model.addAttribute("pj", pj);
            
            return "admin/ec_article";
        }
    }
    
    @AllowRole({1, 4})
    @RequestMapping(value = "result", method = RequestMethod.GET)
    public String getEventCallResult(Model model, @RequestParam(required = false) Integer id) {
        List<SysEventCallTheme> themes = service.getThemes();
        model.addAttribute("themes", themes);
        SysEventCallArticle article = service.getArticle(id);
        model.addAttribute("a", article);
        return "admin/ec_result";
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "result", method = RequestMethod.POST)
    @ResponseBody
    public void updateActicleResult(SysEventCallArticle sysEventCallArticle) {
    	
    	String imagesName = "mate_images";
    	SysEventCallArticle input = new SysEventCallArticle();
    	Date date=new Date();
    	PrintWriter out = null;
    	input.setId(sysEventCallArticle.getId());
    	String url=request.getContextPath()+"/ec/result"+"?id="+input.getId();
		try {
			request1.setCharacterEncoding("utf-8");
			request1.setContentType("text/html;charset=utf-8");
			out = request1.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        input.setEndtime(sysEventCallArticle.getEndtime());
        
    	System.out.println(input.getEndtime().getTime());
    	System.out.println(date.getTime());
    	if(input.getEndtime()!=null&&input.getEndtime().getTime() > date.getTime()) {
    		out.println("<script>alert('活动未结束，不能提交!');window.location.href = '"+url+"'</script>");
    		out.flush();
            out.close();
            return;
        }
    	if(sysEventCallArticle.getResult()==null||sysEventCallArticle.getResult().equals("")) {
    		out.println("<script>alert('总结不能为空,请填写!');window.location.href = '"+url+"'</script>");
    		out.flush();
            out.close();
            return;
        }
    	
       
        SysEventCallArticle article;
        
     	input.setResult(sysEventCallArticle.getResult());
     	System.out.println(input.getId());
     	System.out.println(input.getResult());
     	
        article = service.updateArticle(input);
        System.out.println(article.toString());
//        return ApiResult.SUCCESS(article);
        
        out.println("<script>alert('操作成功!');window.location.href = '"+url+"'</script>");
        
        out.flush();
        out.close();
        
    }

    
//    @AllowRole({1, 4})
//    @RequestMapping(value = "article", method = RequestMethod.POST)
//    @ResponseBody
//    public ApiResult<SysEventCallArticle> updateActicle(SysEventCallArticle sysEventCallArticle) {
//    	String imagesName = "/mate_images";
//    	SysEventCallArticle input = new SysEventCallArticle();
//    	try {
//    		input.setBegintime(sysEventCallArticle.getBegintime());
//    		input.setContent(sysEventCallArticle.getContent());;
//    		input.setEid(sysEventCallArticle.getEid());
//    		input.setEndtime(sysEventCallArticle.getEndtime());
//    		input.setEnrollendtime(sysEventCallArticle.getEnrollendtime());
//    		input.setIsonlinepay(sysEventCallArticle.getIsonlinepay());
//    		input.setMoney(sysEventCallArticle.getMoney());
//    		input.setProject(sysEventCallArticle.getProject());
//    		input.setRegion(sysEventCallArticle.getRegion());
//    		input.setTelephone(sysEventCallArticle.getTelephone());
//    		input.setTitle(sysEventCallArticle.getTitle());
//    		input.setUid(sysEventCallArticle.getUid());
//    		input.setBegintime(sysEventCallArticle.getBegintime());
//    		input.setUname(sysEventCallArticle.getUname());
//    		input.setUpperlimit(sysEventCallArticle.getUpperlimit());
//    		input.setAddress(sysEventCallArticle.getAddress());
//    		input.setPhone(sysEventCallArticle.getPhone());
//    		input.setStatus(sysEventCallArticle.getStatus());
//    		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	
//    	if(input.getEndtime()!=null&&input.getBegintime()!=null&&input.getEndtime().getTime() < input.getBegintime().getTime()) {
//            throw new MessageException("结束日期不能小于开始日期", 400);
//        }
//    	if(input.getEnrollendtime()!=null&&input.getEndtime()!=null&&input.getEnrollendtime().getTime() > input.getEndtime().getTime()) {
//            throw new MessageException("截止日期不能大于结束日期", 400);
//        }
//        if(input.getIsonlinepay() == 0) {
//            input.setMoney(0.0);
//        }
//        input.setUptime(new Date());
//        input.setRegion(1);
//        SysEventCallArticle article;
//        
//        
//        if(input.getId() == null) {
//            article = service.addArticle(input);
//        } else {
//            article = service.updateArticle(input);
//        }
//        System.out.println(article.toString());
//        return ApiResult.SUCCESS(article);
//    }
    
    @AllowRole({1, 4})
    @RequestMapping(value = "article", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult<SysEventCallArticle> updateActicle(SysEventCallArticle sysEventCallArticle,@RequestParam("img") MultipartFile[] files) {
    	
    	String imagesName = "/mate_images";
    	SysEventCallArticle input = new SysEventCallArticle();
    	try {
    		input.setBegintime(sysEventCallArticle.getBegintime());
    		input.setContent(sysEventCallArticle.getContent());;
    		input.setEid(sysEventCallArticle.getEid());
    		input.setEndtime(sysEventCallArticle.getEndtime());
    		input.setEnrollendtime(sysEventCallArticle.getEnrollendtime());
    		//input.setIsonlinepay(sysEventCallArticle.getIsonlinepay());
    		//input.setMoney(sysEventCallArticle.getMoney());
            input.setIsonlinepay(0);
    		input.setMoney(0.0);
    		input.setProject(sysEventCallArticle.getProject());
    		input.setRegion(sysEventCallArticle.getRegion());
    		input.setTelephone(sysEventCallArticle.getTelephone());
    		input.setTitle(sysEventCallArticle.getTitle());
    		input.setUid(sysEventCallArticle.getUid());
    		input.setBegintime(sysEventCallArticle.getBegintime());
    		input.setUname(sysEventCallArticle.getUname());
    		input.setUpperlimit(sysEventCallArticle.getUpperlimit());
    		input.setAddress(sysEventCallArticle.getAddress());
    		input.setPhone(sysEventCallArticle.getPhone());
    		input.setStatus(sysEventCallArticle.getStatus());
    		System.out.println("imagesName="+imagesName);
    		String pathStr = request.getSession().getServletContext().getRealPath(imagesName);//上传的目录
    	    System.out.println("pathStr="+pathStr);
    	    String pictures=ImagesUP.addImage(request, imagesName, files );
    	    System.out.println(pictures);
    	    if(pictures.length()>0)
    	    {
    	    	input.setPictures(pictures); 
    	    	input.setSmallpic(input.getPictures());
    	    }
    		
		} catch (Exception e) {
			e.printStackTrace();
		}

    	if(input.getEndtime()!=null&&input.getBegintime()!=null&&input.getEndtime().getTime() < input.getBegintime().getTime()) {
            throw new MessageException("结束日期不能小于开始日期", 400);
        }
    	if(input.getEnrollendtime()!=null&&input.getEndtime()!=null&&input.getEnrollendtime().getTime() > input.getEndtime().getTime()) {
            throw new MessageException("截止日期不能大于结束日期", 400);
        }
        

        /*if(input.getIsonlinepay() == 0) {
            input.setMoney(0.0);
        }*/
        input.setUptime(new Date());
        input.setRegion(1);
        input.setId(sysEventCallArticle.getId());
        SysEventCallArticle article;
        
        System.out.println("id="+input.getId());
        
        if(sysEventCallArticle.getId()==null) {
        	System.out.println("add");
            article = service.addArticle(input);
        } else {
        	System.out.println("update");
            article = service.updateArticle(input);
        }
        return ApiResult.SUCCESS(article);
    }
    
    
    @AllowRole({1, 4})
    @RequestMapping(value = "article/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult changeArticleStatus(@PathVariable int id, @RequestParam int status) {
        int result = service.changeArticleStatus(id, status);
        return ApiResult.SUCCESS(result);
    }


    @AllowRole({1, 4})
    @RequestMapping(value = "ttake", method = RequestMethod.GET)
    public String getPartakes(Model model, @RequestParam(value = "page", required = false) Integer page, @RequestParam int eid,
    		@RequestParam(value = "key", required = false) String key, @RequestParam(value = "project", required = false) Integer project) {
    	if(page == null) {
           page = 1;
        }
    	Map<String, String[]> pms = new HashMap<>();
        if(project != null && project != 0) {
            pms.put("project", new String[]{project.toString()});
        }
        if(!StringUtils.isEmpty(key)) {
            pms.put("key", new String[]{key});
        } else {
            key = null;
        }
        
        pms.put("eid", new String[]{eid+""});
        
    	SysEventCallArticle article = service.getArticle(eid);
    	Pager<CliPartake> ttakes = service.getPartakes(eid, page, 12);
    	List<CliPartake> takes=ttakes.getDatas();
        StringBuilder userIds = new StringBuilder();
        if(takes!=null&&takes.size()>0)
        {
        	for(int i=0;i<takes.size();i++)
        	{
        		userIds.append(takes.get(i).getUid()).append(',');
        	}
        	if(userIds.length()>1)
        	{
        		userIds.deleteCharAt(userIds.length()-1);
        	}
        }
        System.out.println(userIds);
        List<BrcUserInfo> brcUserInfoList=new ArrayList<BrcUserInfo>();
        brcUserInfoList=BrcUserInfoUtils.getBrcUserInfos(userIds.toString());
        
        for(CliPartake take:takes){
        	if (brcUserInfoList != null && !brcUserInfoList.isEmpty())
				for (BrcUserInfo brcUserInfo : brcUserInfoList) {
					if (take.getUid() == brcUserInfo.getIClientID()&&brcUserInfo.getSNickName()!=null) {
						take.setUname(brcUserInfo.getSNickName());
						break;
					}
				}
        }
        model.addAttribute("pager", ttakes.pagesLink(pms));
        model.addAttribute("article", article);
        model.addAttribute("takes", takes);
        return "admin/ec_takes";
    }

    @AllowRole({1, 4})
    @RequestMapping(value = "ttake/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult<Date> changeTtakeStatus(@PathVariable int id) {
        Date result = service.changeCliPartakeAudit(id);
        return ApiResult.SUCCESS(result);
    }
    
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    
    @AllowRole({1, 4})
    @RequestMapping(value = "ttake/{id}/delete")
    @ResponseBody
    public ApiResult deletePostReportpost(@PathVariable int id) {
        int result = service.deleteCliPartake(id);
        return ApiResult.SUCCESS(result);
    }
    
	  @AllowRole({1, 4})
	  @RequestMapping(value = "ttake/{id}/export")
	  public void getPartakes(@PathVariable int id) {
	      List<CliPartake> takes = service.getPartakes(id);
	      SysEventCallArticle article = service.getArticle(id);
	      StringBuilder userIds = new StringBuilder();
	      if(takes!=null&&takes.size()>0)
	      {
	      	for(int i=0;i<takes.size();i++)
	      	{
	      		userIds.append(takes.get(i).getUid()).append(',');
	      	}
	      	if(userIds.length()>1)
	      	{
	      		userIds.deleteCharAt(userIds.length()-1);
	      	}
	      }
	      System.out.println(userIds);
	      List<BrcUserInfo> brcUserInfoList=new ArrayList<BrcUserInfo>();
	      brcUserInfoList=BrcUserInfoUtils.getBrcUserInfos(userIds.toString());
	      
	      for(CliPartake take:takes){
	      	if (brcUserInfoList != null && !brcUserInfoList.isEmpty())
					for (BrcUserInfo brcUserInfo : brcUserInfoList) {
						if (take.getUid() == brcUserInfo.getIClientID()&&brcUserInfo.getSNickName()!=null) {
							take.setUname(brcUserInfo.getSNickName());
							break;
						}
					}
	      }
	    try 
	    { 
	    OutputStream os =response.getOutputStream();
	    response.reset();// 清空输出流   
	    response.setHeader("Content-disposition", "attachment; filename=fine.xls");// 设定输出文件头   
	    response.setContentType("application/msexcel");// 定义输出类型 
	        
	    WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件   
	    String tmptitle = article.getTitle()+"活动报名情况"; // 标题   
	    WritableSheet wsheet = wbook.createSheet(tmptitle, 0); // sheet名称  
	        
		// 设置excel标题   
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16,WritableFont.BOLD, 
		                       false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);   
		WritableCellFormat wcfFC = new WritableCellFormat(wfont); 
		wsheet.addCell(new Label(2, 0, tmptitle, wcfFC));   
		wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 14,WritableFont.BOLD, 
		                   false, UnderlineStyle.NO_UNDERLINE,Colour.BLACK);   
		wcfFC = new WritableCellFormat(wfont);  
	
		// 开始生成主体内容          
		wsheet.addCell(new Label(0, 1, "ID"));
		wsheet.addCell(new Label(1, 1, "申请者"));
		wsheet.addCell(new Label(2, 1, "电话"));
		wsheet.addCell(new Label(3, 1, "申请时间"));
		wsheet.addCell(new Label(4, 1, "审核时间"));
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(int i=0;i<takes.size();i++)
		{
			wsheet.addCell(new Label(0, i+2, takes.get(i).getId().toString()));
			wsheet.addCell(new Label(1, i+2, takes.get(i).getUname()));
			wsheet.addCell(new Label(2, i+2, takes.get(i).getTelephone()));
			wsheet.addCell(new Label(3, i+2, sdf.format(takes.get(i).getPtime())));
			wsheet.addCell(new Label(4, i+2, sdf.format(takes.get(i).getAudittime())));
		}          
		// 主体内容生成结束           
		wbook.write(); // 写入文件   
		wbook.close();  
		os.close(); // 关闭流
		} 
		catch(Exception ex) 
		{ 
		ex.printStackTrace(); 
		} 
	    PrintWriter out = null;
	    try {
			out = request1.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String url=request.getContextPath()+"/ec/ttake"+"?eid="+id;
	    out.println("<script>window.location.href = '"+url+"'</script>");
		out.flush();
        out.close();
        return;
	    
	}
}
