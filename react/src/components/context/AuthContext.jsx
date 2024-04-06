import {
    createContext,
    useContext,
    useEffect,
    useState
} from "react";
import {login as performLogin} from "../../service/client.js";
import {jwtDecode} from "jwt-decode";

const AuthContext = createContext({});

const AuthProvider = ({children}) => {
    // 声明了客户初试登录状态信息
    const [customer, setCustomer] = useState(null);

    //抽取函数 主要用于设置想要获取的状态信息 设置客户状态信息
    const setCustomerFromToken = () => {
        let token = localStorage.getItem("access_token");
        if (token) {
            token = jwtDecode(token);

            // 设置客户负载
            //设置想要获取的状态信息 设置客户状态信息
            setCustomer({
                username: token.sub,
                roles: token.scopes
            })
        }
    }

    //使用useEffect 进行数据传递
    useEffect(() => {
        //获取了全部的状态信息
        setCustomerFromToken()
    }, [])


    // 定义登录状态
    // 函数定义为数据流形式
    const login = async (usernameAndPassword) => {
        return new Promise((resolve, reject) => {
            performLogin(usernameAndPassword).then(res => {
                //获去状态token信息这是一个状态解码器
                const jwtToken = res.headers["authorization"];
                //todo 保存token信息 save the token
                //存储登录信息 当前状态 在本地的applicantion中 存储了token 服务器传递的信息
                localStorage.setItem("access_token", jwtToken);
                //设置token解码器
                const decodeToken = jwtDecode(jwtToken);
                console.log(jwtToken);
                // 设置客户状态
                // setCustomer({
                //     ...res.data.customerDTO
                // })
                //修改后 设置客户状态 用解析后的数据进行传递
                setCustomer({
                    username: decodeToken.sub,
                    roles: decodeToken.scopes
                })
                resolve(res);
            }).catch(err => {
                reject(err);
            })
        })
    }
    // 退出函数
    const logout = async () => {
        localStorage.removeItem("access_token")
        // 设置客户状态为离线
        setCustomer(null)

    }
    // 用户身份验证
    const isCustomerAuthenticated = () => {
        // 获取token 并且验证token 等
        const token = localStorage.getItem("access_token");
        if (!token) {
            return false;
        }
// 获取有效时间 通过jwt-decode 组件
        const {exp: expiration} = jwtDecode(token);
        if (Date.now() > expiration * 1000) {
            logout()
            return false
        }
        //若都没有就默认认证成功 进入主界面
        return true;
    }

    // 返回携带的用户状态信息  还有子组件
    return (
        <AuthContext.Provider value={{
            customer,
            login,
            logout,
            isCustomerAuthenticated,
            setCustomerFromToken
        }}>
            {/*子组件存放位置*/}
            {children}
        </AuthContext.Provider>
    )
}


export const useAuth = () => useContext(AuthContext);


export default AuthProvider;