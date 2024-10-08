package hkmu.comps380f.dao;

import hkmu.comps380f.exception.AttachmentNotFound;
import hkmu.comps380f.exception.TicketNotFound;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Ticket;
import hkmu.comps380f.model.TicketUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {
    @Resource
    private TicketRepository tRepo;
    @Resource
    private TicketUserRepository tuRepo;

    @Resource
    private AttachmentRepository aRepo;

    @Transactional
    public List<Ticket> getTickets() {
        return tRepo.findAll();
    }

    @Transactional
    public List<Ticket> getTicketsByTickid(long id) {
        return (List<Ticket>) tRepo.findById(id).orElse(null);
    }
    @Transactional
    public List<Ticket> getTicketsByUser(String username){
        return tRepo.findTicketsByCustomerName(username);
    }
    @Transactional
    public Ticket getTicket(long id)
            throws TicketNotFound {
        Ticket ticket = tRepo.findById(id).orElse(null);
        if (ticket == null) {
            throw new TicketNotFound(id);
        }
        return ticket;
    }

    @Transactional
    public Attachment getAttachment(long ticketId, UUID attachmentId)
            throws TicketNotFound, AttachmentNotFound {
        Ticket ticket = tRepo.findById(ticketId).orElse(null);
        if (ticket == null) {
            throw new TicketNotFound(ticketId);
        }
        Attachment attachment = aRepo.findById(attachmentId).orElse(null);
        if (attachment == null) {
            throw new AttachmentNotFound(attachmentId);
        }
        return attachment;
    }

    @Transactional
    public void delete(long id) throws TicketNotFound {
        Ticket deletedTicket = tRepo.findById(id).orElse(null);
        if (deletedTicket == null) {
            throw new TicketNotFound(id);
        }
        deletedTicket.getCustomer().getTickets().remove(deletedTicket);
        tRepo.delete(deletedTicket);
    }

    @Transactional
    public void deleteAttachment(long ticketId, UUID attachmentId)
            throws TicketNotFound, AttachmentNotFound {
        Ticket ticket = tRepo.findById(ticketId).orElse(null);
        if (ticket == null) {
            throw new TicketNotFound(ticketId);
        }
        for (Attachment attachment : ticket.getAttachments()) {
            if (attachment.getId().equals(attachmentId)) {
                ticket.deleteAttachment(attachment);
                tRepo.save(ticket);
                return;
            }
        }
        throw new AttachmentNotFound(attachmentId);
    }

    @Transactional
    public long createTicket(String customerName, String subject,
                             String body, List<MultipartFile> attachments)
            throws IOException {
        TicketUser customer = tuRepo.findById(customerName).orElse(null);
        if (customer == null){
            throw new RuntimeException("User " + customerName + " not found.");
        }
        Ticket ticket = new Ticket();
        ticket.setCustomer(customer);
        ticket.setSubject(subject);
        ticket.setBody(body);
        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setTicket(ticket);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                ticket.getAttachments().add(attachment);
            }
        }
        Ticket savedTicket = tRepo.save(ticket);
        customer.getTickets().add(savedTicket);
        return savedTicket.getId();
    }

    @Transactional
    public void updateTicket(long id, String subject,
                             String body, List<MultipartFile> attachments)
            throws IOException, TicketNotFound {
        Ticket updatedTicket = tRepo.findById(id).orElse(null);
        if (updatedTicket == null) {
            throw new TicketNotFound(id);
        }
        updatedTicket.setSubject(subject);
        updatedTicket.setBody(body);
        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setTicket(updatedTicket);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                updatedTicket.getAttachments().add(attachment);
            }
        }
        tRepo.save(updatedTicket);
    }























    @Resource
    CommentRepository cRepo;

    @Transactional
    public void createComment(String username, long ticketId, String content)
            throws TicketNotFound {
        TicketUser user = tuRepo.findById(username).orElse(null);
        if (user == null){
            throw new RuntimeException("User " + username + " not found.");
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

