import React from "react";
import AuthService from "../../services/AuthService";
import axios from "axios";
import {Button, Form, FormGroup, Modal} from "react-bootstrap";
import {FormText, Input, Label} from "reactstrap";

const CreateInventoryModal = (show, handleClose, sku, name, description, quantity, cost) => {
    const baseURL = "https://4gybudb0ui.execute-api.us-west-2.amazonaws.com/inventory-manager/";
    let message = "";

    const createProduct = (event) => {
        event.preventDefault();
        const companyName = AuthService.getCurrentCompanyName();
        console.log("Adding new Product....");
        message = "Adding new Product.....";
        const url = `${baseURL}/company/${companyName}/products`;
        const requestBody = {
            "sku": sku,
            "name": name,
            "description": description,
            "quantity": quantity,
            "cost": cost
        }

        axios.post(url, requestBody).then(response => {
            console.log(response.data);
            handleClose();
            window.location.reload();
        }).catch(error => {
            console.log(error.message);
            alert("Sku Already exists.")
        });
    }

    return (
        <div>

            <Modal show={show} onHide={handleClose}>
                <Form onSubmit={createProduct}>
                    <Modal.Header closeButton>
                        <Modal.Title>Add Product</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"sku"}>SKU</Label>
                            <Input type={"text"} name={"sku"} id={"sku"} defaultValue={sku} required={true}
                                onChange={event => sku=event.target.value}/>
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"name"}>Name</Label>
                            <Input type={"text"} name={"name"} id={"name"} defaultValue={name} required={true}
                                   onChange={event => name=event.target.value}/>
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"description"}>Description</Label>
                            <Input type={"text"} name={"description"} id={"description"} defaultValue={description}
                                   onChange={event => description=event.target.value} />
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"quantity"}>Quantity</Label>
                            <Input type={"number"} name={"quantity"} id={"quantity"} defaultValue={quantity} required={true}
                                   min={0} step={1} onChange={event => quantity=event.target.value} />
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"cost"}>Cost</Label>
                            <Input type={"number"} name={"cost"} id={"cost"} defaultValue={cost} required={true}
                                   min={0} step={0.01} onChange={event => cost=event.target.value}/>
                        </FormGroup>
                        <FormText>{message}</FormText>

                        <div className={"d-flex justify-content-end"}>
                            <Button type={"submit"} color={"outline-success"}>Add Product</Button>

                        </div>

                    </Modal.Body>

                </Form>

            </Modal>
        </div>
    )
}

export default CreateInventoryModal;