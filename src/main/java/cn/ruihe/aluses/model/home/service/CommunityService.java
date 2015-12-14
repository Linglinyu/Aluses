package cn.ruihe.aluses.model.home.service;

import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.entity.Community;
import cn.ruihe.aluses.entity.Communitycontent;
import cn.ruihe.aluses.entity.Communitymember;
import cn.ruihe.aluses.entity.Grouptype;
import cn.ruihe.aluses.entity.Reporttype;
import cn.ruihe.aluses.model.home.repository.CommunityRepository;
import cn.ruihe.aluses.model.readilytake.repository.CommonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * @Author dhc
 * @Date 2015-07-20 16-35
 */
@Service
public class CommunityService {

    @Autowired
    CommunityRepository communityRepository;
    
    @Autowired
    CommonRepository  commonRepository;
    

    public int getCommunitiesCount(Integer projectId, String key) {
        return communityRepository.getCommunitiesCount(projectId, key);
    }

    
    // 获取圈子列表
    public Pager<Community> getCommunities(Integer projectId, int page, int size, String key) {
    	int count = communityRepository.getCommunitiesCount(projectId, key);
        return communityRepository.getCommunities(projectId, page, size, key, count);
    }

    // 获取圈子内容
    public Community getCommunity(int id) {
        return communityRepository.getCommunity(id);
    }

    // 改变圈子状态
    public int changeCommunityStatus(int id, int status) {
        return communityRepository.changeCommunityStatus(id, status);
    }

    // 改变圈子是否需要审核
    public int changeCommunityAudit(int id, int audit) {
        return communityRepository.changeCommunityAudit(id, audit);
    }

    public Community updateCommunity(Community community) {
        return communityRepository.updateCommunity(community);
    }
    
    public Community addCommunity(Community community) {
        String name=community.getGroupname();



    	int id = commonRepository.getNewId("sysReadilyTtake");
    	community.setId(id);
        return communityRepository.addCommunity(community);
    }


    // 获取成员列表
    public List<Communitymember> getCommunityMembers(int cid) {
        return communityRepository.getCommunityMembers(cid);
    }

    // 更改成员状态
    public int changeCommunityMemberStatus(int id, int status , int groupid , String uname  ) {
        return communityRepository.changeCommunityMemberStatus(id, status , groupid , uname );
    }

    // 获取聊天列表
    public Pager<Communitycontent> getCommunityChats(int cid, int page, int size) {
        return communityRepository.getCommunityChats(cid, page, size);
    }

    // 改变聊天状态
    public int changeCommunityChatStatus(int id, int status) {
        return communityRepository.changeCommunityChatStatus(id, status);
    }

	public List<Grouptype> getGrouptypeTagList() {
		return communityRepository.getGrouptypeTagList();
	}

	public Grouptype addTag(Grouptype tag) {
		int id = commonRepository.getNewId("grouptype");
//		int id = commonRepository.getNewId("sysReadilyTtake");
        tag.setId(id);
        return communityRepository.addGrouptypeTag(tag);
	}

	public Grouptype getTag(int id) {
		return communityRepository.getGrouptypeTag(id);
	}

	public Grouptype updateTag(Grouptype tag) {
		return communityRepository.getGrouptypeTag(tag);
	}

	public int deleteTag(int id) {
		return communityRepository.deleteTag(id);
	}


	/**
	 * @param id
	 * @param page
	 * @param i
	 * @return
	 */
	public Pager<Communitymember> getCommunityMembers(int cid, Integer page,
			int size) {
		int count = communityRepository.getCommunitiesCount(cid);
		return communityRepository.getCommunityMembers(cid,page,size,count);
	}

}
