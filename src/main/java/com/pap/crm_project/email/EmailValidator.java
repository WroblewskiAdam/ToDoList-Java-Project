package com.pap.crm_project.email;

import org.springframework.stereotype.Service;
import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {

    //  TODO zaimplementować
    @Override
    public boolean test(String s) {
        return true;
    }
}
