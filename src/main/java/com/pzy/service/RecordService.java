
package com.pzy.service;

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
    
    public Page<Record> findAll(final int pageNumber, final int pageSize,final String name){
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
       
        Specification<Record> spec = new Specification<Record>() {
             public Predicate toPredicate(Root<Record> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
             Predicate predicate = cb.conjunction();
             if (name != null) {
                  predicate.getExpressions().add(cb.like(root.get("name").as(String.class), name+"%"));
             }
             return predicate;
             }
        };
        Page<Record> result = (Page<Record>) recordRepository.findAll(spec, pageRequest);
        return result;
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