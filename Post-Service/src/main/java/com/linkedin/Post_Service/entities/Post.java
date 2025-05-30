package com.linkedin.Post_Service.entities;

import com.linkedin.Post_Service.entities.Users.Users;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users postedBy;

    @Column(nullable = false, length = 100, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date postedAt;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "post"
    )
    private Set<PostComment> comments = new HashSet<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "post"
    )
    private Set<PostLikes> likes = new HashSet<>();


    @OneToOne(mappedBy = "post",cascade = CascadeType.ALL, orphanRemoval = true)
    private PostFiles postFile;
}
