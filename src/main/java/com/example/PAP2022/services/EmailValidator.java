package com.example.PAP2022.services;

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
