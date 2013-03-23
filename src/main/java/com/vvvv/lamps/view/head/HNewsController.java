package com.vvvv.lamps.view.head;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vvvv.common.view.BaseController;
import com.vvvv.lamps.model.News;

/**
 * @className:HNewsController.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4下午12:36:16 
 */
@Controller
@RequestMapping("/head/news")
public class HNewsController extends BaseController<News>{

}
