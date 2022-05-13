import {useNavigate} from "react-router-dom";
import {Button} from "react-bootstrap";

const LoginButton = () => {
    const navigate = useNavigate();

    const routeToLoginPage = () => {
        let path = '/login';
        navigate(path);
    }

    return (
        <div>
            <Button className={"mx-2 mt-4"} color={"primary"} type={"submit"} onClick={routeToLoginPage}>Login</Button>
        </div>
    )
}

export default LoginButton;