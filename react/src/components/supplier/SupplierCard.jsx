'use client'

import {
    Heading,
    Box,
    Center,
    Image,
    Text,
    Stack,
    Tag,
    useColorModeValue,
    useDisclosure,
} from '@chakra-ui/react'

import {useRef} from 'react'
import UpdateSupplierDrower from "./UpdateSupplierDrower";
export default function CardWithImage({id, name, address,contact, fetchMedicine}) {
    const {isOpen, onOpen, onClose} = useDisclosure()
    const cancelRef = useRef()
    return (
        <Center py={6}>
            <Box
                maxW={'300px'}
                minW={'300px'}
                w={'full'}
                m={2}
                p={2}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'ld'}
                rounded={'md'}
                overflow={'hidden'}>
                <Image
                    h={'120px'}
                    w={'full'}
                    src={
                        'https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80'
                    }
                    objectFit="cover"
                    alt="#"
                />

                <Box p={6}>
                    <Stack spacing={2} align={'center'} mb={5}>
                        <Tag border={"full"}>{id}</Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {name}
                        </Heading>
                        <Text color={'green.500'}>name : {name} </Text>
                        <Text color={'green.500'}>address:{address}</Text>
                        <Text color={'green.500'}> contact:{contact}</Text>
                    </Stack>
                </Box>
                <Stack direction={'row'} justify={'center'} spacing={6} p={4}>
                    <Stack>

                        <UpdateSupplierDrower
                            initialValues={{id,name, address,contact}}
                            medicineId={id}
                        />

                    </Stack>

                </Stack>

            </Box>
        </Center>
    );
}