package com.icefamer.orderserver.repository;

import com.icefamer.orderserver.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

}
