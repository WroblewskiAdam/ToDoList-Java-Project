package com.pap.crm_project.registration.email;

import org.springframework.stereotype.Service;
import java.util.function.Predicate;

@Service
public class RegistrationEmailValidator implements Predicate<String> {

    //  TODO zaimplementować
    @Override
    public boolean test(String s) {
        return true;
    }
}
