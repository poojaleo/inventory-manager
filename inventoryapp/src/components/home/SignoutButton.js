import {useNavigate} from "react-router-dom";
import AuthService from "../../services/AuthService";
import {Button} from "react-bootstrap";

const SignoutButton = () => {
    const navigate = useNavigate();

    const routeToHomePage = () => {
        let path = '/home';
        AuthService.resetUserSession();
        navigate(path);
    }

    return (
        <div>
            <Button color={"outline-secondary"} type={"submit"} onClick={routeToHomePage}>Sign Out</Button>
        </div>
    )
}

export default SignoutButton;