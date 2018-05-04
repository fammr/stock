package com.haotu369.controller;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.stock.StockClassify;
import com.haotu369.model.stock.StockType;
import com.haotu369.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/4
 */
@Controller
@RequestMapping("/stock")
public class StockAction {
    private static final String PATH = "stock/";

    @Autowired
    private StockService stockService;

    @RequestMapping("/index")
    public String index() {
        return PATH + "index";
    }

    // 查询股票分类数据
    @RequestMapping("/type")
    @ResponseBody
    public Object type() {
        List<StockType> stockTypes = stockService.listStockType();
        return JSONObject.toJSON(stockTypes);
    }

    // 查询股票具体类型数据
    @RequestMapping("/classify")
    @ResponseBody
    public Object classify(int type) {
        List<StockClassify> stockClassifies = stockService.listStockClassify(type);
        return JSONObject.toJSON(stockClassifies);
    }
}
