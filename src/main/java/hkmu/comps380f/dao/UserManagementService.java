package hkmu.comps380f.dao;

import hkmu.comps380f.exception.TicketNotFound;
import hkmu.comps380f.model.Attachment;
import hkmu.comps380f.model.Ticket;
import hkmu.comps380f.model.TicketUser;
import hkmu.comps380f.model.UserRole;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserManagementService {
    @Resource
    private TicketUserRepository tuRepo;

    @Transactional
    public void createTicketUser(String username, String password, String[] roles, String email, String phonenum) {
        TicketUser user = new TicketUser(username, password, roles, email, phonenum);
        tuRepo.save(user);
    }
    @Transactional
    public List<TicketUser> getTicketUsers() {
        return tuRepo.findAll();
    }
    @Transactional
    public TicketUser getTicketUsersByUsername(String username) {
        return tuRepo.findById(username).orElse(null);
    }

    @Transactional
    public void updateDescription(String username, String description) {
        TicketUser updatedTicketUser = tuRepo.findById(username).orElse(null);
        updatedTicketUser.setDescription(description);

        tuRepo.save(updatedTicketUser);
    }
    @Transactional
    public void delete(String username) {
        TicketUser ticketUser = tuRepo.findById(username).orElse(null);
        if (ticketUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        tuRepo.delete(ticketUser);
    }


    @Transactional
    public void updateuserdata(String username, String password, String[] roles, String email, String phonenum, String description) {

        TicketUser updatedTicketUser = tuRepo.findById(username).orElse(null);
        if (updatedTicketUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        updatedTicketUser.setDescription(description);
        updatedTicketUser.setPassword(password);
        updatedTicketUser.setEmail(email);
        updatedTicketUser.setPhonenumber(phonenum);
        if(roles != null)
            updatedTicketUser.setRoles(roles);
        tuRepo.save(updatedTicketUser);
    }
}

