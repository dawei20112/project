import {Button, Spinner, Text, Wrap, WrapItem} from "@chakra-ui/react";
import SidebarWithHeader from "./components/shared/SiderBar.jsx";
// import CardWithImage from "./components/customer/Card.jsx";
// import { useEffect } from "react";
import {useEffect, useState} from "react";
import {getCustomers, getMedicine} from "./service/client";
// import CreateCustomerDrower from "./components/customer/CreateCustomerDrower.jsx";
import {errorNotification} from "./service/notification.js";
import CreateMedicineDrower from "./components/medicine/CreateMedicineDrower.jsx";
import CardWithImage from "./components/medicine/MCard.jsx";
// const App = () =>
const Medicine = () => {


     const [medicine, setMedicine] = useState([]);
     const [loading, setLoading] = useState(false);
     const [err, setError] = useState("");

     const fetchMedicine = () => {
        setLoading(true);
        getMedicine()
            .then(res => {
                setMedicine(res.data)
            }).catch(err => {
                setError( err.response.data.message)
            errorNotification(
                err.code,
                err.response.data.message
            )
            // console.log(err);
        }).finally(() => {
            setLoading(false)
        })
    }

     useEffect(() => {
        fetchMedicine();
    }, [])

     if (loading) {
        return (<SidebarWithHeader>
            <Spinner
                thickness='4px'
                speed='0.65s'
                emptyColor='gray.200'
                color='blue.500'
                size='xl'
            />
        </SidebarWithHeader>)
    }
     if (err){
        return (
            <SidebarWithHeader>
                <CreateMedicineDrower
                    fetchCustomers={fetchMedicine}
                />
                <Text mt={5}> No medicine available</Text>
            </SidebarWithHeader>
        )
    }


     if (medicine.length <= 0) {
        return (
            <SidebarWithHeader>
                <CreateMedicineDrower
                    fetchCustomers={fetchMedicine}
                />
                <Text mt={5}> Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }

    return (
        <SidebarWithHeader>
               <CreateMedicineDrower
                   fetchMedicine={fetchMedicine}
                 />
                <Wrap justify={"center"} spacing={"30"}>
                    {medicine.map((medicine, index) => (
                    <WrapItem key={index}>
                        <CardWithImage
                            {...medicine}
                            imageNumber={index}
                            fetchMedicine={fetchMedicine}
                        />
                    </WrapItem>
                    ))}</Wrap>

        </SidebarWithHeader>
    )
}


// export default App;
export default Medicine;