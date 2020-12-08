package com.ifrit.es7.control;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SimpleControl {

    private Map<String, String> map = new HashMap<>();

    @GetMapping("/test")
    public Object testES(@RequestParam("token") String token) {
        String res = map.get(token);
        map.remove(token);
        return "returns " + res;
    }

    @GetMapping("testss")
    public Object testESss() {
        return "returns " ;
    }

    @GetMapping("start")
    public Object getToken() {
        String s = LocalDate.now().toString();
        map.put(s, s);
        return s;
    }
}
