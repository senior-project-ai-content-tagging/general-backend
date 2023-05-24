package me.ponlawat.domain.content.internal;

import lombok.Setter;
import me.ponlawat.domain.content.Content;
import me.ponlawat.domain.content.ContentRepository;
import me.ponlawat.domain.content.ContentService;
import me.ponlawat.domain.content.exception.ContentNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
@Setter
public class ContentServiceImpl implements ContentService {

    @Inject
    ContentRepository contentRepository;

    @Override
    public Content getContentById(long id) {
        Optional<Content> optionalContent = contentRepository.findByIdOptional(id);
        if (optionalContent.isEmpty()) {
            throw new ContentNotFoundException(id);
        }

        return optionalContent.get();
    }
}
