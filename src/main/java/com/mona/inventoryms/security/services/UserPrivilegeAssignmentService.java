package com.mona.inventoryms.security.services;
import com.mona.inventoryms.models.User;
import com.mona.inventoryms.security.models.Privilege;
import com.mona.inventoryms.security.models.UserPrivilegeAssignment;
import com.mona.inventoryms.security.repository.UserPrivilegeAssignmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrivilegeAssignmentService {
    @Autowired
public UserPrivilegeAssignmentRepository repository;

    public List<UserPrivilegeAssignment> findAll() {
        return repository.findAll();
    }

    public UserPrivilegeAssignment getPrivilege(Long id) {
        return repository.findById(id).orElse(null);
    }

    public UserPrivilegeAssignment save(UserPrivilegeAssignment userPrivilegeAssignment) {
        return repository.save(userPrivilegeAssignment);
    }

    public void delete(Long id) {
        repository.deleteById(Long.valueOf(id));
    }

    public UserPrivilegeAssignment getById(Long id) {
        return repository.findById(Long.valueOf(id)).orElse(null);
    }

    public void update(UserPrivilegeAssignment userPrivilegeAssignment) {
        repository.save(userPrivilegeAssignment);
    }

    @Transactional
    public List<Privilege> savePrivileges(List<Privilege> privileges, Long userid) {
        repository.deleteByUserid(userid);

        List<UserPrivilegeAssignment> assignments = privileges.stream()
                .map(privilege -> new UserPrivilegeAssignment(userid, privilege.getId()))
                .toList();

        return repository.saveAll(assignments).stream()
                .map(UserPrivilegeAssignment::getPrivilege)
                .toList();
    }

    public void deletePrivileges(Long userid) {
        repository.deleteByUserid(userid);
    }

    public List<Privilege> getUserPrivileges(Long userid) {
        List<UserPrivilegeAssignment> assignments = repository.findByUserid(userid);
        return assignments.stream()
                .map(UserPrivilegeAssignment::getPrivilege)
                .toList();
    }

    public List<User> getUsersByPrivilege(Long privilegeid) {
        List<UserPrivilegeAssignment> assignments = repository.findByUserid(privilegeid);
        return assignments.stream()
                .map(UserPrivilegeAssignment::getUser)
                .toList();
    }
}
