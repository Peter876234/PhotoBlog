package hkmu.comps380f.controller;

import hkmu.comps380f.dao.UserManagementService;
import hkmu.comps380f.model.TicketUser;
import hkmu.comps380f.model.UserRole;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.substringAfter;

@Controller
@RequestMapping("/user")
public class UserManagementController {
    @Resource
    UserManagementService umService;
    public static class Form {

        private String username;  private String password;  private String[] roles;
        private String email;   private String phonenumber;



        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getemail() {
            return email;
        }

        public void setemail(String email) {
            this.email = email;
        }


        public String getPhoneNumber() {
            return phonenumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phonenumber = phoneNumber;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
    }


    public static class editForm {

        private String username;  private String password;  private String[] roles;
        private String email;   private String phonenumber; private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getemail() {
            return email;
        }

        public void setemail(String email) {
            this.email = email;
        }


        public String getPhoneNumber() {
            return phonenumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phonenumber = phoneNumber;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
    }






    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("addUser", "ticketUser", new Form());
    }

    @PostMapping("/create")
    public String create(Form form) throws IOException {
        umService.createTicketUser(form.getUsername(), form.getPassword(), form.getRoles(), form.getemail(), form.getPhoneNumber());
        return "redirect:/user/list";
    }
    @GetMapping({"", "/", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("ticketUsers", umService.getTicketUsers());
        return "listUser";
    }
    @GetMapping("/delete/{username}")
    public String deleteTicket(@PathVariable("username") String username) {
        umService.delete(username);
        return "redirect:/user/list";
    }

    @GetMapping("/useredit/{username}")
    public ModelAndView edituser(ModelMap model, @PathVariable("username") String username){
        TicketUser user = umService.getTicketUsersByUsername(username);
        model.addAttribute("username", user);

        ModelAndView modelAndView = new ModelAndView("useredit");
        modelAndView.addObject("ticketuser", user);
        editForm userForm = new editForm();
        userForm.setUsername(username);
        userForm.setDescription(user.getDescription());
        userForm.setemail(user.getEmail());
        System.out.println(user.getPassword());
        userForm.setPassword(substringAfter(user.getPassword(), "{noop}"));
        userForm.setPhoneNumber(user.getPhonenumber());
        userForm.setRoles(user.getRoleArray());
        modelAndView.addObject("ticketUser", userForm);

        return modelAndView;
    }

    @PostMapping("/useredit/{username}")
    public String geteditform(editForm form, @PathVariable("username") String username) throws IOException {
        umService.updateuserdata(username, "{noop}"+form.getPassword(), form.getRoles(), form.getemail(), form.getPhoneNumber(), form.getDescription());
        return "redirect:/user/list";
    }
}

