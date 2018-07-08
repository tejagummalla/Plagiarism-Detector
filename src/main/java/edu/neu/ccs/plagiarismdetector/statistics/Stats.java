package edu.neu.ccs.plagiarismdetector.statistics;

import edu.neu.ccs.plagiarismdetector.user.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Representation of plagiarism application usage statistics
 *
 * @version 1.0
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_statistics")
public class Stats {
    /**
     * Store statistics such as id, userID, runningTime and createdDate
     */
    private long id;
    private Long runningTime;
    private LocalDateTime createdDate;
    private User user;

    private Long userID;

    /**
     * Constructor
     * @param stats Stats object
     */
    public Stats(Stats stats) {
        this.id = stats.id;
        this.runningTime = stats.runningTime;
        this.createdDate = stats.createdDate;
        this.userID = stats.user.getId();
    }

    /**
     * Constructor
     */
    public Stats() {
        this(System.currentTimeMillis());
    }

    /**
     * Constructor
     *
     * @param runningTime Long, runningTime
     */
    public Stats(Long runningTime) {
        this.runningTime = runningTime;
    }

    /**
     * Returns the ID
     *
     * @return Long
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    /**
     * Sets the Id
     *
     * @param id Long
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return running time
     */
    public Long getRunningTime() {
        return runningTime;
    }

    /**
     * Sets the runningTime
     * @param runningTime
     */
    public void setRunningTime(Long runningTime) {
        this.runningTime = runningTime;
    }

    /**
     * Returns the user
     * @return User
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    /**
     * Sets the user
     * @param user, User Object
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the created Date
     * @return LocalDateTime Object
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the created date
     * @param createdDate LocalDateTime Object
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Returns the userID
     * @return userID, long
     */
    @Transient
    public Long getUserID() {
        return userID;
    }

    /**
     * Sets the userID
     * @param userID, long
     */
    public void setUserID(Long userID) {
        this.userID = userID;
    }
}
