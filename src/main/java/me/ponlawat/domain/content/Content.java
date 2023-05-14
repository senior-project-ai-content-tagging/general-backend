package me.ponlawat.domain.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.ponlawat.domain.category.Category;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

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

    @Column(name = "title_th")
    private String titleTH;

    @Column(name = "content_th")
    @Type(type = "org.hibernate.type.TextType")
    private String contentTH;

    @Column(name = "title_en")
    private String titleEN;

    @Column(name = "content_en")
    @Type(type = "org.hibernate.type.TextType")
    private String contentEN;

    @Column(name = "identifier", length = 64)
    private String identifier;

    @ManyToMany
    @JoinTable(
            name = "contents_categories",
            joinColumns = @JoinColumn(name="content_id"),
            inverseJoinColumns = @JoinColumn(name="category_id")
    )
    private Set<Category> categories;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return Objects.equals(id, content.id);
    }

    @Override
    public int hashCode() {
        return 13;
    }
}
