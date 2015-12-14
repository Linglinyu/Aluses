package cn.ruihe.aluses.model.readilytake;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.ruihe.aluses.common.message.ApiResult;
import cn.ruihe.aluses.entity.CliReadilyTakeComment;
import cn.ruihe.aluses.entity.CliReadilyTakePost;
import cn.ruihe.aluses.entity.CliReadilyTakePostIn;
import cn.ruihe.aluses.entity.ReportPost;
import cn.ruihe.aluses.entity.ReportPostIn;
import cn.ruihe.aluses.entity.Reporttype;
import cn.ruihe.aluses.model.readilytake.service.CliReadilyTakeService;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeArticleOutput;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeCommentOutput;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeTagOutput;
import cn.ruihe.utils.ImagesUP;

/**
 * @Author DHC
 * @Date 2015-07-15 13:08:06
 */
@Controller
public class ReadilyTakeController {


    @Autowired
    CliReadilyTakeService cliReadilyTakeService;

    @Autowired
    HttpServletRequest request;

    /**
     * API01 获取随手拍标签
     */
    @RequestMapping("api/matebylist")
    @ResponseBody
    public ApiResult getMateTags() {
        List<CliReadilyTakeTagOutput> lists = cliReadilyTakeService.getCliReadilyTakeTagList();
        return ApiResult.SUCCESS(lists);
    }

    /**
     * API02 获取随手拍文章列表
     *
     * @param projectname 项目名称
     * @param pagesize    每一页的数量
     * @param pagenum     第几页 0页开始
     * @return
     */
    @RequestMapping("api/matearticlebylist")
    @ResponseBody
    public ApiResult getMateArticleByList(@RequestParam String projectname, @RequestParam int pagesize, @RequestParam int pagenum) {

        //pagesize每页条数
        if (pagesize < 0) {
            pagesize = 10;
        }
        //第几页
        if (pagenum < 0) {
            pagenum = 0;
        }
        int startCount = pagesize * pagenum + 1;
        int endCount = (pagesize * pagenum) + pagesize;
        List<CliReadilyTakeArticleOutput> lists = cliReadilyTakeService.getTeamListOutputByList(projectname, startCount, endCount);
        System.out.println(lists.size());
        return ApiResult.SUCCESS(lists);
    }

    /**
     * API03 获取随手拍文章评论
     *
     * @param rid 文章ID
     * @return 评论列表
     */
    @RequestMapping("api/matecommentbylist")
    @ResponseBody
    public ApiResult getMateComments(@RequestParam int rid) {
        List<CliReadilyTakeCommentOutput> lists = cliReadilyTakeService.findByRid(rid);
        return ApiResult.SUCCESS(lists);
    }

    /**
     * API08 发布随手拍文章
     *
     * @return
     */
    @RequestMapping(value = "api/matebyadd", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult getMatebyadd(CliReadilyTakePostIn cliReadilyTakePostIn, @RequestParam("files") MultipartFile[] files) throws IOException {
        String imagesName = "/mate_images";
        String imagesNameSmal = "/mate_images_smal";
        CliReadilyTakePost cliReadilyTakePost = new CliReadilyTakePost();
        cliReadilyTakePost.setRid(cliReadilyTakePostIn.getRid());
        cliReadilyTakePost.setUid(cliReadilyTakePostIn.getUid());
        //判断username是否为空（ios客户端2.3版本，如果该值为空，为作处理，直接传了null）
        if (null == cliReadilyTakePostIn.getUname() || "".equals(cliReadilyTakePostIn.getUname().trim()) || "null".equals(cliReadilyTakePostIn.getUname().trim())) {
            String telephone = cliReadilyTakePostIn.getTelephone();
            cliReadilyTakePost.setUname(telephone.substring(0, telephone.length() - (telephone.substring(3)).length()) + "****" + telephone.substring(7));
        } else {
            cliReadilyTakePost.setUname(cliReadilyTakePostIn.getUname());
        }
        cliReadilyTakePost.setTelephone(cliReadilyTakePostIn.getTelephone());
        cliReadilyTakePost.setRegion(cliReadilyTakePostIn.getRegion());
        cliReadilyTakePost.setTitle(cliReadilyTakePostIn.getTitle());
        cliReadilyTakePost.setContent(cliReadilyTakePostIn.getContent());
        String projectname = cliReadilyTakePostIn.getProjectname();
        System.out.println(cliReadilyTakePostIn.toString());
        try {
            cliReadilyTakePost.setPictures(ImagesUP.addImage(request, imagesName, files));
            cliReadilyTakePost.setSmallpic(ImagesUP.addImageBy300(request, imagesName, files));
        } catch (Exception e) {
            e.printStackTrace();
        }


        CliReadilyTakePost saveCliReadilyTakePost = cliReadilyTakeService.getSaveCliReadilyTakePost(cliReadilyTakePost, projectname);
        return ApiResult.SUCCESS();
    }

    /**
     * API09 发布随手拍评论
     *
     * @return
     */
    @RequestMapping(value = "api/matebyaddcomment", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult getMateByAddComment(@RequestParam int rid, @RequestParam int uid, @RequestParam int region, @RequestParam int project,
                                         @RequestParam String projectname, @RequestParam String uname, @RequestParam String telephone, @RequestParam String content) {
        //判断username是否为空（ios客户端2.3版本，如果该值为空，为作处理，直接传了null）
        if (null == uname || "".equals(uname.trim()) || "null".equals(uname.trim())) {
            uname = telephone.substring(0, telephone.length() - (telephone.substring(3)).length()) + "****" + telephone.substring(7);
        }
        CliReadilyTakeComment cliReadilyTakeComment = cliReadilyTakeService.getSaveCliReadilyCommentPost(rid, uid, region, project, projectname, uname, telephone, content);
        return ApiResult.SUCCESS();
    }


    /**
     * API18 验证手机号是否是黑名单
     *
     * @return
     */
    @RequestMapping(value = "/api/phonebystatus", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult getPhonebyStatus(@RequestParam String telephone) {
        int status = cliReadilyTakeService.getPhonebyStatus(telephone);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("status", status);

        return ApiResult.SUCCESS(map);
    }


    /**
     * API20 删除邻里帖子
     *
     * @return
     */
    @RequestMapping(value = "/api/delpost")
    @ResponseBody
    public ApiResult delpost(@RequestParam int id, @RequestParam int uid) {
        int status = cliReadilyTakeService.delpost(id, uid);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("status", status);
        return ApiResult.SUCCESS(map);
    }

    /**
     * API21 收藏帖子
     *
     * @return
     */
    @RequestMapping(value = "/api/collectpost")
    @ResponseBody
    public ApiResult collectpost(@RequestParam int id, @RequestParam int uid) {
        int status = cliReadilyTakeService.collectpost(id, uid);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("status", status);
        return ApiResult.SUCCESS(map);
    }

    /**
     * API22 获取帖子举报类型
     *
     * @return
     */
    @RequestMapping(value = "api/getreporttype")
    @ResponseBody
    public ApiResult getreporttype(@RequestParam int uid, @RequestParam int category) {
        List<Reporttype> lists = cliReadilyTakeService.getreporttype(uid, category);
        return ApiResult.SUCCESS(lists);
    }

    /**
     * API23 举报帖子或者圈子
     *
     * @return
     */
    @RequestMapping(value = "api/reportpost")
    @ResponseBody
    public ApiResult reportpost(ReportPostIn reportPostIn, @RequestParam("files") MultipartFile[] files) {
        String imagesName = "/mate_images";
        ReportPost reportpost = new ReportPost();
        reportpost.setPid(reportPostIn.getId());
        reportpost.setCategory(reportPostIn.getCategory());
        reportpost.setUid(reportPostIn.getUid());
        reportpost.setType(reportPostIn.getType());
        reportpost.setContent(reportPostIn.getContent());
        try {
            reportpost.setPic(ImagesUP.addImage(request, imagesName, files));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int status = cliReadilyTakeService.reportpost(reportpost);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("status", status);
        return ApiResult.SUCCESS(map);
    }

//    /**
//     * API23 举报帖子或者圈子
//     * @return
//     */
//    @RequestMapping(value = "api/reportpost" )
//    @ResponseBody
//    public ApiResult reportpost( @RequestParam ReportPost reportpost) {
//    	
//    	int status =  cliReadilyTakeService.reportpost(reportpost);
//    	Map<String , Integer > map = new HashMap<String , Integer>();
//    	map.put("status",status);
//        return ApiResult.SUCCESS(map);
//    }

    /**
     * API24 获取我的帖子
     *
     * @return
     */
    @RequestMapping(value = "api/getmypost")
    @ResponseBody
    public ApiResult getmypost(@RequestParam int uid, @RequestParam int pagesize, @RequestParam int pagenum) {

        //pagesize每页条数
        if (pagesize < 0) {
            pagesize = 10;
        }
        //第几页
        if (pagenum < 0) {
            pagenum = 0;
        }
        int startCount = pagesize * pagenum + 1;
        int endCount = (pagesize * pagenum) + pagesize;
        List<CliReadilyTakeArticleOutput> lists = cliReadilyTakeService.getmypost(uid, startCount, endCount);
        return ApiResult.SUCCESS(lists);
    }

    /**
     * API32 获取收藏的帖子
     *
     * @return
     */
    @RequestMapping(value = "api/getmycollectpost")
    @ResponseBody
    public ApiResult getmycollectpost(@RequestParam int uid, @RequestParam int pagesize, @RequestParam int pagenum) {

        //pagesize每页条数
        if (pagesize < 0) {
            pagesize = 10;
        }
        //第几页
        if (pagenum < 0) {
            pagenum = 0;
        }
        int startCount = pagesize * pagenum + 1;
        int endCount = (pagesize * pagenum) + pagesize;
        List<CliReadilyTakeArticleOutput> lists = cliReadilyTakeService.getmycollectpost(uid, startCount, endCount);
        return ApiResult.SUCCESS(lists);
    }

    /**
     * API33 是否点赞
     *
     * @return
     */
    @RequestMapping(value = "/api/ispraise")
    @ResponseBody
    public ApiResult ispraise(@RequestParam int uid, @RequestParam int id) {
        int status = cliReadilyTakeService.ispraise(id, uid);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("status", status);
        return ApiResult.SUCCESS(map);
    }

}
