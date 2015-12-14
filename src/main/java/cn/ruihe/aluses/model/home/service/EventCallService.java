package cn.ruihe.aluses.model.home.service;

import cn.ruihe.aluses.common.Pager;
import cn.ruihe.aluses.entity.CliPartake;
import cn.ruihe.aluses.entity.SysEventCallArticle;
import cn.ruihe.aluses.entity.SysEventCallTheme;
import cn.ruihe.aluses.model.home.repository.EventCallRepository;
import cn.ruihe.aluses.model.home.repository.UserRepository;
import cn.ruihe.aluses.model.readilytake.repository.CommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author dhc
 * @Date 2015-07-21 10-35
 */
@Service
public class EventCallService {
    @Autowired
    EventCallRepository repository;

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    UserRepository userRepository;

    // 获取主题列表
    public List<SysEventCallTheme> getThemes() {
        return repository.getThemes();
    }

    // 获取主题
    public SysEventCallTheme getTheme(int id) {
        return repository.getTheme(id);
    }

    // 新增主题
    public SysEventCallTheme addTheme(SysEventCallTheme theme) {
        int nid = commonRepository.getNewId("sysEventCallTheme");
        theme.setId(nid);
        return repository.addTheme(theme);
    }

    // 更新主题
    public SysEventCallTheme updateTheme(SysEventCallTheme theme) {
        return repository.updateTheme(theme);
    }

    // 更新主题状态
    public int changeThemeStatus(int id, int status) {
        return repository.changeThemeStatus(id, status);
    }

    // 获取活动召集文章列表
    public Pager<SysEventCallArticle> getArticles(Integer projectId, int page, int size, String key) {
        int count = repository.getArticleCount(projectId, key);
        return repository.getArticles(projectId, page, size, key, count);
    }

    // 获取召集文章
    public SysEventCallArticle getArticle(int id) {
        return repository.getArticle(id);
    }

    // 新增召集文章
    public SysEventCallArticle addArticle(SysEventCallArticle article) {
        int id = commonRepository.getNewId("sysEventCallArticle");
        int uid = userRepository.getByName(article.getUname()).getId();
        article.setId(id);
        article.setUid(uid);
        return repository.addArticle(article);
    }

    // 更新召集文章
    public SysEventCallArticle updateArticle(SysEventCallArticle article) {
        return repository.updateArticle(article);
    }

    // 改变文章状态
    public int changeArticleStatus(int id, int status) {
        return repository.changeArticleStatus(id, status);
    }

    // 获取参与活动列表
    public List<CliPartake> getPartakes(int eid) {
        return repository.getPartakes(eid);
    }

    // 审核成员
    public Date changeCliPartakeAudit(int id) {
        return repository.changeCliPartakeAudit(id);
    }

    /**
     * @param eid
     * @param page
     * @param i
     * @return
     */
    public Pager<CliPartake> getPartakes(int eid, Integer page, int size) {
        int count = repository.getCommunitiesCount(eid);
        return repository.getPartakes(eid, page, size, count);
    }

    /**
     * @param id
     * @return
     */
    public int deleteCliPartake(int id) {
        return repository.deleteCliPartake(id);
    }
}
