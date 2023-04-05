package me.ponlawat.domain.content.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.ponlawat.domain.content.Content;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentDetailResponse {
    Long id;
    String title;
    String content;
    List<String> categories;

    public static ContentDetailResponse fromContent(Content content) {
        return ContentDetailResponse.builder()
                .id(content.getId())
                .title(content.getTitle_th())
                .content(content.getContent_th())
                .categories(content.getCategories().stream().map(category -> category.getName()).collect(Collectors.toList()))
                .build();
    }
}