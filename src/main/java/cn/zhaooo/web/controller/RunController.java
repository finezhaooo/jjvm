package cn.zhaooo.web.controller;

import cn.zhaooo.web.service.ExecuteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName: RunController
 * @Description:
 * @Author: zhaooo
 * @Date: 2024/01/11 20:32
 */
@RestController
@CrossOrigin("*")
public class RunController {
    @Resource
    private ExecuteService service;

    @PostMapping("/run")
    public String runCode(@RequestBody Map<String, String> source, @RequestParam("main") String mainName) {
        return service.execute(mainName, source);
    }

    @PostMapping("/compile")
    public String compile(@RequestBody Map<String, String> source) {
        return service.compile(source);
    }

    @PostMapping("/step")
    public String step(@RequestBody Map<String, String> source, @RequestParam("main") String mainName, @RequestParam("uid") String uid) {
        return service.step(uid, mainName, source);
    }

    @GetMapping("/stop")
    public String stop(@RequestParam("uid") String uid) {
        return service.stop(uid);
    }
}
