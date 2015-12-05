package Beans;

import java.sql.Date;

public class Coupon {
	
	private long id;
	private String title;
	private Date start_Date;
	private Date end_Date;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + start_Date + ", endDate=" + end_Date
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getStartDate() {
		return start_Date;
	}
	public void setStartDate(Date startDate) {
		this.start_Date = startDate;
	}
	public Date getEndDate() {
		return end_Date;
	}
	public void setEndDate(Date endDate) {
		this.end_Date = endDate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public CouponType getType() {
		return type;
	}
	public void setType(CouponType type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Coupon(long id, String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image) {
		super();
		this.id = id;
		this.title = title;
		this.start_Date = startDate;
		this.end_Date = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}

}
