package com.vpu.mp.dao.main;

/**
 * @author chenjie
 * @date 2020年08月04日
 */
import com.vpu.mp.service.pojo.saas.estest.DocBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.annotations.Query;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

//public interface ElasticRepositoryDao extends ElasticsearchRepository<DocBean, Long> {
public interface ElasticRepositoryDao <DocBean, Long> {

    //默认的注释
    //@Query("{\"bool\" : {\"must\" : {\"field\" : {\"content\" : \"?\"}}}}")
//    Page<DocBean> findByContent(String content, Pageable pageable);
//
//    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"firstCode.keyword\" : \"?\"}}}}")
//    Page<DocBean> findByFirstCode(String firstCode, Pageable pageable);
//
//    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"secordCode.keyword\" : \"?\"}}}}")
//    Page<DocBean> findBySecordCode(String secordCode, Pageable pageable);


}
