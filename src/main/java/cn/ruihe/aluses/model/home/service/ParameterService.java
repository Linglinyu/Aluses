package cn.ruihe.aluses.model.home.service;

import cn.ruihe.aluses.entity.Parameter;
import cn.ruihe.aluses.model.home.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author DHC
 * @Date 2015-07-21 21:39
 */
@Service
public class ParameterService {
    @Autowired
    ParameterRepository repository;

    public List<Parameter> findParmeters() {
        return repository.findParmeters();
    }

    public int changeParameterValue(int id, int value) {
        return repository.changeParameterValue(id, value);
    }
}
