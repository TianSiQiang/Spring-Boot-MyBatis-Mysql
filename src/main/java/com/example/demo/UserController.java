package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @ResponseBody
    @RequestMapping("/")
    public Map<String, Object> index(){
        Map<String, Object> result = new HashMap<>(16);
        List<String> data = new ArrayList<>();
        result.put("errCode",0);
        result.put("errMsg","获取成功");
        List<User> list=userMapper.selectList(null);
        if(list!=null&&list.size()>0){
            for(User g:list){
                data.add(g.toString());
            }
        }else{
            result.put("errMsg","获取失败");
        }
        result.put("data",data);
        return result;
    }
}
