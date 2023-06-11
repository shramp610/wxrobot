package com.tencent.wxcloudrun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * index控制器
 */
@RestController
public class IndexController {

  /**
   * 主页页面
   * @return API response html
   */
  @GetMapping(value = "hello")
  public String index() {
    return "hello，index";
  }

}
