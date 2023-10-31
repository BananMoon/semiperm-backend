package com.project.semipermbackend.domain.store;


import com.project.semipermbackend.domain.common.BaseTimeEntity;
import com.project.semipermbackend.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

// 비식별 관계
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE member_zzim_store SET use_yn = false WHERE member_zzim_store__id = ?")
@Where(clause = "use_yn = true")
@Entity
@Table(name = "MEMBER_ZZIM_STORE")
public class MemberZzimStore extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "member_zzim_store_id")
    private Long memberZzimStoreId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Setter
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

}
