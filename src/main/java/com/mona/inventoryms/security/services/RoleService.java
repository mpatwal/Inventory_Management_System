package com.mona.inventoryms.security.services;

import com.mona.inventoryms.repository.UserRepository;
import com.mona.inventoryms.security.models.Privilege;
import com.mona.inventoryms.security.models.Role;
import com.mona.inventoryms.security.models.UserPrivilegeAssignment;
import com.mona.inventoryms.security.repository.PrivilegeRepository;
import com.mona.inventoryms.security.repository.RoleRepository;
import com.mona.inventoryms.security.repository.UserPrivilegeAssignmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PrivilegeRepository privilegeRepository;
    private final UserPrivilegeAssignmentRepository assignmentRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository, PrivilegeRepository privilegeRepository, UserPrivilegeAssignmentRepository assignmentRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.privilegeRepository = privilegeRepository;
        this.assignmentRepository = assignmentRepository;
    }

    //Get All Roles
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    //Get Role By Id
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    //Delete Role
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    //Update Role
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void assignUserRole(Long userid, Long roleid) {
        List<Privilege> privileges = privilegeRepository.findByRoleid(roleid);

        List<UserPrivilegeAssignment> existingAssignment = assignmentRepository.findByUserid(userid);

        assignmentRepository.deleteAll(existingAssignment);

        List<UserPrivilegeAssignment> assignments  = privileges.stream()
                .map(privilege -> new UserPrivilegeAssignment(userid, privilege.getId()))
                .toList();

        assignmentRepository.saveAll(assignments);
    }

    @Transactional
    public void unAssignUserRole(Long userid, Long roleid) {
        List<Privilege> privileges = privilegeRepository.findByRoleid(roleid);
        privileges.forEach(privilege -> assignmentRepository.deleteByUseridAndPrivilegeId(userid, privilege.getId()));
    }

    public List<Privilege> getPrivilegesInRole(Long roleid) {
        return privilegeRepository.findByRoleid(roleid);
    }
}