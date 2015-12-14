package cn.ruihe.aluses.model.readilytake;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.ruihe.aluses.common.message.ApiResult;
import cn.ruihe.aluses.entity.Community;
import cn.ruihe.aluses.entity.CommunityIn;
import cn.ruihe.aluses.entity.CommunityOut;
import cn.ruihe.aluses.entity.Communitycontent;
import cn.ruihe.aluses.entity.Communitymember;
import cn.ruihe.aluses.entity.Grouptype;
import cn.ruihe.aluses.entity.ReportPost;
import cn.ruihe.aluses.model.readilytake.service.TeamService;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeTagOutput;
import cn.ruihe.aluses.model.readilytake.vo.TeamChatListOutput;
import cn.ruihe.aluses.model.readilytake.vo.TeamListOutput;
import cn.ruihe.aluses.model.readilytake.vo.TeamUserListOutput;
import cn.ruihe.utils.ImagesUP;

/**
 * <p>Title: TeamController.java</p>
 * <p>Description: 圈子信息 </p>
 *
 * @author try
 * @version 1.0
 * @date 2015年7月16日-下午2:50:09
 */
@Controller
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    HttpServletRequest request;

    /**
     * API04 获取圈子列表
     */
    @RequestMapping("api/teamlist")
    @ResponseBody
    public ApiResult getMateTags(@RequestParam int uid, @RequestParam int region, @RequestParam int project, @RequestParam String projectname) {
        List<TeamListOutput> lists = teamService.getTeamList(uid, region, project, projectname, 1);

        return ApiResult.SUCCESS(lists);
    }

    /**
     * API05 5.获取圈子聊天内容
     */
    @RequestMapping("api/teamlchatbylist")
    @ResponseBody
    public ApiResult getTeamChatList(@RequestParam int uid, @RequestParam int groupid, @RequestParam int pagesize, @RequestParam int pagenum) {
        //pagesize每页条数
        if (pagesize < 0) {
            pagesize = 10;
        }
        //第几页
        if (pagenum < 0) {
            pagenum = 0;
        }
        int startCount = pagesize * pagenum;
        int endCount = (pagesize * pagenum) + pagesize;

        List<TeamChatListOutput> lists = teamService.getTeamChatList(uid, groupid, startCount, endCount);
        return ApiResult.SUCCESS(lists);
    }


    /**
     * API11 11.申请圈子
     */
    @RequestMapping("/api/teambycreate")
    @ResponseBody
    public ApiResult getSaveTeambycreate(CommunityOut community1, @RequestParam String projectname, @RequestParam("files") MultipartFile[] files, String username) {
        String imagesName = "/team_images";
        Community community = new Community();
        try {
            community.setGrouppic(ImagesUP.addImage(request, imagesName, files));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (projectname.length() > 10) {
            return ApiResult.FAILURE("圈子名称不能超过10个字");
        }


//        if (StringUtils.isBlank(community1.getTelephone())) {
//            return ApiResult.FAILURE("用户的电话号码不能为空，请重新登录");
//        }

        System.out.println(community.getGrouppic());
        community.setGroupname(community1.getGroupname());
        community.setGroupdescribe(community1.getGroupdescribe());
        community.setGroupowner(community1.getGroupowner());
        community.setTelephone(community1.getTelephone());
        community.setRegion(community1.getRegion() == null ? 1 : community1.getRegion());
        community.setOwnerrole(community1.getOwnerrole() == null ? 1 : community1.getOwnerrole());
        community.setGrouptype(community1.getGrouptype());
        community.setIsmemberaudit(community1.getIsaudit() == null ? 1 : community1.getIsaudit());
        int r = teamService.getSvaeTeamChatCommunity(community, projectname, username);
        if (r == 1) {
            return ApiResult.SUCCESS();
        } else {
            if (r == -4) {
                return ApiResult.FAILURE("圈子名重复错误");
            } else {
                return ApiResult.FAILURE();
            }
        }

    }


    /**
     * API12 12.申请加入圈子
     */
    @RequestMapping("/api/teambyadd")
    @ResponseBody
    public ApiResult getSaveTeambyadd(Communitymember communitymember, @RequestParam String projectname) {
        if (teamService.getSaveTeambyadd(communitymember, projectname, false)) {
            return ApiResult.SUCCESS();
        } else {
            return ApiResult.FAILURE();
        }
    }


    /**
     * API13 13.发送圈子消息
     */
    @RequestMapping("/api/teambypush")
    @ResponseBody
    public ApiResult getSaveTeambypush(Communitycontent communitycontent) {
        teamService.getSaveTeambypush(communitycontent);
        return ApiResult.SUCCESS();
    }


    /**
     * API16 16.获取圈子成员列表
     */
    @RequestMapping("/api/teamuserlist")
    @ResponseBody
    public ApiResult getTeamuserList(@RequestParam int groupid) {
        List<TeamUserListOutput> lists = teamService.getTeamuserList(groupid);
        return ApiResult.SUCCESS(lists);
    }


    /**
     * API17 17.圈子成员删除、设置圈主、取消圈主
     */
    @RequestMapping("/api/teambyoperation")
    @ResponseBody
    public ApiResult getTeamByOperation(@RequestParam int id, @RequestParam int type, @RequestParam int uid) {
        if (teamService.getTeamByOperation(id, type, uid))
            return ApiResult.SUCCESS();
        else return ApiResult.FAILURE();
    }

    /**
     * API19 19.验证当前用户是否已加入传入圈子
     */
    @RequestMapping("/api/isgroupmem")
    @ResponseBody
    public ApiResult getIsGroupMem(@RequestParam int groupid, @RequestParam String telephone) {
        int status = teamService.getIsGroupMem(groupid, telephone);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("status", status);
        return ApiResult.SUCCESS(map);
    }


    /**
     * API25 获取圈子类型
     */
    @RequestMapping("api/getgrouptype")
    @ResponseBody
    public ApiResult getgrouptype() {
        List<Grouptype> lists = teamService.getgrouptype();
        return ApiResult.SUCCESS(lists);
    }

    /**
     * API26 修改圈子成员昵称
     *
     * @return
     */
    @RequestMapping(value = "api/setgroupname")
    @ResponseBody
    public ApiResult setgroupname(@RequestParam int gid, @RequestParam int uid, @RequestParam String groupname) {

        int status = teamService.setgroupname(gid, uid, groupname);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("status", status);
        return ApiResult.SUCCESS(map);
    }

    /**
     * API27 修改圈子信息
     *
     * @return
     */
    @RequestMapping(value = "api/setgroupinfo")
    @ResponseBody
    public ApiResult setgroupinfo(CommunityIn communityIn, @RequestParam("files") MultipartFile[] files) {
        String imagesName = "/team_images";
        Community community = new Community();
        if (communityIn.getGid() != null)
            community.setId(communityIn.getGid());
        if (communityIn.getGroupname() != null)
            community.setGroupname(communityIn.getGroupname());
        if (communityIn.getGroupdescribe() != null)
            community.setGroupdescribe(communityIn.getGroupdescribe());
        if (communityIn.getIsaudit() != null)
            community.setIsmemberaudit(communityIn.getIsaudit());
        if (communityIn.getGrouptype() != null)
            community.setGrouptype(communityIn.getGrouptype());
        if (files != null) {
            try {
                community.setGrouppic(ImagesUP.addImage(request, imagesName, files));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("aaaa");
        int status = teamService.setgroupinfo(community);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("status", status);
        return ApiResult.SUCCESS(map);
    }

    /**
     * API28 解散圈子
     *
     * @return
     */
    @RequestMapping(value = "api/delgroup")
    @ResponseBody
    public ApiResult delgroup(@RequestParam int gid, @RequestParam int uid) {

        int status = teamService.delgroup(gid, uid);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("status", status);
        return ApiResult.SUCCESS(map);
    }

    /**
     * API29 退出圈子
     *
     * @return
     */
    @RequestMapping(value = "api/exitgroup")
    @ResponseBody
    public ApiResult exitgroup(@RequestParam int gid, @RequestParam int uid) {

        int status = teamService.exitgroup(gid, uid);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("status", status);
        return ApiResult.SUCCESS(map);
    }
}
