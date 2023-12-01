package repository.order;

public interface OrderCustomerRepository {
    void addOrder(Long idBook, Long idUser);
}
