
package com.ecommerce.auditlog.ecommerceauditlog.model;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Date;

/**
 * The type Product.
 *
 * @author VISHNU
 */
@Entity
@Table(name = "audit_log")
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "module_name", nullable = false)
    private String moduleName;

    @Column(name = "action_name", nullable = false)
    private String actionName;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_stamp", nullable = false)
    private Date timeStamp;

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
        return id;
    }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(long id) {
        this.id = id;
    }

  /**
   * Gets userName
   *
   * @return the userName
   */
  public String getUserName() {
        return userName;
    }

  /**
   * Sets userName
   *
   * @param userName the userName
   */
  public void setUserName(String userName) {
        this.userName = userName;
    }

  /**
   * Gets last name.
   *
   * @return the last name
   */
  public String getmoduleName() {
        return moduleName;
    }

  /**
   * Sets moduleName
   *
   * @param moduleName the moduleName
   */
  public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

  /**
   * Gets actionName
   *
   * @return actionName
   */
  public String getActionName() {
        return actionName;
    }

  /**
   * Sets actionName
   *
   * @param actionName the actionName
   */
  public void setActionName(String actionName) {
        this.actionName = actionName;
    }

  /**
   * Gets timeStamp
   *
   * @return the timeStamp
   */
  public Date getTimeStamp() {
        return timeStamp;
    }

  /**
   * Sets timeStamp.
   *
   * @param timeStamp the timeStamp
   */
  public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", actionName='" + actionName + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
    

}
