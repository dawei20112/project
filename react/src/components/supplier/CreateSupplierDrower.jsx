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
// import CreatCustomerFrom from "../shared/CreateCustomerForm";
import CreatSupplierFrom from "../supplier/CreateSupplierForm.jsx" ;
// import CreatCustomerFrom from "../shared/CreateCustomerForm.jsx";

const AddIcon = () => "+";
const CloseIcon = () => "x";
const CreateSupplierDrower = ({fetchCustomers}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    return <>
        <Button leftIcon={<AddIcon/>}
            // 设置按钮颜色
                colorScheme={"teal"}
                onClick={onOpen}>
            Create Supplier
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay/>
            <DrawerContent>
                <DrawerCloseButton/>
                <DrawerHeader>Insert Into Supplier</DrawerHeader>

                <DrawerBody>
                    <CreatSupplierFrom
                        onSuccess={fetchCustomers}
                    />

                </DrawerBody>

                <DrawerFooter>
                    <Button leftIcon={<CloseIcon/>}
                        // 设置按钮颜色
                            colorScheme={"teal"}
                            onClick={onClose}>
                        Close
                    </Button>

                </DrawerFooter>
            </DrawerContent>
        </Drawer>

    </>

}
export default CreateSupplierDrower;

