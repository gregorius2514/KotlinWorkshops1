package com.virtuslab.workshops.kotlin.run.dto;

import java.util.Objects;

public class WinnerDto {

    Integer winnerId;
    Integer runId;

    public WinnerDto() {
    }

    public Integer getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Integer winnerId) {
        this.winnerId = winnerId;
    }

    public Integer getRunId() {
        return runId;
    }

    public void setRunId(Integer runId) {
        this.runId = runId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WinnerDto winnerDto = (WinnerDto) o;
        return Objects.equals(winnerId, winnerDto.winnerId) &&
                Objects.equals(runId, winnerDto.runId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(winnerId, runId);
    }
}
