package hkmu.comps380f.dao;

import hkmu.comps380f.exception.TicketNotFound;
import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Ticket;
import hkmu.comps380f.model.TicketUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    @Resource
    CommentRepository cRepo;
    @Resource
    TicketUserRepository tuRepo;
    @Resource
    TicketRepository tRepo;

    @Transactional
    public void createComment(String username, long ticketId, String content)
            throws TicketNotFound {
        TicketUser user = tuRepo.findById(username).orElse(null);
        if (user == null){
            throw new RuntimeException("TicketUser " + username + " not found.");
        }
        Ticket ticket = tRepo.findById(ticketId).orElse(null);
        if (ticket == null) {
            throw new TicketNotFound(ticketId);
        }
        Comment comment = new Comment(user, ticket, content);
        Comment savedComment = cRepo.save(comment);
        user.getComments().add(savedComment);
        ticket.getComments().add(savedComment);
    }

    @Transactional
    public List<Comment> findCommentByTicketId(long ticketId){
        return cRepo.findCommentByTicketIdOrderByCreateTimeDesc(ticketId);
    }

    @Transactional
    public List<Comment> findCommentByUsername(String username){
        return cRepo.findCommentByUsernameOrderByCreateTimeDesc(username);
    }

    @Transactional
    public List<Comment> findCommentByUserId(long ticketId){
        return cRepo.findCommentByTicketIdOrderByCreateTimeDesc(ticketId);
    }

    @Transactional
    public void delete(long ticketId, long commentId){
        Ticket ticket = tRepo.findById(ticketId).orElse(null);
        if (ticket == null) {
            throw new TicketNotFound(ticketId);
        }
        Comment comment = cRepo.findById(commentId).orElse(null);
        if (comment == null) {
            throw new RuntimeException("Comment ID " + commentId + " not found.");
        }
        TicketUser user = comment.getUser();
        ticket.getComments().remove(comment);
        user.getComments().remove(comment);
        cRepo.delete(comment);
    }
}
