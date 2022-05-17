import {Container, Stack, Table} from "react-bootstrap";
import {Input, Label} from "reactstrap";
import SignoutButton from "../home/SignoutButton";
import GoToInventoryButton from "../inventory/GoToInventoryButton";
import React, {useEffect, useState} from "react";
import AuthService from "../../services/AuthService";
import {Button, Form, FormGroup} from "react-bootstrap";
import axios from "axios";
import UpdateShipmentModal from "./UpdateShipmentModal";
import "./Shipping.css";


const Shipping = (props) => {
    const [companyName, setCompanyName] = useState("");
    const [shipmentsList, setShipmentsList] = useState([]);
    const [activeProductsList, setActiveProductsList] = useState([]);
    const [updateShipmentModal, setUpdateShipmentModal] = useState(false);
    const [selectedShipment, setSelectedShipment] = useState({"companyName":companyName,"shipmentId":"","createdAt":"",
        "shippingAddress":"","status": "","trackingNumber":"","productsShipped":{}});
    const [shippingAddress, setShippingAddress] = useState("");
    const [selectedSku, setSelectedSku] = useState("");
    const [selectedSkuQuantity, setSelectedSkuQuantity] = useState(0);
    const [addProductToBeShipped, setAddProductToBeShipped] = useState([{sku:"", quantity: 0}]);
    const [message, setMessage] = useState("");
    const baseURL = "https://4gybudb0ui.execute-api.us-west-2.amazonaws.com/inventory-manager/";

    useEffect(() => {
       getShippingDetails();
       getActiveProductsList();
    },[]);

    const getShippingDetails = () => {
        const companyName = AuthService.getCurrentCompanyName();
        setCompanyName(companyName);
        /*const list = AuthService.getActiveProductList();
        setActiveProductsList(list);*/
        const url = `${baseURL}/company/${companyName}/shipments`

        axios.get(url).then(response => {
            console.log(response.data);
            setShipmentsList(response.data.shipmentModelList);
        }).catch(error => console.log(error.message))
    }

    const getActiveProductsList = () => {
        const companyName = AuthService.getCurrentCompanyName();
        setCompanyName(companyName);

        const url = `${baseURL}/company/${companyName}/products`

        axios.get(url).then(response => {
            console.log(response.data);
            setActiveProductsList(response.data.activeProductsList);
        }).catch(error => console.log(error.message));
    }

    const createShipment = (event) => {
        event.preventDefault();
        const url = `${baseURL}/company/${companyName}/shipments`

        setMessage("Creating Shipment...")

        let productsShipped = {};

        addProductToBeShipped?.map(k => {
            productsShipped[k.sku] = k.quantity;

        })

        const requestBody = {
            "shippingAddress": shippingAddress,
            "productsShipped": productsShipped
        }

        console.log(requestBody);

        axios.post(url, requestBody).then(response => {
            console.log(response.data);
            window.location.reload();
        }).catch(error => {
            console.log(error.message);
            alert("Something went wrong.")
        });
    }

    const splitProducts = (productsShipped) => {
        return (
            <div>
                {Object.keys(productsShipped).map(k => (
                    `${k}-${productsShipped[k]}`
                )).join(',')}
            </div>
        )
    }

    const openUpdateShipmentModal = (event, shipmentId, createdAt, trackingNum, shippingAddress, status, productsShipped) => {
        event.preventDefault();
        let shipmentChange = {...selectedShipment};
        shipmentChange["companyName"] = companyName;
        shipmentChange["shipmentId"] = shipmentId;
        shipmentChange["createdAt"] = createdAt;
        shipmentChange["trackingNumber"] = trackingNum;
        shipmentChange["shippingAddress"] = shippingAddress;
        shipmentChange["status"] = status;
        shipmentChange["productsShipped"] = productsShipped;
        setSelectedShipment(shipmentChange);
        setUpdateShipmentModal(true);
    }

    let shipmentRows = shipmentsList?.map(shipment => {
        return (
            <tr key={shipment.shipmentId}>
                <td>{shipment.shipmentId}</td>
                <td>{shipment.createdAt}</td>
                <td>{shipment.trackingNumber}</td>
                <td>{shipment.shippingAddress}</td>
                <td>{shipment.status}</td>
                <td>{splitProducts(shipment.productsShipped)}</td>
                <td><Button size={"sm"} onClick={(event) =>
                    openUpdateShipmentModal(event, shipment.shipmentId, shipment.createdAt, shipment.trackingNumber,
                        shipment.shippingAddress, shipment.status, shipment.productsShipped)}>Update</Button> </td>
            </tr>
        )
    })

    const handleAddProductToBeShipped = () => {
        const values = [...addProductToBeShipped];
        values.push({
            sku: "",
            quantity: 0
        });
        setAddProductToBeShipped(values);
    }

    const handleRemoveProduct = (index) => {
        const values = [...addProductToBeShipped];
        values.splice(index, 1);
        setAddProductToBeShipped(values);
    }

    const handleInputChange = (index, event) => {
        const values = [...addProductToBeShipped];
        values[index][event.target.name] = event.target.value;
        setAddProductToBeShipped(values);
        setSelectedSku(event.target.value)
    }

    const dynamicForm = addProductToBeShipped.map((element, index) => {
        return (
            <div className={"form-inline"} key={index}>
                <Label>SKU</Label>
                <select className={"mx-3 p-2"} name={"sku"} onChange={event => handleInputChange(index, event)}>
                    <option  disabled selected value> -- select an option -- </option>
                    {activeProductsList.map(({sku, quantity}, index) => <option key={sku} value={sku}>{sku} - Available: {quantity}</option>)}
                </select>
                <Label className={"mx-2"}>Quantity</Label>
                <input type={"number"} name={"quantity"} value={element.quantity || ""}
                       onChange={event => handleInputChange(index, event)} />
                <Button onClick={() => handleRemoveProduct(index)}>Remove</Button>
            </div>
        )
    })


    return (
        <Container className={"mt-5 mx-auto"}>
            <div className={"d-flex flex-row justify-content-end"}>
                <Stack direction={"vertical"}>
                    <h4 className={"me-auto"}>Shipment Management</h4>
                </Stack>
                <Stack className={"ms-auto text-end"} direction={"vertical"} gap={2}>
                    <SignoutButton />
                    <GoToInventoryButton />
                </Stack>
            </div>
            <div>
                <Form onSubmit={createShipment}>
                    <FormGroup className={"mb-3 col-xl-4"}>
                        <Label htmlFor={"shippingAddress"}>Shipping Address</Label>
                        <Input type={"text"} name={"shippingAddress"} id={"shippingAddress"} defaultValue={shippingAddress}
                               onChange={event => setShippingAddress(event.target.value)}/>
                    </FormGroup>
                    <FormGroup className={"mb-3"}>
                        {dynamicForm}
                    </FormGroup>
                    <FormGroup className={"mb-3"}>
                        <Button onClick={handleAddProductToBeShipped}>Add</Button>
                    </FormGroup>
                    <FormGroup className={"mb-3"}>
                        <Button type={"submit"}>Create Shipment</Button>
                    </FormGroup>
                    {message}
                </Form>
            </div>
            <div>
                <Table className={"displayTable"}  >
                    <thead >
                    <tr>
                        <th width={"15%"}>Shipment Id</th>
                        <th>Created At</th>
                        <th>Tracking Number</th>
                        <th>Shipping Address</th>
                        <th>Status</th>
                        <th>Products Shipped</th>
                        <th> </th>
                    </tr>
                    </thead>
                    <tbody>
                    {shipmentRows}
                    </tbody>
                </Table>
            </div>
            {UpdateShipmentModal(updateShipmentModal, ()=>setUpdateShipmentModal(false),
                selectedShipment.shipmentId, selectedShipment.createdAt, selectedShipment.trackingNumber,
            selectedShipment.shippingAddress, selectedShipment.status)}
        </Container>
    )
}

export default Shipping;