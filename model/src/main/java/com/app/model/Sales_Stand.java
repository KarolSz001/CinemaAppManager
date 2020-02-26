package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class Sales_Stand {
    Integer id;
    Integer customerId;
    Integer movieId;
    LocalDateTime start_date_time;
    Boolean discount;
}
