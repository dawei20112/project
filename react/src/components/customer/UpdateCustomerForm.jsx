// import React from 'react';
// import ReactDOM from 'react-dom';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from '@chakra-ui/react';
import {Formik, Form, useField} from 'formik';
import * as Yup from 'yup';
import {saveCustomer, updateMedicine} from '../../service/client.js';
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
// And now we can use these  应该是点击跳转页面但是出了问题直接显示
const UpdateCustomerFrom = ({fetchCustomer, initialValues, customerId}) => {
    return (
        <>
            <Formik
                initialValues={initialValues}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),
                    // lastName: Yup.string()
                    //   .max(20, 'Must be 20 characters or less')
                    //   .required('Required'),
                    email: Yup.string()
                        .email('Invalid email address')
                        .required('Required'),
                    age: Yup.number()
                        .min(16, 'Must be at least 16 years of age')
                        .max(100, 'Must be less 100 years of age ')
                        .required(),

                })}
                onSubmit={(values, {setSubmitting}) => {

                    setSubmitting(true);

                    updateCustomer(customerId, values).then(res => {
                        console.log(res);
                        // alert("customer saved");
                        fetchCustomer();
                        successNotification(
                            "customer update",
                            `${values.name} was successfully update`
                        )
                        fetchCustomer();
                    }).catch(err => {
                        console.log(err);
                        errorNotification(
                            err.code,
                            err.response.data.message
                        )

                    }).finally(() => {
                        setSubmitting(false)
                    })
                }}
            >
                {(isValid, isSubmitting, dirty) => (
                    <Form>
                        <Stack spacing={"24px"}>
                            <MyTextInput
                                label="Name"
                                name="name"
                                type="text"
                                placeholder="Jane"
                            />

                            <MyTextInput
                                label="Email Address"
                                name="email"
                                type="email"
                                placeholder="jane@formik.com"
                            />
                            <MyTextInput
                                label="Age"
                                name="age"
                                type="number"
                                placeholder="24"
                            />
                            {/*<Button disabled={!(isValid && dirty) || isSubmitting} type="submit">Submit</Button>*/}
                            // todo 只有数据变化才会激活按钮没有实现
                            <Button disabled={!(isValid && dirty) || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};
export default UpdateCustomerFrom;