'use client'

import {
    Button,
    Checkbox,
    Flex,
    Text,
    FormControl,
    FormLabel,
    Heading,
    Input,
    Stack,
    Image,
    Link, Box, Alert, AlertIcon,
} from '@chakra-ui/react'
import {Formik, Form, useField} from "formik";
import * as Yup from 'yup';
import {useAuth} from "../context/AuthContext.jsx";
import {errorNotification} from "../../service/notification.js";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

// 自定义的数据获取函数
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

const LoginFrom = () => {
    // 对登录信息的存储抓取
    const {login} = useAuth();

    // 使用导航 useNavigate()  https://reactrouter.com/en/main/hooks/use-navigate 网站来源
    const navigate = useNavigate();

    return (
        <Formik
            // 挂在验证 用户登录验证登
            validateOnMount={true}
            validationSchema={
                Yup.object({
                    username: Yup.string()
                        .email("检查邮箱是否正确/Must be valid email")
                        .required("Email is required"),
                    password: Yup.string()
                        .max(20, " 密码不能超过20个字符/Password cannot be more than 20 characters")
                        .required("Password is required")
                })
            }
            initialValues={{username: '', password: ''}}
            onSubmit={(values, {setSubmitting}) => {
                // alert(JSON.stringify(values, null, 0))
                setSubmitting(true);
                login(values).then(res => {
                    //todo: navigate to dashboard  通过验证登录身份导航到信息面板 通过navigate 跳转到目标组件
                    // console.log("Success login",res);
                    navigate("/dashboard")
                    //todo 完成了账号验证和登录 完成视频474  然后前往clien.js 去解决登录进去数据没有加载问题 视频475
                    console.log("Successfully login in ");


                }).catch(err => {
                    console.log(err);
                    errorNotification(
                        err.code,
                        err.response.data.message
                    )
                }).finally(() => {
                    // 设置不能提交两次
                    setSubmitting(false);
                })
            }}>
            {({isValid, isSubmitting}) => (
                <Form>
                    {/*spacing={}限制组件所站行数 调整使其合适 */}
                    <Stack spacing={19}>
                        <MyTextInput
                            label={"Email address"}
                            name={"username"}
                            type={"email"}
                            placeholder={"hello@gethub.com"}
                        />
                        <MyTextInput
                            label={"Password"}
                            name={"password"}
                            type={"password"}
                            // placeholder={"hello@gethub.com"}
                            placeholder={"Type your password"}
                        />

                        <Stack spacing={6}>
                            <Stack
                                direction={{base: 'column', sm: 'row'}}
                                align={'start'}
                                justify={'space-between'}>
                                {/*添加点击事件保存当前数据*/}
                                <Checkbox>Remember me</Checkbox>
                                <Text color={'blue.500'}>Forgot password?</Text>
                            </Stack>
                            <Button
                                type={"submit"}
                                colorScheme={'blue'}
                                disabled={!isValid || isSubmitting}>
                                Login
                            </Button>
                        </Stack>

                    </Stack>
                </Form>
            )}
        </Formik>
    )
}

// 组件来源网站  https://chakra-templates.vercel.app/forms/authentication

const Login = () => {

    //设置若果用户登了token验证也没有过期 就直接进入不用再次验证
    const {customer} = useAuth();
    const navigate = useNavigate();
    useEffect(() => {
        if (customer) {
            //修改这里完成界面跳转
            navigate("/dashboard/customers");
        }
    })

    // useAuth();
// 设置登录函数 并且改变登录人信息
    return (
        <Stack minH={'100vh'} direction={{base: 'column', md: 'row'}}>
            <Flex p={8}
                  flex={1}
                  align={'center'}
                  justifyContent={'center'}
                  justify={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Stack align={'center'}>
                        <Image
                            // 添加logo图标暂时是我的github图像
                            src={"https://avatars.githubusercontent.com/u/117267416?v=4"}
                            boxSize={"150px"}
                            alt={"Dawei_LI.github Logo"}
                        />
                    </Stack>

                    <Heading fontSize={'2xl'} mb={15}>Sign in to your account</Heading>
                    <LoginFrom/>
                    <Link color={"blue.500"} href={"/signup"}>
                        Don't have an account ? Signup now .
                    </Link>
                </Stack>
            </Flex>

            {/*下面设置了背景渐变*/}
            <Flex flex={1}
                  p={10}
                  flexDirection={"column"}
                  alignItems={"center"}
                  justifyContent={"center"}
                // 控制半边渐变位置的属性
                  bgGradient={{sm: 'linear(to-r,blue.600,green.600)'}}>
                <Text fontSize={"6xl"} color={"white"} fontWeight={"bold"} mb={5}>
                    <Link href={"#"}>
                        {/*这是添加其他链接*/}
                        Enrol Now
                    </Link>
                </Text>
                <Image
                    alt={'Login Image'}
                    objectFit={'scale-down'}  //小图片界面
                    // objectFit={'cover'}  //一半图片界面
                    src={
                        'https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1352&q=80'
                    }
                />
            </Flex>
        </Stack>
    );
}
export default Login;