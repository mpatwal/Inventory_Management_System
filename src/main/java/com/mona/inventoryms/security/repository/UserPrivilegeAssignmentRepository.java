package com.mona.inventoryms.security.repository;

import com.mona.inventoryms.security.models.UserPrivilegeAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPrivilegeAssignmentRepository extends JpaRepository<UserPrivilegeAssignment , Long> {
    public void deleteByUserid(Long userid);
    public List<UserPrivilegeAssignment> findByUserid(Long userid);
    public void deleteByUseridAndPrivilegeId(Long userid,Long privilegeid);
}
