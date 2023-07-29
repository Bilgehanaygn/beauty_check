package com.example.demo.sms;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntriesFilter;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TwilioService {

    public String showEnvironmentVariable(String key){
        Dotenv dotenv;
        dotenv = Dotenv.configure().load();
        return dotenv.get(key);
    }
}
