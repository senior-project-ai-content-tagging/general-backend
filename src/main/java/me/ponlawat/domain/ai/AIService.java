package me.ponlawat.domain.ai;

import com.google.cloud.storage.BlobInfo;

import java.io.InputStream;

public interface AIService {
    void uploadModal(InputStream file);
}
