package hkmu.comps380f.controller;

import hkmu.comps380f.dao.CommentService;
import hkmu.comps380f.dao.TicketService;
import hkmu.comps380f.dao.UserManagementService;
import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Ticket;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryController {
    @Resource
    CommentService cService;

    @Resource
    TicketService tService;

    @GetMapping("/comment/{username}")
    public String cHistory (ModelMap model, @PathVariable ("username") String username){
        List<Comment> comments = cService.findCommentByUsername(username);
        model.addAttribute("comments", comments);
        model.addAttribute("username", username);

        return "commenthistory";
    }

    @GetMapping("/photo/{username}")
    public String tHistory (ModelMap model, @PathVariable ("username") String username){
        model.addAttribute("ticketDatabase", tService.getTicketsByUser(username));
        model.addAttribute("username", username);
        return "photohistory";
    }

}
