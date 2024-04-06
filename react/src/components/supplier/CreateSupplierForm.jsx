import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from '@chakra-ui/react';
import {Formik, Form, useField} from 'formik';
import * as Yup from 'yup';
import {saveSupplier} from '../../service/client.js';
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
const CreatSupplierFrom = ({onSuccess}) => {
    return (
        <>
            {/* <h1>Subscribe!</h1> */}
            <Formik
                initialValues={{
                    name: '',
                    // lastName: '',
                    address: '',
                    contact: 0, // added for our checkbox

                }}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),

                    address: Yup.string()
                        .max(1000,'address')
                        .required('Required'),
                    contact: Yup.number()
                        .min(6, 'Must be at least  6 number')
                        .max(100, 'Must be less 100 number ')
                        .required('Required'),
                })}
                onSubmit={(customer, {setSubmitting}) => {
                    setSubmitting(true);

                    saveSupplier(customer).then(res => {
                        console.log(res);
                        successNotification(
                            "Supplier saved",
                            `${customer.name} was successfully saved`
                        )
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
                                placeholder="哈药六厂"
                            />
                            <MyTextInput
                                label="Address"
                                name="address"
                                type="text"
                                placeholder="XXXX"
                            />
                            <MyTextInput
                                label="Contact"
                                name="contact"
                                type="number"
                                placeholder="XXXXXX"
                            />

                            <Button disabled={!isValid || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>

                )}
            </Formik>
        </>
    );
};
export default CreatSupplierFrom;