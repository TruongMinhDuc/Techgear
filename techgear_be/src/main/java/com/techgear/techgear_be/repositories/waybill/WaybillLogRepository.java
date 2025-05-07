package com.techgear.techgear_be.repositories.waybill;

import com.techgear.techgear_be.models.waybill.WaybillLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WaybillLogRepository extends JpaRepository<WaybillLog, Long>, JpaSpecificationExecutor<WaybillLog> {}
