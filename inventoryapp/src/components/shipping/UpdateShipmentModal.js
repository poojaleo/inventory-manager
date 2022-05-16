import React from "react";
import AuthService from "../../services/AuthService";
import axios from "axios";
import {Button, Form, FormGroup, Modal} from "react-bootstrap";
import {Input, Label} from "reactstrap";

const UpdateShipmentModal = (show, handleClose, shipmentId, createdAt, trackingNumber, shippingAddress, status) => {

    const baseURL = "https://4gybudb0ui.execute-api.us-west-2.amazonaws.com/inventory-manager/";
    let message = "";

    const updateShipment = (event) => {
        event.preventDefault();
        const companyName = AuthService.getCurrentCompanyName();
        console.log("Updating Shipment....");
        message = "Updating Shipment.....";
        const url = `${baseURL}/company/${companyName}/shipments/${shipmentId}`;
        const requestBody = {
            "shippingAddress": shippingAddress,
            "status": status,
            "trackingNumber": trackingNumber
        }
        axios.put(url, requestBody).then(response => {
            console.log(response.data);
            handleClose();
            window.location.reload();
        }).catch(error => console.log(error.message));
    }

    return (
        <div>
            <Modal show={show} onHide={handleClose}>
                <Form onSubmit={updateShipment}>
                    <Modal.Header closeButton>
                        <Modal.Title>Update Shipment</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"shipmentId"}>Shipment Id</Label>
                            <Input type={"text"} name={"shipmentId"} id={"shipmentId"} value={shipmentId} required={true}
                                   readOnly={true} />
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"createdAt"}>Created At</Label>
                            <Input type={"datetime"} name={"createdAt"} id={"createdAt"} value={createdAt} required={true}
                            readOnly={true}/>
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"trackingNumber"}>Tracking Number</Label>
                            <Input type={"text"} name={"trackingNumber"} id={"trackingNumber"} defaultValue={trackingNumber}
                                   onChange={event => trackingNumber=event.target.value} />
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"shippingAddress"}>Shipping Address</Label>
                            <Input type={"text"} name={"shippingAddress"} id={"shippingAddress"} defaultValue={shippingAddress}
                                    onChange={event => shippingAddress=event.target.value} />
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"status"}>Status</Label>
                            <Input type={"select"} name={"status"} id={"status"} defaultValue={status} required={true}
                                   onChange={event => status=event.target.value}>
                            <option>LABELCREATED</option>
                            <option>INTRANSIT</option>
                            <option>COMPLETED</option>
                            <option>DELAYED</option>
                            <option>FAILED</option>
                            <option>UNKNOWN</option>
                            </Input>
                        </FormGroup>
                        <div className={"d-flex justify-content-end"}>
                            <Button type={"submit"} color={"outline-success"}>Update Shipment</Button>

                        </div>
                        {message}
                    </Modal.Body>

                </Form>

            </Modal>
        </div>
    )

}

export default UpdateShipmentModal;