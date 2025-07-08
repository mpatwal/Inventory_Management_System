package com.mona.inventoryms.models;

public class ProductStats {
    private Long currentMonthCount;
    private Long previousMonthCount;
    private double percentageDifference;

    public ProductStats(Long currentMonthCount, Long previousMonthCount, double percentageDifference) {
        this.currentMonthCount = currentMonthCount;
        this.previousMonthCount = previousMonthCount;
        this.percentageDifference = percentageDifference;
    }

    // Getters and setters
    public Long getCurrentMonthCount() { return currentMonthCount; }
    public Long getPreviousMonthCount() { return previousMonthCount; }
    public double getPercentageDifference() { return percentageDifference; }
}
