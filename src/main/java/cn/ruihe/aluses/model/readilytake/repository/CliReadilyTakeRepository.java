package cn.ruihe.aluses.model.readilytake.repository;

import cn.ruihe.aluses.entity.CliReadilyTakePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author DHC
 * @Date 2015-07-15 13:19:23
 */
@Repository
public interface CliReadilyTakeRepository extends JpaRepository<CliReadilyTakePost, Integer>, CliReadilyTakeRepositoryCustom {


}
