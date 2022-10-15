package com.goorm.baromukja.entity;

import com.goorm.baromukja.dto.member.MemberRes;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/14
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String username;        // login ID

    @Column(length = 100,nullable = false)
    private String password;

    @Column(length = 50)
    private String nickName;

    @Column(length = 50)
    private String realName;

    @Setter
    @Column(length = 1000)
    private String refreshToken;		// 리프레쉬 토큰

    @Column
    private LocalDateTime createDate;			// 생성시각

    @Column
    private LocalDateTime updateDate;			// 수정시각

    @Enumerated(EnumType.STRING)
    private MemberRole role;			//ROLE_USER, ROLE_ADMIN...

    public List<String> getRoleList() {
        ArrayList<String> roles = new ArrayList<>();
        roles.add(role.getRole());
        return roles;
    }

    public MemberRes toDto() {
        return MemberRes.builder()
                .id(this.id)
                .username(this.username)
                .role(role.getRole())
                .realName(this.realName)
                .createDate(createDate)
                .updateDate(updateDate)
                .build();
    }
}
