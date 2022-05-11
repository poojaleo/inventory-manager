package com.shopify.inventoryservice.models.response;

import com.shopify.inventoryservice.models.ShipmentModel;

public class GetShipmentResponse {
    private ShipmentModel shipmentModel;

    public GetShipmentResponse() {
    }

    public GetShipmentResponse(Builder builder) {
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

        public GetShipmentResponse build() {
            return new GetShipmentResponse(this);
        }
    }
}
