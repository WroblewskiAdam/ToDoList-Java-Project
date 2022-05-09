package com.example.PAP2022.services;

import com.example.PAP2022.models.Role;
import com.example.PAP2022.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public Long deleteRoleById(Long id) {
        roleRepository.deleteById(id);
        return id;
    }

    public String deleteRoleByName(String name) {
        Role role = roleRepository.findByName(name);
        return name;
    }


}
