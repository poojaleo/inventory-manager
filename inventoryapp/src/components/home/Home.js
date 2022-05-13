import {Container} from "react-bootstrap";
import invImage from "./inv.png"
import LoginButton from "./LoginButton";
import SignupButton from "./SignupButton";

const Home = (props) => {

    return (
        <Container>
            <div>
                <h1 className={"text-center mt-4 fw-bold"}>INVENTORY MANAGEMENT APP</h1>
                <h4 className={"text-center"}>Stay on top of your inventory</h4>
            </div>

            <div className={"d-flex flex-column"}>
                <h3 className={"mt-5"}>Simple to use inventory management system for any business buying and selling goods.</h3>
                <div className={"d-flex flex-row justify-content-between mt-5"}>
                   <div className={"d-flex flex-column"}>
                       <h4>Spend less time on inventory. More time on your business.</h4>
                       <h4>Inventorying doesn’t have to be a major headache–or even a hassle.</h4>
                       <div className={"btn-toolbar"}>
                           <div className={"btn-group"}>
                               <LoginButton />
                               <SignupButton />
                           </div>

                       </div>
                   </div>
                    <div className={"px-5"}>
                        <img src={invImage} alt={"inventory"} />
                    </div>
                </div>

            </div>

                    {/*Image*/}


        </Container>
    )
}

export default Home;