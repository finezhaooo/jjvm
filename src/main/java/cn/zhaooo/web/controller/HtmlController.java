package cn.zhaooo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName: HtmlController
 * @Description:
 * @Author: zhaooo
 * @Date: 2024/01/12 17:20
 */
@Controller
public class HtmlController {
    @GetMapping("/jjvm")
    public String showStaticHtmlPage() {
        return "index.html";
    }
}
