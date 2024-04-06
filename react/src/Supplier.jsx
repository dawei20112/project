import {Button, Spinner, Text, Wrap, WrapItem} from "@chakra-ui/react";
import SidebarWithHeader from "./components/shared/SiderBar.jsx";
// import CardWithImage from "./components/customer/Card.jsx";
// import { useEffect } from "react";
import {useEffect, useState} from "react";
import {getCustomers, getSupplier} from "./service/client";
import CreateCustomerDrower from "./components/customer/CreateCustomerDrower.jsx";
import {errorNotification} from "./service/notification.js";
import CreateSupplierDrower from "./components/supplier/CreateSupplierDrower.jsx";
import CardWithImage from "./components/supplier/SupplierCard.jsx";
// const App = () =>
const Supplier = () => {

     const [supplier, setSupplier] = useState([]);
     const [loading, setLoading] = useState(false);
     const [err, setError] = useState("");

     const fetchCustomers = () => {
        setLoading(true);
        getSupplier()
            .then(res => {
                setSupplier(res.data)
            }).catch(err => {
                setError( err.response.data.messages)
            errorNotification(
                err.code,
                err.response.data.messages
            )
            // console.log(err);
        }).finally(() => {
            setLoading(false)
        })
    }

     useEffect(() => {
        fetchCustomers();
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
                <CreateSupplierDrower
                    fetchCustomers={fetchCustomers}
                />
                <Text mt={5}> No customers available</Text>
            </SidebarWithHeader>
        )
    }


     if (supplier.length <= 0) {
        return (
            <SidebarWithHeader>
                <CreateSupplierDrower
                    fetchCustomers={fetchCustomers}
                />
                <Text mt={5}> Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }
    return (
        <SidebarWithHeader>
           <CreateSupplierDrower
                fetchCustomers={fetchCustomers}
            />
            <Wrap justify={"center"} spacing={"30"}>
               {supplier.map((customers, index) => (
                    <WrapItem key={index}>
                        <CardWithImage
                            {...customers}
                            imageNumber={index}
                            fetchCustomer={fetchCustomers}
                        />
                    </WrapItem>)
                // <Text fontSize={"6xl"}>Supplier</Text>


                   )}
                   </Wrap>
        </SidebarWithHeader>
    )
}


// export default App;
export default Supplier;