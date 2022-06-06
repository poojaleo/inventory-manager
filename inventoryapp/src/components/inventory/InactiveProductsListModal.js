import {Button, Modal, Stack, Table} from "react-bootstrap";
import React from "react";
import AuthService from "../../services/AuthService";
import axios from "axios";
import './inventory.css';


const InactiveProductsListModal = (show, handleClose, inactiveProducts) => {
    const baseURL = "https://4gybudb0ui.execute-api.us-west-2.amazonaws.com/inventory-manager/";

    const undeleteProduct = (sku) => {
        const companyName = AuthService.getCurrentCompanyName();
        console.log("UnDeleting product....");
        const url = `${baseURL}/company/${companyName}/products/inactive/${sku}`

        const requestBody = {
            "isActive": true
        }
        axios.put(url, requestBody).then(response => {
            console.log(response.data);
            handleClose();
            window.location.reload();
        }).catch(error => console.log(error.message))
    }

    let inactiveInventoryRows = inactiveProducts?.map(product => {
        return (
            <tr key={product.sku}>
                <td>{product.sku}</td>
                <td>{product.name}</td>
                <td>{product.quantity}</td>
                <td>{product.cost}</td>
                <td>{product.deleteComment}</td>
                <td><Button size={"sm"} onClick={() => undeleteProduct(product.sku)}>UnDelete Product</Button> </td>
            </tr>
        )
    })

    return (
        <Modal show={show} onHide={handleClose} dialogClassName={"modal-dimensions"}>
            <Modal.Header closeButton>
                <Modal.Title>
                    <Stack direction={"horizontal"} gap={2}>
                        <div>Inactive Products</div>
                    </Stack>
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Table bordered hover >
                    <thead>
                    <th width={"15%"}>SKU</th>
                    <th width={"20%"}>Name</th>
                    <th width={"10%"}>Quantity</th>
                    <th width={"10%"}>Cost</th>
                    <th width={"15%"}>Comment</th>
                    <th>Action</th>
                    </thead>
                    <tbody>
                    {inactiveInventoryRows}
                    </tbody>
                </Table>
            </Modal.Body>
        </Modal>
    )
}

export default InactiveProductsListModal;