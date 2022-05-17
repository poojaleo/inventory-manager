import React from "react";
import AuthService from "../../services/AuthService";
import axios from "axios";
import {Button, Form, FormGroup, Modal} from "react-bootstrap";
import {Input, Label} from "reactstrap";

const DeleteInventoryModal = (show, handleClose, sku, deleteComments) => {

    const baseURL = "https://4gybudb0ui.execute-api.us-west-2.amazonaws.com/inventory-manager/";
    deleteComments = ""
    let message = ""

    const deleteProduct = (event) => {
        event.preventDefault();
        const companyName = AuthService.getCurrentCompanyName();
        console.log("Deleting Product....");
        message = "Deleting Product.....";

        event.preventDefault();
        const url = `${baseURL}/company/${companyName}/products/${sku}?deleteComments=${deleteComments}`;


        axios.delete(url).then(response => {
            console.log(response.data);
            handleClose();
            window.location.reload();
        }).catch(error => console.log(error.message));

    }

    return (
        <div>

            <Modal show={show} onHide={handleClose}>
                <Form onSubmit={deleteProduct}>
                    <Modal.Header closeButton>
                        <Modal.Title>Delete Product</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"sku"}>SKU</Label>
                            <Input type={"text"} name={"sku"} id={"sku"} value={sku} required={true} readOnly={true} />
                        </FormGroup>
                        <FormGroup className={"mb-3"}>
                            <Label htmlFor={"deleteComments"}>Delete Comment</Label>
                            <Input type={"text"} name={"deleteComments"} id={"deleteComments"} defaultValue={deleteComments}
                                   onChange={event => deleteComments=event.target.value}/>
                        </FormGroup>
                        <div className={"d-flex justify-content-end"}>
                            <Button type={"submit"} color={"outline-danger"}>Delete Product</Button>
                        </div>
                        <p>{message}</p>
                    </Modal.Body>
                </Form>

            </Modal>
        </div>
    )


}

export default DeleteInventoryModal;