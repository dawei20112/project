import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './Customer.jsx'
import {ChakraProvider, Text} from '@chakra-ui/react'

import {createStandaloneToast} from '@chakra-ui/react'

//引入路由接口组件
import {createBrowserRouter, RouterProvider} from "react-router-dom";
//登录接口
import Login from "./components/login/Login.jsx";
//注册账户接口
import Signup from "./components/signup/Signup";
//接口引入状态组件
import AuthProvider from "./components/context/AuthContext.jsx";
// 引入保护组件
import ProtectedRoute from "./components/shared/ProtectedRoute.jsx";

import './index.css'
import Customer from "./Customer.jsx";
import Home from "./Home.jsx";
import Setting from "./Setting.jsx";
import Medicine from "./Medicine.jsx";
import FinancialTransaction from "./FinancialTransaction.jsx";
import Supplier from "./Supplier.jsx";


const {ToastContainer, toast} = createStandaloneToast()
// 设置登录路由  路由的两条路线
const router = createBrowserRouter([
    {
        // 登录接口
        path: "/",
        // element: <h1>Login page/登录页面</h1>
        element: <Login/>
    },
    {
        path: 'dashboard',
        element:
            <ProtectedRoute>
                <Home></Home>
            </ProtectedRoute>
        // <Text fontSize={"6xl"}>Dashboard</Text>
    },
    // 注册账号 跳转路径
    {
        path: 'signup',
        element: <Signup/>

    },
    {
        path: 'dashboard/customers',
        element:
            <ProtectedRoute>
                <Customer/>
            </ProtectedRoute>
    },
    {
        path: 'dashboard/setting',
        element:
            <ProtectedRoute>
                <Setting/>
            </ProtectedRoute>
    },
    {
        path: 'dashboard/medicine',
        element:
            <ProtectedRoute>
                <Medicine/>
            </ProtectedRoute>
    },
    {
        path: 'dashboard/financialTransaction',
        element:
            <ProtectedRoute>
                <FinancialTransaction/>
            </ProtectedRoute>
    },
    {
        path: 'dashboard/supplier',
        element:
            <ProtectedRoute>
                <Supplier/>
            </ProtectedRoute>
    }
])

ReactDOM
    .createRoot(document.getElementById('root'))
    .render(
        <React.StrictMode>
            <ChakraProvider>
                <AuthProvider>
                    {/*存放子组件*/}
                    {/*渲染路由路线*/}
                    <RouterProvider router={router}/>
                </AuthProvider>
                {/*<App/>*/}
                <ToastContainer/>
            </ChakraProvider>
        </React.StrictMode>,
    )
