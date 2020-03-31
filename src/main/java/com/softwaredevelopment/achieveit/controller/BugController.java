package com.softwaredevelopment.achieveit.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RainkQ
 * @date 2020/3/30 17:22
 */
@Api(tags = "缺陷接口")
@RequestMapping("bug")
@RestController
public class BugController extends BaseController {

}
