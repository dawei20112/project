import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../context/AuthContext.jsx";

const ProtectedRoute = ({children}) => {
// 设置了验证如果不是真的就返回到登录界面 下面设置了重定向 获取当前状态从AuthContext中获取
    const {isCustomerAuthenticated} = useAuth()
    //注意上上述问题

    // 设置了验证如果不是真的就返回到登录界面 下面设置了重定向 手动设置
    // const isCustomerAuthenticated = false;
    const navigate = useNavigate();
    useEffect(() => {
        if (!isCustomerAuthenticated()) {
            navigate("/")
        }
    })
    // return isCustomerAuthenticated ? children:<></>;  //可以返回具体的组件
    return isCustomerAuthenticated() ? children : "";
}
export default ProtectedRoute;