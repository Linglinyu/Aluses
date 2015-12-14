package cn.ruihe.aluses.model.home.service;

import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.entity.CliReadilyTakeComment;
import cn.ruihe.aluses.entity.CliReadilyTakePost;
import cn.ruihe.aluses.entity.SysReadilyTtake;
import cn.ruihe.aluses.model.home.repository.ReadilyTakeRepository;
import cn.ruihe.aluses.model.readilytake.repository.CommonRepository;
import cn.ruihe.aluses.model.readilytake.vo.CliReadilyTakeTagOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author DHC
 * @Date 2015-07-18 14:50:01
 */
@Service
public class ReadilyTakeService {

    @Autowired
    ReadilyTakeRepository readilyTakeRepository;

    @Autowired
    CommonRepository commonRepository;

    // 标签列表
    public List<CliReadilyTakeTagOutput> getCliReadilyTakeTagList() {
        return readilyTakeRepository.getCliReadilyTakeTagList();
    }

    // 增加标签
    public SysReadilyTtake addTag(SysReadilyTtake tag) {
        tag.setParentid(0);
        int id = commonRepository.getNewId("sysReadilyTtake");
        tag.setId(id);
        return readilyTakeRepository.addTag(tag);
    }

    // 修改标签
    public SysReadilyTtake updateTag(SysReadilyTtake tag) {
        tag.setParentid(0);
        return readilyTakeRepository.updateTag(tag);
    }

    // 改变标签状态
    public int changeTagStatus(int id, int status) {
        return readilyTakeRepository.changeTagStatus(id, status);
    }

    public SysReadilyTtake getTag(int id) {
        return readilyTakeRepository.getTag(id);
    }

    // 获取随手拍帖子列表
    public Pager<CliReadilyTakePost> getPosts(Integer projectId, int page, int size, String key) {
    	int count = readilyTakeRepository.getPostsCount(projectId, key);
        return readilyTakeRepository.getPosts(projectId, page, size, key, count);
    }

    // 获取随手拍
    public CliReadilyTakePost getPost(int id) {
        return readilyTakeRepository.getPost(id);
    }

    // 改变随手拍状态
    public int changePostStatus(int id, int status) {
        return readilyTakeRepository.changePostStatus(id, status);
    }

    // 修改随手拍
    public CliReadilyTakePost updateCliReadilyTakePost(CliReadilyTakePost post) {
        post.setUptime(new Date());
        return readilyTakeRepository.updateCliReadilyTakePost(post);
    }

    // 获取随手拍评论列表
    public List<CliReadilyTakeComment> getPostComments(int rid) {
        return readilyTakeRepository.getPostComments(rid);
    }

    // 修改随手拍评论状态
    public int changeCommentStatus(int id, int status) {
        return readilyTakeRepository.changeCommentStatus(id, status);
    }
}
