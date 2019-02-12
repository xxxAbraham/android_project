package iut.nantes.serverMobile.api.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Djurdjevic Sacha
 */
@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idEvent", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;
    private LocalDate date;
    private Boolean active;

    @OneToMany(mappedBy = "event")
    private List<Expense> expenseList;

    @ManyToMany(cascade = {
        CascadeType.MERGE,
        CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinTable(name = "user_event",
            joinColumns = { @JoinColumn(name = "event_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private List<User> userList;

    @ManyToOne
    private User user;

    public Event() {
        super();
    }

    public Event(String title, LocalDate date, User user) {
        this.title = title;
        this.date = date;
        this.user = user;
        this.active = true;
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", title=" + title + ", date=" + date + ", active=" + active + ", user=" + user + '}';
    }

}
