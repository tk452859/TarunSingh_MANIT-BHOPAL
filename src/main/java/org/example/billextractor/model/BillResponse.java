package org.example.billextractor.model;

import java.util.List;
import java.util.Map;

public class BillResponse {
    private Boolean is_success;
    private Map<String, Integer> token_usage;
    private Data data;

    // No-args constructor
    public BillResponse() {
        this.is_success = true;
    }

    // All-args constructor
    public BillResponse(Boolean is_success, Map<String, Integer> token_usage, Data data) {
        this.is_success = is_success;
        this.token_usage = token_usage;
        this.data = data;
    }

    // Getters and Setters
    public Boolean getIs_success() { return is_success; }
    public void setIs_success(Boolean is_success) { this.is_success = is_success; }

    public Map<String, Integer> getToken_usage() { return token_usage; }
    public void setToken_usage(Map<String, Integer> token_usage) { this.token_usage = token_usage; }

    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }

    // Nested Data class
    public static class Data {
        private List<PagewiseLineItem> pagewise_line_items;
        private Integer total_item_count;
        private Double reconciled_amount;

        public Data() {
            this.total_item_count = 0;
            this.reconciled_amount = 0.0;
        }

        public Data(List<PagewiseLineItem> pagewise_line_items, Integer total_item_count, Double reconciled_amount) {
            this.pagewise_line_items = pagewise_line_items;
            this.total_item_count = total_item_count;
            this.reconciled_amount = reconciled_amount;
        }

        // Getters and Setters
        public List<PagewiseLineItem> getPagewise_line_items() { return pagewise_line_items; }
        public void setPagewise_line_items(List<PagewiseLineItem> pagewise_line_items) { this.pagewise_line_items = pagewise_line_items; }

        public Integer getTotal_item_count() { return total_item_count; }
        public void setTotal_item_count(Integer total_item_count) { this.total_item_count = total_item_count; }

        public Double getReconciled_amount() { return reconciled_amount; }
        public void setReconciled_amount(Double reconciled_amount) { this.reconciled_amount = reconciled_amount; }
    }

    // Nested PagewiseLineItem class
    public static class PagewiseLineItem {
        private String page_no;
        private String page_type;
        private List<BillItem> bill_items;

        public PagewiseLineItem() {
            this.page_no = "1";
            this.page_type = "Bill Detail";
        }

        public PagewiseLineItem(String page_no, String page_type, List<BillItem> bill_items) {
            this.page_no = page_no;
            this.page_type = page_type;
            this.bill_items = bill_items;
        }

        // Getters and Setters
        public String getPage_no() { return page_no; }
        public void setPage_no(String page_no) { this.page_no = page_no; }

        public String getPage_type() { return page_type; }
        public void setPage_type(String page_type) { this.page_type = page_type; }

        public List<BillItem> getBill_items() { return bill_items; }
        public void setBill_items(List<BillItem> bill_items) { this.bill_items = bill_items; }
    }

    // Nested BillItem class
    public static class BillItem {
        private String item_name;
        private Double item_amount;
        private Double item_rate;
        private Double item_quantity;

        public BillItem() {
            this.item_name = "";
            this.item_amount = 0.0;
            this.item_rate = 0.0;
            this.item_quantity = 0.0;
        }

        public BillItem(String item_name, Double item_amount, Double item_rate, Double item_quantity) {
            this.item_name = item_name;
            this.item_amount = item_amount;
            this.item_rate = item_rate;
            this.item_quantity = item_quantity;
        }

        // Getters and Setters
        public String getItem_name() { return item_name; }
        public void setItem_name(String item_name) { this.item_name = item_name; }

        public Double getItem_amount() { return item_amount; }
        public void setItem_amount(Double item_amount) { this.item_amount = item_amount; }

        public Double getItem_rate() { return item_rate; }
        public void setItem_rate(Double item_rate) { this.item_rate = item_rate; }

        public Double getItem_quantity() { return item_quantity; }
        public void setItem_quantity(Double item_quantity) { this.item_quantity = item_quantity; }
    }

}
