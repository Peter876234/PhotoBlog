package hkmu.comps380f.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class TicketUser {
    @Id
    private String username;
    private String password;
    private String email;
    private String phonenumber;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> roles = new ArrayList<>();
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comments = new ArrayList<>();

    @CreationTimestamp
    private Date createTime;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @UpdateTimestamp
    private Date updateTime;
    private String description;



    public TicketUser() {}
    public TicketUser(String username, String password, String[] roles, String email, String phonenumber) {
        this.username = username;
        this.password = "{noop}" + password;
        this.email = email;
        this.phonenumber = phonenumber;
        for (String role : roles) {
            this.roles.add(new UserRole(this, role));
        }
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public List<UserRole> getRoles() {

        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public void setRoles(String[] roles) {
        this.roles.clear();
        for (String role : roles) {
            this.roles.add(new UserRole(this, role));
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String[] getRoleArray() {
        String[] roles = new String[this.roles.size()];
        for(int i = 0; i < roles.length; i++) {
            roles[i] = this.roles.get(i).getRole();
            System.out.println(roles[i]);
        }

        return roles;
    }
}

