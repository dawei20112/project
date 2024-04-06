import {
    Button,
    useDisclosure,
    Drawer,
    DrawerBody,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    DrawerContent,
    DrawerCloseButton,
    Input,

} from "@chakra-ui/react"
import {color} from "framer-motion";
import CreatCustomerFrom from "../shared/CreateCustomerForm.jsx";

const AddIcon = () => "+";
const CloseIcon = () => "x";
const CreateCustomerDrower = ({fetchCustomers}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    return <>
        <Button leftIcon={<AddIcon/>}
            // 设置按钮颜色
                colorScheme={"teal"}
                onClick={onOpen
                }>
            Create customer
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay/>
            <DrawerContent>
                <DrawerCloseButton/>
                <DrawerHeader>Create new customer</DrawerHeader>

                <DrawerBody>
                    <CreatCustomerFrom
                        // fetchCustomer={fetchCustomers}
                        onSuccess={fetchCustomers}
                    />
                    {/* <form
                id='my-form'
                onSubmit={(e) => {
                  e.preventDefault()
                  console.log('submitted')
                }}
              >
                <Input name='nickname' placeholder='Type here...' />
              </form> */}
                </DrawerBody>

                <DrawerFooter>
                    <Button leftIcon={<CloseIcon/>}
                        // 设置按钮颜色
                            colorScheme={"teal"}
                            onClick={onClose}>
                        Close
                    </Button>
                    {/* <Button type='submit' form='my-form'>
                Close
              </Button> */}
                </DrawerFooter>
            </DrawerContent>
        </Drawer>

    </>

}
// export default CreateMedicineDrower;
export default CreateCustomerDrower;

// export const App = () => {

//     return (
//       <>
//         <Button onClick={onOpen}>Open</Button>

//       </>
//     )
//   }