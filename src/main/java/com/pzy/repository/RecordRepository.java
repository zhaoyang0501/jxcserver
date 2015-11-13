package com.pzy.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.pzy.entity.Record;
public interface RecordRepository extends PagingAndSortingRepository<Record, Long>,JpaSpecificationExecutor<Record>{
    
}

