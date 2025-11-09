package com.irene.productService.dto;

public class InventoryResponse {
	    private String productName;
	    private int availableQuantity;
	    private boolean inStock;

	    public InventoryResponse(String productName, int availableQuantity) {
	        this.productName = productName;
	        this.availableQuantity = availableQuantity;
	        this.inStock = availableQuantity > 0;
	    }

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public int getAvailableQuantity() {
			return availableQuantity;
		}

		public void setAvailableQuantity(int availableQuantity) {
			this.availableQuantity = availableQuantity;
		}

		public boolean isInStock() {
			return inStock;
		}

		public void setInStock(boolean inStock) {
			this.inStock = inStock;
		}

		@Override
		public String toString() {
			return "InventoryDto [productName=" + productName + ", availableQuantity=" + availableQuantity
					+ ", inStock=" + inStock + "]";
		}
}
