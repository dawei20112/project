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
import CreatCustomerFrom from "../shared/CreateCustomerForm";
import CreateMedicineForm from "./CreateMedicineForm";
// import CreatCustomerFrom from "../shared/CreateCustomerForm.jsx";

const AddIcon = () => "+";
const CloseIcon = () => "x";
const CreateMedicineDrower = ({fetchCustomers}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    return <>
        <Button leftIcon={<AddIcon/>}
            // 设置按钮颜色
                colorScheme={"teal"}
                onClick={onOpen
                }>
            Create medicine
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay/>
            <DrawerContent>
                <DrawerCloseButton/>
                <DrawerHeader>Insert Into drug</DrawerHeader>

                <DrawerBody>
                    <CreateMedicineForm
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
export default CreateMedicineDrower;

