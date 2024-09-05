package hkmu.comps380f.controller;


import hkmu.comps380f.dao.UserManagementService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/signup")
public class SignUpController {

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


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String[] getRoles() {
            String[] value = new String[]{"ROLE_USER"};
            return value;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
    }

    @GetMapping("")
    public String signup() {
        return "redirect:/signup/create";
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("signup", "ticketUser", new Form());
    }
    @PostMapping("/create")
    public String create(Form form) throws IOException {
        umService.createTicketUser(form.getUsername(), form.getPassword(), form.getRoles(), form.getEmail(), form.getPhonenumber());
        return "redirect:/login";
    }


}
