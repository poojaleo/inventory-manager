package com.shopify.inventoryservice.models.response;

import com.shopify.inventoryservice.models.ShipmentModel;

public class UpdateShipmentResponse {
    private ShipmentModel shipmentModel;

    public UpdateShipmentResponse() {
    }

    public UpdateShipmentResponse(Builder builder) {
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

        public UpdateShipmentResponse build() {
            return new UpdateShipmentResponse(this);
        }
    }
}
