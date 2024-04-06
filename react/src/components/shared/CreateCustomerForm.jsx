// import React from 'react';
// import ReactDOM from 'react-dom';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from '@chakra-ui/react';
import {Formik, Form, useField} from 'formik';
import * as Yup from 'yup';
import {saveCustomer} from '../../service/client.js';
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


const MySelect = ({label, ...props}) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
                // <div className="error">{meta.error}</div>
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
                    email: '',
                    age: 0, // added for our checkbox
                    password: '',
                    gender: '', // added for our select
                }}
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
                    password: Yup.string()
                        .min(4, 'Must be at least 4 characters or password')
                        .max(15, 'Must be at least 15 characters or password')
                        .required('Required'),
                    // acceptedTerms: Yup.boolean()
                    //   .required('Required')
                    //   .oneOf([true], 'You must accept the terms and conditions.'),
                    gender: Yup.string()
                        .oneOf(
                            ['MALE', 'FEMALE'],
                            'Invalid gender'
                        )
                        .required('Required'),
                })}
                onSubmit={(customer, {setSubmitting}) => {
                    // setTimeout(() => {
                    //   alert(JSON.stringify(values, null, 2));
                    //   setSubmitting(false);
                    // }, 400);
                    setSubmitting(true);

                    saveCustomer(customer).then(res => {
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

                            {/* <MyTextInput
      label="Last Name"
      name="lastName"
      type="text"
      placeholder="Doe"
    />                            */}

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
                            <MyTextInput
                                label="Password"
                                name="password"
                                type="password"
                                placeholder={"pick a secure password"}
                            />

                            <MySelect label="Gender" name="gender">
                                <option value="">Select gender</option>
                                <option value="MALE">MALE</option>
                                <option value="FEMALE">FEMALE</option>
                                {/* <option value="product">Product Manager</option>
      <option value="other">Other</option> */}
                            </MySelect>

                            {/* <MyCheckbox name="acceptedTerms">
      I accept the terms and conditions
    </MyCheckbox> */}

                            <Button disabled={!isValid || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>

                )}
            </Formik>
        </>
    );
};
export default CreatCustomerFrom;