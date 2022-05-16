import {useNavigate} from "react-router-dom";
import {Button} from "react-bootstrap";

const GoToInventoryButton = () => {
    const navigate = useNavigate();

    const routeToInventoryPage = () => {
        let path = '/inventory';
        navigate(path);
    }

    return (
        <div>
            <Button color={"primary"} type={"submit"} onClick={routeToInventoryPage}>Manage Inventory</Button>
        </div>
    )
}

export default GoToInventoryButton;