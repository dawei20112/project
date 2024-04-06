import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from '@chakra-ui/react';
import {Formik, Form, useField} from 'formik';
import * as Yup from 'yup';
import {saveMedicine} from '../../service/client.js';
import {successNotification, errorNotification} from '../../service/notification.js';

const MyTextInput = ({label, ...props}) => {

    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};


// And now we can use these
// const CreatCustomerFrom = ({fetchCustomer})
const CreatCustomerFrom = ({onSuccess}) => {
    return (
        <>
            {/* <h1>Subscribe!</h1> */}
            <Formik
                initialValues={{
                    name: '',
                    // lastName: '',
                    mid: '',
                    date: 0, // added for our checkbox
                    stock: '',
                    price: '', // added for our select
                }}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),
                    // lastName: Yup.string()
                    //   .max(20, 'Must be 20 characters or less')
                    //   .required('Required'),
                    mid: Yup.string()
                        .max(30, '药品条形码')
                        .required('Required'),
                    date: Yup.number()
                        .max(100, '日期')
                        .required(),
                    stock: Yup.string()
                        .min(0, 'Must be at least 4 characters or password')
                        .max(99999, 'Must be at least 15 characters or password')
                        .required('Required'),
                    price: Yup.string()
                        .max(40, "价格")
                        .required('Required'),
                })}
                onSubmit={(customer, {setSubmitting}) => {
                    setSubmitting(true);
                    saveMedicine(customer).then(res => {
                        console.log(res);
                        // alert("customer saved");
                        // fetchCustomer();
                        successNotification(
                            "customer saved",
                            `${customer.name} was successfully saved`
                        )
                        // fetchCustomer();
                        // fetchCustomer && fetchCustomer();
                        onSuccess(res.headers["authorization"]);
                    }).catch(err => {
                        console.log(err)
                        console.log(err.response.data)
                        // console.log(err.response.data.path);
                        errorNotification(
                            err.code,
                            err.response.data.message
                        )
                    }).finally(() => {
                        setSubmitting(false)
                    })
                }}
            >
                {(isValid, isSubmitting) => (<Form>
                        <Stack spacing={"24px"}>
                            <MyTextInput
                                label="Name"
                                name="name"
                                type="text"
                                placeholder="Jane"
                            />
                            <MyTextInput
                                label="药品批次码"
                                name="mid"
                                type="text"
                                placeholder="XXXXX"
                            />

                            <MyTextInput
                                label="日期"
                                name="date"
                                type="text"
                                placeholder="XXXXX"
                            />
                            <MyTextInput
                                label="数量"
                                name="stock"
                                type="text"
                                placeholder="XXXXX"
                            />
                            <MyTextInput
                            label="价格"
                            name="price"
                            type="text"
                            placeholder="XXXXX"
                        />


                            <Button disabled={!isValid || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>

                )}
            </Formik>
        </>
    );
};
export default CreatCustomerFrom;