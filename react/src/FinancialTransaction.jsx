import {Button, Spinner, Text, Wrap, WrapItem} from "@chakra-ui/react";
import SidebarWithHeader from "./components/shared/SiderBar.jsx";
import CardWithImage from "./components/customer/Card.jsx";
// import { useEffect } from "react";
import {useEffect, useState} from "react";
import {getCustomers} from "./service/client";
import CreateCustomerDrower from "./components/customer/CreateCustomerDrower.jsx";
import {errorNotification} from "./service/notification.js";
// const App = () =>
const FinancialTransaction = () => {

    /**
     const [customers, setCustomers] = useState([]);
     const [loading, setLoading] = useState(false);
     const [err, setError] = useState("");

     const fetchCustomers = () => {
        setLoading(true);
        getCustomers()
            .then(res => {
                setCustomers(res.data)
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
                <CreateMedicineDrower
                    fetchCustomers={fetchCustomers}
                />
                <Text mt={5}> No customers available</Text>
            </SidebarWithHeader>
        )
    }


     if (customers.length <= 0) {
        return (
            <SidebarWithHeader>
                <CreateMedicineDrower
                    fetchCustomers={fetchCustomers}
                />
                <Text mt={5}> Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }
     */
    return (
        <SidebarWithHeader>
            {/** <CreateMedicineDrower
                fetchCustomers={fetchCustomers}
            />*/}
            <Wrap justify={"center"} spacing={"30"}>
                {/**{customers.map((customers, index) => (
                    <WrapItem key={index}>
                        <CardWithImage
                            {...customers}
                            imageNumber={index}
                            fetchCustomer={fetchCustomers}
                        />
                    </WrapItem>*/ /*这是原本内部的详见Customer 文件*/}
                <Text fontSize={"6xl"}>FinancialTransaction</Text>
            </Wrap>
        </SidebarWithHeader>
    )
}


// export default App;
export default FinancialTransaction