package orz.wizard.mao.forum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import orz.wizard.mao.forum.dao.MessageDao;
import orz.wizard.mao.forum.dao.UserDao;
import orz.wizard.mao.forum.entity.Comment;
import orz.wizard.mao.forum.entity.Group;
import orz.wizard.mao.forum.entity.Topic;
import orz.wizard.mao.forum.entity.User;
import orz.wizard.mao.forum.entity.UserInfo;

@Service
public class MessageService {
    
    @Autowired
    private MessageDao messageDao;

    public List<Topic> getTopicMsgList(long userId) {
        return messageDao.getTopicMsgList(userId);
    }

    public void setReadTopic(long topicId, long userId) {
        messageDao.setReadTopic(topicId, userId);
    }

    public List<Comment> getCmtMsgList(long userId) {
        return messageDao.getCmtMsgList(userId);
    }
    
    public void setReadCmt(long cmtId, long userId) {
        messageDao.setReadCmt(cmtId, userId);
    }
}