package com.shopify.inventoryservice.models.response;

import com.shopify.inventoryservice.models.ShipmentModel;

public class CreateShipmentResponse {
    private ShipmentModel shipmentModel;

    public CreateShipmentResponse() {
    }

    public CreateShipmentResponse(Builder builder) {
        this.shipmentModel = builder.shipmentModel;
    }

    public ShipmentModel getShipmentModel() {
        return shipmentModel;
    }

    public void setShipmentModel(ShipmentModel shipmentModel) {
        this.shipmentModel = shipmentModel;
    }

    public static Builder builder() { return new Builder();}

    public static final class Builder {
        private ShipmentModel shipmentModel;

        public Builder withShipmentModel(ShipmentModel shipmentModelToUse) {
            this.shipmentModel = shipmentModelToUse;
            return this;
        }

        public CreateShipmentResponse build() {
            return new CreateShipmentResponse(this);
        }
    }
}
