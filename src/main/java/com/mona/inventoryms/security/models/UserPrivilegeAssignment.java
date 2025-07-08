package com.mona.inventoryms.security.models;

import com.mona.inventoryms.models.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_privilege_assignment")
public class UserPrivilegeAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @Column(name = "userid", insertable = false, updatable = false)
    private Long userid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "privilegeid", nullable = false)
    private Privilege privilege;

    @Column(name = "privilegeid", insertable = false, updatable = false)
    private Long privilegeid;

    public UserPrivilegeAssignment(Long userid, Long privilegeid) {
        this.userid = userid;
        this.privilegeid = privilegeid;
    }
}
