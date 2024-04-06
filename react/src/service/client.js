import axios from 'axios';
//todo 创建一个函数去获取token信息 用于下面的方法携带去完成服务器的验证
const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }

})

export const getCustomers = async () => {
    try {
        // 信息获取接口设置 填的URL就是服务启动后的接口
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}
export const getSupplier = async () => {
    try {
        // 信息获取接口设置 填的URL就是服务启动后的接口
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/supplier`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}
export const getMedicine = async () => {
    try {
        // 信息获取接口设置 填的URL就是服务启动后的接口
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/medicine`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

//todo 创建用户不需要config （token）
export const saveCustomer = async (customer) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`,
            customer)
    } catch (e) {
        throw e;
    }

}
export const saveMedicine = async (customer) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/medicine`,
            customer)
    } catch (e) {
        throw e;
    }

}
export const saveSupplier = async (customer) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/supplier`,
            customer)
    } catch (e) {
        throw e;
    }

}
export const updateCustomer = async (id, update) => {
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`,
            getAuthConfig(),
            update)
    } catch (e) {
        throw e;
    }

}
export const updateMedicine = async (id, update) => {
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/medicine/${id}`,
            getAuthConfig(),
            update)
    } catch (e) {
        throw e;
    }

}
export const deletedCustomer = async (id) => {
    try {
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}
export const deletedMedicine = async (id) => {
    try {
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/v1/medicine/${id}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}
// 设置Login按钮的api接口和跳转逻辑
export const login = async (usernameAndPassword) => {
    try {
        // 返回接口调用和实体信息
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login`,
            usernameAndPassword
        )
    } catch (e) {
        throw e;
    }

}