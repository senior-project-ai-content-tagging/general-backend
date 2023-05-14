package me.ponlawat.domain.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketContentRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
