package com.haotu369.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.*;
import com.haotu369.service.CommunityService;
import com.haotu369.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Properties;

/**
 * 选股社区
 *
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/11
 */
@Controller
@RequestMapping("/community")
public class CommunityAction {
    private static final String PATH = "community/";

    @Autowired
    private CommunityService communityService;

    @Autowired
    private UserService userService;

    // 社区首页
    @RequestMapping("/index")
    public String community(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        // 精选内容
        List<Article> choiceArticle = communityService.listChoiceArticle(1, 6);
        // 最新内容
        List<Article> recentArticle = communityService.listRecentArticle(1, 6);
        // 分类标签
        List<Tag> tag = communityService.listTag();

        modelMap.put("choiceArticle", choiceArticle);
        modelMap.put("recentArticle", recentArticle);
        modelMap.put("tag", tag);
        return PATH + "index";
    }

    // 联系我们
    @RequestMapping("/contact")
    public String contact(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        List<Article> recentArticle = communityService.listRecentArticle(1, 4);
        modelMap.put("recentArticle", recentArticle);
        return PATH + "contact";
    }

    // 保存联系我们消息内容
    @RequestMapping("/saveContact")
    @ResponseBody
    public JSONObject saveContact(ContactUs contactUs) {
        return communityService.saveContactUs(contactUs);
    }

    // 常见问题
    @RequestMapping("/faq")
    public String listFaq(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        List<Article> recentArticle = communityService.listRecentArticle(1, 4);
        List<Tag> tag = communityService.listTag();
        List<FAQ> faq = communityService.listFaq();

        modelMap.put("recentArticle", recentArticle);
        modelMap.put("tag", tag);
        modelMap.put("faq", faq);
        return PATH + "faq";
    }

    // 内容列表
    @RequestMapping("/article")
    public String article(String tagId, ModelMap modelMap) {
        tagId = (null == tagId) ? "" : tagId;
        List<Article> choiceArticle = communityService.listChoiceArticle(1, 4);
        int count = communityService.getArticleCount(tagId);
        modelMap.put("count", count);
        modelMap.put("choiceArticle", choiceArticle);
        modelMap.put("tagId", tagId);
        return PATH + "article";
    }

    // 内容分页
    @RequestMapping("/listArticle")
    @ResponseBody
    public Object listArticle(int pageNo, int pageSize) {
        List<Article> articles = communityService.listRecentArticle(pageNo, pageSize);
        return JSONObject.toJSON(articles);
    }

    // 按标签内容分页
    @RequestMapping("/listArticleByTag")
    @ResponseBody
    public Object listArticleByTag(int tagId, int pageNo, int pageSize) {
        List<Article> articles = communityService.listArticleByTag(tagId, pageNo, pageSize);
        return JSONObject.toJSON(articles);
    }

    // 点赞
    @RequestMapping("/updateLike")
    @ResponseBody
    public JSONObject updateLike(int id) {
        return communityService.updateLike(id);
    }

    // 取消点赞
    @RequestMapping("/removeLike")
    @ResponseBody
    public JSONObject removeLike(int id) {
        return communityService.removeLike(id);
    }

    // 内容详情
    @RequestMapping("/articleDetail")
    public String articleDetail(int id, ModelMap modelMap) {
        Article article = communityService.getArticleDetail(id);
        List<Article> choiceArticle = communityService.listChoiceArticle(1, 4);
        modelMap.put("article", article);
        modelMap.put("choiceArticle", choiceArticle);
        return PATH + "article_detail";
    }

    // 文章内容评论
    @RequestMapping("/addComment")
    @ResponseBody
    public JSONObject addComment(Comment comment) {
        // TODO 从token中或许用户的id,判断用户是否为游客
        Integer userId = userService.getUserIdFromCookie() ;
        return communityService.addComment(comment, userId);
    }

    // 获取文章评论
    @RequestMapping("/listComment")
    @ResponseBody
    public Object listComment(int id) {
        List<Comment> comments = communityService.getComment(id);
        return JSONObject.toJSON(comments);
    }

    // 获取最新评论
    @RequestMapping("/listRecentComment")
    @ResponseBody
    public Object listRecentComment(int pageNo, int pageSize) {
        List<Comment> recentComments = communityService.listRecentComment(pageNo, pageSize);
        return JSONObject.toJSON(recentComments);
    }

    // 发表文章页面
    @RequestMapping("/addArticle")
    public String addArticle(ModelMap modelMap) {
        List<Tag> tag = communityService.listTag();
        modelMap.put("tag", tag);
        return PATH + "article_add";
    }

    // 保存文章
    @RequestMapping("/saveArticle")
    @ResponseBody
    public JSONObject saveArticle(Article article, String tagId) {
        return communityService.saveArticle(article, tagId);
    }

    // 高级查询
    @RequestMapping("/search")
    public String search(String content,ModelMap modelMap) {
        List<Article> articles = communityService.search(content);
        List<Article> choiceArticle = communityService.listChoiceArticle(1, 4);
        modelMap.put("choiceArticle", choiceArticle);
        modelMap.put("articles", articles);
        return PATH + "search_result";
    }
}
