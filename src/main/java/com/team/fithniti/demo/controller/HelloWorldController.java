package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.controller.api.HelloWorldAPI;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hello")
@CrossOrigin(origins = "*")
public class HelloWorldController implements HelloWorldAPI {

    @Override
    public Map<Object, Object> hello(){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("msg","Hello World !");
        map.put("from","HelloWorldController");
        return map;
    }
}
