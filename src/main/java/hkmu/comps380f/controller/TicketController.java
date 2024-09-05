package hkmu.comps380f.controller;

import hkmu.comps380f.dao.CommentService;
import hkmu.comps380f.dao.TicketService;
import hkmu.comps380f.exception.AttachmentNotFound;
import hkmu.comps380f.exception.TicketNotFound;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Ticket;
import hkmu.comps380f.view.DownloadingView;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/PhotoBlog")
public class TicketController {

    @Resource
    private TicketService tService;

    @Resource
    private CommentService cService;

    // Controller methods, Form-backing object, ...
    @GetMapping(value = {"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("ticketDatabase", tService.getTickets());
        return "list";
    }
    @GetMapping(value = {"/list/user/{username}"})
    public String list(ModelMap model, @PathVariable("username") String username) {
        model.addAttribute("ticketDatabase", tService.getTicketsByUser(username));
        return "list";
    }
    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("add", "ticketForm", new Form());
    }

    @PostMapping("/create")
    public View create(Form form, Principal principal) throws IOException {
        long ticketId = tService.createTicket(principal.getName(),
                form.getSubject(), form.getBody(), form.getAttachments());
        return new RedirectView("/PhotoBlog/view/" + ticketId, true);
    }
    public static class Form {
        private String subject;
        private String body;
        private List<MultipartFile> attachments;
        // Getters and Setters of customerName, subject, body, attachments

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }



    @GetMapping("/view/{ticketId}")
    public ModelAndView view(@PathVariable("ticketId") long ticketId,
                       ModelMap model)
            throws TicketNotFound {
        Ticket ticket = tService.getTicket(ticketId);
        List<Comment> comments = cService.findCommentByTicketId(ticketId);
        model.addAttribute("ticketId", ticketId);
        model.addAttribute("ticket", ticket);
        model.addAttribute("comments", comments);
        return new ModelAndView("view", "cForm", new cForm());
    }



    public static class cForm {
        private String comment;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

    @PostMapping("/view/{ticketId}/comment/new/")
    public View newComment(cForm cform, Principal principal, @PathVariable("ticketId") long ticketId) throws IOException {
        String customerName = principal.getName();
        cService.createComment(customerName, ticketId, cform.getComment());
        return new RedirectView("/PhotoBlog/view/" + ticketId, true);
    }


    @GetMapping("/{ticketId}/attachment/{attachment:.+}")
    public View download(@PathVariable("ticketId") long ticketId,
                         @PathVariable("attachment") UUID attachmentId)
            throws TicketNotFound, AttachmentNotFound {
        Attachment attachment = tService.getAttachment(ticketId, attachmentId);
        return new DownloadingView(attachment.getName(),
                    attachment.getMimeContentType(), attachment.getContents());
    }

    @GetMapping("/delete/{ticketId}")
    public String deleteTicket(@PathVariable("ticketId") long ticketId)
            throws TicketNotFound {
        tService.delete(ticketId);
        return "redirect:/PhotoBlog/list";
    }

    @GetMapping("/{ticketId}/delete/{attachment:.+}")
    public String deleteAttachment(@PathVariable("ticketId") long ticketId,
                                   @PathVariable("attachment") UUID attachmentId)
            throws TicketNotFound, AttachmentNotFound {
        tService.deleteAttachment(ticketId, attachmentId);
        return "redirect:/PhotoBlog/view/" + ticketId;
    }

    @GetMapping("/comment/delete/{ticketId}/{commentId}")
    public String deleteComment(@PathVariable("ticketId") long ticketId,
                                @PathVariable("commentId") long commentId)
            throws TicketNotFound{
        tService.delete(ticketId, commentId);
        return "redirect:/PhotoBlog/view/" + ticketId;
    }


    @ExceptionHandler({TicketNotFound.class, AttachmentNotFound.class})
    public ModelAndView error(Exception e) {
        return new ModelAndView("error", "message", e.getMessage());
    }

    @GetMapping("/edit/{ticketId}")
    public ModelAndView showEdit(@PathVariable("ticketId") long ticketId,
                                 Principal principal, HttpServletRequest request)
            throws TicketNotFound {
        Ticket ticket = tService.getTicket(ticketId);
        if (ticket == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(ticket.getCustomerName()))) {
            return new ModelAndView(new RedirectView("/PhotoBlog/list", true));
        }
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("ticket", ticket);
        Form ticketForm = new Form();
        ticketForm.setSubject(ticket.getSubject());
        ticketForm.setBody(ticket.getBody());
        modelAndView.addObject("ticketForm", ticketForm);
        return modelAndView;
    }
    @PostMapping("/edit/{ticketId}")
    public String edit(@PathVariable("ticketId") long ticketId, Form form,
                       Principal principal, HttpServletRequest request)
            throws IOException, TicketNotFound {
        Ticket ticket = tService.getTicket(ticketId);
        if (ticket == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(ticket.getCustomerName()))) {
            return "redirect:/PhotoBlog/list";
        }
        tService.updateTicket(ticketId, form.getSubject(),
                form.getBody(), form.getAttachments());
        return "redirect:/PhotoBlog/view/" + ticketId;
    }








}

