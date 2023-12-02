package model;

public class Order {
    private Long id;
    private Long customerId;
    private Long employeeId;
    private Long bookId;
    private String author;
    private String title;
    private String publishedDate;
    private String customerUsername;
    private String employeeUsername;

    public Order(Long id, Long customerId, Long bookId, Long employeeId) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.bookId = bookId;
    }
    public Order(Long id, Long bookId, Long customerId, String author, String title, String publishedDate, String customerUsername, String employeeUsername) {
        this.id = id;
        this.bookId = bookId;
        this.customerId = customerId;
        this.author = author;
        this.title = title;
        this.publishedDate = publishedDate;
        this.customerUsername = customerUsername;
        this.employeeUsername = employeeUsername;
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
    public boolean employeeIdIsZero() {
        if (employeeId == 0L) {
            return true;
        } else {
            return false;
        }
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

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }
    @Override
    public String toString() {

        return "Order{ book : " +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", customer='" + customerUsername + '\'' +
                ", employee='" + employeeUsername + '\'' +
                ", quantity = 1 book" +
                '}';
    }
}
