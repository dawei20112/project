
const UserProfile =({name,age,gender,imageNumber, ...props})=>{
    gender = gender === "MALE" ? "men" : "women"
// return "John"
return<div>
    <h1>{name}</h1>
    <p>{age}</p>
    <p>{gender}</p>
    <img 
    src={`https://randomuser.me/api/portraits/${gender}/${imageNumber}.jpg`}/>
    {/* 渲染下级组件 */ }
    {props.children}
</div>
}

// 显示配置组件

export default UserProfile;

