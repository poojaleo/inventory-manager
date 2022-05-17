import React, {useEffect, useState} from "react";
import {Container, Stack} from "react-bootstrap";
import SignoutButton from "../home/SignoutButton";
import AuthService from "../../services/AuthService";
import axios from "axios";
import {Button} from "reactstrap";
import {Table} from "react-bootstrap";
import UpdateInventoryModal from "./UpdateInventoryModal";
import CreateInventoryModal from "./CreateInventoryModal";
import InactiveProductsListModal from "./InactiveProductsListModal";
import GoToShippingButton from "../shipping/GoToShippingButton";
import DeleteInventoryModal from "./DeleteInventoryModal";

const Inventory = (props) => {
    const [companyName, setCompanyName] = useState("");
    const [activeProductsList, setActiveProductsList] = useState([]);
    const [inactiveProductsList, setInactiveProductsList] = useState([]);
    const [updateModal, setUpdateModal]  = useState(false);
    const [inactiveProductsModal, setInactiveProductsModal] = useState(false);
    const[selectedProduct, setSelectedProduct] = useState({"companyName":companyName,"sku":"","name":"",
        "description":"","quantity": 0,"cost":0.00});
    const [createModal, setCreateModal] = useState(false);
    const [deleteModal, setDeleteModal] = useState(false);
    const [deleteComments, setDeleteComments] = useState(" ");
    const [deleteSku, setDeleteSku]  = useState("");
    const baseURL = "https://4gybudb0ui.execute-api.us-west-2.amazonaws.com/inventory-manager/";

    useEffect(() => {
        getCompanyDetails();
        getInactiveProductsList();
    },[]);

    const getCompanyDetails = () => {
        const companyName = AuthService.getCurrentCompanyName();
        setCompanyName(companyName);

        const url = `${baseURL}/company/${companyName}/products`

        axios.get(url).then(response => {
            console.log(response.data);
            setActiveProductsList(response.data.activeProductsList);
        }).catch(error => console.log(error.message)).then(r => AuthService.setActiveProductList(activeProductsList));
    }

    const getInactiveProductsList = () => {
        const companyName = AuthService.getCurrentCompanyName();
        const url = `${baseURL}/company/${companyName}/products/inactive`

        axios.get(url).then(response => {
            setInactiveProductsList(response.data.inactiveProductsList);
        }).catch(error => console.log(error.message))
    }

    const openDeleteProductModal = (sku, event) => {
        event.preventDefault();
        setDeleteSku(sku);
        setDeleteModal(true);
        /*event.preventDefault();
        const url = `${baseURL}/company/${companyName}/products/${sku}`;

        axios.delete(url).then(response => {
            console.log(response.data);
            window.location.reload();
        }).catch(error => console.log(error.message));*/
    }


    const openUpdateProductModal = (event, sku, name, description, quantity, cost) => {
        event.preventDefault();
        let productChange = {...selectedProduct};
        productChange["companyName"] = companyName;
        productChange["sku"] = sku;
        productChange["name"] = name;
        productChange["description"] = description;
        productChange["quantity"] = quantity;
        productChange["cost"] = cost;
        setSelectedProduct(productChange);
        setUpdateModal(true);
    }

    const openCreateProductModal = (event) => {
        event.preventDefault();
        let productChange = {...selectedProduct};
        productChange["companyName"] = companyName;
        productChange["sku"] = "";
        productChange["name"] = "";
        productChange["description"] = "";
        productChange["quantity"] = 0;
        productChange["cost"] = 0.00;
        setSelectedProduct(productChange);
        setCreateModal(true);
    }

    const openInactiveProductModal = (event) => {
        event.preventDefault();
        setInactiveProductsModal(true);
    }

    let activeInventoryRows = activeProductsList?.map(product => {
        return (
                <tr key={product.sku}>
                    <td>{product.sku}</td>
                    <td>{product.name}</td>
                    <td>{product.description}</td>
                    <td>{product.quantity}</td>
                    <td>{product.cost}</td>
                    <td><Button size={"sm"} color={"primary"}
                                onClick={(event => openUpdateProductModal(event, product["sku"], product["name"],
                                    product["description"], product["quantity"], product["cost"]) )}>Update</Button></td>

                    <td><Button size={"sm"} color={"danger"} onClick={(event) => openDeleteProductModal(product.sku, event)}>Delete</Button> </td>
                </tr>
        )
    })

    return (
        <Container className={"mt-5 mx-auto"}>
            <div className={"d-flex flex-row justify-content-end"}>
                <Stack direction={"vertical"}>
                    <h4 className={"me-auto"}>Inventory Management</h4>
                    <div>
                        <Button className={"mb-4"} size={"md"} color={"primary"}
                                onClick={(event => openCreateProductModal(event) )}>Add Product</Button>
                    </div>
                </Stack>
                <Stack className={"ms-auto text-end"} direction={"vertical"} gap={2}>
                    <SignoutButton />
                    <Stack className={"ms-auto text-end"} direction={"horizontal"} gap={2}>
                        <Button  size={"md"} color={"primary"}
                                 onClick={event => openInactiveProductModal(event)}>All Inactive Products</Button>
                        <GoToShippingButton />
                    </Stack>
                </Stack>
            </div>
            <div>
                <Table className={"displayTable"}  >
                    <thead >
                    <tr>
                        <th width={"15%"}>SKU</th>
                        <th>NAME</th>
                        <th>DESCRIPTION</th>
                        <th>QUANTITY</th>
                        <th>COST</th>
                        <th> </th>
                        <th> </th>
                    </tr>
                    </thead>
                    <tbody>
                    {activeInventoryRows}
                    </tbody>
                </Table>
                {UpdateInventoryModal(updateModal, ()=>setUpdateModal(false), selectedProduct.sku,
                    selectedProduct.name, selectedProduct.description, selectedProduct.quantity, selectedProduct.cost)}
                {CreateInventoryModal(createModal, ()=>setCreateModal(false), selectedProduct.sku,
                    selectedProduct.name, selectedProduct.description, selectedProduct.quantity, selectedProduct.cost)}
                {InactiveProductsListModal(inactiveProductsModal, ()=>setInactiveProductsModal(false),
                    inactiveProductsList)}
                {DeleteInventoryModal(deleteModal, ()=>setDeleteModal(false), deleteSku, deleteComments)}
            </div>

        </Container>
    )
}

export default Inventory;