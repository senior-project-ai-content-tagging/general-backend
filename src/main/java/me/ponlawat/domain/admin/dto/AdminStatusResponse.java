package me.ponlawat.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminStatusResponse {
    private long openCount;
    private long processingCount;
    private long doneCount;
    private long userCount;
}
