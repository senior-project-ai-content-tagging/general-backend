package me.ponlawat.domain.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.ponlawat.domain.user.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "contents")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contents_id_gen")
    @SequenceGenerator(name = "contents_id_gen", sequenceName = "content_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    @Type(type = "org.hibernate.type.TextType")
    private String content;

    @Column(name = "identifier", length = 64)
    private String identifier;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return Objects.equals(id, content.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}