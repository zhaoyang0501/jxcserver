
package com.pzy.service;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pzy.entity.Record;
import com.pzy.repository.RecordRepository;

@Service
public class RecordService {
	
    @Autowired
    private RecordRepository recordRepository;
    public List<Record> findAll() {
         return (List<Record>) recordRepository.findAll();
    }
    public List<Record> findRecordSubs() {
        return (List<Record>) recordRepository.findAll();
    }
    public List<Record> findRecords() {
        return (List<Record>) recordRepository.findAll();
    }
    
    public List<Record> findAll(final Date b,final Date e,final String type){
        Specification<Record> spec = new Specification<Record>() {
             public Predicate toPredicate(Root<Record> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
             Predicate predicate = cb.conjunction();
             if (b != null) {
                  predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createDate").as(Date.class), b));
             }
             if (e != null) {
                 predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createDate").as(Date.class),e));
             }
             if (type != null) {
                 predicate.getExpressions().add(cb.equal(root.get("type").as(String.class),type));
             }
             return predicate;
             }
        };
        return recordRepository.findAll(spec,new Sort(Direction.DESC, "id"));
    	}
		public void delete(Long id){
			recordRepository.delete(id);
		}
		public Record findRecord(Long id){
			  return recordRepository.findOne(id);
		}
		public Record find(Long id){
			  return recordRepository.findOne(id);
		}
		public void save(Record record){
			recordRepository.save(record);
		}
}