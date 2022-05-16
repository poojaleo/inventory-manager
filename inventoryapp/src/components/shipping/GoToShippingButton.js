import {useNavigate} from "react-router-dom";
import {Button} from "react-bootstrap";

const GoToShippingButton = () => {
    const navigate = useNavigate();

    const routeToShippingPage = () => {
        let path = '/shipping';
        navigate(path);
    }

    return (
        <div>
            <Button color={"primary"} type={"submit"} onClick={routeToShippingPage}>Manage Shipping</Button>
        </div>
    )
}

export default GoToShippingButton;