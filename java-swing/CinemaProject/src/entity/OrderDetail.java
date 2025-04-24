package entity;

import java.util.Objects;

public class OrderDetail {
    private String orderDetailID;
    private TicketDetail ticketDetail;
    private Orders order;
    private Product product;
    private MovieSchedule schedule;
    private int quantity;

    public OrderDetail(String orderDetailID, TicketDetail ticketDetail,
                       Orders order, Product product,
                       MovieSchedule schedule, int quantity) {
        this.orderDetailID = orderDetailID;
        this.ticketDetail = ticketDetail;
        this.order = order;
        this.product = product;
        this.schedule = schedule;
        this.quantity = quantity;
    }

    public String getOrderDetailID() { return orderDetailID; }
    public void setOrderDetailID(String orderDetailID) { this.orderDetailID = orderDetailID; }

    public TicketDetail getTicketDetail() { return ticketDetail; }
    public void setTicketDetail(TicketDetail ticketDetail) { this.ticketDetail = ticketDetail; }

    public Orders getOrder() { return order; }
    public void setOrder(Orders order) { this.order = order; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public MovieSchedule getSchedule() { return schedule; }
    public void setSchedule(MovieSchedule schedule) { this.schedule = schedule; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetail)) return false;
        OrderDetail other = (OrderDetail) o;
        return Objects.equals(orderDetailID, other.orderDetailID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDetailID);
    }
}
