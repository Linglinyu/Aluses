/**
 * 
 */
package cn.ruihe.aluses.model.home.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ruihe.aluses.entity.ReportPost;
import cn.ruihe.aluses.entity.Reporttype;
import cn.ruihe.aluses.model.home.repository.ReadilyTakeRepository;
import cn.ruihe.aluses.model.readilytake.repository.CommonRepository;

/**
 * @author Administrator
 *
 */
@Service
public class ReportPostService {
	
	@Autowired
    ReadilyTakeRepository readilyTakeRepository;

    @Autowired
    CommonRepository commonRepository;
    
    // 标签列表
    public List<Reporttype> getReportPostTagList() {
        return readilyTakeRepository.getReportPostTagList();
    }
    
    // 增加标签
    public Reporttype addTag(Reporttype tag) {
        int id = commonRepository.getNewId("reporttype");
        tag.setId(id);
        return readilyTakeRepository.addReportPostTag(tag);
    }

    // 修改标签
    public Reporttype updateTag(Reporttype tag) {
        
        return readilyTakeRepository.updateReportPostTag(tag);
    }

    // 改变标签状态
    public int changeTagStatus(int id, int status) {
        return readilyTakeRepository.changeReportPostTagStatus(id, status);
    }
    
    public Reporttype getTag(int id) {
        return readilyTakeRepository.getReportPostTag(id);
    }
    
    // 获取随手拍举报列表
    public List<ReportPost> getPostReportposts(int rid,int category) {
        return readilyTakeRepository.getPostReportposts(rid,category);
    }

    // 修改随手拍举报状态
    public int changeReportpostStatus(int id, int status) {
        return readilyTakeRepository.changeReportpostStatus(id, status);
    }


}
