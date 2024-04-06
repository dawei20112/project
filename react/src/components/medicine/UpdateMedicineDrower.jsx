import {
    Button,
    DrawerBody,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    DrawerContent,
    DrawerCloseButton,
    useDisclosure,
    Drawer,
} from "@chakra-ui/react"
import UpdateCustomerFrom from "./UpdateMedicineForm.jsx";
import UpdateMedicineForm from "./UpdateMedicineForm.jsx";
// import { updateCustomer } from "../service/client.js";
const AddIcon = () => "+";
const CloseIcon = () => "x";
const UpdateMedicineDrower = ({ fetchMedicine, initialValues, medicineId }) => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    return <>

        <Button bg={'green.400'} color={'white'} rounded={'full'}
                _hover={{
            transform: 'translateY(-2px)',
            boxShadow: 'lg'
        }}
            onClick={onOpen}
        >
            Update medicine
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                <DrawerHeader>Update customer</DrawerHeader>
                <DrawerBody>
                    <UpdateMedicineForm
                        fetchMedicine={fetchMedicine}
                        initialValues={initialValues}
                        medicineId={medicineId}
                    />
                </DrawerBody>
                <DrawerFooter>
                    <Button leftIcon={<CloseIcon />}
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
export default UpdateMedicineDrower;

