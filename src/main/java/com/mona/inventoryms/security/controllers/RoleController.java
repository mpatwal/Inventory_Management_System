package com.mona.inventoryms.security.controllers;

import com.mona.inventoryms.security.models.Privilege;
import com.mona.inventoryms.security.repository.PrivilegeRepository;
import com.mona.inventoryms.security.services.RoleService;
import com.mona.inventoryms.security.models.Role;
import com.mona.inventoryms.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
public class RoleController {

    private final PrivilegeRepository privilegeRepository;
    private final RoleService roleService;
    private final UserService userService;

    public RoleController(PrivilegeRepository privilegeRepository, RoleService roleService, UserService userService) {
        this.privilegeRepository = privilegeRepository;
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/roles")
    public List<Role> parameters(Model model) {
        return roleService.findAll();
    }

    @GetMapping("/role/{id}")
    public Role getById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    @PostMapping("/roles")
    public Role save(Role role) {
        return roleService.save(role);
    }

    @DeleteMapping("/role/{id}")
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }

    @PostMapping("/role/{roleid}/assign/user/{userid}")
    public void assignUserRole(@PathVariable("roleid") Long roleid, @PathVariable("userid") Long userid) {
        roleService.assignUserRole(userid, roleid);
    }

    @Transactional
    @DeleteMapping("/role/{roleid}/unAssign/user/{userid}")
    public void unAssignUserRole(@PathVariable("roleid") Long roleid, @PathVariable("userid") Long userid) {
        roleService.unAssignUserRole(userid, roleid);
    }

    @GetMapping("role/{roleid}/privileges")
    public List<Privilege> getPrivilegesInRole(@PathVariable("roleid") Long roleid) {
        return roleService.getPrivilegesInRole(roleid);
    }

}