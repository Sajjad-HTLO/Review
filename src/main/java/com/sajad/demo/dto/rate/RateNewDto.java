package com.sajad.demo.dto.rate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sajad.demo.domain.CommentRateStatus;
import com.sajad.demo.dto.ContextDto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RateNewDto extends ContextDto {
    /**
     * Initial status of the rate.
     */
    @JsonIgnore
    private CommentRateStatus status = CommentRateStatus.PENDING;

    /**
     * The actual rate value ranging from 1 to 5.
     */
    @NotNull
    @Max(value = 5, message = "max is 5")
    @Min(1)
    private Integer rate;

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public CommentRateStatus getStatus() {
        return status;
    }

    public void setStatus(CommentRateStatus status) {
        this.status = status;
    }

}
