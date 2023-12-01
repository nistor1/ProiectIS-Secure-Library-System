package model;

public class Order {
    private Long id;
    private Long customerId;
    private Long employeeId;
    private Long bookId;

    public Order(Long id, Long customerId, Long bookId, Long employeeId) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.bookId = bookId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
