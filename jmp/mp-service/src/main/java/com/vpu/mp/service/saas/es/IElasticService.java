package com.vpu.mp.service.saas.es;

import com.vpu.mp.service.pojo.saas.estest.DocBean;
//import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @author chenjie
 * @date 2020年08月04日
 */
@Service
public interface IElasticService {

    void createIndex();

    void deleteIndex(String index);

    void save(DocBean docBean);

    void saveAll(List<DocBean> list);

    Iterator<DocBean> findAll();

//    Page<DocBean> findByContent(String content);
//
//    Page<DocBean> findByFirstCode(String firstCode);
//
//    Page<DocBean> findBySecordCode(String secordCode);
//
//    Page<DocBean> query(String key);
}