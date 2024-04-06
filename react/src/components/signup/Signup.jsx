import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import {Flex, Heading, Image, Link, Stack, Text} from "@chakra-ui/react";
import CreatCustomerFrom from "../shared/CreateCustomerForm";

const Signup = () => {
    // const Login = () => {

    //设置若果用户登了token验证也没有过期 就直接进入不用再次验证
    const {customer,setCustomerFromToken} = useAuth();
    const navigate = useNavigate();
    useEffect(() => {
        if (customer) {
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

                        {/*可以进行替换 暂时还是我自己的*/}
                        <Image
                            // 添加logo图标暂时是我的github图像
                            src={"https://avatars.githubusercontent.com/u/117267416?v=4"}
                            boxSize={"150px"}
                            alt={"Dawei_LI.github Logo"}
                        />
                    </Stack>

                    <Heading fontSize={'2xl'} mb={15}>Creat in to your account</Heading>
                    <CreatCustomerFrom onSuccess={(token)=>{
                        localStorage.setItem("access_token",token)
                        //获取注册时候的token信息并传递给登录界面
                        setCustomerFromToken()
                        navigate("/dashboard");
                    }}

                    />
                    <Link color={"blue.500"} href={"/"}>
                        Have an account ? Login now ?
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

    // return "Signup"
}
export default Signup;