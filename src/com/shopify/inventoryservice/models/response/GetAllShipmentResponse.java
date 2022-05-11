package com.shopify.inventoryservice.models.response;

import com.shopify.inventoryservice.models.ShipmentModel;

import java.util.List;

public class GetAllShipmentResponse {
    private List<ShipmentModel> shipmentModelList;

    public GetAllShipmentResponse() {
    }

    public GetAllShipmentResponse(Builder builder) {
        this.shipmentModelList = builder.shipmentModelList;
    }

    public List<ShipmentModel> getShipmentModelList() {
        return shipmentModelList;
    }

    public void setShipmentModelList(List<ShipmentModel> shipmentModelList) {
        this.shipmentModelList = shipmentModelList;
    }

     public static Builder builder() { return new Builder();}

    public static final class Builder {
        private List<ShipmentModel> shipmentModelList;

        public Builder withShipmentList(List<ShipmentModel> shipmentModelListToUse) {
            shipmentModelList = shipmentModelListToUse;
            return this;
        }

        public GetAllShipmentResponse build() {
            return new GetAllShipmentResponse(this);
        }
    }
}
