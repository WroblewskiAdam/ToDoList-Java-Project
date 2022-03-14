package com.pap.crm_project.service.implementation;

import com.pap.crm_project.entity.AppUser;
import com.pap.crm_project.repository.AppUserRepository;
import com.pap.crm_project.service.interfaces.AppUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public List<AppUser> getAllAppUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser getAppUserById(Long id) {
        return appUserRepository.getById(id);
    }

    @Override
    public void saveAppUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    @Override
    public void deleteAppUser(Long id) {
        appUserRepository.deleteById(id);
    }
}
