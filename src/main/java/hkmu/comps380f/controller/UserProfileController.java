package hkmu.comps380f.controller;

import hkmu.comps380f.dao.TicketService;
import hkmu.comps380f.dao.UserManagementService;
import hkmu.comps380f.exception.TicketNotFound;
import hkmu.comps380f.model.Ticket;
import hkmu.comps380f.model.TicketUser;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/user_profile")
public class UserProfileController {





    public static class Form {
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }





    @Resource
    private TicketService tService;
    @Resource
    private UserManagementService UMService;

    @GetMapping("/")
    public String showprofile() {
        return "user_profile";
    }

    @GetMapping(value = {"", "/{username}"})
    public String showprofile(ModelMap model, @PathVariable("username") String username) {
        model.addAttribute("ticketDatabase", tService.getTicketsByUser(username));
        model.addAttribute("profile", UMService.getTicketUsersByUsername(username));
        return "user_profile";
    }







    @GetMapping("/edit/{username}")
    public ModelAndView showEdit(@PathVariable("username") String username, Principal principal, HttpServletRequest request){
        TicketUser ticketuser = UMService.getTicketUsersByUsername(username);
        if (ticketuser == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(ticketuser.getUsername()))) {
            return new ModelAndView(new RedirectView("/user_profile/"+ticketuser.getUsername(), true));
        }
        ModelAndView modelAndView = new ModelAndView("edit_user_profile");
        modelAndView.addObject("ticketuser", ticketuser);
        Form ticketuserForm = new Form();
        ticketuserForm.setDescription(ticketuser.getDescription());
        modelAndView.addObject("ticketuserForm", ticketuserForm);
        return modelAndView;
    }






    @PostMapping("/edit/{username}")
    public String edit(@PathVariable("username") String username, Form form,
                       Principal principal, HttpServletRequest request){
        TicketUser ticketuser = UMService.getTicketUsersByUsername(username);
        if (ticketuser == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(ticketuser.getUsername()))) {
            return "redirect:/user_profile/" + username;
        }
        UMService.updateDescription(username, form.getDescription());
        return "redirect:/user_profile/" + username;
    }







}
