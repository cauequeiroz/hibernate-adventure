package dto;

import java.time.LocalDate;

public class SalesReportDTO {
	private String productName;
	private Long quantity;
	private LocalDate date;
	
	public SalesReportDTO(String productName, Long quantity, LocalDate date) {
		this.productName = productName;
		this.quantity = quantity;
		this.date = date;
	}
	
	@Override
	public String toString() {
		return String.format("%10s | %5d | %10s", this.productName, this.quantity, this.date);
	}	
}
