package com.pap.crm_project.service.interfaces;

import com.pap.crm_project.entity.AppUser;

import java.util.List;

public interface AppUserService {

    List<AppUser> getAllAppUsers();
    AppUser getAppUserById(Long id);
    void saveAppUser(AppUser appUser);
    void deleteAppUser(Long id);
}
