package orz.wizard.mao.forum.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import orz.wizard.mao.forum.entity.Group;
import orz.wizard.mao.forum.entity.Topic;
import orz.wizard.mao.forum.entity.User;
import orz.wizard.mao.forum.service.GroupService;
import orz.wizard.mao.forum.service.TopicService;

@Controller
@RequestMapping("/group")
public class GroupController {
    
    @Autowired
    private GroupService groupService;
    @Autowired
    private TopicService topicService;
    
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String showGroupTopic(HttpSession session, Map<String, Object> model) {
        User user = (User) session.getAttribute("user");
        model.put("groupTopicList", topicService.getGroupTopicList(user.getUserId()));
        return "group/groupTopic";
    }
    
    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public String showCreateGroup(HttpSession session) {
        return "group/createGroup";
    }
    
    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public String processCreateGroup(@Valid Group group, HttpSession session, Map<String, Object> model, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        User user = (User) session.getAttribute("user");
        group.setUserId(user.getUserId());
        groupService.insertGroup(group);
        return "redirect:" + group.getGroupId();
    }
    
    @RequestMapping(value = {"/{groupId}"}, method = RequestMethod.GET)
    public String showGroup(@PathVariable long groupId, Map<String, Object> model) {
        Group group = groupService.getGroup(groupId);
        model.put("group", group);
        List<Topic> topicList = topicService.getTopicList(groupId);
        model.put("topicList", topicList);
        // TODO: recent join users
        return "group/groupHome";
    }
    
    @RequestMapping(value = {"/join/{groupId}"})
    public @ResponseBody String joinGroup(HttpSession session, @PathVariable long groupId) {
        User user = (User) session.getAttribute("user");
        groupService.joinGroup(user.getUserId(), groupId);
        return "success";
    }
    
    @RequestMapping(value = {"/can_join/{groupId}"})
    public @ResponseBody boolean canJoin(HttpSession session, @PathVariable long groupId) {
        User user = (User) session.getAttribute("user");
        return !groupService.isJoined(user.getUserId(), groupId);
    }
    
    @RequestMapping(value = {"/{id}/new_topic"}, method = RequestMethod.GET)
    public String newTopic(@PathVariable long id, Map<String, Object> model) {
        return "topic/newTopic";
    }
    
    @RequestMapping(value = {"/{id}/new_topic"}, method = RequestMethod.POST)
    public String processNewTopic(@PathVariable long groupId, @Valid Topic topic, HttpSession session, Map<String, Object> model) {
        User user = (User) session.getAttribute("user");
        Topic newTopic = topicService.saveTopic(groupId, user.getUserId(), topic);
        return "redirect:topic/" + newTopic.getUserId();
    }
    
}
